package it.sijmen.movienotifier.api;

import static it.sijmen.movienotifier.model.FilterOption.*;
import static org.mockito.Mockito.when;

import it.sijmen.movienotifier.model.FilterOption;
import it.sijmen.movienotifier.model.Watcher;
import it.sijmen.movienotifier.model.WatcherFilters;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.junit.After;
import org.mockito.Mockito;

abstract class WatcherTestBase extends UserTestBase {

  final Watcher testwatcher;

  public WatcherTestBase() {
    super();
    testwatcher =
        new Watcher(
            "WATCHERID",
            testuser.getId(),
            "First Watcher",
            1,
            new DateTime(2027, 8, 1, 20, 30, 15).getMillis(),
            new DateTime(2027, 8, 7, 20, 30, 15).getMillis(),
            new WatcherFilters(
                12,
                new DateTime(2028, 8, 1, 20, 30, 15).getMillis(),
                new DateTime(2028, 8, 6, 20, 30, 15).getMillis(),
                NO,
                YES,
                NOPREFERENCE,
                NO,
                NO,
                NO,
                NOPREFERENCE,
                NO,
                NO,
                NOPREFERENCE,
                YES));
  }

  @After
  public void resetMocks() {
    Mockito.reset(userRepo);
    Mockito.reset(watcherRepo);
  }

  String buildJson(
      String id, String user, String name, int movieid, long begin, long end, String filters) {
    List<String> items = new ArrayList<>();
    if (id != null) items.add("\"id\": \"" + id + "\"");
    if (user != null) items.add("\"userid\": \"" + user + "\"");
    if (name != null) items.add("\"name\": \"" + name + "\"");
    if (movieid != -1) items.add("\"movieid\": \"" + movieid + "\"");
    if (begin != -1) items.add("\"begin\": \"" + begin + "\"");
    if (end != -1) items.add("\"end\": \"" + end + "\"");
    if (filters != null) items.add("\"filters\": " + filters + "");

    return "{\n" + String.join(",\n", items) + "}";
  }

  String buildJson(WatcherFilters d) {
    return buildJson(
        d.isOv(),
        d.isNl(),
        d.isImax(),
        d.isD3(),
        d.isHfr(),
        d.isK4(),
        d.isLaser(),
        d.isDx4(),
        d.isScreenx(),
        d.isDolbycinema(),
        d.isDolbyatmos(),
        d.getCinemaid(),
        d.getStartafter(),
        d.getStartbefore());
  }

  private String buildJson(
      FilterOption ov,
      FilterOption nl,
      FilterOption imax,
      FilterOption d3,
      FilterOption hfr,
      FilterOption k4,
      FilterOption laser,
      FilterOption dx4,
      FilterOption screenx,
      FilterOption dolbycinema,
      FilterOption dolbyatmos,
      int cinemaid,
      long startafter,
      long startbefore) {
    List<String> items = new ArrayList<>();
    if (cinemaid != 0) items.add("\"cinemaid\": \"" + cinemaid + "\"");
    if (ov != null) items.add("\"ov\": \"" + ov + "\"");
    if (nl != null) items.add("\"nl\": \"" + nl + "\"");
    if (imax != null) items.add("\"imax\": \"" + imax + "\"");
    if (d3 != null) items.add("\"3d\": \"" + d3 + "\"");
    if (hfr != null) items.add("\"hfr\": \"" + hfr + "\"");
    if (k4 != null) items.add("\"4k\": \"" + k4 + "\"");
    if (laser != null) items.add("\"laser\": \"" + laser + "\"");
    if (dx4 != null) items.add("\"4dx\": \"" + dx4 + "\"");
    if (screenx != null) items.add("\"screenx\": \"" + screenx + "\"");
    if (dolbycinema != null) items.add("\"dolbycinema\": \"" + dolbycinema + "\"");
    if (dolbyatmos != null) items.add("\"dolbyatmos\": \"" + dolbyatmos + "\"");
    if (startafter != -1) items.add("\"startafter\": \"" + startafter + "\"");
    if (startbefore != -1) items.add("\"startbefore\": \"" + startbefore + "\"");

    return "{\n" + String.join(",\n", items) + "}";
  }

  void addToMockedDb(Watcher watcher) {
    when(watcherRepo.getFirstByUuid(watcher.getId())).thenReturn(new Watcher(watcher));
  }
}
