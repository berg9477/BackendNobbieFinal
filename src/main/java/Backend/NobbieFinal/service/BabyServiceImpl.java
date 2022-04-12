package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.BabyDto;
import Backend.NobbieFinal.model.Baby;
import Backend.NobbieFinal.repository.BabyRepository;
import Backend.NobbieFinal.repository.UserProfileRepository;
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
    public List<BabyDto> getAllBabies() {
        List<Baby> bl = this.repos.findAll();
        List<BabyDto> babies = new ArrayList<>();
        bl.forEach(b -> babies.add(new BabyDto(b.getId(), b.getNickname(), b.getGender(), b.getBirthdate(), b.getExpected(), b.getUser())));
        return babies;
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
    public List<BabyDto> getBabiesById(Long id) {
        List<Baby> bl = this.repos.findByUser(this.userRepos.findById(id).get());
        List<BabyDto> babies = new ArrayList<>();
        bl.forEach(b -> babies.add(new BabyDto(b.getId(), b.getNickname(), b.getGender(), b.getBirthdate(), b.getExpected(), b.getUser())));
        return babies;
    }
}
