package uk.co.huntersix.spring.rest.referencedata;

import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonDataService {
    public static   List<Person> PERSON_DATA = new ArrayList<>();

    {
        PERSON_DATA.add(new Person("Mary", "Smith"));
        PERSON_DATA.add(  new Person("Brian", "Archer"));
        PERSON_DATA.add(  new Person("Collin", "Brown"));
        PERSON_DATA.add(  new Person("Andrew", "Brown"));

    };

    public Person findPerson(String lastName, String firstName) {
        return PERSON_DATA.stream()
            .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                && p.getLastName().equalsIgnoreCase(lastName))
            .collect(Collectors.toList()).get(0);
    }
    public List<Person> findPersons(String lastName) {
        return PERSON_DATA.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }
    public void addPerson(Person person){
        if(!PERSON_DATA.contains(person))
           PERSON_DATA.add(person);
    }
}
