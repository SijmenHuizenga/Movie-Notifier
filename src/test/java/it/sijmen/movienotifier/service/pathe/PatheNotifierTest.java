package it.sijmen.movienotifier.service.pathe;

import static it.sijmen.movienotifier.model.FilterOption.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import it.sijmen.movienotifier.model.PatheMovieCache;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherFilters;
import it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer;
import it.sijmen.movienotifier.repositories.PatheCacheRepository;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.service.NotificationService;
import it.sijmen.movienotifier.service.pathe.api.PatheApiClient;
import it.sijmen.movienotifier.service.pathe.api.PatheShowing;
import it.sijmen.movienotifier.service.pathe.api.PatheShowings;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class PatheNotifierTest {

  private static final long DAY = 86_400_000;
  private static final long HOUR = 3_600_000;
  private static final long TODAY = (System.currentTimeMillis() / 1000) * 1000;

  private static final int MOVIEID = 1;
  private static final int CINEMAID = 2;

  @MockBean UserRepository userRepo;

  @MockBean WatcherRepository watcherRepo;

  @MockBean PatheCacheRepository patheCacheRepository;

  @MockBean NotificationService notificationService;

  @Test
  public void testWatcherBASE() throws Exception {
    // just a default wide range test case
    testWatcher(
        true,
        TODAY + DAY, // movie starts tommorow
        TODAY + DAY + 2 * HOUR, // movie ends two hour later
        TODAY - DAY, // watcher started yesterday
        TODAY + DAY, // watcher ends tommorrow
        TODAY, // movie must start later than today
        TODAY + DAY * 6 // and start before the end of this week
        );
  }

  @Test
  public void testWatcherOK2() throws Exception {
    // should be notified when the movie starts exacly on the moment the watcher
    testWatcher(
        true,
        TODAY + DAY, // movie starts exacly tommorow
        TODAY + DAY + 2 * HOUR, // movie ends two hour later
        TODAY - DAY, // watcher started yesterday
        TODAY + DAY, // watcher ends tommorrow
        TODAY + DAY, // movie must start later than exacly tommorrow
        TODAY + DAY * 6 // and start before the end of this week
        );
  }

  @Test
  public void testWatcherNO1() throws Exception {
    // should not be notified when the movie start time does not fit between the watcher filter
    // times.
    testWatcher(
        false,
        TODAY + DAY - 1, // movie starts one second earlyer than tommorow
        TODAY + DAY + 2 * HOUR, // movie ends two hour later
        TODAY - DAY, // watcher started yesterday
        TODAY + DAY, // watcher ends tommorrow
        TODAY + DAY, // movie must start later than exacly tommorrow
        TODAY + DAY * 6 // and start before the end of this week
        );
  }

  public void testWatcher(
      boolean fired,
      long showingstart,
      long showingend,
      long watcherbegin,
      long watcherend,
      long filterafter,
      long filterbefore)
      throws Exception {
    PatheApiClient apiClient = mock(PatheApiClient.class);
    PatheShowings patheShowings = new PatheShowings(123456);
    patheShowings.setShowings(
        Arrays.asList(
            new PatheShowing(
                CINEMAID,
                MOVIEID,
                123456,
                showingstart,
                showingend,
                0,
                0,
                0,
                1,
                0,
                1,
                0,
                0,
                false,
                false,
                false)));
    when(apiClient.getShowingsForMovie(anyInt())).thenReturn(patheShowings);

    PatheNotifier api =
        spy(new PatheNotifier(patheCacheRepository, notificationService, apiClient));

    when(patheCacheRepository.getFirstByMovieid(MOVIEID)).thenReturn(new PatheMovieCache(MOVIEID));

    api.checkWatcher(
        Collections.singletonList(
            new Watcher(
                "SOMEID",
                "SOMEUSER",
                "SOMENAME",
                MOVIEID,
                watcherbegin,
                watcherend,
                new WatcherFilters(
                    CINEMAID,
                    filterafter,
                    filterbefore,
                    YES,
                    NO,
                    NO,
                    NO,
                    NO,
                    NO,
                    NO,
                    NOPREFERENCE,
                    NOPREFERENCE,
                    NOPREFERENCE,
                    YES,
                    NOPREFERENCE))));

    verify(notificationService, times(fired ? 1 : 0)).sendUpdates(any(), any());
  }

  @Test
  public void testIS4DX() throws ParseException {
    PatheNotifier api = spy(new PatheNotifier(patheCacheRepository, notificationService, null));

    Watcher watcher =
        new Watcher(
            "SOMEID",
            "SOMEUSER",
            "Star Wars 8 (R'dam 4DX week 1)",
            21432,
            1510992000185L,
            1513186080310L,
            new WatcherFilters(
                12,
                1513353540786L,
                1513540800699L,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NO,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE));

    PatheShowing patheShowingResponse =
        new PatheShowing(
            12,
            21432,
            2382115,
            UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T21:00:00+01:00").getTime(),
            UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T23:50:00+01:00").getTime(),
            1,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            true,
            false,
            false);

    assertFalse(api.accepts(watcher, patheShowingResponse));
  }

  @Test
  public void testISDolbyCinema() throws ParseException {
    PatheNotifier api = spy(new PatheNotifier(patheCacheRepository, notificationService, null));

    Watcher watcher =
        new Watcher(
            "SOMEID",
            "SOMEUSER",
            "Star Wars 8 (R'dam Dolby Cinema week 1)",
            21432,
            1510992000185L,
            1513186080310L,
            new WatcherFilters(
                12,
                1513353540786L,
                1513540800699L,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NO,
                NOPREFERENCE,
                NOPREFERENCE));

    PatheShowing patheShowingResponse =
        new PatheShowing(
            12,
            21432,
            2382115,
            UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T21:00:00+01:00").getTime(),
            UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T23:50:00+01:00").getTime(),
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            false,
            false,
            true);

    assertFalse(api.accepts(watcher, patheShowingResponse));
  }

  @Test
  public void testISAtmosDolbyCinema() throws ParseException {
    PatheNotifier api = spy(new PatheNotifier(patheCacheRepository, notificationService, null));

    Watcher watcher =
        new Watcher(
            "SOMEID",
            "SOMEUSER",
            "SOMENAME",
            23469,
            1564380000000L,
            1564432200000L,
            new WatcherFilters(
                12,
                1564840800000L,
                1564866000000L,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                YES,
                NOPREFERENCE));

    PatheShowing patheShowingResponse =
        new PatheShowing(
            12,
            23469,
            3098804,
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T19:50:00+02:00").getTime(),
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T22:26:00+02:00").getTime(),
            0,
            0,
            0,
            1,
            0,
            0,
            0,
            0,
            false,
            false,
            true);

    assertTrue(api.accepts(watcher, patheShowingResponse));
  }

  @Test
  public void testISLaserIMAX() throws ParseException {
    PatheNotifier api = spy(new PatheNotifier(patheCacheRepository, notificationService, null));

    Watcher watcher =
        new Watcher(
            "SOMEID",
            "SOMEUSER",
            "SOMENAME",
            23469,
            1564380000000L,
            1564432200000L,
            new WatcherFilters(
                6,
                1564840800000L,
                1564866000000L,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                YES,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE));

    PatheShowing patheShowingResponse =
        new PatheShowing(
            6,
            23469,
            3098306,
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T18:15:00+02:00").getTime(),
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T20:46:00+02:00").getTime(),
            1,
            0,
            1,
            1,
            0,
            0,
            0,
            0,
            false,
            false,
            false);

    assertTrue(api.accepts(watcher, patheShowingResponse));
  }

  @Test
  public void testISScreenX() throws ParseException {
    PatheNotifier api = spy(new PatheNotifier(patheCacheRepository, notificationService, null));

    Watcher watcher =
        new Watcher(
            "SOMEID",
            "SOMEUSER",
            "Star Wars 8 X",
            21432,
            1510992000185L,
            1513186080310L,
            new WatcherFilters(
                12,
                1513353540786L,
                1513540800699L,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                YES,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE));

    PatheShowing patheShowingResponse =
        new PatheShowing(
            12,
            21432,
            2382115,
            UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T21:00:00+01:00").getTime(),
            UnixTimestampDeserializer.PATHEFORMAT.parse("2017-12-15T23:50:00+01:00").getTime(),
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            false,
            true,
            false);

    assertTrue(api.accepts(watcher, patheShowingResponse));
  }

  @Test
  public void testISRegularShowing() throws ParseException {
    PatheNotifier api = spy(new PatheNotifier(patheCacheRepository, notificationService, null));

    Watcher watcher =
        new Watcher(
            "SOMEID",
            "SOMEUSER",
            "SOMENAME",
            23469,
            1564380000000L,
            1564432200000L,
            new WatcherFilters(
                6,
                1564840800000L,
                1564866000000L,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                YES));

    PatheShowing patheShowingResponse =
        new PatheShowing(
            6,
            23469,
            3098306,
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T18:15:00+02:00").getTime(),
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T20:46:00+02:00").getTime(),
            0,
            0,
            1,
            1,
            0,
            0,
            0,
            0,
            false,
            false,
            false);

    assertFalse(api.accepts(watcher, patheShowingResponse));
  }

  @Test
  public void testISNoRegularShowing() throws ParseException {
    PatheNotifier api = spy(new PatheNotifier(patheCacheRepository, notificationService, null));

    Watcher watcher =
        new Watcher(
            "SOMEID",
            "SOMEUSER",
            "SOMENAME",
            23469,
            1564380000000L,
            1564432200000L,
            new WatcherFilters(
                6,
                1564840800000L,
                1564866000000L,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NOPREFERENCE,
                NO));

    PatheShowing patheShowingResponse =
        new PatheShowing(
            6,
            23469,
            3098306,
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T18:15:00+02:00").getTime(),
            UnixTimestampDeserializer.PATHEFORMAT.parse("2019-08-03T20:46:00+02:00").getTime(),
            1,
            0,
            0,
            1,
            0,
            1,
            0,
            0,
            false,
            false,
            true);

    assertTrue(api.accepts(watcher, patheShowingResponse));
  }
}
