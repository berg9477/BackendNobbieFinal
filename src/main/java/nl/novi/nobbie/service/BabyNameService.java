package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.BabyName;

import java.util.List;

public interface BabyNameService {
    public List<BabyNameDto> getAllNames();
    public List<BabyNameDto> getNameStartsWith(Character ch);
    public List<BabyNameDto> getNamesContaining(String input);
    public BabyName insertBabyName(BabyNameDto babyNameDto);
    public BabyName findNameById(Long id);
}
