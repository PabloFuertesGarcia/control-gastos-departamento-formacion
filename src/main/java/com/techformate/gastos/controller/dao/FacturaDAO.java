package com.techformate.gastos.controller.dao;
import com.techformate.gastos.model.Factura;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    public Integer realizarInsercionFactura(Factura factura, Connection connection) {
        try {
            String query = "INSERT INTO facturas (id_sociedad_interna, id_sociedad_proveedor, num_factura, fecha_emision, importe) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            pStatement.setInt(1, factura.getIdSociedadInterna());
            pStatement.setInt(2, factura.getIdSociedadProveedor());
            pStatement.setString(3, factura.getNumFactura());
            pStatement.setDate(4, Date.valueOf(factura.getFechaEmision()));
            pStatement.setBigDecimal(5, factura.getImporte());

            pStatement.executeUpdate();

            ResultSet resultado = pStatement.getGeneratedKeys();
            Integer idResultado = null;
            if (resultado.next()) {
                idResultado = resultado.getInt(1);
            }
            pStatement.close();
            return idResultado;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Factura> realizarConsultaTodasFacturasJoin() {
        GestorDB gestorDB = new GestorDB();
        List<Factura>facturasSQL = new ArrayList<>();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT " +
                        "f.*, " +
                        "si.nombre_sociedad AS nombre_sociedad_interna, "+
                        "sp.nombre_sociedad AS nombre_sociedad_proveedor "+
                        "FROM facturas f " +
                        "LEFT JOIN sociedades si ON si.id_sociedad = f.id_sociedad_interna "+
                        "LEFT JOIN sociedades sp ON sp.id_sociedad = f.id_sociedad_proveedor;"
                        ;
                Statement statement = gestorDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(query));
                System.out.println("ejecutado");
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.println();
                        Integer id = resultSet.getInt("id_factura");
                        Integer idSociedadInterna = resultSet.getInt("id_sociedad_interna");
                        String nombreSociedadInterna = resultSet.getString("nombre_sociedad_interna");

                        Integer idSociedadProveedor = resultSet.getInt("id_sociedad_proveedor");
                        String nombreSociedadProveedor = resultSet.getString("nombre_sociedad_proveedor");

                        String numFactura = resultSet.getString("num_factura");
                        LocalDate fechaEmision = resultSet.getDate("fecha_emision").toLocalDate();
                        BigDecimal importe = resultSet.getBigDecimal("importe");

                        Factura nuevafactura = new Factura(id,idSociedadProveedor,nombreSociedadProveedor,idSociedadInterna,nombreSociedadInterna,numFactura,fechaEmision,importe);
                        facturasSQL.add(nuevafactura);

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return facturasSQL;
    }



    public void realizarActualizacionFactura(Integer id, Factura factura) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "UPDATE facturas " +
                        "SET id_sociedad_interna = ?, " +
                        "id_sociedad_proveedor = ?, " +
                        "num_factura = ?, " +
                        "fecha_emision = ?, " +
                        "importe = ? "+
                        "WHERE id_factura = ?;"
                        ;
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setInt(1, factura.getIdSociedadInterna());
                pStatement.setInt(2, factura.getIdSociedadProveedor());
                pStatement.setString(3, factura.getNumFactura());
                pStatement.setDate(4, Date.valueOf(factura.getFechaEmision()));
                pStatement.setBigDecimal(5, factura.getImporte());
                pStatement.setInt(6, id);

                pStatement.executeUpdate();
                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarBorradoFactura(Integer id) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "DELETE FROM facturas" +
                        " WHERE id_factura = ?";
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
