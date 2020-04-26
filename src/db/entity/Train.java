package db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Train entity.
 *
 * @author M.Palaguta
 *
 */
public class Train implements Serializable {

    private int id;
    private String numTrain;
    private List<Carriage> carriages;
    private int sumCoupe;
    private int sumCommon;
    private int sumReservedSeat;


    public Train(int id, String numTrain, List<Carriage> carriages, int sumCoupe, int sumCommon, int sumReservedSeat) {
        this.id = id;
        this.numTrain = numTrain;
        this.carriages = carriages;
        this.sumCoupe = sumCoupe;
        this.sumCommon = sumCommon;
        this.sumReservedSeat = sumReservedSeat;
    }

    public Train() {
        id = -1;
        numTrain = "";
        carriages = new ArrayList<>();
        sumCommon = -1;
        sumCoupe = -1;
        sumReservedSeat = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumTrain() {
        return numTrain;
    }

    public void setNumTrain(String numTrain) {
        this.numTrain = numTrain;
    }

    public List<Carriage> getCarriages() {
        return carriages;
    }

    public void setCarriages(List<Carriage> carriages) {
        this.carriages = carriages;
    }

    public int getSumCoupe() {
        return sumCoupe;
    }

    public void setSumCoupe(int sumCoupe) {
        this.sumCoupe = sumCoupe;
    }

    public int getSumCommon() {
        return sumCommon;
    }

    public void setSumCommon(int sumCommon) {
        this.sumCommon = sumCommon;
    }

    public int getSumReservedSeat() {
        return sumReservedSeat;
    }

    public void setSumReservedSeat(int sumReservedSeat) {
        this.sumReservedSeat = sumReservedSeat;
    }
}
