package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.User;
import it.sijmen.movienotifier.repositories.UserRepository;
import it.sijmen.movienotifier.repositories.WatcherRepository;
import it.sijmen.movienotifier.util.PasswordAuthentication;
import org.junit.After;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

abstract class UserTestBase {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserRepository userRepo;

    @MockBean
    WatcherRepository watcherRepo;

    final User testuser;
    final User testuser2;

    public UserTestBase() {
        testuser = new User("TESTUSERID1", "testgebruiker1", "test1@example.com", "+31654321094",
                PasswordAuthentication.hash("123456"), "5B88FF72CD8A003704261DB5809513FF902E0DBFB5BC4D9B7C87AB2085369A87",
                new Calendar.Builder().setDate(2017,7,30).setTimeOfDay(20, 30, 15).build().getTime(),
                Collections.singletonList("FBM"));
        testuser2 = new User("TESTUSERID2", "testgebruiker2", "test2@example.com", "+31654321095",
                PasswordAuthentication.hash("123455"), "5B88FF72CD8A003704261DB5809513FF902E0DBFB5BC4D9B7C87AB2085369A88",
                new Calendar.Builder().setDate(2017,7,30).setTimeOfDay(20, 50, 15).build().getTime(),
                Collections.singletonList("FBM"));
    }

    @After
    public void resetMocks() {
        Mockito.reset(userRepo);
        Mockito.reset(watcherRepo);
    }

    String buildJson(String name, String email, String phone, String password, List<String> notifications){
        List<String> items = new ArrayList<>();
        if(name != null)
            items.add("\"name\": \""+name+"\"");
        if(email != null)
            items.add("\"email\": \""+email+"\"");
        if(phone != null)
            items.add("\"phonenumber\": \""+phone+"\"");
        if(password != null)
            items.add("\"password\": \""+password+"\"");
        if(notifications != null)
            items.add("\"notifications\": ["+notifications.stream().map(s -> "\"" + s + "\"").collect(Collectors.joining(", "))+"]\n");

        return "{\n" + String.join(",\n", items) +"}";
    }

    void addToMockedDb(User user){
        when(userRepo.findOne(user.getId())).thenReturn(user);
        when(userRepo.findFirstByApikey(user.getApikey())).thenReturn(user);
        when(userRepo.findFirstByName(user.getName())).thenReturn(user);

        when(userRepo.getAllByName(user.getName())).thenReturn(Collections.singletonList(user));
        when(userRepo.getAllByEmail(user.getEmail())).thenReturn(Collections.singletonList(user));
    }
    void removeFromMockedDb(User user){
        when(userRepo.findOne(user.getId())).thenReturn(null);
        when(userRepo.findFirstByApikey(user.getApikey())).thenReturn(null);
        when(userRepo.findFirstByName(user.getName())).thenReturn(null);

        when(userRepo.getAllByName(user.getName())).thenReturn(Collections.emptyList());
        when(userRepo.getAllByEmail(user.getEmail())).thenReturn(Collections.emptyList());
    }

}
