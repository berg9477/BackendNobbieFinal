package Backend.NobbieFinal.service;

import Backend.NobbieFinal.dto.BabyDto;
import Backend.NobbieFinal.model.Baby;
import Backend.NobbieFinal.repository.BabyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BabyServiceImpl implements BabyService{

    private final BabyRepository repos;

    public BabyServiceImpl(BabyRepository repos){
        this.repos = repos;
    }

    @Override
    public List<BabyDto> getAllBabies() {
        List<Baby> bl = this.repos.findAll();
        List<BabyDto> babies = new ArrayList<>();
        bl.forEach(b -> babies.add(new BabyDto(b.getId(), b.getNickname(), b.getGender(), b.getBirthdate(), b.getExpected(), b.getWeeksLeft(), b.getUserId())));
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
        b.setUserId(babyDto.getUser());
        b.setWeeksLeft(babyDto.getWeeksLeft());
        return this.repos.save(b);
    }
}
