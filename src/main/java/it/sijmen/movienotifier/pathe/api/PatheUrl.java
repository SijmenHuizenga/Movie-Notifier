package it.sijmen.movienotifier.pathe.api;

/**
 * Created by Sijmen on 7-4-2017.
 */
public class PatheUrl {

    static final String API_URL = "https://connect.pathe.nl/v1/";

    static final String ARTICLE_BY_CODE = "articles/code/%s";
    static final String CARDS_TILE = "users/%d/tiles";
    static final String CINEMA = "cinemas/%d";
    static final String CINEMAS = "cinemas";
    static final String CINEMA_EVENTS = "cinemas/%d/events";
    static final String CINEMA_MOVIES_NOWPLAYING = "cinemas/%d/movies/nowplaying";
    static final String CINEMA_SCHEDULES = "cinemas/schedules";
    static final String CINEMA_SPECIALS = "cinemas/%d/specials";
    static final String CINEMA_TRAILERS = "cinemas/%d/trailers";
    static final String DEVICE = "devices/%s";
    static final String DEVICE_NOTIFICATIONS = "devices/%s/notifications";
    static final String DEVICE_NOTIFICATIONS_SINGLE = "devices/%s/notifications/%d";
    static final String FAQ = "faq";
    static final String GENRES = "genres";
    static final String GET_SEAT_SELECTION = "transactions/%d/seats";
    static final String MOVIES = "movies/%d";
    static final String MOVIES_LIST_BY_TYPE = "movies/%s";
    static final String MOVIES_OVERVIEW = "movies/overview";
    static final String MOVIES_RATINGS = "movies/%d/ratings";
    static final String MOVIES_SEARCH = "movies/nowplaying/search/";
    static final String MOVIE_PER_CINEMA_SCHEDULES = "movies/%d/schedules";
    static final String NOTIFICATIONS = "notifications";
    static final String PERSON = "people/%d";
    static final String PUT_SEAT_SELECTION = "transactions/%d/seats/%s";
    static final String QUICKSEARCH = "quicksearch";
    static final String QUICKSEARCH_NOW_PLAYING = "quicksearch/movies/nowplaying";
    static final String SCHEDULE_TICKETS = "schedules/%d/tickets";
    static final String SESSIONS = "sessions";
    static final String SESSIONS_BY_ID = "sessions/%d";
    static final String SESSIONS_FACEBOOK = "sessions/facebook";
    static final String SPECIAL = "specials/%d";
    static final String SPECIALS = "specials";
    static final String SPLASH = "splash/current";
    static final String TILES_MOBILE = "tiles/mobile";
    static final String TILES_TABLET = "tiles/tablet";
    static final String TRAILERS = "trailers/new";
    static final String USER = "users/%d";
    static final String USERS_FACEBOOK = "users/facebook";
    static final String USER_CARDS = "users/%d/cards";
    static final String USER_CARD_PIN = "users/%d/cards/%d/pin";
    static final String USER_CINEMAS = "users/%d/cinemas";
    static final String USER_FAVORITES = "users/%d/movies/favorites";
    static final String USER_FAVORITES_BY_ID = "users/%d/movies/favorites/%d";
    static final String USER_PHOTO = "users/%d/photos";
    static final String USER_USERDATA = "users/%d/favorites";
}
