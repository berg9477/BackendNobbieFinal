package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.BabyName;
import nl.novi.nobbie.repository.BabyNameRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BabyNameServiceImpl implements BabyNameService {

    private final BabyNameRepository repos;

    public BabyNameServiceImpl(BabyNameRepository repos) {
        this.repos = repos;
    }

    @Override
    public List<BabyNameDto> getAllNames() throws Exception {
        List<BabyName> bn = this.repos.findAll();
        if (bn.size() == 0) {
            throw new Exception("No names found");
        } else {
            List<BabyNameDto> babyNames = new ArrayList<>();
            bn.forEach(b -> babyNames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
            return babyNames;
        }
    }

    @Override
    public List<BabyNameDto> getNameStartsWith(Character ch) throws Exception {
        List<BabyName> bn = this.repos.findBabyNameByNameStartingWith(ch);
        if (bn.size() == 0) {
            throw new Exception("No names found for character: "+ch);
        } else {
            List<BabyNameDto> babyNames = new ArrayList<>();
            bn.forEach(b -> babyNames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
            return babyNames;
        }
    }

    @Override
    public List<BabyNameDto> getNamesContaining(String input) throws Exception {
        List<BabyName> bn = this.repos.findBabyNameByNameContaining(input);
        if (bn.size() == 0) {
            throw new Exception("No names found for input: "+input);
        } else {
            List<BabyNameDto> babyNames = new ArrayList<>();
            bn.forEach(b -> babyNames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
            return babyNames;
        }
    }

    @Override
    public BabyName insertBabyName(BabyNameDto babyNameDto) {
        BabyName bn = new BabyName();
        bn.setGender(babyNameDto.getGender());
        bn.setName(babyNameDto.getName());
        bn.setListingNumber(babyNameDto.getListingNumber());
        return this.repos.save(bn);
    }

    @Override
    public BabyName findNameById(Long id) {
        return this.repos.findById(id).get();
    }
}
