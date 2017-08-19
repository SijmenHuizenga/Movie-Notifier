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
//todo
//    @Test
//    public void testCreateSuccess() throws Exception {
//        addToMockedDb(testuser);
//
//        when(watcherRepo.save((Watcher) any())).then(a -> {
//            Watcher arg = (Watcher) a.getArguments()[0];
//            arg.setId(testwatcher.getId());
//            return arg;
//        });
//
//        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
//                .header("APIKEY", testuser.getApikey()).content(buildJsonCreation(testwatcher)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(testwatcher.getId()))
//                .andExpect(jsonPath("$.userid").value(testwatcher.getUserid()))
//                .andExpect(jsonPath("$.name").value(testwatcher.getName()))
//                .andExpect(jsonPath("$.movieid").value(testwatcher.getMovieid()))
//                .andExpect(jsonPath("$.end").value(testwatcher.getEnd()))
//                .andExpect(jsonPath("$.begin").value(testwatcher.getBegin()));
//        verify(watcherRepo, times(1)).save((Watcher) any());
//    }

    @Test
    public void testCreateInvalidJson() throws Exception {
        addToMockedDb(testuser);
        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()).content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(Matchers.startsWith("JSON not valid")));
        verifyZeroInteractions(watcherRepo);
    }
//todo
//    @Test
//    public void testCreateNoParams() throws Exception {
//        addToMockedDb(testuser);
//        this.mvc.perform(put("/watchers/").accept(MediaType.APPLICATION_JSON)
//                .header("APIKEY", testuser.getApikey()).content("{\"userid\": \""+testuser.getId()+"\"}"))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errors").value(Matchers.containsInAnyOrder(
//                        "name may not be empty",
//                        "cinemaid may not be empty",
//                        "movieid must be between 1 and 9223372036854775807"
//                )));
//        verifyZeroInteractions(watcherRepo);
//    }

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
