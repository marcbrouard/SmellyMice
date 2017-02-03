package com.smelly_mice_sim;

import com.smelly_mice_sim.animals.Mouse;
import com.smelly_mice_sim.animals.Rodent;
import com.smelly_mice_sim.animals.Vole;

import java.security.InvalidParameterException;
import java.util.*;

/**
 * Created by marcbrouard on 16/07/2014.
 */
public class Population {

  private int voleCount = 0;

  public int getVoleCount() {
    return voleCount;
  }

  private double voleCaptureChance = 0;

  public double getVoleCaptureChance() {
    return voleCaptureChance;
  }

  public void setVoleCaptureChance(double voleCaptureChance) {
    this.voleCaptureChance = voleCaptureChance;
  }

  private double voleSameSpeciesMultiplier = 0;

  public double getVoleSameSpeciesMultiplier() {
    return voleSameSpeciesMultiplier;
  }

  public void setVoleSameSpeciesMultiplier(double voleSameSpeciesMultiplier) {
    this.voleSameSpeciesMultiplier = voleSameSpeciesMultiplier;
  }

  private double voleDifferentSpeciesMultiplier = 0;

  public double getVoleDifferentSpeciesMultiplier() {
    return voleDifferentSpeciesMultiplier;
  }

  public void setVoleDifferentSpeciesMultiplier(double voleDifferentSpeciesMultiplier) {
    this.voleDifferentSpeciesMultiplier = voleDifferentSpeciesMultiplier;
  }

  private int mouseCount = 0;

  public int getMouseCount() {
    return mouseCount;
  }

  private double mouseCaptureChance = 0;

  public double getMouseCaptureChance() {
    return mouseCaptureChance;
  }

  public void setMouseCaptureChance(double mouseCaptureChance) {
    this.mouseCaptureChance = mouseCaptureChance;
  }

  private double mouseSameSpeciesMultiplier = 0;

  public double getMouseSameSpeciesMultiplier() {
    return mouseSameSpeciesMultiplier;
  }

  public void setMouseSameSpeciesMultiplier(double mouseSameSpeciesMultiplier) {
    this.mouseSameSpeciesMultiplier = mouseSameSpeciesMultiplier;
  }

  private double mouseDifferentSpeciesMultiplier = 0;

  public double getMouseDifferentSpeciesMultiplier() {
    return mouseDifferentSpeciesMultiplier;
  }

  public void setMouseDifferentSpeciesMultiplier(double mouseDifferentSpeciesMultiplier) {
    this.mouseDifferentSpeciesMultiplier = mouseDifferentSpeciesMultiplier;
  }

  private Set<Rodent> Individuals = new HashSet<>();

  public void addMice(int count) {
    for (int i = 0; i < count; i++) {
      Individuals.add(new Mouse());
      mouseCount++;
    }
  }

  public void addVoles(int count) {
    for (int i = 0; i < count; i++) {
      Individuals.add(new Vole());
      voleCount++;
    }
  }

  public void clearAvailability() {
    Individuals.stream()
      .forEach(e -> e.setAvailable(true));
  }

  public Rodent getCapture(Rodent prevCapture) {
    Random rand = new Random();
    double captureChance;

    TreeMap<Double, Rodent> tm = new TreeMap<>();
    double count = 0.0;

    for (Rodent ind : Individuals)
      if (ind.getAvailable()) {
        captureChance = getIndCaptureChance(prevCapture, ind);
        if (captureChance > 0) {
          tm.put(count, ind);
          count += captureChance;
        }
      }

    double num = rand.nextDouble() * 100;
    if (num > count) return null;

    try {
      return tm.get(tm.lowerEntry(num).getKey());
    } catch (NullPointerException e) {
      return null;
    }
  }

  private double getIndCaptureChance(Rodent prevCapture, Rodent currentRodent) {
    if (currentRodent.getSpecies().equals("Mouse")) {
      if (prevCapture == null) return mouseCaptureChance / mouseCount;
      // check for matching class of previous occupant and this object
      return prevCapture.getClass().equals(currentRodent.getClass()) ? ((mouseCaptureChance * mouseSameSpeciesMultiplier) / mouseCount) : ((mouseCaptureChance * mouseDifferentSpeciesMultiplier) / mouseCount);
    } else if (currentRodent.getSpecies().equals("Vole")) {
      if (prevCapture == null) return voleCaptureChance / voleCount;
      // check for matching class of previous occupant and this object
      return prevCapture.getClass().equals(currentRodent.getClass()) ? ((voleCaptureChance * voleSameSpeciesMultiplier) / voleCount) : ((voleCaptureChance * voleDifferentSpeciesMultiplier) / voleCount);
    } else throw new InvalidParameterException("current Rodent is invalid");
  }
}
