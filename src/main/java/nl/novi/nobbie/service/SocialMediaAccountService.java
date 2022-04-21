package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.SocialMediaAccountDto;
import nl.novi.nobbie.model.MediaType;
import nl.novi.nobbie.model.SocialMediaAccount;

import java.util.List;

public interface SocialMediaAccountService {
    List<SocialMediaAccountDto> getAllAccounts() throws Exception;
    SocialMediaAccount createSMA(SocialMediaAccountDto SMAdto) throws Exception;
    String getSMAMessage(MediaType mediaType, Long id) throws Exception;
}
