package nl.novi.nobbie.service;


import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.BabyName;
import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.repository.BabyNameRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes={NobbieFinalApplication.class})
class BabyNameServiceImplTest {

    @Autowired
    private BabyNameService service;

    @MockBean
    private BabyNameRepository repos;

    @Mock
    BabyName babyName;

    BabyName name = new BabyName("Saskia", Gender.F, 1);
    BabyName nameTwo = new BabyName("Laurens", Gender.M, 2);
    BabyName nameThree = new BabyName("Rene", Gender.X, 3);
    List<BabyName> list = new ArrayList<BabyName>();

    @Test
    public void getAllNames() throws Exception {

        list.add(name);
        list.add(nameTwo);
        list.add(nameThree);

        given(repos.findAll()).willReturn(list);

        //test
        List<BabyNameDto> bn = service.getAllNames();

        assertEquals(3, bn.size());
    }

    @Test
    void getNameStartsWith() throws Exception {

        list.add(name);

        when(repos.findBabyNameByNameStartingWith('s')).thenReturn(list);

        List<BabyNameDto> nameList = service.getNameStartsWith('s');

        assertEquals(1, nameList.size());
        assertEquals("Saskia", nameList.get(0).getName());

    }

    @Test
    void getNamesContaining() throws Exception {

        list.add(nameTwo);
        list.add(nameThree);

        when(repos.findBabyNameByNameContaining("en")).thenReturn(list);

        List<BabyNameDto> nameList = service.getNamesContaining("en");

        assertEquals(2, nameList.size());
        assertEquals("Laurens", nameList.get(0).getName());

    }
}