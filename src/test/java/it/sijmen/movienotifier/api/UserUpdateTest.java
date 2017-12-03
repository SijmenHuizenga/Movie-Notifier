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

import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JumpConfiguration.class)
public class UserUpdateTest extends UserTestBase {

    @Test
    public void updateUserSuccess() throws Exception {
        addToMockedDb(testuser);
        removeFromMockedDb(testuser2);

        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(
                        buildJson(testuser2.getName(), testuser2.getEmail(), testuser2.getPhonenumber(), "564321", Arrays.asList("FBM","MIL"))
        )).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(testuser2.getName()))
                .andExpect(jsonPath("$.email").value(testuser2.getEmail()))
                .andExpect(jsonPath("$.phonenumber").value(testuser2.getPhonenumber()))
                .andExpect(jsonPath("$.id").value(testuser.getId()))
                .andExpect(jsonPath("$.apikey").value(IsNull.notNullValue()))
                .andExpect(jsonPath("$.notifications")
                        .value(new JSONArray().appendElement("FBM").appendElement("MIL")))
                .andExpect(jsonPath("$.password").doesNotExist());
        verify(userRepo, times(1)).save((User)any());
    }

    @Test
    public void updateUnauthorizedNotification() throws Exception {
        addToMockedDb(testuser);
        removeFromMockedDb(testuser2);

        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(
                        buildJson(null, null, null, null, Arrays.asList("FBM","SMS"))
                )).andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                            "You do not have permission to use the SMS notification type."
                    )));
        verify(userRepo, times(0)).save((User)any());
    }

    public void updateToTakenUsernameEmail() throws Exception {
        addToMockedDb(testuser);
        addToMockedDb(testuser2);

        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(
                        buildJson(testuser2.getName(), testuser2.getEmail(), null, null, null)
                )).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "The given username is already in use.",
                        "The given email is already in use."
                )));
        verify(userRepo, times(0)).save((User)any());
    }

    public void updateWithoutFields() throws Exception {
        addToMockedDb(testuser);

        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(
                        buildJson(null, null, null, null, null)
                )).andExpect(status().isOk());
        verify(userRepo, times(1)).save((User)any());
    }

    public void updateWithoutApikey() throws Exception {
        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON).content(
                        buildJson(null, null, null, null, null)
                )).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                    "apikey is not provided"
            )));
    }

    public void updateWrongApikey() throws Exception {
        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", "ASDF").content(
                buildJson(null, null, null, null, null)
        )).andExpect(status().isUnauthorized());
    }

    public void updateEmptyApikey() throws Exception {
        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", "").content(
                        buildJson(null, null, null, null, null)
                )).andExpect(status().isUnauthorized());
    }

    public void updateEmptyBody() throws Exception {
        this.mvc.perform(post("/user/" + testuser.getId()).accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content("")).andExpect(status().isBadRequest());
    }
}
