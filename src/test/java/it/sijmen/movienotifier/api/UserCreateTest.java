package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.User;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JumpConfiguration.class)
public class UserCreateTest extends UserTestBase {

    @Test
    public void testCreateSuccess() throws Exception {
        removeFromMockedDb(testuser);
        when(userRepo.save((User)any())).then(a -> {
            User arg = (User) a.getArguments()[0];
            arg.setId(testuser.getId());
            return arg;
        });

        this.mvc.perform(put("/user/").accept(MediaType.APPLICATION_JSON).content(
                buildJson(testuser.getName(), testuser.getEmail(), testuser.getPhonenumber(), "12345678", null)
        )).andExpect(status().isOk())
          .andExpect(jsonPath("$.name").value(testuser.getName()))
          .andExpect(jsonPath("$.email").value(testuser.getEmail()))
          .andExpect(jsonPath("$.phonenumber").value(testuser.getPhonenumber()))
          .andExpect(jsonPath("$.id").value(testuser.getId()))
          .andExpect(jsonPath("$.apikey").value(IsNull.notNullValue()))
          .andExpect(jsonPath("$.notifications").value(new JSONArray().appendElement("FBM")))
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
                buildJson("inv", "inv", "+3108", "123", null)
        ))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "email not a well-formed email address",
                        "name may only contain letters (a-z) and numbers (0-9), but no capital letters (A-Z). The first 4 characters must always be letters",
                        "name size must be between 4 and 16",
                        "phonenumber must be in the format +[countrycode][phonenumber]",
                        "password size must be between 8 and 128"
                )));
        verifyZeroInteractions(userRepo);
    }

    @Test
    public void testCreateUsernameEmailTaken() throws Exception {
        when(userRepo.getAllByName(testuser.getName())).thenReturn(Collections.singletonList(testuser));
        when(userRepo.getAllByEmail(testuser.getEmail())).thenReturn(Collections.singletonList(testuser));

        this.mvc.perform(put("/user/").accept(MediaType.APPLICATION_JSON).content(
                buildJson(testuser.getName(), testuser.getEmail(), testuser.getPhonenumber(), "12345678", null)
        )).andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                  "The given username is already in use.",
                  "The given email is already in use."
          )));
    }

}