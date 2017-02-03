package com.smelly_mice_sim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by marcbrouard on 16/07/2014.
 */
public class SmellyMiceSim2 extends JFrame implements ActionListener {
  private FileWriter writer;

  JPanel pane = new JPanel();

  JSpinner mouseCaptureChanceControl;
  //    JSpinner mouseSameSpeciesMultiplierControl;
  JSpinner mouseSameSpeciesMultiplierStartControl;
  JSpinner mouseSameSpeciesMultiplierEndControl;
  JSpinner mouseSameSpeciesMultiplierIncControl;
  JSpinner mouseDifferentSpeciesMultiplierControl;

  JSpinner voleCaptureChanceControl;
  JSpinner voleSameSpeciesMultiplierControl;
  JSpinner voleDifferentSpeciesMultiplierControl;

  JSpinner trappingSessionsControl;
  JSpinner runsControl;
  JSpinner trapControl;
  JSpinner mouseControl;
  JSpinner voleControl;
  JButton runButton = new JButton("Run");
  JLabel doneLabel;

  SmellyMiceSim2() {
    super("Smelly Mouse Sim");
    setBounds(100, 100, 450, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container con = this.getContentPane();
    con.add(pane);

    mouseCaptureChanceControl = new JSpinner(new SpinnerNumberModel(20.0, 0.0, 100.0, 0.25));
//        mouseSameSpeciesMultiplierControl = new JSpinner(new SpinnerNumberModel(1.0,0.0,100.0,1.0));
    mouseSameSpeciesMultiplierStartControl = new JSpinner(new SpinnerNumberModel(0.1, 0.0, 100.0, 0.1));
    mouseSameSpeciesMultiplierEndControl = new JSpinner(new SpinnerNumberModel(70.0, 0.0, 100.0, 0.1));
    mouseSameSpeciesMultiplierIncControl = new JSpinner(new SpinnerNumberModel(0.1, 0.0, 100.0, 0.1));
    mouseDifferentSpeciesMultiplierControl = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 100.0, 1.0));

    voleCaptureChanceControl = new JSpinner(new SpinnerNumberModel(20.0, 0.0, 100.0, 0.25));
    voleSameSpeciesMultiplierControl = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 100.0, 1.0));
    voleDifferentSpeciesMultiplierControl = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 100.0, 1.0));

    trappingSessionsControl = new JSpinner(new SpinnerNumberModel(5, 0, 20, 1));
    runsControl = new JSpinner(new SpinnerNumberModel(1000, 20, 10000, 5));
    trapControl = new JSpinner(new SpinnerNumberModel(100, 10, 1000, 5));
    mouseControl = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 5));
    voleControl = new JSpinner(new SpinnerNumberModel(50, 1, 1000, 5));

    doneLabel = new JLabel("");

    pane.setLayout(new GridLayout(15, 2));

    pane.add(new JLabel(("Mouse capture chance")));
    pane.add(mouseCaptureChanceControl);

//        pane.add(new JLabel("Mouse same species multiplier"));
//        pane.add(mouseSameSpeciesMultiplierControl);

    pane.add(new JLabel("Mouse same species multiplier Start"));
    pane.add(mouseSameSpeciesMultiplierStartControl);
    pane.add(new JLabel("Mouse same species multiplier End"));
    pane.add(mouseSameSpeciesMultiplierEndControl);
    pane.add(new JLabel("Mouse same species multiplier Increment"));
    pane.add(mouseSameSpeciesMultiplierIncControl);


    pane.add(new JLabel("Mouse different species multiplier"));
    pane.add(mouseDifferentSpeciesMultiplierControl);

    pane.add(new JLabel(("Vole capture chance")));
    pane.add(voleCaptureChanceControl);
    pane.add(new JLabel("Vole same species multiplier"));
    pane.add(voleSameSpeciesMultiplierControl);
    pane.add(new JLabel("Vole different species multiplier"));
    pane.add(voleDifferentSpeciesMultiplierControl);

    pane.add(new JLabel("No. of Runs"));
    pane.add(runsControl);
    pane.add(new JLabel("Trapping Sessions"));
    pane.add(trappingSessionsControl);
    pane.add(new JLabel("No. of Traps"));
    pane.add(trapControl);
    pane.add(new JLabel("No. of Mice"));
    pane.add(mouseControl);
    pane.add(new JLabel("No. of Voles"));
    pane.add(voleControl);
    pane.add(runButton);
    pane.add(doneLabel);

    runButton.addActionListener(this);

    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    Object source = event.getSource();
    if (source == runButton) {
      // Choose file to save to
      JFileChooser fc = new JFileChooser();
      if (JFileChooser.APPROVE_OPTION == fc.showSaveDialog(this)) {
        String fileName = String.valueOf(fc.getSelectedFile());

        int runs = (Integer) runsControl.getValue();

        // write header of file
        try {
          writer = new FileWriter(fileName);
          writer.append("mouseSameSpeciesMultiplier");
          writer.append(',');
          writer.append("Captures");
          writer.append(',');
          writer.append("UniqueCaptures");
          writer.append(',');
          writer.append("UniqueMice");
          writer.append(',');
          writer.append("UniqueVoles");
          writer.append(',');
          writer.append("MouseCaptures");
          writer.append(',');
          writer.append("VoleCaptures");
          writer.append('\n');
        } catch (IOException e) {
          e.printStackTrace();
        }

        doneLabel.setText("Working");

        for (int i = 0; i < runs; i++) {
          conductTrapping();
        }

        doneLabel.setText("Done");

        try {
          writer.flush();
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public static void main(String[] args) {
    new SmellyMiceSim2();

  }

  private void conductTrapping() {
    // get the values for the runs from the interface
    // for mice
    try {
      this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

      double mouseCaptureChance = (Double) mouseCaptureChanceControl.getValue();
//        double mouseSameSpeciesMultiplier = (Double) mouseSameSpeciesMultiplierControl.getValue();
      double mouseSameSpeciesMultiplierStart = (Double) mouseSameSpeciesMultiplierStartControl.getValue();
      double mouseSameSpeciesMultiplierEnd = (Double) mouseSameSpeciesMultiplierEndControl.getValue();
      double mouseSameSpeciesMultiplierInc = (Double) mouseSameSpeciesMultiplierIncControl.getValue();
      double mouseDifferentSpeciesMultiplier = (Double) mouseDifferentSpeciesMultiplierControl.getValue();
      // for voles
      double voleCaptureChance = (Double) voleCaptureChanceControl.getValue();
      double voleSameSpeciesMultiplier = (Double) voleSameSpeciesMultiplierControl.getValue();
      double voleDifferentSpeciesMultiplier = (Double) voleDifferentSpeciesMultiplierControl.getValue();
      // for general parameters
      int trappingSessions = (Integer) trappingSessionsControl.getValue();
      int traps = (Integer) trapControl.getValue();
      int mice = (Integer) mouseControl.getValue();
      int voles = (Integer) voleControl.getValue();

      for (double mouseSameSpeciesMultiplier = mouseSameSpeciesMultiplierStart;
           mouseSameSpeciesMultiplier <= mouseSameSpeciesMultiplierEnd;
           mouseSameSpeciesMultiplier += mouseSameSpeciesMultiplierInc) {
        Trapping trapping = new Trapping();

        // add the mice to the population
        trapping.population().addMice(mice);
        trapping.population().setMouseCaptureChance(mouseCaptureChance);
        trapping.population().setMouseSameSpeciesMultiplier(mouseSameSpeciesMultiplier);
        trapping.population().setMouseDifferentSpeciesMultiplier(mouseDifferentSpeciesMultiplier);

        // add the voles to the population
        trapping.population().addVoles(voles);
        trapping.population().setVoleCaptureChance(voleCaptureChance);
        trapping.population().setVoleSameSpeciesMultiplier(voleSameSpeciesMultiplier);
        trapping.population().setVoleDifferentSpeciesMultiplier(voleDifferentSpeciesMultiplier);

        // add the traps
        trapping.addTraps(traps);

        trapping.doTrapping(trappingSessions);

        try {
          writer.append("" + mouseSameSpeciesMultiplier);
          writer.append(',');
          writer.append("" + trapping.getCaptures().size());
          writer.append(',');
          writer.append("" + trapping.getUniqueCaptures().size());
          writer.append(',');
          writer.append("" + (trapping.getUniqueMouseCaptures().size()));
          writer.append(',');
          writer.append("" + (trapping.getUniqueVoleCaptures().size()));
          writer.append(',');
          writer.append("" + (trapping.getMouseCaptures().size()));
          writer.append(',');
          writer.append("" + (trapping.getVoleCaptures().size()));
          writer.append('\n');
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    } finally {
      this.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

  }


}
