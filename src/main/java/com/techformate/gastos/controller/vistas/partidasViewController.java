package com.techformate.gastos.controller.vistas;

import com.techformate.gastos.controller.service.ContabilidadController;
import com.techformate.gastos.controller.service.GestorController;
import com.techformate.gastos.controller.service.PresupuestoController;
import com.techformate.gastos.model.Partida;
import com.techformate.gastos.model.Sociedad;
import com.techformate.gastos.view.AvisosUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static com.techformate.gastos.view.AvisosUsuario.mostrarAlerta;

public class partidasViewController implements Initializable {

    private Partida partidaEnEdicion = null;

    @FXML
    private TableView<Partida> partidas;
    @FXML
    private TableColumn<Partida, String> sociedadInterna;
    @FXML
    private TableColumn<Partida, Integer> importe, iniciativa, tipoGasto;

    @FXML
    private Button btnAnadir, btnBorrar, btnCancelar, btnEditar, btnGuardar, btnIrFacturas, btnIrGastos, btnIrPartidas, btnVolver, btnExportarXML;

    @FXML
    private VBox contenedorFormulario;
    @FXML
    private Label tituloFormulario;
    @FXML
    private ComboBox<Sociedad> cmboSociedadInterna;
    @FXML
    private TextField fieldImporte, fieldTipoGasto, fieldIniciativa;

    //MÉTODOS DE CARGA DE DATOS E INTERFAZ VISUAL***********************************************************************

    Integer rolUsuario = GestorController.usuarioActual.getRol();

    public void cargarTabla() {
        List<Partida> partidasList = PresupuestoController.obtenerTodasJoin();
        if (partidasList != null) {
            ObservableList<Partida> observablePartidas = FXCollections.observableArrayList(partidasList);
            partidas.setItems(observablePartidas);
        }
    }

    public void cargarCmbSociedades() {
        List<Sociedad> sociedadesList = GestorController.obtenerSociedadesInternas();
        if (sociedadesList != null) {
            ObservableList<Sociedad> observableSociedades = FXCollections.observableArrayList(sociedadesList);
            cmboSociedadInterna.setItems(observableSociedades);
        }
    }

    private void mostrarFormulario(boolean mostrar) {
        contenedorFormulario.setVisible(mostrar);
        contenedorFormulario.setManaged(mostrar);
    }

    private void limpiarFormulario() {
        fieldImporte.clear();
        fieldTipoGasto.clear();
        fieldIniciativa.clear();
        cmboSociedadInterna.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (rolUsuario == 1) {
            btnExportarXML.setVisible(true);
            btnBorrar.setVisible(true);
        } else {
            btnExportarXML.setVisible(false);
            btnBorrar.setVisible(false);
        }
        mostrarFormulario(false);
        sociedadInterna.setCellValueFactory(new PropertyValueFactory<>("nombreSociedadInterna"));
        importe.setCellValueFactory(new PropertyValueFactory<>("importe"));
        tipoGasto.setCellValueFactory(new PropertyValueFactory<>("tipoGasto"));
        iniciativa.setCellValueFactory(new PropertyValueFactory<>("iniciativa"));
        cargarTabla();
        cargarCmbSociedades();
        actions();
    }

    //MÉTODOS DE ACCIONES **********************************************************************************************

    private void actions() {

        //NAVEGACIÓN

        btnIrPartidas.setDisable(true);

        btnIrGastos.setOnAction(event -> {
            NavegacionController.cambiarVista(btnIrGastos, NavegacionController.Ruta.GASTOS);
        });

        btnIrFacturas.setOnAction(event -> {
            NavegacionController.cambiarVista(btnIrFacturas, NavegacionController.Ruta.FACTURAS);
        });

        btnVolver.setOnAction(actionEvent -> {
            GestorController.cerrarSesion();
            NavegacionController.cambiarVista(btnVolver, NavegacionController.Ruta.LOGIN);
        });

        //INTERACCIÓN CON EL CONTENIDO

        btnAnadir.setOnAction(event -> {
            System.out.println("Btn anadir pulsado");
            tituloFormulario.setText("AÑADIR REGISTRO");
            mostrarFormulario(true);
            partidaEnEdicion = null;
            limpiarFormulario();

        });

        btnEditar.setOnAction(event -> {

            partidaEnEdicion = partidas.getSelectionModel().getSelectedItem();

            if (partidaEnEdicion != null) {
                mostrarFormulario(true);
                tituloFormulario.setText("EDITAR REGISTRO");
                fieldImporte.setText(partidaEnEdicion.getImporte().toString());
                fieldTipoGasto.setText(partidaEnEdicion.getTipoGasto());

                for (Sociedad sociedad : cmboSociedadInterna.getItems()) {
                    if (sociedad.getId().equals(partidaEnEdicion.getIdSociedadInterna())) {
                        cmboSociedadInterna.setValue(sociedad);
                    }
                }
                fieldIniciativa.setText(partidaEnEdicion.getIniciativa());
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Por favor, selecciona una fila de la tabla primero.");
            }
        });

        btnGuardar.setOnAction(event -> {
            try {
                BigDecimal importeNumerico = new BigDecimal(fieldImporte.getText());
                String tipoGasto = fieldTipoGasto.getText();
                Integer idSociedad = cmboSociedadInterna.getValue().getId();
                String iniciativa = fieldIniciativa.getText();

                if (partidaEnEdicion == null) {
                    Partida nuevaPartida = new Partida(null, idSociedad, importeNumerico, tipoGasto, iniciativa);
                    int resultadoValidacion = ContabilidadController.validarTipoImporte(nuevaPartida);
                    if (resultadoValidacion == 0) {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "El importe no puede estar vacío.");
                        return;
                    }
                    if (resultadoValidacion == -1) {
                        mostrarAlerta(Alert.AlertType.WARNING, "Importe inválido", "Has introducido un importe negativo. Las partidas del presupuesto no pueden ser negativas. Por favor, corrígelo.");
                        return;
                    }
                    PresupuestoController.registrarPartida(nuevaPartida);
                } else {
                    partidaEnEdicion.setImporte(importeNumerico);
                    partidaEnEdicion.setTipoGasto(tipoGasto);
                    partidaEnEdicion.setIdSociedadInterna(idSociedad);
                    partidaEnEdicion.setIniciativa(iniciativa);

                    int resultadoValidacion = ContabilidadController.validarTipoImporte(partidaEnEdicion);
                    if (resultadoValidacion == 0) {
                        mostrarAlerta(Alert.AlertType.ERROR, "Error", "El importe no puede estar vacío.");
                        return;
                    }
                    if (resultadoValidacion == -1) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Aviso de Devolución", "Has introducido un importe negativo. Las partidas del presupuesto no pueden ser negativas. Por favor, corrígelo.");
                        return;
                    }
                    PresupuestoController.actualizarPartida(partidaEnEdicion.getId(), partidaEnEdicion);
                    partidaEnEdicion = null;
                }

                limpiarFormulario();

                cargarTabla();
                partidas.refresh();

                mostrarFormulario(false);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Confirmación", "Cambios realizados con éxito.");

            } catch (Exception e) {
                System.err.println("Error al guardar: " + e.getMessage());
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al guardar. Revisa que los datos sean correctos.");
            }
        });

        btnCancelar.setOnAction(event -> {

            if (!fieldIniciativa.getText().isEmpty() ||
                    !fieldImporte.getText().isEmpty() ||
                    !fieldTipoGasto.getText().isEmpty() ||
                    !(cmboSociedadInterna.getValue() == null)) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("AVISO", "Vas a salir del formulario de edición. ¿Quieres continuar?");
                if (!confirmar) {
                    return;
                }
            }
            limpiarFormulario();
            mostrarFormulario(false);
        });

        btnBorrar.setOnAction(event -> {
            Partida partidaBorrar = partidas.getSelectionModel().getSelectedItem();

            if (partidaBorrar != null) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("AVISO", "Se va a proceder a borrar la partida seleccionada. ¿Continuar?");
                if (confirmar) {
                    Integer idPartidaBorrar = partidaBorrar.getId();

                    try {
                        PresupuestoController.borrarPartida(idPartidaBorrar);
                        cargarTabla();
                        partidas.refresh();
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Partida borrada correctamente.");
                        limpiarFormulario();
                        mostrarFormulario(false);
                    } catch (SQLException e) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error en Base de Datos", "Ha ocurrido un error inesperado.\n\nRevisa que esta Partida no tenga gastos asociados. De ser así, han de borrarse primero.");
                    }
                }
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "No se ha seleccionado ninguna Partida.");
            }
        });


        btnExportarXML.setOnAction(event -> {
            boolean exito = PresupuestoController.exportarPartidasXML();
            if (exito) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Confirmación", "Datos exportados con éxito.");
            } else {
                mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "No se ha podido exportar el XML.");
            }
        });

    }
}
