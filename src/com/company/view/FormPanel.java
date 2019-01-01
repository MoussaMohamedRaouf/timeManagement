package com.company.view;

import datechooser.beans.DateChooserCombo;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.*;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
	
	private JLabel nameLabel;
	private JLabel typeLabel;
	private JLabel startDateLabel;
	private JLabel endDateLabel;
	private JTextField nameField;
	private JTextField typeField;
	private JButton submitButton;
	private FormListener formListener;
	private DateChooserCombo startDate;
	private DateChooserCombo endDate;

	public FormPanel() {
		Dimension dim = getPreferredSize();
		dim.width = 240;
		setPreferredSize(dim);
		Border innerBorder = BorderFactory.createTitledBorder("Ajouter un nouveau projet");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		nameLabel = new JLabel("Nom:  ");
		typeLabel = new JLabel("Type:  ");
		startDateLabel = new JLabel("Début: ");
		endDateLabel = new JLabel("Fin: ");
		nameField = new JTextField(13);
		typeField = new JTextField(13);
		startDate = new DateChooserCombo();
		endDate = new DateChooserCombo();
		submitButton = new JButton("Créer");
		// set Mnemonic
		submitButton.setMnemonic(KeyEvent.VK_C);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gridLayout = new GridBagConstraints();
		gridLayout.fill = GridBagConstraints.NONE;
		
		
		//////// FIRST ROW //////////////////
		gridLayout.weightx = 1;
		gridLayout.weighty = 0.1;
		gridLayout.gridx = 0;
		gridLayout.gridy = 0;
		gridLayout.anchor = GridBagConstraints.LINE_END;// end of the first column
		// gridLayout.insets = new Insets(0, 0, 0, 5);
		add(nameLabel, gridLayout);
		gridLayout.gridx = 1;// add a new column
		gridLayout.gridy = 0;
		gridLayout.anchor = GridBagConstraints.LINE_START; // start of the column
		add(nameField, gridLayout);
		
		////////SECOND ROW //////////////////
		gridLayout.gridx = 0;
		gridLayout.gridy = 1; // add a new row
		gridLayout.anchor = GridBagConstraints.LINE_END;
		add(typeLabel, gridLayout);
		gridLayout.gridx = 1;
		gridLayout.gridy = 1; // add a new row
		gridLayout.anchor = GridBagConstraints.LINE_START;
		add(typeField,gridLayout);

		////////THIRD ROW //////////////////
		gridLayout.gridx = 0;
		gridLayout.gridy = 2; // add a new row
		gridLayout.anchor = GridBagConstraints.LINE_END;
		add(startDateLabel, gridLayout);
		gridLayout.gridx = 1;
		gridLayout.gridy = 2; // add a new row
		gridLayout.anchor = GridBagConstraints.LINE_START;
		add(startDate,gridLayout);

		////////FOURTH ROW //////////////////
		gridLayout.gridx = 0;
		gridLayout.gridy = 3; // add a new row
		gridLayout.anchor = GridBagConstraints.LINE_END;
		add(endDateLabel, gridLayout);
		gridLayout.gridx = 1;
		gridLayout.gridy = 3; // add a new row
		gridLayout.anchor = GridBagConstraints.LINE_START;
		add(endDate,gridLayout);
		
		////////FIFTH ROW //////////////////
		gridLayout.weightx = 1;
		gridLayout.weighty = 1;
		gridLayout.gridx = 1;
		gridLayout.gridy = 4;
		gridLayout.anchor = GridBagConstraints.FIRST_LINE_START;
		add(submitButton, gridLayout);
		
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String type = typeField.getText();
				String startTimeString = startDate.getText();
				String startParts[] = startTimeString.split("/");
				int startYear = Integer.parseInt("20" + startParts[2]);
				int startMonth = Integer.parseInt(startParts[1]);
				int startDay = Integer.parseInt(startParts[0]);
				LocalDate startProjectDate = LocalDate.of(startYear, startMonth, startDay);


				String endTimeString = endDate.getText();
				String endParts[] = endTimeString.split("/");
				int endYear = Integer.parseInt("20" + endParts[2]);
				int endMonth = Integer.parseInt(endParts[1]);
				int endDay = Integer.parseInt(endParts[0]);
				LocalDate endProjectDate = LocalDate.of(endYear, endMonth, endDay);

				System.out.println(LocalDate.now().until(endProjectDate, ChronoUnit.DAYS));
				if(name.isEmpty() || type.isEmpty() || startTimeString.isEmpty() || endTimeString.isEmpty()){
					JOptionPane.showMessageDialog(FormPanel.this,
							"Veuillez remplir les champs correctement.",
							"Ajout d'un projet",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(startProjectDate.isAfter(endProjectDate)){
					JOptionPane.showMessageDialog(FormPanel.this,
							"Vous ne pouvez pas commencez le projet aprés la date de fin fixée.",
							"Vérification des dates",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				FormEvent event = new FormEvent(this, name, type, startProjectDate, endProjectDate);
				if(formListener != null){
					formListener.actionFormEventOccured(event);
				}
			}
		});
	}
	public void setFormListener(FormListener formListener){
		this.formListener = formListener;
	}
}
