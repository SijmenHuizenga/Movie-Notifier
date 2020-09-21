package it.sijmen.movienotifier.service.pathe.api;

import static it.sijmen.movienotifier.model.serialization.UnixTimestampDeserializer.PATHEFORMAT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import org.junit.Test;

public class PatheApiClientTest {

  private static final long HOUR = 3_600_000;
  private static final long TODAY = (System.currentTimeMillis() / 1000) * 1000;

  @Test
  public void testGetShowingsForMovie() throws IOException {

    final int movieid = 12;
    final int cinemaid = 123456;
    final int scheduleid = 654321;

    HttpResponse<String> mockResponse = mock(HttpResponse.class);
    when(mockResponse.getStatus()).thenReturn(200);
    when(mockResponse.getBody())
        .thenReturn(
            "{\"movieid\": "
                + movieid
                + ","
                + "    \"schedules\": ["
                + "        {"
                + "            \"id\": "
                + scheduleid
                + ","
                + "            \"cinemaId\": "
                + cinemaid
                + ","
                + "            \"movieId\":"
                + movieid
                + ","
                + "            \"start\": \""
                + PATHEFORMAT.format(new Date(TODAY))
                + "\","
                + "            \"end\": \""
                + PATHEFORMAT.format(new Date(TODAY + HOUR))
                + "\","
                + "            \"imax\": 0,"
                + "            \"3d\": 0,"
                + "            \"ov\": 1,"
                + "            \"nl\": 0,"
                + "            \"vip\": 1,"
                + "            \"hfr\": 0,"
                + "            \"isAtmos\": 1,"
                + "            \"is4k\": 0,"
                + "            \"isLaser\": 0,"
                + "            \"is4dx\": false,"
                + "            \"isScreenx\": false,"
                + "            \"isVision\": false"
                + "        }"
                + "    ]"
                + "}");

    PatheApiClient testSubject =
        spy(new PatheApiClient(new ObjectMapper(), "some value, not used in this test"));

    doReturn(mockResponse)
        .when(testSubject)
        .makeGetRequest("https://connect.pathe.nl/v1/movies/" + movieid + "/schedules");

    PatheShowings expected =
        new PatheShowings(
            movieid,
            Arrays.asList(
                new PatheShowing(
                    cinemaid,
                    movieid,
                    scheduleid,
                    TODAY,
                    TODAY + HOUR,
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
    PatheShowings result = testSubject.getShowingsForMovie(12);
    assertEquals(expected, result);
    assertTrue(expected.getShowings().get(0).equalsContent(result.getShowings().get(0)));
  }
}
