package uk.co.huntersix.spring.rest.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").exists())
            .andExpect(jsonPath("firstName").value("Mary"))
            .andExpect(jsonPath("lastName").value("Smith"));
    }
    @Test
    public void shouldReturnPersonsFromService() throws Exception {
        List<Person> persons = Arrays.asList( new Person("Collin", "Brown"),  new Person("Andrew", "Brown") );
        when(personDataService.findPersons("Brown")).thenReturn(persons);
        this.mockMvc.perform(get("/persons/Brown"))
                .andDo(print())
                .andExpect(status().isOk()).
         andExpect(jsonPath("$", hasSize(2)));
     }
@Test
    public void shouldAddPerson() throws Exception {
        Person newPerson = new Person("Roman","Abramovich");
      when(personDataService.findPerson("Abramovich", "Roman")).thenReturn(null);

    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(newPerson);

        this.mockMvc.perform(post("/addPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(status().is2xxSuccessful());

}
    @Test
    public void shouldNotAddExistingPerson() throws Exception {
        Person newPerson = new Person("Roman","Abramovich");
        when(personDataService.findPerson("Abramovich", "Roman")).thenReturn(new Person("Roman","Abramovich"));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(newPerson);

        this.mockMvc.perform(post("/addPerson")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(status().is2xxSuccessful());

    }



    @Test
    public void shouldReturn404FromService() throws Exception {
        List<Person> persons = new ArrayList<>();
        when(personDataService.findPersons(any())).thenReturn(persons);
        this.mockMvc.perform(get("/persons/brown"))
                .andDo(print())
                .andExpect(status().is4xxClientError()).
                andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
    public void shouldReturn404() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(null);
        this.mockMvc.perform(get("/person/smith/mary"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
       }
}