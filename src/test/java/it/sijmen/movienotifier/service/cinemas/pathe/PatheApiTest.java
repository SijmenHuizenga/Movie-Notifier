package it.sijmen.movienotifier.service.cinemas.pathe;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.request.GetRequest;
import it.sijmen.movienotifier.api.JumpConfiguration;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherFilters;
import it.sijmen.movienotifier.repositories.PatheCacheRepository;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.service.notification.NotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;

import static it.sijmen.movienotifier.model.FilterOption.NOPREFERENCE;
import static it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer.PATHEFORMAT;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(JumpConfiguration.class)
public class PatheApiTest {

    private static final long DAY = 86_400_000;
    private static final long HOUR = 3_600_000;

    @MockBean
    UserRepository userRepo;

    @MockBean
    WatcherRepository watcherRepo;

    @MockBean
    PatheCacheRepository patheCacheRepository;

    @MockBean
    NotificationService notificationService;

    @Test
    public void checkWatcher() throws Exception {
        long today = System.currentTimeMillis();
        int movieid = 1;
        int cinemaid = 2;

        PatheApi api = spy(
                new PatheApi(new ObjectMapper(), "SOMEKEY", patheCacheRepository, notificationService)
        );

        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.getBody()).thenReturn("{" +
                "    \"schedules\": [" +
                "        {" +
                "            \"id\": 123456," +
                "            \"cinemaId\": "+cinemaid+"," +
                "            \"movieId\": "+movieid+"," +
                "            \"start\": \""+PATHEFORMAT.format(new Date(today+DAY))+"\"," +
                "            \"end\": \""+PATHEFORMAT.format(new Date(today+DAY+2*HOUR))+"\"," +
                "            \"imax\": 0," +
                "            \"3d\": 0," +
                "            \"ov\": 1," +
                "            \"nl\": 0," +
                "            \"vip\": 1," +
                "            \"hfr\": 0," +
                "            \"isAtmos\": 1," +
                "            \"is4k\": 0," +
                "            \"isLaser\": 0," +
                "        }" +
                "    ]" +
                "}");

        Mockito.doReturn(mockResponse).when(api).makeGetRequest(anyString());

        when(patheCacheRepository.getFirstByMovieid(movieid)).thenReturn(new PatheMoviesResponse(movieid));

        api.checkWatcher(Collections.singletonList(
                new Watcher("SOMEID", "SOMEUSER", "SOMENAME", movieid, today-DAY, today+DAY, new WatcherFilters(
                        "PATHE"+cinemaid, today, today+DAY*6, NOPREFERENCE,NOPREFERENCE,NOPREFERENCE,NOPREFERENCE,
                        NOPREFERENCE, NOPREFERENCE,NOPREFERENCE,NOPREFERENCE,NOPREFERENCE, NOPREFERENCE
                ))
        ));

        verify(notificationService, times(1)).notify(eq("SOMEUSER"), any());
    }

}