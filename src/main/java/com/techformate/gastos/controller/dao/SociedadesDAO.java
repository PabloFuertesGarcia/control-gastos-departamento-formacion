package com.techformate.gastos.controller.dao;

import com.techformate.gastos.model.Sociedad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SociedadesDAO {


    public List<Sociedad> realizarConsultaTodasSociedades() {
        GestorDB gestorDB = new GestorDB();
        List<Sociedad> sociedadesSQL = new ArrayList<>();

        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT * FROM sociedades";
                Statement statement = gestorDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(query));
                System.out.println("ejecutado");
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.println();
                        Integer id = resultSet.getInt("id_sociedad");
                        String tipoSociedad = resultSet.getString("tipo_sociedad");
                        String nombreSociedad = resultSet.getString("nombre_sociedad");
                        String cif = resultSet.getString("cif");

                        Sociedad sociedadIterada = new Sociedad(id,tipoSociedad,nombreSociedad,cif);
                        sociedadesSQL.add(sociedadIterada);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return sociedadesSQL;

    }

    public List<Sociedad> realizarConsultaSociedadesInternas() {
        List<Sociedad> sociedadesInternasSQL = new ArrayList<>();
        System.out.println(realizarConsultaTodasSociedades());
        sociedadesInternasSQL = realizarConsultaTodasSociedades().stream().filter(sociedad -> sociedad.getTipoSociedad().equalsIgnoreCase("interna")).toList();
        System.out.println(sociedadesInternasSQL);

        return sociedadesInternasSQL;
    }

    public List<Sociedad> realizarConsultaSociedadesProveedores() {
        List<Sociedad> sociedadesProveedoresSQL = new ArrayList<>();
        sociedadesProveedoresSQL = realizarConsultaTodasSociedades().stream().filter(sociedad -> sociedad.getTipoSociedad().equalsIgnoreCase("proveedor")).toList();

        return sociedadesProveedoresSQL;
    }

}
