package db.entity;

import java.io.Serializable;

/**
 * IntermediateStation entity.
 *
 * @author M.Palaguta
 *
 */
public class IntermediateStation implements Serializable {

    private int id;
    private Station station;
    private String stopTime;
    private String departureTime;
    private String destinationTime;
    private int order;
    private Rout rout;

    public IntermediateStation() {
        id = 0;
        stopTime = "";
        departureTime = "";
        destinationTime = "";
        station = new Station();
        order = 0;
        rout = new Rout();
    }

    public IntermediateStation(int id, Station station, String stopTime, String departureTime, String destinationTime, int order, Rout rout) {
        this.id = id;
        this.station = station;
        this.stopTime = stopTime;
        this.departureTime = departureTime;
        this.destinationTime = destinationTime;
        this.order = order;
        this.rout = rout;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Rout getRout() {
        return rout;
    }

    public void setRout(Rout rout) {
        this.rout = rout;
    }
}
