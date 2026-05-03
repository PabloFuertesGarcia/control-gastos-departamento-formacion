package com.techformate.gastos.controller.vistas;

import com.techformate.gastos.controller.service.GestorController;
import com.techformate.gastos.view.AvisosUsuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Button btnIniciarSesion;
    @FXML private PasswordField fieldContrasena;
    @FXML private TextField fieldEmail;

    //MÉTODOS DE CARGA DE DATOS E INTERFAZ VISUAL***********************************************************************

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        actions();
    }

    //MÉTODOS DE ACCIONES **********************************************************************************************

    private void actions() {

        btnIniciarSesion.setOnAction(event -> {
            String email = fieldEmail.getText();
            String contrasena = fieldContrasena.getText();

            if (email.trim().isEmpty() || contrasena.trim().isEmpty()) {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Por favor, introduce tu email y contraseña.");
                return;
            }

            boolean loginExitoso = GestorController.iniciarSesion(email, contrasena);

            if (loginExitoso) {
                System.out.println("¡Bienvenido, " + GestorController.usuarioActual.getNombre() + "!");
                NavegacionController.cambiarVista(btnIniciarSesion, NavegacionController.Ruta.FACTURAS);
            } else {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error de autenticación", "Email o contraseña incorrectos. Por favor, inténtalo de nuevo.");
                fieldContrasena.clear();
            }
        });
    }
}
