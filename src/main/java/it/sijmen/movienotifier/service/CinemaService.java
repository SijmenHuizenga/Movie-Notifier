package it.sijmen.movienotifier.service;

import it.sijmen.movienotifier.model.Cinema;

import java.util.ArrayList;
import java.util.List;

public class CinemaService {

    private static final List<Cinema> allCinemas;

    private static final String TIMEZONE_PATHE = "Europe/Amsterdam";

    static {
        allCinemas = new ArrayList<>();

        allCinemas.add(new Cinema(9, "Pathé Arena", 52.31233f, 4.94577f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(2, "Pathé Tuschinski", 52.36655f, 4.89469f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(1, "Pathé City", 52.36345f, 4.88383f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(10, "Pathé De Munt", 52.36648f, 4.89342f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(12, "Pathé De Kuip", 51.8968f, 4.5231f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(6, "Pathé Schouwburgplein", 51.92078f, 4.47341f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(13, "Pathé Spuimarkt", 52.07727f, 4.31522f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(5, "Pathé Buitenhof", 52.07858f, 4.31089f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(7, "Pathé Scheveningen", 52.11241f, 4.2839f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(3, "Pathé Rembrandt Utrecht", 52.09388f, 5.11629f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(30, "Pathé Utrecht Leidsche Rijn", 52.09684f, 5.07095f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(23, "Pathé Amersfoort", 52.15876f, 5.3808f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(27, "Pathé Arnhem", 51.98422f, 5.90339f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(20, "Pathé Breda", 51.58972f, 4.78503f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(18, "Pathé Delft", 52.00861f, 4.36329f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(8, "Pathé Eindhoven", 51.44082f, 5.48053f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(4, "Pathé Groningen", 53.21434f, 6.56636f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(22, "Pathé Haarlem", 52.38176f, 4.6294f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(11, "Pathé Helmond", 51.47793f, 5.65092f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(17, "Pathé Maastricht", 50.85643f, 5.68988f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(19, "Pathé Tilburg", 51.5579f, 5.08961f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(14, "Pathé Zaandam", 52.43842f, 4.81777f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(28, "Pathé Zwolle", 52.514f, 6.08677f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(29, "Pathé Ede", 52.01575f, 5.64801f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(31, "Pathé Nijmegen", 51.87994f, 5.862f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(32, "Tivoli en Cinema Leeuwarden", 53.20119f, 5.79235f, TIMEZONE_PATHE));
        allCinemas.add(new Cinema(34, "Cinema Hengelo", 52.2646f, 6.79098f, TIMEZONE_PATHE));
    }

    public static List<Cinema> getAllCinemaLocations(){
        return allCinemas;
    }

    public static Cinema getFirstById(int id) {
        return allCinemas.stream()
                .filter(cinema -> cinema.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No cinema exists for id " + id));
    }

}

