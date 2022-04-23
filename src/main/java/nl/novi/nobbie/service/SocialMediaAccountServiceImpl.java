package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.SocialMediaAccountDto;
import nl.novi.nobbie.model.Baby;
import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.model.SocialMediaAccount;
import nl.novi.nobbie.repository.SocialMediaAccountRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocialMediaAccountServiceImpl implements SocialMediaAccountService {

    private final SocialMediaAccountRepository repos;
    private final UserProfileRepository upRepos;

    public SocialMediaAccountServiceImpl(SocialMediaAccountRepository repos, UserProfileRepository upRepos) {
        this.repos = repos;
        this.upRepos = upRepos;
    }

    @Override
    public List<SocialMediaAccountDto> getAllAccounts() throws Exception {
        List<SocialMediaAccount> sma = this.repos.findAll();
        if (sma.size() == 0) { //Check if any sma's are found, if not throw an error
            throw new Exception("No social media accounts found");
        } else {
            List<SocialMediaAccountDto> smos = new ArrayList<>();
            sma.forEach(s -> smos.add(new SocialMediaAccountDto(s.getId(), s.getUserId(), s.getSocialMediaType())));
            return smos;
        }
    }

    @Override
    public SocialMediaAccount createSMA(SocialMediaAccountDto SMADto) throws Exception {
        if(SMADto.getId() != null) {
            SocialMediaAccount sma = new SocialMediaAccount();
            sma.setId(SMADto.getId());
            sma.setSocialMediaType(SMADto.getSocialMediaType());
            sma.setUserId(SMADto.getUser());
            return this.repos.save(sma);
        } else {
            throw new Exception("Please provide SocialMediaAccountDto object");
        }
    }

    @Override
    public String getSMAMessage(MediaType mediaType, Long id) throws Exception {
        StringBuilder sb = new StringBuilder();
        //get expecting data
        Integer weeksLeft = 0;
        if (this.upRepos.findById(id).isPresent()) { //check if user exists
            List<Baby> babies = this.upRepos.getById(id).getBabies();
            if (babies.size() == 0) { //check if any babies are found, if not throw an error
                throw new Exception("No babies found for userId: " + id);
            } else {
                for (Baby b : babies) {
                    if (b.getExpected().equals(true)) {
                        weeksLeft = b.getWeeksLeft(b.getBirthdate());
                    }
                }
            }
            //generate the message
            sb.append("Hoi ");
            sb.append(mediaType);
            sb.append(".\n");
            sb.append("Ik ben aan het aftellen tot je komt. Mijn kleine wonder, nog maar ");
            sb.append(weeksLeft);
            sb.append(" weken en dan is het zover!");
            sb.append("\n");
            sb.append("#nobbie #aftellen #zwanger #kleineopkomst");
            return sb.toString();
        } else {
            throw new Exception("No user found for userId: " + id);
        }
    }
}
