package com.techformate.gastos.controller.dao;

import com.techformate.gastos.model.Gasto;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GastoDAO {

    public void realizarInsercionGasto(Gasto gasto) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);

                pStatement.setInt(1, gasto.getIdPartida());
                pStatement.setInt(2, gasto.getIdAccionForm());
                pStatement.setInt(3, gasto.getIdUsuario());
                pStatement.setBigDecimal(4, gasto.getImporte());
                pStatement.setString(5, gasto.getTipoGasto());
                pStatement.setString(6, gasto.getConceptoGasto());

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarConsultaTodosGastos() {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT * FROM gastos";
                Statement statement = gestorDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(query));
                System.out.println("ejecutado");
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.println();
                        Integer id = resultSet.getInt("id_gasto");
                        Integer idPartida = resultSet.getInt("id_partida");
                        Integer idAccionForm = resultSet.getInt("id_accion_form");
                        Integer idusuario = resultSet.getInt("id_usuario");
                        BigDecimal importe = resultSet.getBigDecimal("importe");
                        String tipoCoste = resultSet.getString("tipo_coste");
                        String conceptoGasto = resultSet.getString("concepto_gasto");
                        System.out.printf("id: %d | id Partida: %d | id Accion Formativa: %d |id usuario: %d | Importe: %f | Tipo coste %s | Concepto gasto: %s", id, idPartida,idAccionForm, idusuario, importe, tipoCoste, conceptoGasto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarActualizaciongasto(Integer id, Gasto gasto) {
        GestorDB gestorDB = new GestorDB();
        try {

            if (gestorDB.conexionAbierta()) {
                String query = "UPDATE gastos " +
                        "SET id_partida = ?," +
                        "id_accion_form = ?,"+
                        "id_usuario = ?,"+
                        "importe = ?," +
                        "tipo_coste = ?," +
                        "concepto_gasto = ?" +
                        " WHERE id_gasto = ?";

                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setInt(1, gasto.getIdPartida());
                pStatement.setInt(2, gasto.getIdAccionForm());
                pStatement.setInt(3, gasto.getIdUsuario());
                pStatement.setBigDecimal(4, gasto.getImporte());
                pStatement.setString(5, gasto.getTipoGasto());
                pStatement.setString(6, gasto.getConceptoGasto());
                pStatement.setInt(7, id);

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarBorradoGasto(Integer id) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "DELETE FROM gastos" +
                        " WHERE id_gasto = ?";
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


