package com.techformate.gastos.controller.dao;
import com.techformate.gastos.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {
    public Usuario usuarioAutentificar(String email, String contrasena) {
        GestorDB gestorDB = new GestorDB();
        Usuario usuarioLogueado = null;

        try {
            if (gestorDB.conexionAbierta()) {
                String query = "SELECT id_usuario, email, nombre, rol " +
                        "FROM usuarios " +
                        "WHERE email = ? AND " +
                        "contrasena = ?";
                PreparedStatement pStatement = gestorDB.getConnection().prepareStatement(query);
                pStatement.setString(1, email);
                pStatement.setString(2, contrasena);
                ResultSet resultSet = pStatement.executeQuery();
                if (resultSet.next()) {
                    Integer id = resultSet.getInt("id_usuario");
                    String emailDB = resultSet.getString("email");
                    String nombre = resultSet.getString("nombre");
                    Integer rol = resultSet.getInt("rol");

                    usuarioLogueado = new Usuario(id, emailDB, nombre, rol);
                }
                pStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioLogueado;
    }
}


