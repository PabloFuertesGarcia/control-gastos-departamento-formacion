package com.techformate.gastos.controller.dao;

import com.techformate.gastos.model.Factura;
import com.techformate.gastos.model.Imputacion;

import java.math.BigDecimal;
import java.sql.*;

public class ImputaciónDAO {

    public boolean realizarInsercionImputacion(Imputacion imputacion, Connection connection) {
        try {
            if (!connection.isClosed()) {
                String query = "INSERT INTO imputaciones (id_gasto, id_factura, importe_factura_gasto) " +
                        "VALUES (?, ?, ?)";
                PreparedStatement pStatement = connection.prepareStatement(query);

                pStatement.setInt(1, imputacion.getIdGasto());
                pStatement.setInt(2, imputacion.getIdFactura());
                pStatement.setBigDecimal(3, imputacion.getImporte());

                pStatement.executeUpdate();

                pStatement.close();

                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void realizarActualizacionImputacion (Integer id, Imputacion imputacion) {
        GestorDB gestorDB = new GestorDB();
        try {

            if (gestorDB.conexionAbierta()) {
                String query = "UPDATE imputaciones " +
                        "SET id_gasto = ?," +
                        "id_factura = ?,"+
                        "importe_factura_gasto = ?"+
                        " WHERE id_imputacion = ?";

                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setInt(1, imputacion.getIdGasto());
                pStatement.setInt(2, imputacion.getIdFactura());
                pStatement.setBigDecimal(3, imputacion.getImporte());
                pStatement.setInt(1, id);

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void realizarBorradoImputacion(Integer id) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "DELETE FROM imputaciones" +
                        " WHERE id_imputacion = ?";
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

}
