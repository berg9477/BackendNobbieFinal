package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.BabyName;

import java.util.List;

public interface BabyNameService {
    List<BabyNameDto> getAllNames() throws Exception;
    List<BabyNameDto> getNameStartsWith(Character ch) throws Exception;
    List<BabyNameDto> getNamesContaining(String input) throws Exception;
    BabyName insertBabyName(BabyNameDto babyNameDto) throws Exception;
}
