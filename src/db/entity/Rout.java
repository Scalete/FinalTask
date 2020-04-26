package db.entity;

import java.io.Serializable;

/**
 * Rout entity.
 *
 * @author M.Palaguta
 *
 */
public class Rout implements Serializable {

    private int id;
    private Station departureStation;
    private Station destinationStation;
    private String departureDateTime;
    private String destinationDateTime;
    private String timeDiff;

    public Rout(int id, Station departureStation, Station destinationStation, String departureDateTime, String destinationDateTime, String timeDiff) {
        this.id = id;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.departureDateTime = departureDateTime;
        this.destinationDateTime = destinationDateTime;
        this.timeDiff = timeDiff;
    }

    public Rout() {
        id = -1;
        departureStation = new Station();
        destinationStation = new Station();
        departureDateTime = "";
        destinationDateTime ="";
        timeDiff = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(Station departureStation) {
        this.departureStation = departureStation;
    }

    public Station getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(Station destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getDestinationDateTime() {
        return destinationDateTime;
    }

    public void setDestinationDateTime(String destinationDateTime) {
        this.destinationDateTime = destinationDateTime;
    }

    public String getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(String timeDiff) {
        this.timeDiff = timeDiff;
    }
}
