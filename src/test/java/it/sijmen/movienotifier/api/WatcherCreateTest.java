package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.Watcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(JumpConfiguration.class)
public class WatcherCreateTest extends WatcherTestBase {

    private String buildJsonCreation(Watcher watcher){
        return buildJsonCreation(watcher.getUser(), watcher);
    }

    private String buildJsonCreation(String userid, Watcher watcher){
        return buildJson(null, userid, watcher.getName(), watcher.getMovieid(), watcher.getCinemaid(),
                watcher.getStartAfter(), watcher.getStartBefore(), buildJson(watcher.getProps()));
    }
    private String buildJsonCreation(long startBefore, long startAfter, Watcher watcher){
        return buildJson(null, watcher.getUser(), watcher.getName(), watcher.getMovieid(), watcher.getCinemaid(),
                startAfter, startBefore, buildJson(watcher.getProps()));
    }

    @Test
    public void testCreateSuccess() throws Exception {
        addToMockedDb(testuser);

        when(watcherRepo.save((Watcher) any())).then(a -> {
            Watcher arg = (Watcher) a.getArguments()[0];
            arg.setId(testwatcher.getId());
            return arg;
        });

        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(buildJsonCreation(testwatcher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(testwatcher.getId()))
                .andExpect(jsonPath("$.user").value(testwatcher.getUser()))
                .andExpect(jsonPath("$.name").value(testwatcher.getName()))
                .andExpect(jsonPath("$.movieid").value(testwatcher.getMovieid()))
                .andExpect(jsonPath("$.cinemaid").value(testwatcher.getCinemaid()))
                .andExpect(jsonPath("$.startAfter").value(testwatcher.getStartAfter()))
                .andExpect(jsonPath("$.startBefore").value(testwatcher.getStartBefore()));
        verify(watcherRepo, times(1)).save((Watcher) any());
    }

    @Test
    public void testCreateInvalidJson() throws Exception {
        addToMockedDb(testuser);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Matchers.startsWith("JSON not valid")));
        verifyZeroInteractions(watcherRepo);
    }

    @Test
    public void testCreateNoParams() throws Exception {
        addToMockedDb(testuser);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content("{\"user\": \""+testuser.getId()+"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "name may not be empty",
                        "cinemaid may not be empty",
                        "movieid must be between 1 and 9223372036854775807",
                        "startBefore Date must be in the present or future",
                        "startAfter Date must be in the present or future"
                )));
        verifyZeroInteractions(watcherRepo);
    }

    @Test
    public void testCreateNotYourApikey() throws Exception {
        addToMockedDb(testuser);
        addToMockedDb(testuser2);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser2.getApikey()).content(buildJsonCreation(testwatcher)))
                .andExpect(status().isUnauthorized());
        verifyZeroInteractions(watcherRepo);
    }

    @Test
    public void testCreateWrongApikey() throws Exception {
        addToMockedDb(testuser);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", "ASDFADSF").content(buildJsonCreation(testwatcher)))
                .andExpect(status().isUnauthorized());
        verifyZeroInteractions(watcherRepo);
    }

    @Test
    public void testCreateWrongUser() throws Exception {
        addToMockedDb(testuser);
        addToMockedDb(testuser2);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(buildJsonCreation(testuser2.getId(), testwatcher)))
                .andExpect(status().isUnauthorized());
        verifyZeroInteractions(watcherRepo);
    }

    @Test
    public void testStarTimeInPast() throws Exception {
        addToMockedDb(testuser);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(buildJsonCreation(1501797276, 1501793276, testwatcher)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "startAfter Date must be in the present or future",
                        "startBefore Date must be in the present or future"
                )));
        verifyZeroInteractions(watcherRepo);
    }

}
