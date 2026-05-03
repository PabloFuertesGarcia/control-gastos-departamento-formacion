package com.techformate.gastos.controller.vistas;

import com.techformate.gastos.view.AvisosUsuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NavegacionController {

    public enum Ruta {
        PARTIDAS("/view/partidasView.fxml", "Gestión de Presupuesto"),
        GASTOS("/view/gastosView.fxml", "Gestión de Gastos"),
        FACTURAS("/view/facturasView.fxml", "Gestión de Facturas"),
        IMPUTACIONES("/view/imputacionesView.fxml", "Imputaciones de factura"),
        LOGIN("/view/loginView.fxml", "Login");

        private final String url;
        private final String titulo;

        Ruta(String url, String titulo) {
            this.url = url;
            this.titulo = titulo;
        }

        public String getUrl() { return url; }
        public String getTitulo() { return titulo; }
    }


    public static void cambiarVista(Button boton, Ruta rutaDestino) {
        try {
            Stage stage = (Stage) boton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(NavegacionController.class.getResource(rutaDestino.getUrl()));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            stage.setTitle(rutaDestino.getTitulo());
            stage.setMaximized(true);

        } catch (Exception e) {
            AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING,"ERROR", "Error al intentar cargar la vista: " + rutaDestino.name());
            e.printStackTrace();
        }
    }
}
