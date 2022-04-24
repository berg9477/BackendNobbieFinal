package nl.novi.nobbie.service;


import nl.novi.nobbie.NobbieFinalApplication;
import nl.novi.nobbie.dto.BabyNameDto;
import nl.novi.nobbie.model.BabyName;
import nl.novi.nobbie.model.Gender;
import nl.novi.nobbie.repository.BabyNameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {NobbieFinalApplication.class})
class BabyNameServiceImplTest {

    //Insert general testdata
    BabyName name = new BabyName("Saskia", Gender.F, 1);
    BabyName nameTwo = new BabyName("Laurens", Gender.M, 2);
    BabyName nameThree = new BabyName("Rene", Gender.X, 3);
    List<BabyName> list = new ArrayList<>();
    @Autowired
    private BabyNameService service;
    @MockBean
    private BabyNameRepository repos;

    @Test
    public void getAllNames() throws Exception {
        //Add to list for mocking findAll action
        list.add(name);
        list.add(nameTwo);
        list.add(nameThree);

        given(repos.findAll()).willReturn(list);

        //Execute test
        List<BabyNameDto> bn = service.getAllNames();

        //Check results
        assertEquals(3, bn.size());
    }

    @Test
    void getNameStartsWith() throws Exception {
        //Add name to list for mocking of findBabyNameByNameStatingWith action
        list.add(name);

        //Given
        when(repos.findBabyNameByNameStartingWith('s')).thenReturn(list);

        //Execute test
        List<BabyNameDto> nameList = service.getNameStartsWith('s');

        //Check results
        assertEquals(1, nameList.size());
        assertEquals("Saskia", nameList.get(0).getName());
    }

    @Test
    void getNamesContaining() throws Exception {
        //Add name to list for mocking of finaBabyNameByNameContaining action
        list.add(nameTwo);
        list.add(nameThree);

        //Given
        when(repos.findBabyNameByNameContaining("en")).thenReturn(list);

        //Execute test
        List<BabyNameDto> nameList = service.getNamesContaining("en");

        //Check results
        assertEquals(2, nameList.size());
        assertEquals("Laurens", nameList.get(0).getName());
    }

    @Test
    void insertBabyNameFails() {
        //Create Dto baby name for input of service
        BabyNameDto bn = new BabyNameDto(1L, "Saskia", Gender.F, 1);

        //Given
        when(repos.existsByName(name.getName())).thenReturn(true);

        try {
            //Execute test
            service.insertBabyName(bn);
        } catch (Exception e) {
            //Check results
            assertEquals("Name already exists: " + bn.getName(), e.getMessage());
        }
    }
}