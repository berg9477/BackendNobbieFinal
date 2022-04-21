package nl.novi.nobbie.service;

import nl.novi.nobbie.dto.BabyDto;
import nl.novi.nobbie.model.Baby;
import nl.novi.nobbie.repository.BabyRepository;
import nl.novi.nobbie.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BabyServiceImpl implements BabyService{

    private final BabyRepository repos;
    private final UserProfileRepository userRepos;


    public BabyServiceImpl(BabyRepository repos, UserProfileRepository userRepos){
        this.repos = repos;
        this.userRepos = userRepos;
    }

    @Override
    public List<BabyDto> getAllBabies() throws Exception {
        List<Baby> bl = this.repos.findAll();
        List<BabyDto> babies = new ArrayList<>();
        if (bl.size() == 0){
            throw new Exception("No baby's found");
        } else {
            bl.forEach(b -> babies.add(new BabyDto(b.getId(), b.getNickname(), b.getGender(), b.getBirthdate(), b.getExpected(), b.getUser())));
            return babies;
        }
    }

    @Override
    public Baby createBaby(BabyDto babyDto) {
        Baby b = new Baby();
        b.setId(babyDto.getId());
        b.setNickname(babyDto.getNickname());
        b.setBirthdate(babyDto.getBirthdate());
        b.setGender(babyDto.getGender());
        b.setExpected(babyDto.getExpected(), babyDto.getBirthdate());
        b.setWeeksLeft(b.getWeeksLeft(babyDto.getBirthdate()));
        b.setUser(babyDto.getUser());
        return this.repos.save(b);
    }

    @Override
    public List<BabyDto> getBabiesById(Long id) throws Exception {
        if(this.userRepos.findById(id).isPresent()) {
            List<Baby> bl = this.repos.findByUser(this.userRepos.findById(id).get());
            if (bl.size() == 0) { //Check if any baby's are found, if not throw an error
                throw new Exception("No baby's found for userId: " + id);
            } else {
                List<BabyDto> babies = new ArrayList<>();
                bl.forEach(b -> babies.add(new BabyDto(b.getId(), b.getNickname(), b.getGender(), b.getBirthdate(), b.getExpected(), b.getUser())));
                return babies;
            }
        } else{
            throw new Exception("No user found for userId: " + id);
        }
    }
}
