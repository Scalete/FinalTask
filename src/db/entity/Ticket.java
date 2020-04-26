package db.entity;

import java.io.Serializable;

/**
 * Ticket entity.
 *
 * @author M.Palaguta
 *
 */
public class Ticket implements Serializable {

    private int id;
    private User user;
    private Trip trip;
    private Carriage carriage;


    public Ticket(User user, Trip trip, Carriage carriage) {
        this.user = user;
        this.trip = trip;
        this.carriage = carriage;
    }

    public Ticket(int id, User user, Trip trip, Carriage carriage) {
        this(user, trip, carriage);
        this.id = id;
    }

    public Ticket() {
        id = 0;
        user = new User();
        trip = new Trip();
        carriage = new Carriage();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }
}
