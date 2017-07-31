package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
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

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserApiController.class)
public class UserApiCreateTest extends UserTestBase {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepo;

    @MockBean
    private WatcherRepository watcherRepository;

    private String buildJson(String name, String email, String phone, String password){
        return "{\n" +
                "    \"name\": \""+name+"\",\n" +
                "    \"email\": \""+email+"\",\n" +
                "    \"phonenumber\": \""+phone+"\",\n" +
                "    \"password\": \""+password+"\"\n" +
                "}";
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
                buildJson(testuser.getName(), testuser.getEmail(), testuser.getPhonenumber(), "123456")
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

    @Test
    public void testCreateInvalidJson() throws Exception {
        this.mvc.perform(put("/user/").accept(MediaType.APPLICATION_JSON).content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Matchers.startsWith("JSON not valid")));
        verifyZeroInteractions(userRepo);
    }

    @Test
    public void testCreateNoParams() throws Exception {
        this.mvc.perform(put("/user/").accept(MediaType.APPLICATION_JSON).content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "email may not be empty",
                        "name may not be empty",
                        "phonenumber may not be empty",
                        "password may not be empty"
                )));
        verifyZeroInteractions(userRepo);
    }

    @Test
    public void testCreateInvalidParams() throws Exception {
        this.mvc.perform(put("/user/").accept(MediaType.APPLICATION_JSON).content(
                buildJson("inv", "inv", "+3108", "123")
        ))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "email not a well-formed email address",
                        "name may only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters",
                        "name size must be between 4 and 16",
                        "phonenumber must be in the format +[countrycode][phonenumber]",
                        "password size must be between 6 and 128"
                )));
        verifyZeroInteractions(userRepo);
    }

    @Test
    public void testCreateUsernameEmailTaken() throws Exception {
        when(userRepo.getAllByName(any())).thenReturn(Collections.singletonList(testuser));
        when(userRepo.getAllByEmail(any())).thenReturn(Collections.singletonList(testuser));

        this.mvc.perform(put("/user/").accept(MediaType.APPLICATION_JSON).content(
                buildJson(testuser.getName(), testuser.getEmail(), testuser.getPhonenumber(), "123456")
        )).andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                  "The given username is already in use.",
                  "The given email is already in use."
          )));
    }

}