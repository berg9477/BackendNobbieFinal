package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyDto;
import nl.novi.nobbie.model.Baby;

import java.util.List;

public interface BabyService {
    List<BabyDto> getAllBabies() throws Exception;
    Baby createBaby(BabyDto babyDto);
    List<BabyDto> getBabiesById(Long id) throws Exception;
}
