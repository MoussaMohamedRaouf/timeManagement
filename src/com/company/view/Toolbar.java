package com.company.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Toolbar extends JToolBar implements ActionListener {
	private JButton saveButton;
	private JButton refreshButton;
	private ToolbarListener toolbarListener;
	
	public Toolbar(){
		// get out of the border, if you want the toolbar draggable
		setBorder(BorderFactory.createEtchedBorder());
		saveButton = new JButton("Enregistrer");
		refreshButton = new JButton("Rafraichir");

		saveButton.addActionListener(this);

		refreshButton.addActionListener(this);
		
		add(saveButton);
		addSeparator();
		add(refreshButton);
	}
	
	public void setToolbarListener(ToolbarListener toolbarListener){
		this.toolbarListener = toolbarListener;
	}

	public void actionPerformed(ActionEvent e) {
		JButton buttonClicked = (JButton)e.getSource();
		if(buttonClicked == saveButton){
			if(toolbarListener != null){
				toolbarListener.saveEventOccured();
			}
		} else if(buttonClicked == refreshButton){
			if(toolbarListener != null){
				toolbarListener.refreshEventOccured();
			}
		}
	}
}
