package com.company.controller;

import com.company.view.FormEvent;
import com.company.model.Database;
import com.company.model.Project;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.prefs.Preferences;

public class ProjectController {
    Database database = new Database();

    public void addProject(FormEvent e){
        String name = e.getName();
        String type = e.getType();
        LocalDate startTime = e.getStart();
        LocalDate endTime = e.getEnd();
        Project project = new Project(name, type, startTime, endTime);
        database.addProject(project);
    }
    public List<Project> getAllProjects(){
        return database.getAllProjects();
    }

    public void removeProject(int index) {
        this.database.removeProject(index);
    }

    public void connect() throws Exception {
        database.connect();
    }

    public void save() throws SQLException {
        database.save();
    }

    public void load(Preferences preferences) throws SQLException {
        database.load(preferences);
    }

    public void disconnect() {
        database.disconnect();
    }
}
