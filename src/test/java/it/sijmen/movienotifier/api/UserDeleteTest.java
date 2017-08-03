package it.sijmen.movienotifier.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JumpConfiguration.class)
public class UserDeleteTest extends UserTestBase {

    @Test
    public void testSuccess() throws Exception {
        addToMockedDb(testuser);

        this.mvc.perform(
                delete("/user/"+testuser.getId())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("APIKEY", testuser.getApikey())
        ).andExpect(status().isOk());

        verify(userRepo, times(1)).delete(testuser);
    }

    @Test
    public void testNonExistingId() throws Exception {
        addToMockedDb(testuser);

        this.mvc.perform(
                delete("/user/asdf")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("APIKEY", testuser.getApikey())
        ).andExpect(status().isUnauthorized());

        verify(userRepo, never()).delete(testuser);
    }

    @Test
    public void testUnauthorizedId() throws Exception {
        addToMockedDb(testuser);
        addToMockedDb(testuser2);

        this.mvc.perform(
                delete("/user/" + testuser2.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("APIKEY", testuser.getApikey())
        ).andExpect(status().isUnauthorized());

        verify(userRepo, never()).delete(testuser);
    }

    @Test
    public void testNoId() throws Exception {
        this.mvc.perform(
                delete("/user/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("APIKEY", testuser.getApikey())
        ).andExpect(status().isNotFound());
    }

    @Test
    public void testNonexistentApikey() throws Exception {
        removeFromMockedDb(testuser);
        addToMockedDb(testuser2);

        this.mvc.perform(
                delete("/user/" + testuser2.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("APIKEY", testuser.getApikey())
        ).andExpect(status().isUnauthorized());

        verify(userRepo, never()).delete(testuser);
    }


}
