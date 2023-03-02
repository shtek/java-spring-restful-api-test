package uk.co.huntersix.spring.rest.referencedata;

import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import uk.co.huntersix.spring.rest.model.Person;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
@RunWith(SpringRunner.class)
public class PersonDataServiceTest {

    @Test
  public  void addPerson() {
        PersonDataService service = new PersonDataService();
        int x= service.PERSON_DATA.size();
        service.addPerson(new Person("John","Doe"));
        int y = service.PERSON_DATA.size();
        Assertions.assertEquals(x+1,y);
         service.addPerson(new Person("John","Doe"));
        int z = service.PERSON_DATA.size();
        Assertions.assertEquals(z,y);


    }
}