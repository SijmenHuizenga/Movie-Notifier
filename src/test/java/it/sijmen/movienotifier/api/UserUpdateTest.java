package it.sijmen.movienotifier.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.sijmen.movienotifier.controllers.UserController;
import it.sijmen.movienotifier.model.User;
import java.util.Arrays;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserUpdateTest extends UserTestBase {

  @Test
  public void updateUserSuccess() throws Exception {
    addToMockedDb(testuser);
    removeFromMockedDb(testuser2);

    this.mvc
        .perform(
            post("/user/" + testuser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey())
                .content(
                    buildJson(
                        testuser2.getName(),
                        testuser2.getEmail(),
                        "564321",
                        Arrays.asList("ABC", "DEF"))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(testuser2.getName()))
        .andExpect(jsonPath("$.email").value(testuser2.getEmail()))
        .andExpect(jsonPath("$.id").value(testuser.getId()))
        .andExpect(jsonPath("$.apikey").value(IsNull.notNullValue()))
        .andExpect(
            jsonPath("$.fcm-registration-tokens")
                .value(new JSONArray().appendElement("ABC").appendElement("DEF")))
        .andExpect(jsonPath("$.password").doesNotExist());
    verify(userRepo, times(1)).save((User) any());
  }

  public void updateToTakenUsernameEmail() throws Exception {
    addToMockedDb(testuser);
    addToMockedDb(testuser2);

    this.mvc
        .perform(
            post("/user/" + testuser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey())
                .content(buildJson(testuser2.getName(), testuser2.getEmail(), null, null)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors")
                .value(
                    Matchers.containsInAnyOrder(
                        "The given username is already in use.",
                        "The given email is already in use.")));
    verify(userRepo, times(0)).save((User) any());
  }

  public void updateWithoutFields() throws Exception {
    addToMockedDb(testuser);

    this.mvc
        .perform(
            post("/user/" + testuser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey())
                .content(buildJson(null, null, null, null)))
        .andExpect(status().isOk());
    verify(userRepo, times(1)).save((User) any());
  }

  public void updateWithoutApikey() throws Exception {
    this.mvc
        .perform(
            post("/user/" + testuser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .content(buildJson(null, null, null, null)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.errors").value(Matchers.containsInAnyOrder("apikey is not provided")));
  }

  public void updateWrongApikey() throws Exception {
    this.mvc
        .perform(
            post("/user/" + testuser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", "ASDF")
                .content(buildJson(null, null, null, null)))
        .andExpect(status().isUnauthorized());
  }

  public void updateEmptyApikey() throws Exception {
    this.mvc
        .perform(
            post("/user/" + testuser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", "")
                .content(buildJson(null, null, null, null)))
        .andExpect(status().isUnauthorized());
  }

  public void updateEmptyBody() throws Exception {
    this.mvc
        .perform(
            post("/user/" + testuser.getId())
                .accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey())
                .content(""))
        .andExpect(status().isBadRequest());
  }
}
