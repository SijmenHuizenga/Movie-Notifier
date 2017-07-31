package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import org.hamcrest.Matchers;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class UserApiLoginTest extends UserTestBase {

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
    public void testLoginSucces() throws Exception {
        when(userRepo.findOne(testuser.getId())).thenReturn(testuser);
        when(userRepo.findFirstByApikey(testuser.getApikey())).thenReturn(testuser);
        when(userRepo.findFirstByName(testuser.getName())).thenReturn(testuser);

        this.mvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
                "{\n" +
                "    \"name\": \""+testuser.getName()+"\",\n" +
                "    \"password\": \"123456\"\n" +
                "}"
        ))
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
    public void testLoginWrongUsername() throws Exception {
        when(userRepo.findOne(testuser.getId())).thenReturn(testuser);
        when(userRepo.findFirstByApikey(testuser.getApikey())).thenReturn(testuser);
        when(userRepo.findFirstByName(testuser.getName())).thenReturn(testuser);

        this.mvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
                "{\n" +
                        "    \"name\": \""+testuser.getName()+"1\",\n" +
                        "    \"password\": \"123456\"\n" +
                        "}"
        ))
                .andExpect(status().isUnauthorized()).andExpect(content().string(""));
    }

    @Test
    public void testLoginWrongPassword() throws Exception {
        when(userRepo.findOne(testuser.getId())).thenReturn(testuser);
        when(userRepo.findFirstByApikey(testuser.getApikey())).thenReturn(testuser);
        when(userRepo.findFirstByName(testuser.getName())).thenReturn(testuser);

        this.mvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
                "{\n" +
                        "    \"name\": \""+testuser.getName()+"\",\n" +
                        "    \"password\": \"123789\"\n" +
                        "}"
        ))
            .andExpect(status().isUnauthorized()).andExpect(content().string(""));
    }

    @Test
    public void testLoginInvalidData() throws Exception {
        when(userRepo.findOne(testuser.getId())).thenReturn(testuser);
        when(userRepo.findFirstByApikey(testuser.getApikey())).thenReturn(testuser);
        when(userRepo.findFirstByName(testuser.getName())).thenReturn(testuser);

        this.mvc.perform(post("/login").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(
                "{\n" +
                        "    \"name\": \"NAM\",\n" +
                        "    \"password\": \"123\"\n" +
                        "}"
        ))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                    "name may only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters",
                    "name size must be between 4 and 16",
                    "password size must be between 6 and 128"
            )));
    }

}