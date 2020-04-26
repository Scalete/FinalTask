package db.entity;

import java.io.Serializable;

/**
 * Station entity.
 *
 * @author M.Palaguta
 *
 */
public class Station implements Serializable {
    private int id;
    private String name;

    public Station(int id, String name) {
        this(name);
        this.id = id;
    }

    public Station(String name) {
        this.name = name;
    }

    public Station() {
        id = -1;
        name = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
