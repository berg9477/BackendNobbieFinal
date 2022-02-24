package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.BabyNameDto;
import Backend.NobbieFinal.model.BabyName;
import Backend.NobbieFinal.repository.BabyNameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BabyNameServiceImpl implements BabyNameService{

    private final BabyNameRepository repos;

    public BabyNameServiceImpl(BabyNameRepository repos){
        this.repos = repos;
    }
    
    @Override
    public List<BabyNameDto> getAllNames() {
        List<BabyName> bn = this.repos.findAll();
        List<BabyNameDto> babynames = new ArrayList<>();
        bn.forEach(b -> babynames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
        return babynames;
    }

    @Override
    public List<BabyNameDto> getNameStartsWith(Character ch) {
        List<BabyName> bn = this.repos.findBabyNameByNameStartingWith(ch);
        List<BabyNameDto> babynames = new ArrayList<>();
        bn.forEach(b -> babynames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
        return babynames;
    }

    @Override
    public List<BabyNameDto> getNamesContaining(String input) {
        List<BabyName> bn = this.repos.findBabyNameByNameContaining(input);
        List<BabyNameDto> babynames = new ArrayList<>();
        bn.forEach(b -> babynames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
        return babynames;
    }

    @Override
    public BabyName insertBabyName(BabyNameDto babyNameDto) {
        BabyName bn = new BabyName();
        bn.setGender(babyNameDto.getGender());
        bn.setName(babyNameDto.getName());
        bn.setListingNumber(babyNameDto.getListingNumber());
        return this.repos.save(bn);
    }
}
