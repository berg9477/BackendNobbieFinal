package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.SocialMediaAccountDto;
import Backend.NobbieFinal.model.SocialMediaAccount;

import java.util.List;

public interface SocialMediaAccountService {
    public List<SocialMediaAccountDto> getAllAccounts();
    public SocialMediaAccount createSMA(SocialMediaAccountDto SMAdto);
}
