package com.techformate.gastos.controller.dao;

import com.techformate.gastos.model.Estado;
import com.techformate.gastos.model.Gasto;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GastoDAO {

    public void realizarInsercionGasto(Gasto gasto) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto, estado) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);

                pStatement.setObject(1, gasto.getIdPartida());
                pStatement.setObject(2, gasto.getIdAccionForm());
                pStatement.setObject(3, gasto.getIdUsuario());
                pStatement.setBigDecimal(4, gasto.getImporte());
                pStatement.setString(5, gasto.getTipoCoste());
                pStatement.setString(6, gasto.getConceptoGasto());
                pStatement.setString(7, gasto.getEstado().toString());

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Gasto> realizarConsultaTodosGastosJoin() {
        GestorDB gestorDB = new GestorDB();
        List<Gasto>gastosSQL = new ArrayList<>();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT " +
                        "g.*, " +
                        "a.id_accion_form, a.denominacion, " +
                        "p.id_partida, p.iniciativa, " +
                        "u.id_usuario, u.nombre " +
                        "FROM gastos g " +
                        "LEFT JOIN acciones_form a ON a.id_accion_form = g.id_accion_form " +
                        "LEFT JOIN partidas p ON p.id_partida = g.id_partida " +
                        "LEFT JOIN usuarios u ON u.id_usuario = g.id_usuario;"
                        ;

                Statement statement = gestorDB.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(String.format(query));
                System.out.println("ejecutado");
                if (resultSet != null) {
                    while (resultSet.next()) {
                        System.out.println();
                        Integer id = resultSet.getInt("id_gasto");
                        BigDecimal importe = resultSet.getBigDecimal("importe");
                        String tipoCoste = resultSet.getString("tipo_coste");
                        String conceptoGasto = resultSet.getString("concepto_gasto");
                        String estado = resultSet.getString("estado");

                        Integer idPartida = resultSet.getInt("id_partida");
                        String iniciativa = resultSet.getString("iniciativa");

                        Integer idAccionForm = resultSet.getInt("id_accion_form");
                        String denominacionAccion = resultSet.getString("denominacion");

                        Integer idusuario = resultSet.getInt("id_usuario");
                        String nombre = resultSet.getString("nombre");

                        Estado estadoTraducido = Estado.stringAEnum(estado);

                        Gasto nuevoGasto = new Gasto(id, idPartida, idAccionForm, idusuario, importe, tipoCoste, conceptoGasto, estadoTraducido, iniciativa, denominacionAccion, nombre);
                        gastosSQL.add(nuevoGasto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return gastosSQL;
    }

    public void realizarActualizaciongasto(Integer id, Gasto gasto) {
        GestorDB gestorDB = new GestorDB();
        try {
            if (gestorDB.conexionAbierta()) {
                String query = "UPDATE gastos " +
                        "SET id_partida = ?, " +
                        "id_accion_form = ?, " +
                        "id_usuario = ?, " +
                        "importe = ?, " +
                        "tipo_coste = ?, " +
                        "concepto_gasto = ?, " +
                        "estado = ? " +
                        "WHERE id_gasto = ?";

                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setObject(1, gasto.getIdPartida());
                pStatement.setObject(2, gasto.getIdAccionForm());
                pStatement.setObject(3, gasto.getIdUsuario());
                pStatement.setBigDecimal(4, gasto.getImporte());
                pStatement.setString(5, gasto.getTipoCoste());
                pStatement.setString(6, gasto.getConceptoGasto());
                pStatement.setString(7, gasto.getEstado().toString());
                pStatement.setInt(8, id);

                pStatement.executeUpdate();

                pStatement.getConnection().close();
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void realizarBorradoGasto(Integer id) throws SQLException {
        GestorDB gestorDB = new GestorDB();
        if (gestorDB.conexionAbierta()) {
            String query = "DELETE FROM gastos WHERE id_gasto = ?";
            PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
            pStatement.setInt(1, id);
            pStatement.executeUpdate();
            pStatement.getConnection().close();
            pStatement.close();
        }
    }
}


