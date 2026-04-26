package com.techformate.gastos.controller.dao;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class GestorDB {

    private Connection connection;

    public GestorDB() {
        realizarConexion();
    }

    private void realizarConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String user="root";
            String pass="";
            String host="localhost:3306";
            String dbName= "gastosFormacion";
            String url= "jdbc:mysql://"+host+"/"+dbName;
            connection = DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void llamarConexion(){
        System.out.println(connection.toString());
    };

    public boolean conexionAbierta() throws SQLException {
        return (!connection.isClosed());
    }

}
