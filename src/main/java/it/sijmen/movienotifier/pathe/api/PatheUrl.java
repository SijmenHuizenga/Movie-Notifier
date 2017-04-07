package it.sijmen.movienotifier.pathe.api;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class PatheUrl {

    public static final String API_ACCEPT_URL = "http://pathe-api.accept.poort80.net/";
    public static final String API_PRODUCTION_URL = "https://connect.pathe.nl/v1/";
    public static final String API_TEST_URL = "http://pathe-api.test.poort80.nl/";

    public static final String ARTICLE_BY_CODE = "articles/code/%s";
    public static final String CARDS_TILE = "users/%d/tiles";
    public static final String CINEMA = "cinemas/%d";
    public static final String CINEMAS = "cinemas";
    public static final String CINEMA_EVENTS = "cinemas/%d/events";
    public static final String CINEMA_MOVIES_NOWPLAYING = "cinemas/%d/movies/nowplaying";
    public static final String CINEMA_SCHEDULES = "cinemas/schedules";
    public static final String CINEMA_SPECIALS = "cinemas/%d/specials";
    public static final String CINEMA_TRAILERS = "cinemas/%d/trailers";
    public static final String DEVICE = "devices/%s";
    public static final String DEVICE_NOTIFICATIONS = "devices/%s/notifications";
    public static final String DEVICE_NOTIFICATIONS_SINGLE = "devices/%s/notifications/%d";
    public static final String FAQ = "faq";
    public static final String GENRES = "genres";
    public static final String GET_SEAT_SELECTION = "transactions/%d/seats";
    public static final String MOVIES = "movies/%d";
    public static final String MOVIES_LIST_BY_TYPE = "movies/%s";
    public static final String MOVIES_OVERVIEW = "movies/overview";
    public static final String MOVIES_RATINGS = "movies/%d/ratings";
    public static final String MOVIES_SEARCH = "movies/nowplaying/search/";
    public static final String MOVIE_PER_CINEMA_SCHEDULES = "movies/%d/schedules";
    public static final String NOTIFICATIONS = "notifications";
    public static final String PERSON = "people/%d";
    public static final String PUT_SEAT_SELECTION = "transactions/%d/seats/%s";
    public static final String QUICKSEARCH = "quicksearch";
    public static final String QUICKSEARCH_NOW_PLAYING = "quicksearch/movies/nowplaying";
    public static final String SCHEDULE_TICKETS = "schedules/%d/tickets";
    public static final String SESSIONS = "sessions";
    public static final String SESSIONS_BY_ID = "sessions/%d";
    public static final String SESSIONS_FACEBOOK = "sessions/facebook";
    public static final String SPECIAL = "specials/%d";
    public static final String SPECIALS = "specials";
    public static final String SPLASH = "splash/current";
    public static final String TILES_MOBILE = "tiles/mobile";
    public static final String TILES_TABLET = "tiles/tablet";
    public static final String TRAILERS = "trailers/new";
    public static final String USER = "users/%d";
    public static final String USERS_FACEBOOK = "users/facebook";
    public static final String USER_CARDS = "users/%d/cards";
    public static final String USER_CARD_PIN = "users/%d/cards/%d/pin";
    public static final String USER_CINEMAS = "users/%d/cinemas";
    public static final String USER_FAVORITES = "users/%d/movies/favorites";
    public static final String USER_FAVORITES_BY_ID = "users/%d/movies/favorites/%d";
    public static final String USER_PHOTO = "users/%d/photos";
    public static final String USER_USERDATA = "users/%d/favorites";
}
