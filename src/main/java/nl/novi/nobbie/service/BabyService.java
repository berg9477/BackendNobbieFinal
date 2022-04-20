package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyDto;
import nl.novi.nobbie.model.Baby;

import java.util.List;

public interface BabyService {
    public List<BabyDto> getAllBabies();
    public Baby createBaby(BabyDto babyDto);
    public List<BabyDto> getBabiesById(Long id);
}
