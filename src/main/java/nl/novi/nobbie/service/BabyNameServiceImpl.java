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
    //retrieves a list of all baby names from repository
    public List<BabyNameDto> getAllNames() throws Exception {
        List<BabyName> bn = this.repos.findAll();
        if (bn.size() == 0) { //Check if any results are found
            throw new Exception("No names found");
        } else {
            List<BabyNameDto> babyNames = new ArrayList<>();
            //Map to Dto object and add to result List
            bn.forEach(b -> babyNames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
            return babyNames;
        }
    }

    @Override
    //Return a list of names that start with a specific character
    public List<BabyNameDto> getNameStartsWith(Character ch) throws Exception {
        List<BabyName> bn = this.repos.findBabyNameByNameStartingWith(ch);
        if (bn.size() == 0) { //Check if any results are found
            throw new Exception("No names found for character: " + ch);
        } else {
            List<BabyNameDto> babyNames = new ArrayList<>();
            //Map to Dto object and add to result List
            bn.forEach(b -> babyNames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
            return babyNames;
        }
    }

    @Override
    //Returns a list of baby names that contain a specific keyword
    public List<BabyNameDto> getNamesContaining(String input) throws Exception {
        List<BabyName> bn = this.repos.findBabyNameByNameContaining(input);
        if (bn.size() == 0) { //Check if any results are found
            throw new Exception("No names found for input: " + input);
        } else {
            List<BabyNameDto> babyNames = new ArrayList<>();
            //Map to Dto object and add to result List
            bn.forEach(b -> babyNames.add(new BabyNameDto(b.getId(), b.getName(), b.getGender(), b.getListingNumber())));
            return babyNames;
        }
    }

    @Override
    //Insert a new baby name to the repository
    public BabyName insertBabyName(BabyNameDto babyNameDto) throws Exception {
        //First check if the name is not already known in the database
        if (!this.repos.existsByName(babyNameDto.getName())) {
            BabyName bn = new BabyName();
            //Map from Dto object for saving
            bn.setGender(babyNameDto.getGender());
            bn.setName(babyNameDto.getName());
            bn.setListingNumber(babyNameDto.getListingNumber());
            return this.repos.save(bn);
        } else {
            throw new Exception("Name already exists: " + babyNameDto.getName());

        }
    }
}
