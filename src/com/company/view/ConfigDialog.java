package com.company.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigDialog extends JDialog {

    private JButton submitButton;
    private JButton cancelButton;
   // private JSpinner sessionSpinner;
    private JSpinner breaksSpinner;
   private SpinnerNumberModel spinnerBreaksNumberModel;
    private ConfigurationListener configuration;

    public ConfigDialog(JFrame parent){
        super(parent, "Configuration", false);
        submitButton = new JButton("appliquer");
        cancelButton = new JButton("Annuler");

        new SpinnerNumberModel(60, 30, 90, 5);
        //sessionSpinner = new JSpinner(spinnerSessionsNumberModel);
        spinnerBreaksNumberModel = new SpinnerNumberModel(5, 5, 30, 1);
        breaksSpinner = new JSpinner(spinnerBreaksNumberModel);
        new SpinnerNumberModel(8, 5, 40, 1);

        layoutControls();

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // Integer sessionLength = (Integer) sessionSpinner.getValue();
                Integer breakLength = (Integer) breaksSpinner.getValue();
               // Integer numberOfSessionPerDay = (Integer) sessionsPerDayNumber.getValue();
                setVisible(false);
                if(configuration != null){
                    configuration.configurationSet(breakLength);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setSize(340, 250);
        setLocationRelativeTo(null);
    }

    public void setConfigurationListener(ConfigurationListener configuration){
        this.configuration = configuration;
    }

    public void setDefaults(int sessionLength, int breakLength, int numberOfSessions){
       // sessionSpinner.setValue(sessionLength);
        breaksSpinner.setValue(breakLength);
        //sessionsPerDayNumber.setValue(numberOfSessions);
    }

    private void layoutControls(){

        JPanel controlsPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        int space = 15;
        Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);
        Border titleBorder = BorderFactory.createTitledBorder("Configurer votre session");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titleBorder));
        // buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gb = new GridBagConstraints();

        gb.weightx = 1;
        gb.weighty = 1;
        // do not resize the component
        gb.fill = GridBagConstraints.NONE;
        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.EAST;
        gb.insets = new Insets(0, 0, 0, 15);
        controlsPanel.add(new JLabel("Session (minutes): "), gb);

        gb.gridx = 1;
        gb.anchor = GridBagConstraints.WEST;
        gb.insets = new Insets(0, 0, 0, 0);
        //controlsPanel.add(sessionSpinner, gb);
        //////// SECOND ROW ////////////////
        gb.weightx = 1;
        gb.weighty = 1;
        // do not resize the component
        gb.fill = GridBagConstraints.NONE;
        gb.gridx = 0;
        gb.gridy = 1;
        gb.anchor = GridBagConstraints.EAST;
        gb.insets = new Insets(0, 0, 0, 15);
        controlsPanel.add(new JLabel("Durée de la pause (minutes)"), gb);

        gb.gridx = 1;
        gb.anchor = GridBagConstraints.WEST;
        gb.insets = new Insets(0, 0, 0, 0);
        controlsPanel.add(breaksSpinner, gb);

        //////// THIRD ROW ////////////////
        gb.weightx = 1;
        gb.weighty = 1;
        // do not resize the component
        gb.fill = GridBagConstraints.NONE;
        gb.gridx = 0;
        gb.gridy = 2;
        gb.anchor = GridBagConstraints.EAST;
        gb.insets = new Insets(0, 0, 0, 15);
        controlsPanel.add(new JLabel("Nombre de sessions par jour"), gb);

        gb.gridx = 1;
        gb.anchor = GridBagConstraints.WEST;
        gb.insets = new Insets(0, 0, 0, 0);
        //controlsPanel.add(sessionsPerDayNumber, gb);

        ///////////Buttons panel/////////////////
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);

        Dimension dimension = submitButton.getPreferredSize();
        cancelButton.setPreferredSize(dimension);


        //////////// Adding panels to the dialog component ////////////////
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

    }
}
