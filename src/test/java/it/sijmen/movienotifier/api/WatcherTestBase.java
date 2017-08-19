package it.sijmen.movienotifier.api;

import it.sijmen.movienotifier.model.FilterOption;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherFilters;
import org.joda.time.DateTime;
import org.junit.After;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static it.sijmen.movienotifier.model.FilterOption.NO;
import static it.sijmen.movienotifier.model.FilterOption.NOPREFERENCE;
import static it.sijmen.movienotifier.model.FilterOption.YES;
import static org.mockito.Mockito.when;

abstract class WatcherTestBase extends UserTestBase {

    final Watcher testwatcher;

    public WatcherTestBase() {
        super();
        testwatcher = new Watcher("WATCHERID", testuser.getId(), "First Watcher", 1,
                new DateTime(2027, 8, 1, 20, 30, 15).getMillis(),
                new DateTime(2027, 8, 7, 20, 30, 15).getMillis(),
                new WatcherFilters("A1", new DateTime(2028, 8, 1, 20, 30, 15).getMillis(),
                                         new DateTime(2028, 8, 1, 20, 30, 15).getMillis(),
                NO, YES, NOPREFERENCE, NO, NO, NO, NOPREFERENCE, NO, NOPREFERENCE, YES));
    }

    @After
    public void resetMocks() {
        Mockito.reset(userRepo);
        Mockito.reset(watcherRepo);
    }

    String buildJson(String id, String user, String name, int movieid, long begin, long end, String watcherDetails){
        List<String> items = new ArrayList<>();
        if(id != null)
            items.add("\"id\": \""+id+"\"");
        if(user != null)
            items.add("\"userid\": \""+user+"\"");
        if(name != null)
            items.add("\"name\": \""+name+"\"");
        if(movieid != -1)
            items.add("\"movieid\": \""+movieid+"\"");
        if(begin != -1)
            items.add("\"begin\": \""+begin+"\"");
        if(end != -1)
            items.add("\"end\": \""+end+"\"");
        if(watcherDetails != null)
            items.add("\"props\": "+watcherDetails+"");

        return "{\n" + String.join(",\n", items) +"}";
    }

    String buildJson(WatcherFilters d) {
        return buildJson(d.isOv(), d.isNl(), d.isImax(), d.isD3(), d.isHfr(), d.isK4(), d.isLaser(), d.isDbox(),
                d.isDolbycinema(), d.isDolbyatmos(), "N1");
    }
    String buildJson(FilterOption ov, FilterOption nl, FilterOption imax, FilterOption d3, FilterOption hfr, FilterOption k4, FilterOption laser,
                     FilterOption dbox, FilterOption dolbycinema, FilterOption dolbyatmos, String cinemaid){
        List<String> items = new ArrayList<>();
        if(cinemaid != null)
            items.add("\"cinemaid\": \""+cinemaid+"\"");
        if(ov != NOPREFERENCE)
            items.add("\"ov\": \""+ov+"\"");
        if(nl != NOPREFERENCE)
            items.add("\"nl\": \""+nl+"\"");
        if(imax != NOPREFERENCE)
            items.add("\"imax\": \""+imax+"\"");
        if(d3 != NOPREFERENCE)
            items.add("\"33\": \""+d3+"\"");
        if(hfr != NOPREFERENCE)
            items.add("\"hfr\": \""+hfr+"\"");
        if(k4 != NOPREFERENCE)
            items.add("\"4k\": \""+k4+"\"");
        if(laser != NOPREFERENCE)
            items.add("\"laser\": \""+laser+"\"");
        if(dbox != NOPREFERENCE)
            items.add("\"dbox\": \""+dbox+"\"");
        if(dolbycinema != NOPREFERENCE)
            items.add("\"dolbycinema\": \""+dolbycinema+"\"");
        if(dolbyatmos != NOPREFERENCE)
            items.add("\"dolbyatmos\": \""+dolbyatmos+"\"");

        return "{\n" + String.join(",\n", items) +"}";
    }

    void addToMockedDb(Watcher watcher){
        when(watcherRepo.getFirstByUuid(watcher.getId())).thenReturn(watcher);
    }


}
