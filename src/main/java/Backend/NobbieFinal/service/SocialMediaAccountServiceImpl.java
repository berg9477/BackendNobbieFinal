package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.SocialMediaAccountDto;
import Backend.NobbieFinal.model.Baby;
import Backend.NobbieFinal.model.MediaType;
import Backend.NobbieFinal.model.SocialMediaAccount;
import Backend.NobbieFinal.repository.SocialMediaAccountRepository;
import Backend.NobbieFinal.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocialMediaAccountServiceImpl implements SocialMediaAccountService {

    private final SocialMediaAccountRepository repos;
    private final UserProfileRepository upRepos;

    public SocialMediaAccountServiceImpl(SocialMediaAccountRepository repos, UserProfileRepository upRepos){
        this.repos = repos;
        this.upRepos = upRepos;
    }

    @Override
    public List<SocialMediaAccountDto> getAllAccounts() {
        List<SocialMediaAccount> sma = this.repos.findAll();
        List<SocialMediaAccountDto> smos = new ArrayList<>();
        sma.forEach(s -> smos.add(new SocialMediaAccountDto(s.getId(), s.getUserId(), s.getSocialMediaType())));
        return smos;
    }

    @Override
    public SocialMediaAccount createSMA(SocialMediaAccountDto SMAdto) {
        SocialMediaAccount sma = new SocialMediaAccount();
        sma.setId(SMAdto.getId());
        sma.setSocialMediaType(SMAdto.getSocialMediaType());
        sma.setUserId(SMAdto.getUser());
        return this.repos.save(sma);
    }

    @Override
    public String getSMAMessage(MediaType mediaType, Long id) {
        StringBuilder sb = new StringBuilder();
        //get expecting data
        Integer weeksLeft = 0;
        List<Baby> babies = upRepos.getById(id).getBabies();
        for(Baby b : babies){
            if(b.getExpected().equals(true)){
               weeksLeft = b.getWeeksLeft(b.getBirthdate());
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
    }


}
