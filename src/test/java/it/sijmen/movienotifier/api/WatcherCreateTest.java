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
        return buildJsonCreation(watcher.getUserid(), watcher);
    }

    private String buildJsonCreation(String userid, Watcher watcher){
        return buildJson(null, userid, watcher.getName(), watcher.getMovieid(),
                watcher.getBegin(), watcher.getEnd(), buildJson(watcher.getFilters()));
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
                .andExpect(jsonPath("$.id").value(testwatcher.getId()))
                .andExpect(jsonPath("$.userid").value(testwatcher.getUserid()))
                .andExpect(jsonPath("$.name").value(testwatcher.getName()))
                .andExpect(jsonPath("$.movieid").value(testwatcher.getMovieid()))
                .andExpect(jsonPath("$.end").value(testwatcher.getEnd()))
                .andExpect(jsonPath("$.begin").value(testwatcher.getBegin()));
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
    public void testCreateBeginBiggerThanEnd() throws Exception {
        addToMockedDb(testuser);
        Watcher watcher = new Watcher(testwatcher);
        watcher.setBegin(50);
        watcher.setEnd(30);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(buildJsonCreation(watcher)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "end must be later than begin"
                )));
        verifyZeroInteractions(watcherRepo);
    }

    @Test
    public void testCreateBeginTooFarFromEnd() throws Exception {
        addToMockedDb(testuser);
        Watcher watcher = new Watcher(testwatcher);
        watcher.setBegin(10);
        watcher.setEnd(10L+2629746000L);//10 seconds + 1 month
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content(buildJsonCreation(watcher)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "time between begin and end must be smaller than 1 month"
                )));
        verifyZeroInteractions(watcherRepo);
    }

    @Test
    public void testCreateNoParams() throws Exception {
        addToMockedDb(testuser);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content("{\"userid\": \""+testuser.getId()+"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
                        "name may not be empty",
                        "movieid must be greater than or equal to 1",
                        "filters may not be null"
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

}
