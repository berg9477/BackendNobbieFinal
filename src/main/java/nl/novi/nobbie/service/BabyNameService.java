package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.BabyName;

import java.util.List;

public interface BabyNameService {
    List<BabyNameDto> getAllNames();
    List<BabyNameDto> getNameStartsWith(Character ch);
    List<BabyNameDto> getNamesContaining(String input);
    BabyName insertBabyName(BabyNameDto babyNameDto);
    BabyName findNameById(Long id);
}
