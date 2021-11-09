package com.soen387.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    static Connection conn = null;

    public static Connection getConnection() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("DbInfo.properties");
        Properties properties = new Properties();
        try {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String JDBC_DRIVER = properties.getProperty("db.driver");
        String DB_URL = properties.getProperty("db.url");
        String DB_NAME = properties.getProperty("db.name");
        String DB_USER = properties.getProperty("db.username");
        String DB_PASSWORD = properties.getProperty("db.password");

        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL+DB_NAME,DB_USER,DB_PASSWORD);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database",e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("Error Class Not Found",e);
        }
    }

    public static void closeConnection() throws SQLException{
        if(conn!=null) conn.close();
    }


}
