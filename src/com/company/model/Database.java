package com.company.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

public class Database {
    private LinkedList<Project> projects;
    private Connection connection;

    public Database(){
        projects = new LinkedList<>();
    }

    public void connect() throws Exception{
        if(connection != null) return;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e){
             throw new Exception("driver not found");
        }
        String connectionUrl = "jdbc:mysql://localhost:3306/time_management?useSSL=false";
        connection = DriverManager.getConnection(connectionUrl, "root", "Lapakita123Ffff++872");
        System.out.println("database connected");
    }

    public void disconnect(){
        if(connection != null){
            try {
                System.out.println("trying to close database try");
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("trying to close database catch");
            }
        }

    }

    public void load(Preferences preferences) throws SQLException {
        projects.clear();

        String sql = "select * from projects order by name";

        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String type = resultSet.getString("type");
            LocalDate startTime = resultSet.getDate("startTime").toLocalDate();
            LocalDate endTime = resultSet.getDate("endTime").toLocalDate();
            long elapsedTime = LocalDate.now().until(endTime, ChronoUnit.DAYS);
            int numberOfSessionByDay = preferences.getInt("numberOfSessions", 8);
            long totalSessionByProject = numberOfSessionByDay * elapsedTime;

            Project project = new Project(id, name, type, startTime, endTime, elapsedTime, numberOfSessionByDay, totalSessionByProject);
            projects.add(project);
        }

        resultSet.close();
        statement.close();
    }

    public void save() throws SQLException {
        System.out.println(connection);
        String checkSql = "select count(*) as count from projects where id=?";
        PreparedStatement checkStatement = connection.prepareStatement(checkSql);

        String insertSql = "insert into projects values(?, ?, ?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);

        String updateSql = "update projects set name = ?, type = ?, startTime=?, endTime=? where id = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);

        deleteNotExistProjects();
        
        

        for (Project project: projects){

            int id = project.getId();
            String name = project.getName();
            String type = project.getType();
            LocalDate startTime = project.getStartTime();
            LocalDate endTime = project.getEndTime();

            checkStatement.setInt(1,id);

            ResultSet resultSet = checkStatement.executeQuery();
            // resultSet always it start at the before first.
            resultSet.next();
            int count = resultSet.getInt(1);
            System.out.println("count" + count);
            if(count == 0){
                insertStatement.setInt(1, id);
                insertStatement.setString(2, name);
                insertStatement.setString(3, type);
                insertStatement.setDate(4, java.sql.Date.valueOf(startTime));
                insertStatement.setDate(5, java.sql.Date.valueOf(endTime));
                insertStatement.executeUpdate();
            }else{
                updateStatement.setString(1, name);
                updateStatement.setString(2, type);
                updateStatement.setDate(3, java.sql.Date.valueOf(startTime));
                updateStatement.setDate(4, java.sql.Date.valueOf(endTime));
                updateStatement.setInt(5, id);
                updateStatement.executeUpdate();
            }

        }
        updateStatement.close();
        insertStatement.close();
        checkStatement.close();
    }

    private void deleteNotExistProjects() throws SQLException {
        String deleteSql = "delete from projects where id=?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
        String selectSql = "select id from projects";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSql);
        while (resultSet.next()){
            boolean checkExistingRow = false;
            for (Project project:projects) {
                if(project.getId() == resultSet.getInt("id")){
                    checkExistingRow = true;
                }
            }
            if(!checkExistingRow){
                deleteStatement.setInt(1, resultSet.getInt("id"));
                deleteStatement.executeUpdate();
            }
        }
        resultSet.close();
        statement.close();

    }

    public void addProject(Project project){
        projects.add(project);
    }

    public List<Project> getAllProjects(){
        return Collections.unmodifiableList(projects);
    }

    public void removeProject(int index) {
        this.projects.remove(index);
    }

    public void addSession(Session session) throws SQLException{
        String insertSql = "insert into session (length, break_length, session_day) values(?, ?, ?)";
        System.out.println(connection);
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        int length = session.getLength();
        int breakLength = session.getBreakLength();
        int numberOfSession = session.getNumberOfSessions();
        insertStatement.setInt(1, length);
        insertStatement.setInt(2, breakLength);
        insertStatement.setInt(3, numberOfSession);
        insertStatement.executeUpdate();
        insertStatement.close();
    }

    public boolean checkUser(String username, String password) throws SQLException {
        String checkSql = "select count(*) as count from users where username=? AND password=?";
        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
        checkStatement.setString(1,username);
        checkStatement.setString(2,password);

        ResultSet resultSet = checkStatement.executeQuery();
        // resultSet always it start at the before first.
        resultSet.next();
        int count = resultSet.getInt(1);
        if(count == 1){
            return true;
        }
        return false;
    }
}
