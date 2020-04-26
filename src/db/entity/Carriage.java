package db.entity;

import java.io.Serializable;

/**
 * Carriage entity.
 *
 * @author M.Palaguta
 *
 */
public class Carriage implements Serializable {

    private int id;
    private String placeType;
    private int numSeats;
    private Train train;

    public Carriage(int id, String placeType, int numSeats, Train train) {
        this.id = id;
        this.placeType = placeType;
        this.numSeats = numSeats;
        this.train = train;
    }

    public Carriage() {
        id = -1;
        placeType = "";
        numSeats = 0;
        train = new Train();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaceType() {
        return placeType;
    }

    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }
}
