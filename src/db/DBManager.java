package db;

import db.entity.*;
import exeption.DBException;
import exeption.Messages;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * DB manager. Works with MYSQL DB. Only the required DAO methods are
 * defined!
 *
 * @author M.Palaguta
 *
 */
public class DBManager {
    private static DBManager instance;
    private static final String url = ResourceBundle.getBundle("app").getString("connection.url");

    private static final Logger LOG = Logger.getLogger(DBManager.class);

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    public static DBManager getInstance(){
        if(instance == null){
            synchronized (DBManager.class){
                instance = new DBManager();
            }
        }
        return instance;
    }

    // //////////////////////////////////////////////////////////
    // SQL queries
    // //////////////////////////////////////////////////////////

    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM user";

    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user WHERE id = ?";

    private static final String SQL_FIND_USER_BY_PASSWORD_AND_MAIL = "SELECT * FROM user WHERE email = ? AND password = ?";

    private static final String SQL_ADD_USER = "INSERT INTO user (first_name, last_name, password, email, tel) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_CHANGE_USER_COUNT = "UPDATE user SET count = ? WHERE id = ?";

    private static final String SQL_EDIT_USER_ACCOUNT = "UPDATE user SET first_name = ?,  last_name = ?, " +
            "email = ?, tel = ? WHERE id = ?";

    private static final String SQL_FIND_ALL_ROUTS = "SELECT * FROM rout;";

    private static final String SQL_ADD_ROUT = "INSERT INTO rout (id_departure_station, id_destination_station, " +
            "departure_date_time, destination_date_time) VALUES (?, ?, ?, ?)";

    private static final String SQL_EDIT_ROUT = "UPDATE rout SET id_departure_station = ?,  id_destination_station = ?, " +
            "departure_date_time = ?, destination_date_time = ? WHERE id = ?";

    private static final String SQL_DELETE_ROUT = "DELETE FROM rout where id = ?";

    private static final String SQL_FIND_ROUT_BY_ID = "SELECT * FROM rout WHERE id = ?";

    private static final String SQL_FIND_ALL_STATIONS = "SELECT id, station.name FROM station";

    private static final String SQL_ADD_STATION = "INSERT INTO station (station.name) VALUES (?)";

    private static final String SQL_DELETE_STATION = "DELETE FROM station WHERE id = ?";

    private static final String SQL_EDIT_STATION = "UPDATE station SET station.name = ? WHERE id = ?";

    private static final String SQL_FIND_STATION_BY_NAME = "SELECT * FROM station WHERE name = ?";

    private static final String SQL_FIND_STATION_BY_ID = "SELECT * FROM station WHERE id = ?";

    private static final String SQL_FIND_ALL_INTERMEDIATE_STATION_IN_ROUT = "SELECT * FROM intermediate_station i_s\n" +
            "WHERE i_s.id_rout = ? ORDER BY i_s.order ASC";

    private static final String SQL_FIND_INTERMEDIATE_STATION_BY_ID = "SELECT * FROM intermediate_station WHERE id = ?";

    private static final String SQL_ADD_INTERMEDIATE_STATION = "INSERT INTO intermediate_station " +
            "(intermediate_station.id_station, intermediate_station.stop_time, " +
            "intermediate_station.departure_time, intermediate_station.destination_time, " +
            "intermediate_station.order, intermediate_station.id_rout) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_EDIT_INTERMEDIATE_STATION = "UPDATE intermediate_station i_s SET " +
            "i_s.id_station = ?, i_s.stop_time = ?, i_s.departure_time = ?, i_s.destination_time = ?, i_s.id_rout = ?, " +
            "i_s.order = ? WHERE id = ?";

    private static final String SQL_DELETE_INTERMEDIATE_STATION = "DELETE FROM intermediate_station WHERE id = ?";

    private static final String SQL_FIND_TRIP = "SELECT tp.id, tp.id_rout, tp.id_train, t.num_train, " +
            "(SELECT station.name FROM station WHERE r.id_departure_station = station.id AND station.name = ?) AS departure_station,\n" +
            "(SELECT station.name FROM station WHERE r.id_destination_station = station.id AND station.name = ?) AS destination_station, " +
            "r.departure_date_time, r.destination_date_time, tp.price, TIMESTAMPDIFF(hour, r.departure_date_time, " +
            "r.destination_date_time) AS time_diff_hour, \n" +
            "TIMESTAMPDIFF(minute, r.departure_date_time, r.destination_date_time) AS time_diff_minute," +
            "(SELECT SUM(num_seats) FROM carriage c WHERE t.id = c.id_train AND id_place_type = 1) AS 'coupe',\n" +
            "(SELECT SUM(num_seats) FROM carriage c WHERE t.id = c.id_train AND id_place_type = 2) AS 'common',\n" +
            "(SELECT SUM(num_seats) FROM carriage c WHERE t.id = c.id_train AND id_place_type = 3) AS 'reserved_seat'\n" +
            "FROM rout r, train t, trip tp WHERE tp.id_rout = r.id AND tp.id_train = t.id AND (r.departure_date_time BETWEEN ? \n" +
            "AND ADDTIME(?, '08:00:00'));";

    private static final String SQL_FIND_TRIP_BY_ID = "SELECT tp.id, tp.id_rout, tp.id_train, t.num_train,\n" +
            "(SELECT station.name FROM station WHERE r.id_departure_station = station.id) AS departure_station,\n" +
            "(SELECT station.name FROM station WHERE r.id_destination_station = station.id) AS destination_station," +
            "r.departure_date_time, r.destination_date_time, tp.price,\n" +
            "TIMESTAMPDIFF(hour, r.departure_date_time, r.destination_date_time) AS time_diff_hour,\n" +
            "TIMESTAMPDIFF(minute, r.departure_date_time, r.destination_date_time) AS time_diff_minute\n" +
            "FROM rout r, train t, trip tp WHERE tp.id_rout = r.id AND tp.id_train = t.id AND tp.id = ?;";

    private static final String SQL_FIND_ALL_CARRIAGE_IN_TRAIN = "SELECT pt.place_type, c.num_seats, c.id\n" +
            "FROM carriage c, place_type pt\n" +
            "WHERE c.id_place_type = pt.id AND id_train = ?;";

    private static final String SQL_FIND_CARRIAGE_BY_ID = "SELECT pt.place_type, c.num_seats, c.id\n" +
            "FROM carriage c, place_type pt\n" +
            "WHERE c.id_place_type = pt.id AND c.id = ?;";

    private static final String SQL_BUY_SEAT_IN_CARRIAGE = "UPDATE carriage SET num_seats = ? WHERE id = ?";

    private static final String SQL_FIND_TICKET_BY_USER_ID = "SELECT *\n" +
            "FROM ticket\n" +
            "WHERE id_user = ?;";

    private static final String SQL_ADD_TICKET = "INSERT INTO ticket (id_user, id_trip, id_carriage) VALUES (?, ?, ?)";

    // //////////////////////////////////////////////////////////
    // Entity access methods
    // //////////////////////////////////////////////////////////

    /**
     * Returns all users.
     *
     * @return List of user entities.
     */
    public List<User> findAllUsers() throws DBException {
        List<User> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_USERS)) {
            while (resultSet.next()){
                list.add(setUser(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_USERS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_USERS,e);
        }
        return list;
    }

    /**
     * Returns all routes.
     *
     * @return List of rout entities.
     */
    public List<Rout> findAllRouts() throws DBException {
        List<Rout> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ROUTS)) {
            while (resultSet.next()){
                list.add(setRout(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_ROUTS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_ROUTS,e);
        }

        return list;
    }

    /**
     * Returns all stations.
     *
     * @return List of station entities.
     */
    public List<Station> findAllStation() throws DBException {
        List<Station> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_STATIONS)) {
            while (resultSet.next()){
                list.add(setStation(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_STATIONS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_STATIONS,e);
        }

        return list;
    }

    /**
     * Returns all intermediate stations in the given route.
     *
     * @param rout
     *            Route entity.
     * @return List of intermediate station entities.
     */
    public List<IntermediateStation> findAllIntermediateStationInRout(Rout rout) throws DBException {
        List<IntermediateStation> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_INTERMEDIATE_STATION_IN_ROUT)) {

            preparedStatement.setInt(1, rout.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    list.add(setIntermediateStation(resultSet));
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_STATIONS, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_STATIONS,e);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_STATIONS, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_STATIONS,e);
        }

        return list;
    }

    /**
     * Returns all carriages in the given train.
     *
     * @param train
     *            Train entity.
     * @return List of carriage entities.
     */
    public List<Carriage> findAllCarriageInTrain(Train train) throws DBException {
        List<Carriage> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_CARRIAGE_IN_TRAIN)) {

            preparedStatement.setInt(1, train.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    list.add(setCarriage(resultSet));
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_CARRIAGE_IN_TRAIN, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_CARRIAGE_IN_TRAIN,e);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_CARRIAGE_IN_TRAIN, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_CARRIAGE_IN_TRAIN,e);
        }

        return list;
    }

    /**
     * Returns all tickets in the given user.
     *
     * @param user
     *            User entity.
     * @return List of ticket entities.
     */
    public List<Ticket> findAllTicketsInUser(User user) throws DBException {
        List<Ticket> list = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_TICKET_BY_USER_ID)) {

            preparedStatement.setInt(1, user.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    list.add(setTicket(resultSet));
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_TICKETS_IN_USER, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_TICKETS_IN_USER,e);
            }

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_ALL_TICKETS_IN_USER, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ALL_TICKETS_IN_USER,e);
        }

        return list;
    }

    /**
     * Returns a user with the given identifier.
     *
     * @param id
     *            User identifier.
     * @return User entity.
     */
    public User findUserById(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_ID)){

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return setUser(resultSet);
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID,e);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID,e);
        }
        return null;
    }

    /**
     * Returns a route with the given identifier.
     *
     * @param id
     *            Route identifier.
     * @return Route entity.
     */
    public Rout findRoutById(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ROUT_BY_ID)) {

            preparedStatement.setInt(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    return setRout(resultSet);
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_ROUT_BY_ID,e);
            }
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_ROUT_BY_ID,e);
        }
        return null;
    }

    /**
     * Returns a station with the given identifier.
     *
     * @param id
     *            Station identifier.
     * @return Station entity.
     */
    public Station findStationById(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STATION_BY_ID)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return setStation(resultSet);
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_STATION_BY_ID, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_STATION_BY_ID,e);
            }

        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_STATION_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_STATION_BY_ID,e);
        }
        return null;
    }

    /**
     * Returns a intermediate station with the given identifier.
     *
     * @param id
     *            Intermediate station identifier.
     * @return IntermediateStation entity.
     */
    public IntermediateStation findIntermediateStationById(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_INTERMEDIATE_STATION_BY_ID)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return setIntermediateStation(resultSet);
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_INTERMEDIATE_STATION_BY_ID, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_INTERMEDIATE_STATION_BY_ID,e);
            }
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_INTERMEDIATE_STATION_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_INTERMEDIATE_STATION_BY_ID,e);
        }
        return null;
    }

    /**
     * Returns a trip with the given identifier.
     *
     * @param id
     *            Trip identifier.
     * @return Trip entity.
     */
    public Trip findTripById(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_TRIP_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return setTrip(resultSet, true);
                }
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ID, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ID,e);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ID,e);
        }
        return null;
    }

    /**
     * Returns a carriage with the given identifier.
     *
     * @param id
     *            Carriage identifier.
     * @return Carriage entity.
     */
    public Carriage findCarriageById(int id) throws DBException {

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_CARRIAGE_BY_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return setCarriage(resultSet);
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_CARRIAGE_BY_ID, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARRIAGE_BY_ID,e);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_CARRIAGE_BY_ID, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_CARRIAGE_BY_ID,e);
        }
        return null;
    }

    /**
     * Returns a station with the given name.
     *
     * @param name
     *            Station name.
     * @return Station entity.
     *
     */
    public Station findStationByName(String name) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STATION_BY_NAME)) {
            preparedStatement.setString(1, name);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()){
                    return setStation(resultSet);
                }
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_STATION_BY_NAME, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_STATION_BY_NAME,e);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_STATION_BY_NAME, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_STATION_BY_NAME,e);
        }
        return null;
    }

    /**
     * Returns trips of the given route.
     *
     * @param rout
     *            Route entity.
     * @return List of trip entities.
     */
    public List<Trip> findTripByRout(Rout rout) throws DBException {
        List<Trip> list = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_TRIP)) {
            preparedStatement.setString(1, rout.getDepartureStation().getName());
            preparedStatement.setString(2, rout.getDestinationStation().getName());
            preparedStatement.setString(3, rout.getDepartureDateTime());
            preparedStatement.setString(4, rout.getDepartureDateTime());
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    if (setTrip(resultSet, false) != null){
                        list.add(setTrip(resultSet, false));
                    }
                }
            } catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ROUT, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ROUT,e);
            }
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ROUT, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_TRIP_BY_ROUT,e);
        }
        return list;
    }

    /**
     * Return user by the given mail and password
     *
     * @param mail
     *            User entity.
     * @param password
     *            User entity.
     * @return User entity.
     */
    public User findUserByPasswordAndMail(String mail, String password) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_PASSWORD_AND_MAIL)){

            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    return setUser(resultSet);
                }
            }catch (SQLException e) {
                LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_PASSWORD_AND_MAIL, e);
                throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_PASSWORD_AND_MAIL,e);
            }
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_OBTAIN_USER_BY_PASSWORD_AND_MAIL, e);
            throw new DBException(Messages.ERR_CANNOT_OBTAIN_USER_BY_PASSWORD_AND_MAIL,e);
        }
        return null;
    }

    /**
     * Add user.
     *
     * @param user
     *            user to add.
     */
    public void addUser(User user) throws DBException {

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getTelephone());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_ADD_USER, e);
            throw new DBException(Messages.ERR_CANNOT_ADD_USER,e);
        }
    }

    /**
     * Add route.
     *
     * @param rout
     *            route to add.
     */
    public void addRout(Rout rout) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_ROUT)) {

            preparedStatement.setInt(1, rout.getDepartureStation().getId());
            preparedStatement.setInt(2, rout.getDestinationStation().getId());
            preparedStatement.setString(3, rout.getDepartureDateTime());
            preparedStatement.setString(4, rout.getDestinationDateTime());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_ADD_ROUT, e);
            throw new DBException(Messages.ERR_CANNOT_ADD_ROUT,e);
        }
    }

    /**
     * Add station.
     *
     * @param station
     *            station to add.
     */
    public void addStation(Station station) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_STATION)) {
            preparedStatement.setString(1, station.getName());

            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_ADD_STATION, e);
            throw new DBException(Messages.ERR_CANNOT_ADD_STATION,e);
        }
    }

    /**
     * Add intermediateStation.
     *
     * @param station
     *            intermediate station to add.
     */
    public void addIntermediateStation(IntermediateStation station) throws DBException {
        int idIntermediateStation = findStationByName(station.getStation().getName()).getId();

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_INTERMEDIATE_STATION)) {

            preparedStatement.setInt(1, idIntermediateStation);
            preparedStatement.setString(2, station.getStopTime());
            preparedStatement.setString(3, station.getDepartureTime());
            preparedStatement.setString(4, station.getDestinationTime());
            preparedStatement.setInt(5, station.getOrder());
            preparedStatement.setInt(6, station.getRout().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_ADD_INTERMEDIATE_STATION, e);
            throw new DBException(Messages.ERR_CANNOT_ADD_INTERMEDIATE_STATION,e);
        }
    }

    /**
     * Add ticket.
     *
     * @param ticket
     *            ticket to add.
     */
    public void addTicket(Ticket ticket) throws DBException {

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_TICKET)) {

            preparedStatement.setInt(1, ticket.getUser().getId());
            preparedStatement.setInt(2, ticket.getTrip().getId());
            preparedStatement.setInt(3, ticket.getCarriage().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_ADD_TICKET, e);
            throw new DBException(Messages.ERR_CANNOT_ADD_TICKET,e);
        }
    }

    /**
     * Edit user account.
     *
     * @param user
     *            user to edit.
     */
    public void editUserAccount(User user) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_USER_ACCOUNT)){

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getTelephone());
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_EDIT_USER, e);
            throw new DBException(Messages.ERR_CANNOT_EDIT_USER,e);
        }
    }

    /**
     * Edit route.
     *
     * @param rout
     *            route to edit.
     */
    public void editRout(Rout rout) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_ROUT)){

            preparedStatement.setInt(1, rout.getDepartureStation().getId());
            preparedStatement.setInt(2, rout.getDestinationStation().getId());
            preparedStatement.setString(3, rout.getDepartureDateTime());
            preparedStatement.setString(4, rout.getDestinationDateTime());
            preparedStatement.setInt(5, rout.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_EDIT_ROUT, e);
            throw new DBException(Messages.ERR_CANNOT_EDIT_ROUT,e);
        }
    }

    /**
     * Edit station.
     *
     * @param station
     *            station to edit.
     */
    public void editStation(Station station) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_STATION)){

            preparedStatement.setString(1, station.getName());
            preparedStatement.setInt(2, station.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_EDIT_STATION, e);
            throw new DBException(Messages.ERR_CANNOT_EDIT_STATION,e);
        }
    }

    /**
     * Edit intermediate station.
     *
     * @param station
     *            intermediate station to edit.
     */
    public void editIntermediateStation(IntermediateStation station) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_EDIT_INTERMEDIATE_STATION)){

            preparedStatement.setInt(1, station.getStation().getId());
            preparedStatement.setString(2, station.getStopTime());
            preparedStatement.setString(3, station.getDepartureTime());
            preparedStatement.setString(4, station.getDestinationTime());
            preparedStatement.setInt(5, station.getRout().getId());
            preparedStatement.setInt(6, station.getOrder());
            preparedStatement.setInt(7, station.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_EDIT_INTERMEDIATE_STATION, e);
            throw new DBException(Messages.ERR_CANNOT_EDIT_INTERMEDIATE_STATION,e);
        }
    }

    /**
     * Change user count by user and cost
     *
     * @param user
     *            User entity.
     * @param cost
     *            Trip cost.
     */
    public void changeUserCount(User user, double cost) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_USER_COUNT)){

            preparedStatement.setDouble(1, user.getCount() - cost);
            preparedStatement.setInt(2, user.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_EDIT_USER_COUNT, e);
            throw new DBException(Messages.ERR_CANNOT_EDIT_USER_COUNT,e);
        }
    }

    /**
     * Delete route with the given identifier.
     *
     * @param id
     *            Route identifier.
     */
    public void deleteRout(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ROUT)) {

            preparedStatement.setInt(1,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_ROUT, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_ROUT,e);
        }
    }

    /**
     * Delete station with the given identifier.
     *
     * @param id
     *            Station identifier.
     */
    public void deleteStation(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_STATION)) {

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }  catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_STATION, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_STATION,e);
        }
    }

    /**
     * Delete intermediate station with the given identifier.
     *
     * @param id
     *            IntermediateStation identifier.
     */
    public void deleteIntermediateStation(int id) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_INTERMEDIATE_STATION)) {
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }  catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_DELETE_INTERMEDIATE_STATION, e);
            throw new DBException(Messages.ERR_CANNOT_DELETE_INTERMEDIATE_STATION,e);
        }
    }

    // //////////////////////////////////////////////////////////
    // Other methods
    // //////////////////////////////////////////////////////////

    /**
     * Extracts a user entity from the result set.
     *
     * @param resultSet
     *            Result set from which a user entity will be extracted.
     * @return User entity
     */
    private User setUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(Fields.ENTITY_ID));
        user.setPassword(resultSet.getString(Fields.USER_PASSWORD));
        user.setEmail(resultSet.getString(Fields.USER_EMAIL));
        user.setCount(resultSet.getDouble(Fields.USER_COUNT));
        user.setTelephone(resultSet.getString(Fields.USER_TELEPHONE));
        user.setFirstName(resultSet.getString(Fields.USER_FIRST_NAME));
        user.setLastName(resultSet.getString(Fields.USER_LAST_NAME));
        return user;
    }

    /**
     * Extracts a route entity from the result set.
     *
     * @param resultSet
     *            Result set from which a route entity will be extracted.
     * @return Rout entity
     */
    private Rout setRout(ResultSet resultSet) throws SQLException, DBException {
        Rout rout = new Rout();
        rout.setId(resultSet.getInt(Fields.ENTITY_ID));
        rout.setDepartureStation(findStationById(resultSet.getInt(Fields.ID_DEPARTURE_STATION)));
        rout.setDestinationStation(findStationById(resultSet.getInt(Fields.ID_DESTINATION_STATION)));
        rout.setDepartureDateTime(resultSet.getString(Fields.ROUT_DEPARTURE_DATE_TIME));
        rout.setDestinationDateTime(resultSet.getString(Fields.ROUT_DESTINATION_DATE_TIME));
        return rout;
    }

    /**
     * Extracts a station entity from the result set.
     *
     * @param resultSet
     *            Result set from which a station entity will be extracted.
     * @return Station entity
     */
    private Station setStation(ResultSet resultSet) throws SQLException {
        Station station = new Station();
        station.setId(resultSet.getInt(Fields.ENTITY_ID));
        station.setName(resultSet.getString(Fields.STATION_NAME));
        return station;
    }

    /**
     * Extracts an intermediate station entity from the result set.
     *
     * @param resultSet
     *            Result set from which an intermediate station entity will be extracted.
     * @return IntermediateStation entity
     */
    private IntermediateStation setIntermediateStation(ResultSet resultSet) throws SQLException, DBException {
        IntermediateStation station = new IntermediateStation();
        station.setId(resultSet.getInt(Fields.ENTITY_ID));
        station.setStation(findStationById(resultSet.getInt(Fields.ID_STATION)));
        station.setStopTime(resultSet.getString(Fields.INTERMEDIATE_STATION_STOP_TIME));
        station.setDepartureTime(resultSet.getString(Fields.INTERMEDIATE_STATION_DEPARTURE_TIME));
        station.setDestinationTime(resultSet.getString(Fields.INTERMEDIATE_STATION_DESTINATION_TIME));
        station.setRout(findRoutById(resultSet.getInt(Fields.ID_ROUT)));
        station.setOrder(resultSet.getInt(Fields.INTERMEDIATE_STATION_ORDER));
        return station;
    }

    /**
     * Return a trip with the given resultSet.
     *
     * @param resultSet
     *            ResultSet set.
     * @param isIdTrip
     *            boolean isIdTrip.
     * @return Trip entity.
     */
    private Trip setTrip(ResultSet resultSet, boolean isIdTrip) throws SQLException, DBException {
        Rout rout = new Rout();
        Train train = new Train();
        int idTrip = resultSet.getInt(Fields.ENTITY_ID);

        if(!isIdTrip){
            train.setSumCoupe(resultSet.getInt(Fields.OWN_COUPE));
            train.setSumCommon(resultSet.getInt(Fields.OWN_COMMON));
            train.setSumReservedSeat(resultSet.getInt(Fields.OWN_RESERVED_SEAT));
        }

        train.setId(resultSet.getInt(Fields.ID_TRAIN));
        train.setNumTrain(resultSet.getString(Fields.TRAIN_NUMBER));
        train.setCarriages(findAllCarriageInTrain(train));


        rout.setId(resultSet.getInt(Fields.ID_ROUT));
        rout.setDepartureStation(findStationByName(resultSet.getString(Fields.OWN_DEPARTURE_STATION)));
        rout.setDestinationStation(findStationByName(resultSet.getString(Fields.OWN_DESTINATION_STATION)));
        rout.setDepartureDateTime(resultSet.getString(Fields.ROUT_DEPARTURE_DATE_TIME));
        rout.setDestinationDateTime(resultSet.getString(Fields.ROUT_DESTINATION_DATE_TIME));
        rout.setTimeDiff(
                resultSet.getInt(Fields.OWN_TIME_DIFF_HOUR) == 0?
                        resultSet.getInt(Fields.OWN_TIME_DIFF_MINUTE) + " м.":
                        resultSet.getInt(Fields.OWN_TIME_DIFF_HOUR) + " ч."
        );

        if(IsTripNull(rout)){
            return null;
        }
        return new Trip(idTrip, rout, train, resultSet.getDouble(Fields.TRIP_PRICE));
    }

    /**
     * Extracts a carriage entity from the result set.
     *
     * @param resultSet
     *            Result set from which a carriage entity will be extracted.
     * @return Carriage entity
     */
    private Carriage setCarriage(ResultSet resultSet) throws SQLException {
        Carriage carriage = new Carriage();
        carriage.setId(resultSet.getInt(Fields.ENTITY_ID));
        carriage.setNumSeats(resultSet.getInt(Fields.CARRIAGE_NUM_SEATS));
        carriage.setPlaceType(resultSet.getString(Fields.PLACE_TYPE));
        return carriage;
    }

    /**
     * Extracts a ticket entity from the result set.
     *
     * @param resultSet
     *            Result set from which a ticket entity will be extracted.
     * @return Ticket entity
     */
    private Ticket setTicket(ResultSet resultSet) throws SQLException, DBException {
        Ticket ticket = new Ticket();
        ticket.setUser(findUserById(resultSet.getInt("id_user")));
        ticket.setTrip(findTripById(resultSet.getInt("id_trip")));
        ticket.setCarriage(findCarriageById(resultSet.getInt("id_carriage")));
        return ticket;
    }

    // //////////////////////////////////////////////////////////
    // Check functions
    // //////////////////////////////////////////////////////////

    /**
     * Check user duplicate by email
     *
     * @param email
     *            User email.
     * @return boolean value.
     */
    public boolean checkUserDuplicate(String email) throws DBException {
        List<User> users = findAllUsers();
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check route duplicate by rout
     *
     * @param rout
     *            Rout rout.
     * @return boolean value.
     */
    public boolean isDuplicateRout(Rout rout) throws DBException {
        List<Rout> routList = findAllRouts();
        for(int i = 0; i < routList.size(); i++){
            if(routList.get(i).getDepartureStation().getName().equals(rout.getDepartureStation().getName()) &&
                    routList.get(i).getDestinationStation().getName().equals(rout.getDestinationStation().getName()) &&
                    routList.get(i).getDepartureDateTime().substring(0,
                            routList.get(i).getDepartureDateTime().length() - 3).equals(rout.getDepartureDateTime()) &&
                    routList.get(i).getDestinationDateTime().substring(0,
                            routList.get(i).getDestinationDateTime().length() - 3).equals(rout.getDestinationDateTime())){
                return true;
            }
        }
        return false;
    }

    /**
     * Check station duplicate by station
     *
     * @param station
     *            Station station.
     * @return boolean value.
     */
    public boolean isDuplicateStation(Station station) throws DBException {
        List<Station> stationList = findAllStation();
        for(int i = 0; i < stationList.size(); i++){
            if(stationList.get(i).getName().equals(station.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Check trip null by rout
     *
     * @param rout
     *            Rout rout.
     * @return boolean value.
     */
    private boolean IsTripNull(Rout rout){
        if(rout.getDepartureStation() == null || rout.getDestinationStation() == null
                || rout.getDepartureDateTime() == null){
            return true;
        }
        return false;
    }

    //OPERATIONS

    /**
     * Calculate intermediate stations order with the given identifier
     *
     * @param idRout
     *            Rout rout.
     * @return int value.
     */
    public int calculateIntermediateStationOrder(int idRout) throws DBException {
        Rout rout = new Rout();
        rout.setId(idRout);
        List<IntermediateStation> stations = findAllIntermediateStationInRout(rout);

        return stations.size() + 1;
    }

    /**
     * Buy seat in carriage
     *
     * @param carriage
     *            Carriage carriage.
     */
    public void buySeatInCarriage(Carriage carriage) throws DBException {
        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_BUY_SEAT_IN_CARRIAGE)){

            preparedStatement.setInt(1, carriage.getNumSeats());
            preparedStatement.setInt(2, carriage.getId());
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            LOG.error(Messages.ERR_CANNOT_BUY_SEAT_IN_CARRIAGE, e);
            throw new DBException(Messages.ERR_CANNOT_BUY_SEAT_IN_CARRIAGE,e);
        }
    }
}
