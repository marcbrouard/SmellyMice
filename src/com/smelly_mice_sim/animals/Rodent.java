package com.smelly_mice_sim.animals;

/**
 * Created by marcbrouard on 16/07/2014.
 */
public class Rodent {
    private static long idCounter = 0;

    private long id;
    private boolean available;


    public Rodent() {
        id = idCounter++;
        available = true;
    }

    public long getId() {
        return id;
    }

    public String getSpecies() {
        return "Rodent";
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
