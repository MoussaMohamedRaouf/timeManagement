package com.company.controller;

import com.company.model.Database;

import java.sql.SQLException;

public class UserController {
    Database database = new Database();

    public boolean userIsValid(String username, String password) throws SQLException{
        return database.checkUser(username, password);
    }
    public void connect() throws Exception {
        database.connect();
    }

    public void disconnect() {
        database.disconnect();
    }
}
