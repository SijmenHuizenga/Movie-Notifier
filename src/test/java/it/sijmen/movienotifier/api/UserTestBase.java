package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.util.PasswordAuthentication;

import java.util.Calendar;
import java.util.Collections;

abstract class UserTestBase {

    final User testuser;
    final User testuser2;

    public UserTestBase() {
        testuser = new User("TESTUSERID", "testgebruiker1", "test1@example.com", "+31654321094",
                PasswordAuthentication.hash("123456"), "TESTAIKEY",
                new Calendar.Builder().setDate(2017,7,30).setTimeOfDay(20, 30, 15).build().getTime(),
                Collections.singletonList("FBM"));
        testuser2 = new User("TESTUSERID2", "testgebruiker2", "test2@example.com", "+31654321095",
                PasswordAuthentication.hash("123455"), "TESTAIKEY2",
                new Calendar.Builder().setDate(2017,7,30).setTimeOfDay(20, 50, 15).build().getTime(),
                Collections.singletonList("FBM"));
    }

}
