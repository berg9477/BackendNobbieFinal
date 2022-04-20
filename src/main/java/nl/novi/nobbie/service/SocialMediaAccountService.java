package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.SocialMediaAccountDto;
import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.model.SocialMediaAccount;

import java.util.List;

public interface SocialMediaAccountService {
    List<SocialMediaAccountDto> getAllAccounts();
    SocialMediaAccount createSMA(SocialMediaAccountDto SMAdto);
    String getSMAMessage(MediaType mediaType, Long id);
}
