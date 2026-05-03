package com.techformate.gastos.controller.dao;

import com.techformate.gastos.model.Imputacion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImputacionDAO {

    public boolean realizarInsercionImputacion(Imputacion imputacion, Connection connection) {
        try {
            if (!connection.isClosed()) {
                String query = "INSERT INTO imputaciones (id_gasto, id_factura, importe_factura_gasto) VALUES (?, ?, ?)";
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

    public List<Imputacion> realizarConsultaImputacionesPorFactura(Integer idFactura) {
        GestorDB gestorDB = new GestorDB();
        List<Imputacion> imputacionesSQL = new ArrayList<>();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT " +
                        "i.id_gasto, i.id_factura, i.importe_factura_gasto, " +
                        "g.concepto_gasto, " +
                        "f.num_factura " +
                        "FROM imputaciones i " +
                        "INNER JOIN gastos g ON g.id_gasto = i.id_gasto " +
                        "INNER JOIN facturas f ON f.id_factura = i.id_factura " +
                        "WHERE i.id_factura = ?;";

                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setInt(1, idFactura);

                ResultSet resultSet = pStatement.executeQuery();

                if (resultSet != null) {
                    while (resultSet.next()) {
                        Integer idGasto = resultSet.getInt("id_gasto");
                        String conceptoGasto = resultSet.getString("concepto_gasto");
                        Integer idFac = resultSet.getInt("id_factura");
                        String numFactura = resultSet.getString("num_factura");
                        BigDecimal importe = resultSet.getBigDecimal("importe_factura_gasto");

                        Imputacion imputacionIterada = new Imputacion(idGasto, conceptoGasto, idFac, numFactura, importe);
                        imputacionesSQL.add(imputacionIterada);
                    }
                }
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return imputacionesSQL;
    }

    public void realizarBorradoTodasPorFactura(Integer idFactura) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "DELETE FROM imputaciones WHERE id_factura = ?";
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setInt(1, idFactura);
                pStatement.executeUpdate();
                Connection conn = pStatement.getConnection();
                pStatement.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}