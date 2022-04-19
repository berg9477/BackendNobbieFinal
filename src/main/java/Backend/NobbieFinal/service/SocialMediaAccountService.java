package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.SocialMediaAccountDto;
import Backend.NobbieFinal.model.MediaType;
import Backend.NobbieFinal.model.SocialMediaAccount;

import java.util.List;

public interface SocialMediaAccountService {
    List<SocialMediaAccountDto> getAllAccounts();
    SocialMediaAccount createSMA(SocialMediaAccountDto SMAdto);
    String getSMAMessage(MediaType mediaType, Long id);
}
