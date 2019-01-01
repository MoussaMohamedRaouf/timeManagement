package com.company.model;

import java.time.LocalDate;

public class Project {
    private int id;
    private String name;
    private String type;
    private LocalDate startTime;
    private LocalDate endTime;
    private long elapsedTime;
    private int sessionByDay;
    private long sessionsByProject;

    private static int count = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public long getElapsedTime(){
        return this.elapsedTime;
    }

    public void setElapsedTime(long elapsedTime){
        this.elapsedTime = elapsedTime;
    }

    public int getSessionByDay() {
        return sessionByDay;
    }

    public void setSessionByDay(int sessionByDay) {
        this.sessionByDay = sessionByDay;
    }

    public long getSessionsByProject() {
        return sessionsByProject;
    }

    public void setSessionsByProject(long sessionsByProject) {
        this.sessionsByProject = sessionsByProject;
    }

    public Project(String name, String type, LocalDate startTime, LocalDate endTime) {
        this.name = name;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.id = count;
        count++;
    }

    public Project(int id, String name, String type, LocalDate startTime, LocalDate endTime) {
        this(name, type, startTime, endTime);
        this.id = id;
    }
    public Project(int id, String name, String type, LocalDate startTime, LocalDate endTime, long elapsedTime ,int sessionByDay, long sessionsByProject) {
        this(id, name, type, startTime, endTime);
        this.elapsedTime = elapsedTime;
        this.sessionByDay = sessionByDay;
        this.sessionsByProject = sessionsByProject;
    }

    @Override
    public String toString() {
        return "project name is: " + getName();
    }
}
