package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.BabyDto;
import Backend.NobbieFinal.model.Baby;

import java.util.List;

public interface BabyService {
    public List<BabyDto> getAllBabies();
    public Baby createBaby(BabyDto babyDto);
}
