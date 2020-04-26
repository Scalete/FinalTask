package db.entity;

import java.io.Serializable;

/**
 * Trip entity.
 *
 * @author M.Palaguta
 *
 */
public class Trip implements Serializable {

    private int id;
    private Rout rout;
    private Train train;
    private double price;

    public Trip(int id, Rout rout, Train train, double price) {
        this.id = id;
        this.rout = rout;
        this.train = train;
        this.price = price;
    }

    public Trip() {
        rout = new Rout();
        train = new Train();
        price = 0.0;
        id = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Rout getRout() {
        return rout;
    }

    public void setRout(Rout rout) {
        this.rout = rout;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
