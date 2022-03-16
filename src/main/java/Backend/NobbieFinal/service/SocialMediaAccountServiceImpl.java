package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.SocialMediaAccountDto;
import Backend.NobbieFinal.model.Baby;
import Backend.NobbieFinal.model.SocialMediaAccount;
import Backend.NobbieFinal.repository.SocialMediaAccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SocialMediaAccountServiceImpl implements SocialMediaAccountService {

    private final SocialMediaAccountRepository repos;

    public SocialMediaAccountServiceImpl(SocialMediaAccountRepository repos){
        this.repos = repos;
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


}
