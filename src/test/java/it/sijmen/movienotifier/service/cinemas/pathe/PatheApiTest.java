package it.sijmen.movienotifier.service.cinemas.pathe;

import com.mashape.unirest.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static final long TODAY = (System.currentTimeMillis()/1000)*1000;

    private static final int MOVIEID = 1;
    private static final int CINEMAID = 2;

    @MockBean
    UserRepository userRepo;

    @MockBean
    WatcherRepository watcherRepo;

    @MockBean
    PatheCacheRepository patheCacheRepository;

    @MockBean
    NotificationService notificationService;

    @Test
    public void testWatcherBASE() throws Exception {
        //just a default wide range test case
        testWatcher(
                true,
                TODAY + DAY,            //movie starts tommorow
                TODAY + DAY + 2*HOUR,   //movie ends two hour later
                TODAY - DAY,       //watcher started yesterday
                TODAY + DAY,       //watcher ends tommorrow
                TODAY,        //movie must start later than today
                TODAY + DAY*6 //and start before the end of this week
        );
    }

    @Test
    public void testWatcherOK2() throws Exception {
        //should be notified when the movie starts exacly on the moment the watcher
        testWatcher(
                true,
                TODAY + DAY,            //movie starts exacly tommorow
                TODAY + DAY + 2*HOUR,   //movie ends two hour later
                TODAY - DAY,       //watcher started yesterday
                TODAY + DAY,       //watcher ends tommorrow
                TODAY + DAY,  //movie must start later than exacly tommorrow
                TODAY + DAY*6 //and start before the end of this week
        );
    }

    @Test
    public void testWatcherNO1() throws Exception {
        //should not be notified when the movie start time does not fit between the watcher filter times.
        testWatcher(
                false,
                TODAY + DAY-1,          //movie starts one second earlyer than tommorow
                TODAY + DAY + 2*HOUR,   //movie ends two hour later
                TODAY - DAY,       //watcher started yesterday
                TODAY + DAY,       //watcher ends tommorrow
                TODAY + DAY,  //movie must start later than exacly tommorrow
                TODAY + DAY*6 //and start before the end of this week
        );
    }

    @Test
    public void testWatcherNO2() throws Exception {
        //should not be notified when the watcher is not watching
        testWatcher(
                false,
                TODAY + DAY,            //movie starts tommorow
                TODAY + DAY + 2*HOUR,   //movie ends two hour later
                TODAY - 2*DAY,     //watcher started yesterday
                TODAY - DAY,       //watcher ends today
                TODAY,        //movie must start later than today
                TODAY + DAY*6 //and start before the end of this week
        );
    }

    public void testWatcher(boolean fired,
                            long showingstart, long showingend,
                            long watcherbegin, long watcherend,
                            long filterafter, long filterbefore) throws Exception {
        PatheApi api = spy(new PatheApi(new ObjectMapper(), "SOMEKEY", patheCacheRepository, notificationService));
        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.getBody()).thenReturn("{" +
                "    \"schedules\": [" +
                "        {" +
                "            \"id\": 123456," +
                "            \"cinemaId\": "+ CINEMAID +"," +
                "            \"movieId\": "+ MOVIEID +"," +
                "            \"start\": \""+PATHEFORMAT.format(new Date(showingstart))+"\"," +
                "            \"end\": \""+PATHEFORMAT.format(new Date(showingend))+"\"," +
                "            \"imax\": 0," +
                "            \"3d\": 0," +
                "            \"ov\": 1," +
                "            \"nl\": 0," +
                "            \"vip\": 1," +
                "            \"hfr\": 0," +
                "            \"isAtmos\": 1," +
                "            \"is4k\": 0," +
                "            \"isLaser\": 0" +
                "        }" +
                "    ]" +
                "}");

        Mockito.doReturn(mockResponse).when(api).makeGetRequest(anyString());

        when(patheCacheRepository.getFirstByMovieid(MOVIEID)).thenReturn(new PatheMoviesResponse(MOVIEID));

        api.checkWatcher(Collections.singletonList(
                new Watcher("SOMEID", "SOMEUSER", "SOMENAME", MOVIEID, watcherbegin, watcherend,
                    new WatcherFilters(
                        "PATHE"+ CINEMAID, filterafter, filterbefore, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE,
                        NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE,NOPREFERENCE
                    )
                )
        ));

        verify(notificationService, times(fired ? 1 : 0)).notify(eq("SOMEUSER"), any());
    }

}