package uk.co.huntersix.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public ResponseEntity<Person> person(@PathVariable(value="lastName") String lastName,
                                        @PathVariable(value="firstName") String firstName) {

        Person person = personDataService.findPerson(lastName, firstName);
        if(person!=null)
           return new ResponseEntity<>(person,HttpStatus.OK);
        else
            return new ResponseEntity<>(person,HttpStatus.NOT_FOUND);
    }
    @GetMapping("/persons/{lastName}")
    public ResponseEntity<List<Person>> persons(@PathVariable(value="lastName") String lastName) {

        List<Person> persons = personDataService.findPersons(lastName);
        if (persons.size() > 0)
                return new ResponseEntity<>(persons,HttpStatus.OK);
        else
            return new ResponseEntity<>(persons,HttpStatus.NOT_FOUND);

    }
    @PostMapping(value = "/addPerson")
    public ResponseEntity<Object> addPerson(@RequestBody @Valid Person person){
      Person present = personDataService.findPerson(person.getLastName(),person.getFirstName() );
        if (present==null)
        {
            personDataService.addPerson(person);
            return new ResponseEntity<>(HttpStatus.valueOf(201));
        }
        else

            return new ResponseEntity<>(HttpStatus.valueOf(200));

    }
}