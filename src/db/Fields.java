package db;


/**
 * Holder for fields names of DB tables and beans.
 *
 * @author M.Palaguta
 *
 */
public final class Fields {

    // entities
    public static final String ENTITY_ID = "id";

    public static final String ID_TRAIN = "id_train";
    public static final String ID_ROUT = "id_rout";
    public static final String ID_USER = "id_user";
    public static final String ID_TRIP = "id_trip";
    public static final String ID_STATION = "id_station";
    public static final String ID_DEPARTURE_STATION = "id_departure_station";
    public static final String ID_DESTINATION_STATION = "id_destination_station";

    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_TELEPHONE = "tel";
    public static final String USER_COUNT = "count";

    public static final String ROUT_ID_DEPARTURE_STATION = "id_departure_station";
    public static final String ROUT_ID_DESTINATION_STATION = "id_destination_station";
    public static final String ROUT_DEPARTURE_DATE_TIME= "departure_date_time";
    public static final String ROUT_DESTINATION_DATE_TIME = "destination_date_time";

    public static final String STATION_NAME = "name";

    public static final String INTERMEDIATE_STATION_ID_STATION = "id_station";
    public static final String INTERMEDIATE_STATION_STOP_TIME = "stop_time";
    public static final String INTERMEDIATE_STATION_DEPARTURE_TIME = "departure_time";
    public static final String INTERMEDIATE_STATION_DESTINATION_TIME = "destination_time";
    public static final String INTERMEDIATE_STATION_ORDER = "order";

    public static final String TRAIN_NUMBER = "num_train";

    public static final String CARRIAGE_ID_PLACE_TYPE = "id_place_type";
    public static final String CARRIAGE_NUM_SEATS = "num_seats";

    public static final String PLACE_TYPE = "place_type";

    public static final String TRIP_PRICE = "price";

    // own fields

    public static final String OWN_DEPARTURE_STATION = "departure_station";
    public static final String OWN_DESTINATION_STATION = "destination_station";
    public static final String OWN_COUPE = "coupe";
    public static final String OWN_COMMON = "common";
    public static final String OWN_RESERVED_SEAT = "reserved_seat";
    public static final String OWN_TIME_DIFF_HOUR = "time_diff_hour";
    public static final String OWN_TIME_DIFF_MINUTE = "time_diff_minute";

}
