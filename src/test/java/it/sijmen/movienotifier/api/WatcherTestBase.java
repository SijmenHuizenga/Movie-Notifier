package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherDetails;
import org.joda.time.DateTime;
import org.junit.After;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

abstract class WatcherTestBase extends UserTestBase {

    final Watcher testwatcher;

    public WatcherTestBase() {
        super();
        testwatcher = new Watcher("WATCHERID", testuser.getId(), "First Watcher", 1, "A1",
                new DateTime(2027, 8, 1, 20, 30, 15).getMillis(),
                new DateTime(2027, 8, 7, 20, 30, 15).getMillis(),
                new WatcherDetails(true, false, true, null, false, false, false, null, false, null));
    }

    @After
    public void resetMocks() {
        Mockito.reset(userRepo);
        Mockito.reset(watcherRepo);
    }

    String buildJson(String uuid, String user, String name, int movieid, String cinemaid, long startAfter, long startBefore, String watcherDetails){
        List<String> items = new ArrayList<>();
        if(uuid != null)
            items.add("\"uuid\": \""+uuid+"\"");
        if(user != null)
            items.add("\"user\": \""+user+"\"");
        if(name != null)
            items.add("\"name\": \""+name+"\"");
        if(movieid != -1)
            items.add("\"movieid\": \""+movieid+"\"");
        if(cinemaid != null)
            items.add("\"cinemaid\": \""+cinemaid+"\"");
        if(startAfter != -1)
            items.add("\"startAfter\": \""+startAfter+"\"");
        if(startBefore != -1)
            items.add("\"startBefore\": \""+startBefore+"\"");
        if(watcherDetails != null)
            items.add("\"props\": "+watcherDetails+"");

        return "{\n" + String.join(",\n", items) +"}";
    }

    String buildJson(WatcherDetails d) {
        return buildJson(d.isOv(), d.isNl(), d.isImax(), d.isD3(), d.isHfr(), d.isK4(), d.isLaser(), d.isDbox(),
                d.isDolbycinema(), d.isDolbyatmos());
    }
    String buildJson(Boolean ov, Boolean nl, Boolean imax, Boolean d3, Boolean hfr, Boolean k4, Boolean laser,
                     Boolean dbox, Boolean dolbycinema, Boolean dolbyatmos){
        List<String> items = new ArrayList<>();
        if(ov != null)
            items.add("\"ov\": \""+ov+"\"");
        if(nl != null)
            items.add("\"nl\": \""+nl+"\"");
        if(imax != null)
            items.add("\"imax\": \""+imax+"\"");
        if(d3 != null)
            items.add("\"33\": \""+d3+"\"");
        if(hfr != null)
            items.add("\"hfr\": \""+hfr+"\"");
        if(k4 != null)
            items.add("\"4k\": \""+k4+"\"");
        if(laser != null)
            items.add("\"laser\": \""+laser+"\"");
        if(dbox != null)
            items.add("\"dbox\": \""+dbox+"\"");
        if(dolbycinema != null)
            items.add("\"dolbycinema\": \""+dolbycinema+"\"");
        if(dolbyatmos != null)
            items.add("\"dolbyatmos\": \""+dolbyatmos+"\"");

        return "{\n" + String.join(",\n", items) +"}";
    }

    void addToMockedDb(Watcher watcher){
        when(watcherRepo.findOne(watcher.getId())).thenReturn(watcher);
    }


}
