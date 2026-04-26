package com.techformate.gastos.controller.dao;

import com.techformate.gastos.model.Partida;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PartidaDAO {

    public void realizarInsercionPartida(Partida partida) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "INSERT INTO partidas (id_sociedad_interna, importe, tipo_gasto, iniciativa) " +
                        "VALUES (?, ?, ?, ?)";
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);

                pStatement.setInt(1, partida.getIdSociedadInterna());
                pStatement.setBigDecimal(2, partida.getImporte());
                pStatement.setString(3, partida.getTipoGasto());
                pStatement.setString(4, partida.getIniciativa());

                pStatement.executeUpdate();

                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarConsultaTodasPartidas() {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT * FROM partidas";
                Statement statement = gestorDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(query));
                System.out.println("ejecutado");
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.println();
                        Integer id = resultSet.getInt("id_partida");
                        Integer idSociedadInterna = resultSet.getInt("id_sociedad_interna");
                        BigDecimal importe = resultSet.getBigDecimal("importe");
                        String tipoGasto = resultSet.getString("tipo_gasto");
                        String iniciativa = resultSet.getString("iniciativa");
                        System.out.printf("id: %d | id Soc Interna: %d | Importe: %f | Tipo gasto %s | Iniciativa: %s", id, idSociedadInterna, importe, tipoGasto, iniciativa);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarActualizacionPartida(Integer id, Partida partida) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "UPDATE partidas " +
                        "SET id_sociedad_interna = ?," +
                        "importe = ?," +
                        "tipo_gasto = ?," +
                        "iniciativa = ?" +
                        " WHERE id_partida = ?";

                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setInt(1, partida.getIdSociedadInterna());
                pStatement.setBigDecimal(2, partida.getImporte());
                pStatement.setString(3, partida.getTipoGasto());
                pStatement.setString(4, partida.getIniciativa());
                pStatement.setInt(5, id);

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void realizarBorradoPartida(Integer id) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "DELETE FROM partidas" +
                        " WHERE id_partida = ?";
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
