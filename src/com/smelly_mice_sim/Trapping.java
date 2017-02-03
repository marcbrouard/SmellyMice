package com.smelly_mice_sim;

import com.smelly_mice_sim.animals.Mouse;
import com.smelly_mice_sim.animals.Rodent;
import com.smelly_mice_sim.animals.Vole;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by marcbrouard on 18/07/2014.
 */
public class Trapping {
    private List<Trap> traps = new ArrayList<>();
    private Population pop = new Population();
    private List<Rodent> captures = new ArrayList<>();

    public void addTraps(int count) {
        for (int i = 0; i < count; i++) traps.add(new Trap());
    }

    public Population population() {
        return pop;
    }

    public void doTrapping(int trapNights) {
        for (int trapNight = 0; trapNight < trapNights; trapNight++) {
            pop.clearAvailability();

            for (Trap t : traps) {
                Rodent prev =  t.getLastOccupant();
                Rodent capture = pop.getCapture(prev);
                t.addCapture(capture);
                if (capture != null) captures.add(capture);
            }
        }
    }

    public List<Rodent> getCaptures() {
        return captures;
    }

    public List<Rodent> getUniqueCaptures() {
        return new ArrayList<>(new HashSet<>(captures));
    }

    public List<Mouse> getUniqueMouseCaptures() {
        return getUniqueCaptures().stream().filter(r -> r instanceof Mouse).map(r -> (Mouse) r).collect(Collectors.toList());
    }

    public List<Vole> getUniqueVoleCaptures() {
        return getUniqueCaptures().stream().filter(r -> r instanceof Vole).map(r -> (Vole) r).collect(Collectors.toList());
    }

    public List<Mouse> getMouseCaptures() {
        return getCaptures().stream().filter(r -> r instanceof Mouse).map(r -> (Mouse) r).collect(Collectors.toList());
    }

    public List<Vole> getVoleCaptures() {
        return getCaptures().stream().filter(r -> r instanceof Vole).map(r -> (Vole) r).collect(Collectors.toList());
    }
}
