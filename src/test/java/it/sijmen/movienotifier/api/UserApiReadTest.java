package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class UserApiReadTest extends UserTestBase {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private WatcherRepository watcherRepository;

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