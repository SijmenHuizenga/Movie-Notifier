package it.sijmen.movienotifier.service.cinemas.pathe;

import com.mashape.unirest.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.sijmen.movienotifier.controllers.WatcherController;
import it.sijmen.movienotifier.model.PatheMovieCache;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherFilters;
import it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer;
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

import java.text.ParseException;
import java.util.Collections;
import java.util.Date;

import static it.sijmen.movienotifier.model.FilterOption.NO;
import static it.sijmen.movienotifier.model.FilterOption.NOPREFERENCE;
import static it.sijmen.movienotifier.model.FilterOption.YES;
import static it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer.PATHEFORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WatcherController.class)
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
                "            \"isLaser\": 0," +
                "            \"is4dx\": false," +
                "            \"isVision\": false" +
                "        }" +
                "    ]" +
                "}");

        Mockito.doReturn(mockResponse).when(api).makeGetRequest(anyString());

        when(patheCacheRepository.getFirstByMovieid(MOVIEID)).thenReturn(new PatheMovieCache(MOVIEID));

        api.checkWatcher(Collections.singletonList(
                new Watcher("SOMEID", "SOMEUSER", "SOMENAME", MOVIEID, watcherbegin, watcherend,
                    new WatcherFilters(
                        "PATHE"+ CINEMAID, filterafter, filterbefore, YES, NO, NO,
                        NO, NO, NO, NO, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, YES
                    )
                )
        ));

        verify(notificationService, times(fired ? 1 : 0)).notify(eq("SOMEUSER"), any(), any());
    }

    @Test
    public void testIS4DX() throws ParseException {
        PatheApi api = spy(new PatheApi(new ObjectMapper(), "SOMEKEY", patheCacheRepository, notificationService));

        Watcher watcher = new Watcher("SOMEID", "SOMEUSER", "Star Wars 8 (R'dam 4DX week 1)", 21432, 1510992000185L, 1513186080310L, new WatcherFilters(
                "PATHE12",
                1513353540786L, 1513540800699L, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE,
                NOPREFERENCE, NO, NOPREFERENCE, NOPREFERENCE
        ));

        PatheShowing patheShowing = new PatheShowing(12, 21432, 2382115, UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T21:00:00+01:00").getTime(),
                UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T23:50:00+01:00").getTime(), 1, 0, 0, 0, 0, 0, 0, 0, true, false);

        assertFalse(api.accepts(watcher, patheShowing));
    }

    @Test
    public void testISDolbyCinema() throws ParseException {
        PatheApi api = spy(new PatheApi(new ObjectMapper(), "SOMEKEY", patheCacheRepository, notificationService));

        Watcher watcher = new Watcher("SOMEID", "SOMEUSER", "Star Wars 8 (R'dam Dolby Cinema week 1)", 21432, 1510992000185L, 1513186080310L, new WatcherFilters(
                "PATHE12",
                1513353540786L, 1513540800699L, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE, NOPREFERENCE,
                NOPREFERENCE, NOPREFERENCE, NO, NOPREFERENCE
        ));

        PatheShowing patheShowing = new PatheShowing(12, 21432, 2382115, UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T21:00:00+01:00").getTime(),
                UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T23:50:00+01:00").getTime(), 1, 0, 0, 0, 0, 0, 0, 1, false, true);

        assertFalse(api.accepts(watcher, patheShowing));
    }

    @Test
    public void testEq(){
        PatheApi api = spy(new PatheApi(new ObjectMapper(), "SOMEKEY", patheCacheRepository, notificationService));
        assertTrue(api.eq(YES, 1));
        assertFalse(api.eq(YES, 0));
        assertTrue(api.eq(YES, null));

        assertFalse(api.eq(NO, 1));
        assertTrue(api.eq(NO, 0));
        assertTrue(api.eq(NO, null));

        assertTrue(api.eq(NOPREFERENCE, 1));
        assertTrue(api.eq(NOPREFERENCE, 0));
        assertTrue(api.eq(NOPREFERENCE, null));

        assertTrue(api.eqBool(YES, true));
        assertFalse(api.eqBool(YES, false));
        assertTrue(api.eqBool(YES, null));

        assertFalse(api.eqBool(NO, true));
        assertTrue(api.eqBool(NO, false));
        assertTrue(api.eqBool(NO, null));

        assertTrue(api.eqBool(NOPREFERENCE, true));
        assertTrue(api.eqBool(NOPREFERENCE, false));
        assertTrue(api.eqBool(NOPREFERENCE, null));
    }

}