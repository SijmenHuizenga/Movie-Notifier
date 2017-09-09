package it.sijmen.movienotifier.service.cinemas;

import org.junit.Test;

import static org.junit.Assert.*;

public class CinemaServiceTest {
    @Test
    public void runningNow() throws Exception {

        assertTrue(CinemaService.runningNow(5, 4, 6));
        assertTrue(CinemaService.runningNow(5, 5, 5));
        assertTrue(CinemaService.runningNow(5, 5, 6));
        assertTrue(CinemaService.runningNow(5, 4, 5));

        assertFalse(CinemaService.runningNow(5, 3, 4));
        assertFalse(CinemaService.runningNow(5, 6, 7));
    }

}