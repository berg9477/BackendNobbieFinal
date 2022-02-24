package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.BabyNameDto;
import Backend.NobbieFinal.model.BabyName;

import java.util.List;

public interface BabyNameService {
    public List<BabyNameDto> getAllNames();
    public List<BabyNameDto> getNameStartsWith(Character ch);
    public List<BabyNameDto> getNamesContaining(String input);
    public BabyName insertBabyName(BabyNameDto babyNameDto);
}
