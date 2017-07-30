package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class UserApiReadTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private WatcherRepository watcherRepository;

    private final User testuser;
    private final User testuser2;

    public UserApiReadTest() {
        testuser = new User("TESTUSERID", "TESTUSER", "TEST@example.com", "+31654321094",
                PasswordAuthentication.hash("123456"), "TESTAIKEY",
                new Calendar.Builder().setDate(2017,7,30).setTimeOfDay(20, 30, 15).build().getTime(),
                Collections.singletonList("FBM"));
        testuser2 = new User("TESTUSERID2", "TESTUSER2", "TEST2@example.com", "+31654321095",
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
    public void testGetOwnUser() throws Exception {
        when(userRepo.findOne(testuser.getId())).thenReturn(testuser);
        when(userRepo.findFirstByApikey(testuser.getApikey())).thenReturn(testuser);

        this.mvc.perform(get("/user/"+testuser.getId()).accept(MediaType.APPLICATION_JSON).header("APIKEY", testuser.getApikey()))
                .andExpect(status().isOk()).andExpect(content().json(
                        "{\n" +
                        "    \"uuid\": \""+testuser.getId()+"\",\n" +
                        "    \"name\": \""+testuser.getName()+"\",\n" +
                        "    \"email\": \""+testuser.getEmail()+"\",\n" +
                        "    \"phonenumber\": \""+testuser.getPhonenumber()+"\",\n" +
                        "    \"apikey\": \""+testuser.getApikey()+"\",\n" +
                        "    \"enabledNotifications\": [\""+String.join(",", testuser.getEnabledNotifications())+"\"]\n" +
                        "}"
        ));
    }

    @Test
    public void testGetNonExistingUser() throws Exception {
        when(userRepo.findOne((String) any())).thenReturn(null);
        when(userRepo.findFirstByApikey(testuser.getApikey())).thenReturn(testuser);

        this.mvc.perform(get("/user/asdf").accept(MediaType.APPLICATION_JSON).header("APIKEY", testuser.getApikey()))
        .andExpect(status().isUnauthorized()).andExpect(content().string(""));
    }

    @Test
    public void testGetOtherExistingUser() throws Exception {
        when(userRepo.findOne(testuser2.getId())).thenReturn(testuser2);
        when(userRepo.findOne(testuser.getId())).thenReturn(testuser);
        when(userRepo.findFirstByApikey(testuser.getApikey())).thenReturn(testuser);
        when(userRepo.findFirstByApikey(testuser2.getApikey())).thenReturn(testuser2);

        this.mvc.perform(get("/user/"+testuser2.getApikey()).accept(MediaType.APPLICATION_JSON).header("APIKEY", testuser.getApikey()))
                .andExpect(status().isUnauthorized()).andExpect(content().string(""));
    }

}