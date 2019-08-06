package it.sijmen.movienotifier.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import it.sijmen.movienotifier.controllers.WatcherController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(WatcherController.class)
public class WatcherReadTest extends WatcherTestBase {

  @Test
  public void testReadOwn() throws Exception {
    addToMockedDb(testuser);
    addToMockedDb(testwatcher);

    this.mvc
        .perform(
            get("/watchers/" + testwatcher.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser.getApikey()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(testwatcher.getId()));
    verify(watcherRepo, times(1)).getFirstByUuid(testwatcher.getId());
  }

  @Test
  public void testReadSomeoneElses() throws Exception {
    addToMockedDb(testuser);
    addToMockedDb(testuser2);
    addToMockedDb(testwatcher);

    this.mvc
        .perform(
            get("/watchers/" + testwatcher.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .header("APIKEY", testuser2.getApikey()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").doesNotExist());
    verify(watcherRepo, times(1)).getFirstByUuid(testwatcher.getId());
  }
}
