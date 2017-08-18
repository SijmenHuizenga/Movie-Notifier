package it.sijmen.movienotifier.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JumpConfiguration.class)
public class UserReadTest extends UserTestBase {

    @Test
    public void testGetOwnUser() throws Exception {
        addToMockedDb(testuser);

        this.mvc.perform(get("/user/"+testuser.getId()).accept(MediaType.APPLICATION_JSON).header("APIKEY", testuser.getApikey()))
                .andExpect(status().isOk()).andExpect(content().json(
                        "{\n" +
                        "    \"id\": \""+testuser.getId()+"\",\n" +
                        "    \"name\": \""+testuser.getName()+"\",\n" +
                        "    \"email\": \""+testuser.getEmail()+"\",\n" +
                        "    \"phonenumber\": \""+testuser.getPhonenumber()+"\",\n" +
                        "    \"apikey\": \""+testuser.getApikey()+"\",\n" +
                        "    \"notifications\": [\""+String.join(",", testuser.getEnabledNotifications())+"\"]\n" +
                        "}"
        ));
    }

    @Test
    public void testGetNonExistingUser() throws Exception {
        addToMockedDb(testuser);
        removeFromMockedDb(testuser2);

        this.mvc.perform(get("/user/"+testuser2.getId()).accept(MediaType.APPLICATION_JSON).header("APIKEY", testuser.getApikey()))
        .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Unauthorized Request"));
    }

    @Test
    public void testGetOtherExistingUser() throws Exception {
        addToMockedDb(testuser);
        addToMockedDb(testuser2);

        this.mvc.perform(get("/user/"+testuser2.getApikey()).accept(MediaType.APPLICATION_JSON).header("APIKEY", testuser.getApikey()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Unauthorized Request"));
    }

}