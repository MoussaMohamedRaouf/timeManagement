package com.company.view;

import com.company.controller.ProjectController;
//import com.company.controller.SessionController;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.prefs.Preferences;

import javax.swing.*;

public class MainFrame extends JFrame {
// All component communications are here. MainFrame act like a controller	
	private Toolbar toolbar;
	private TextPanel textPanel;
	//private JButton btn;
	private FormPanel formPanel;
	private TablePanel tablePanel;
	//private ConfigDialog configDialog;
	private ProjectController projectController;
	//private SessionController sessionController;
	private Preferences preferences;
	private JTabbedPane jTabbedPane;
	
	public MainFrame(){
		super("Gestion de temps");
		
		setLayout(new BorderLayout());
		textPanel = new TextPanel();
		//btn = new JButton("Lancer une session");
		toolbar = new Toolbar();
		formPanel = new FormPanel();
		tablePanel = new TablePanel();
		//configDialog = new ConfigDialog(this);
		jTabbedPane = new JTabbedPane();
		jTabbedPane.addTab("liste des projets", tablePanel);
		jTabbedPane.addTab("notes", textPanel);


		preferences = Preferences.userRoot().node("database");

		projectController = new ProjectController();
		//sessionController = new SessionController();

		tablePanel.setData(projectController.getAllProjects());
		tablePanel.setProjectTableListener(new ProjectTableListener(){
			public void rowDeleted(int row){
				projectController.removeProject(row);
			}
		});

		/*configDialog.setConfigurationListener(new ConfigurationListener() {
			@Override
			public void configurationSet(int sessionLength, int breakLength, int numberOfSessions) {
				preferences.putInt("sessions", sessionLength);
				preferences.putInt("breaks", breakLength);
				preferences.putInt("numberOfSessions", numberOfSessions);
			}
		});*/

		//int sessionLength = preferences.getInt("sessions", 30);
		//int breakLength = preferences.getInt("breaks", 5);
		//int numberOfSessions = preferences.getInt("numberOfSessions", 8);

		//configDialog.setDefaults(sessionLength, breakLength, numberOfSessions);

		
		add(toolbar, BorderLayout.PAGE_START);
		add(jTabbedPane, BorderLayout.CENTER);
		//add(btn, BorderLayout.SOUTH);
		add(formPanel, BorderLayout.WEST);

		setJMenuBar(createMenuBar());
		
		toolbar.setToolbarListener(new ToolbarListener(){
			@Override
			public void saveEventOccured() {
				connectProjectController();
				try {
					projectController.save();
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"échoue d'enregistrement des données.",
							"Probléme de connexion a la base de donnée",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				// projectController.disconnect();

			}

			@Override
			public void refreshEventOccured() {
				connectProjectController();
				try {
					projectController.load(preferences);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(MainFrame.this,
							"échoue de chargement de la base de donnée.",
							"Probléme de connexion a la base de donnée",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				tablePanel.refresh();
				// projectController.disconnect();
			}
		});
		
		formPanel.setFormListener(new FormListener(){
			public void actionFormEventOccured(FormEvent e){
				projectController.addProject(e);
				tablePanel.refresh();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				projectController.disconnect();
				dispose();
			}
		});

		setSize(1100, 600);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void connectProjectController() {
		try {
			projectController.connect();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this,
					"Connexion à la base donnée échouée (project).",
					"Probléme de connexion a la base de donnée",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();

		JMenu projectMenu = new JMenu("Projet");
		JMenuItem addProject = new JMenuItem("ajouter un projet...");
		JMenuItem projectList = new JMenuItem("Liste des Projets...");
		JMenuItem exitItem = new JMenuItem("Exit");

		projectMenu.add(addProject);
		projectMenu.add(projectList);
		projectMenu.addSeparator();
		projectMenu.add(exitItem);


		//JMenu sessionMenu = new JMenu("Session");
		//JMenuItem addSession = new JMenuItem("ajouter une session");
		//JMenuItem sessionList = new JMenuItem("afficher les sessions");
		//sessionMenu.add(addSession);
		//sessionMenu.add(sessionList);

		JMenu windowMenu = new JMenu("Fenètre");
		JMenu showMenu = new JMenu("afficher");
		JCheckBoxMenuItem showProjectForm = new JCheckBoxMenuItem("création du projet...");
		showMenu.add(showProjectForm);
		//JMenuItem configItem = new JMenuItem("Configurations...");

		showProjectForm.setSelected(true);


		windowMenu.add(showMenu);
		//windowMenu.add(configItem);
		windowMenu.add(exitItem);

		/*configItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configDialog.setVisible(true);
			}
		});*/



//		menuBar.add(projectMenu);
//		menuBar.add(sessionMenu);
		menuBar.add(windowMenu);

		showProjectForm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();

				formPanel.setVisible(menuItem.isSelected());
			}
		});

		projectMenu.setMnemonic(KeyEvent.VK_P);
		exitItem.setMnemonic(KeyEvent.VK_X);

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		//configItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Voulez vous vraiment quitter l'app?", "Quitter", JOptionPane.OK_CANCEL_OPTION);

				if(action == JOptionPane.OK_OPTION){
					WindowListener[] listeners = getWindowListeners();
					for (WindowListener listener: listeners){
						listener.windowClosing(new WindowEvent(MainFrame.this, 0));
					}

				}
			}
		});

		return menuBar;
	}
}
