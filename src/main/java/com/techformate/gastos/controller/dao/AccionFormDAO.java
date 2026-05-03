package com.techformate.gastos.controller.dao;

import com.techformate.gastos.model.AccionForm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccionFormDAO {

    public List<AccionForm> realizarConsultaTodasAcciones() {
        GestorDB gestorDB = new GestorDB();
        List<AccionForm> accionesSQL = new ArrayList<>();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT * FROM acciones_form";
                Statement statement = gestorDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(query));
                System.out.println("ejecutado");
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.println();

                        Integer id = resultSet.getInt("id_accion_form");
                        String denominacion = resultSet.getString("denominacion");
                        String habilidad= resultSet.getString("habilidad");

                        AccionForm accionFormIterada = new AccionForm(id,denominacion,habilidad);
                        accionesSQL.add(accionFormIterada);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accionesSQL;
    }

    // NO USADO EN ESTA VERSIÓN - FUERA DEL MVP *********************************

        /*

    public void realizarInsercionAccion(AccionForm accionForm) {

        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "INSERT INTO acciones_form (denominacion, habilidad) " +
                        "VALUES (?, ?)";
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);

                pStatement.setString(1, accionForm.getDenominacion());
                pStatement.setString(2, accionForm.getHabilidad());

                pStatement.executeUpdate();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarActualizacionAccionForm(Integer id, AccionForm accionForm) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "UPDATE acciones_form " +
                        "SET denominacion = ?," +
                        "habilidad = ?" +
                        " WHERE id_accion_form = ?";

                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setString(1, accionForm.getDenominacion());
                pStatement.setString(2, accionForm.getHabilidad());
                pStatement.setInt(3, id);

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarBorradoAccionForm(Integer id) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "DELETE FROM acciones_form" +
                        " WHERE id_accion_form = ?";
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);

                pStatement.setInt(1, id);

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    */
}

