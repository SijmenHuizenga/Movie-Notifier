package it.sijmen.movienotifier.integrationtests;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Every.everyItem;

class WatcherIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatcherIT.class);

    private final Random r = new Random();
    private final Map<String, Object> watcherdata = new HashMap<>();

    private final String HEADKEY = "APIKEY";
    private final String ID = "id", USERID = "userid", NAME = "name", MOVIEID = "movieid", BEGIN = "begin", END = "end", FILTERS = "filters";

    private final UserIT user;

    public WatcherIT(UserIT user) {
        this.user = user;
    }

    void create(){
        watcherdata.put(USERID, user.id());
        watcherdata.put(NAME, "IT"+r.nextInt(50));
        watcherdata.put(MOVIEID, r.nextInt(50)+15);
        watcherdata.put(BEGIN, System.currentTimeMillis());
        watcherdata.put(END, System.currentTimeMillis() + 432_000_000L); // + 5 days
        makeRandomFilters(false);

        Response response = given().header(HEADKEY, user.apikey())
                .body(watcherdata())
                .when().put("/watchers").then()
                .extract().response();
        watcherdata.put(ID, response.path(ID));
        checkWatcherResponse(response.then());

        LOGGER.info("Created watcher");
    }

    void update(){
        watcherdata.put(NAME, "Detective"+r.nextInt(50));
        watcherdata.put(MOVIEID, r.nextInt(50)+15);
        watcherdata.put(BEGIN, System.currentTimeMillis());
        watcherdata.put(END, System.currentTimeMillis() + 432_000_000L); // + 5 days
        makeRandomFilters(true);

        checkWatcherResponse(
                given().header(HEADKEY, user.apikey())
                .body(watcherdata())
                .when().post("/watchers/"+id()).then()
        );

        LOGGER.info("Updated watcher");
    }

    void read(){
        checkWatcherResponse(
                given().header(HEADKEY, user.apikey())
                .when().get("/watchers/"+id()).then()
        );

        LOGGER.info("Read watcher");
    }

    void delete(){
        given().header(HEADKEY, user.apikey()).delete("/watchers/"+id()).then().statusCode(200);
        LOGGER.info("Deleted watcher");
    }

    private ValidatableResponse checkWatcherResponse(ValidatableResponse then){
        return then.body("$", new BaseMatcher<HashMap>() {
            @Override
            public boolean matches(Object a) {
                return HashMapEqualizer.equals((HashMap<String, Object>) a, watcherdata);
            }

            @Override
            public void describeTo(Description description) {
                description.appendValue(watcherdata);
            }
        });
    }

    private Map<String, Object> watcherdata(){
        Map<String, Object> out = new HashMap<>(watcherdata);
        out.remove(ID);
        return out;
    }

    private void makeRandomFilters(boolean includeNulls) {
        Map<String, Object> out = (Map<String, Object>) watcherdata.getOrDefault(FILTERS, new HashMap<>());
        out.put("cinemaid", "PATHE"+r.nextInt(18));
        out.put("startafter", r.nextInt(500)+500);
        out.put("startbefore", r.nextInt(500) + 1001L);
        String[] opts = new String[]{"ov", "nl", "imax", "hfr", "laser", "dbox", "dolbycinema", "dolbyatmos", "3d", "4k"};
        for(String o : opts) {
            if (includeNulls && r.nextBoolean())
                continue;
            out.put(o, randomYesNoMabye());
        }
        watcherdata.put(FILTERS, out);
    }

    private String randomYesNoMabye(){
        switch(r.nextInt(3)){
            case 0: return "yes";
            case 1: return "no";
            case 2: return "no-preference";
        }
        throw new IllegalStateException("wrong code");
    }

    String id(){ return (String) watcherdata.get(ID); }

}
