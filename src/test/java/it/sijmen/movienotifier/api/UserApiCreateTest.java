package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.array;
import static org.hamcrest.core.Is.*;

import net.minidev.json.JSONArray;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Collections;

import static org.assertj.core.api.Assertions.not;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class UserApiCreateTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private WatcherRepository watcherRepository;

    private final User testuser;
    private final User testuser2;

    public UserApiCreateTest() {
        testuser = new User("TESTUSERID", "testgebruiker1", "TEST@example.com", "+31654321094",
                PasswordAuthentication.hash("123456"), "TESTAIKEY",
                new Calendar.Builder().setDate(2017,7,30).setTimeOfDay(20, 30, 15).build().getTime(),
                Collections.singletonList("FBM"));

        testuser2 = new User("TESTUSERID2", "testgebruiker2", "TEST2@example.com", "+31654321095",
                PasswordAuthentication.hash("123455"), "TESTAIKEY2",
                new Calendar.Builder().setDate(2017,7,30).setTimeOfDay(20, 50, 15).build().getTime(),
                Collections.singletonList("FBM"));
    }

    @After
    public void resetMocks() {
        Mockito.reset(userRepo);
        Mockito.reset(watcherRepository);
    }

    @Test
    public void testCreateSuccess() throws Exception {
        when(userRepo.getAllByName(any())).thenReturn(Collections.emptyList());
        when(userRepo.getAllByEmail(any())).thenReturn(Collections.emptyList());
        when(userRepo.save((User)any())).then(a -> {
            User arg = (User) a.getArguments()[0];
            arg.setId(testuser.getId());
            return arg;
        });

        this.mvc.perform(put("/user/").accept(MediaType.APPLICATION_JSON).content(
                "{\n" +
                "    \"name\": \""+testuser.getName()+"\",\n" +
                "    \"email\": \""+testuser.getEmail()+"\",\n" +
                "    \"phonenumber\": \""+testuser.getPhonenumber()+"\",\n" +
                "    \"password\": \"123456\"\n" +
                "}"
        )).andExpect(status().isOk())
          .andExpect(jsonPath("$.name").value(testuser.getName()))
          .andExpect(jsonPath("$.email").value(testuser.getEmail()))
          .andExpect(jsonPath("$.phonenumber").value(testuser.getPhonenumber()))
          .andExpect(jsonPath("$.uuid").value(testuser.getId()))
          .andExpect(jsonPath("$.apikey").value(IsNull.notNullValue()))
          .andExpect(jsonPath("$.enabledNotifications").value(new JSONArray().appendElement("FBM")))
          .andExpect(jsonPath("$.password").doesNotExist());
        verify(userRepo, times(1)).save((User)any());
    }

}