package com.smelly_mice_sim;

import com.smelly_mice_sim.animals.Rodent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcbrouard on 18/07/2014.
 */
public class Trap {
    private List<Rodent> prevOccupantHistory = new ArrayList<>();

    public void addCapture(Rodent ind) {
        if (ind != null) {
            prevOccupantHistory.add(ind);
            ind.setAvailable(false);
        }
    }

    public Rodent getLastOccupant() {
        if (prevOccupantHistory.size() == 0) return null;
        return prevOccupantHistory.get(prevOccupantHistory.size()-1);
    }
}
