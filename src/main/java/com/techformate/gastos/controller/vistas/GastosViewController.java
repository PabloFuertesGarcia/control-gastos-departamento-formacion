package com.techformate.gastos.controller.vistas;

import com.techformate.gastos.controller.service.ContabilidadController;
import com.techformate.gastos.controller.service.GestorController;
import com.techformate.gastos.controller.service.PresupuestoController;
import com.techformate.gastos.model.AccionForm;
import com.techformate.gastos.model.Estado;
import com.techformate.gastos.model.Gasto;
import com.techformate.gastos.model.Partida;
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

public class GastosViewController implements Initializable {

    private Gasto gastoEnEdicion = null;

    @FXML private TableView<Gasto> gastos;
    @FXML private TableColumn<Gasto, String> colAccion, colConcepto, colEstado, colPartida, colTipoCoste, colUsuario;
    @FXML private TableColumn<Gasto, BigDecimal> colImporte;

    @FXML private Button btnAnadir, btnBorrar, btnCancelar, btnEditar, btnGuardar, btnIrFacturas, btnIrGastos, btnIrPartidas, btnVolver;

    @FXML private VBox contenedorFormulario;
    @FXML private Label tituloFormulario;
    @FXML private ComboBox<Estado> cmboEstado;
    @FXML private ComboBox<Partida> cmboPartida;
    @FXML private ComboBox<AccionForm> cmboAccionForm;
    @FXML private TextField fieldImporte, fieldIniciativa, fieldTipoCoste;

    //MÉTODOS DE CARGA DE DATOS E INTERFAZ VISUAL***********************************************************************

    public void cargarTabla() {
        List<Gasto> gastosList = ContabilidadController.obtenerTodosGastosJoin();
        if (gastosList != null) {
            ObservableList<Gasto> observableGastos = FXCollections.observableArrayList(gastosList);
            gastos.setItems(observableGastos);
        }
    }

    public void cargarDesplegables() {
        cmboEstado.setItems(FXCollections.observableArrayList(Estado.values()));
        cargarCmbPartidas();
        cargarCmbAcciones();
    }

    public void cargarCmbPartidas() {
        List<Partida> partidasList = PresupuestoController.obtenerTodasPartidas();
        if (partidasList != null) {
            ObservableList<Partida> observablePartidas = FXCollections.observableArrayList(partidasList);
            cmboPartida.setItems(observablePartidas);
        }
    }

    public void cargarCmbAcciones() {
        List<AccionForm> accionesList = GestorController.obtenerTodasAccionesForm();
        if (accionesList != null) {
            AccionForm opcionNinguna = new AccionForm(null, "--- Ninguna ---", null);
            accionesList.add(0, opcionNinguna);
            ObservableList<AccionForm> observableAccionesForm = FXCollections.observableArrayList(accionesList);
            cmboAccionForm.setItems(observableAccionesForm);
        }
    }

    private void mostrarFormulario(boolean mostrar) {
        contenedorFormulario.setVisible(mostrar);
        contenedorFormulario.setManaged(mostrar);
    }

    private void limpiarFormulario() {
        fieldImporte.clear();
        fieldIniciativa.clear();
        fieldTipoCoste.clear();
        if (cmboEstado.getItems() != null) cmboEstado.getSelectionModel().clearSelection();
        if (cmboPartida.getItems() != null) cmboPartida.getSelectionModel().clearSelection();
        if (cmboAccionForm.getItems() != null) cmboAccionForm.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mostrarFormulario(false);
        colAccion.setCellValueFactory(new PropertyValueFactory<>("denominacionAccion"));
        colConcepto.setCellValueFactory(new PropertyValueFactory<>("conceptoGasto"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colPartida.setCellValueFactory(new PropertyValueFactory<>("iniciativa"));
        colTipoCoste.setCellValueFactory(new PropertyValueFactory<>("tipoCoste"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));

        cargarTabla();
        cargarDesplegables();
        actions();
    }

    //MÉTODOS DE ACCIONES **********************************************************************************************

    private void actions() {

        //NAVEGACIÓN

        btnIrPartidas.setOnAction(actionEvent ->
                NavegacionController.cambiarVista(btnIrPartidas, NavegacionController.Ruta.PARTIDAS));

        btnIrGastos.setDisable(true);

        btnIrFacturas.setOnAction(event -> {
            NavegacionController.cambiarVista(btnIrFacturas, NavegacionController.Ruta.FACTURAS);
        });

        btnVolver.setOnAction(actionEvent -> {
            GestorController.cerrarSesion();
            NavegacionController.cambiarVista(btnVolver, NavegacionController.Ruta.LOGIN);
        });

        //INTERACCIÓN CON EL CONTENIDO

        btnAnadir.setOnAction(event -> {
            tituloFormulario.setText("AÑADIR REGISTRO");
            mostrarFormulario(true);
            gastoEnEdicion = null;
            limpiarFormulario();
        });

        btnEditar.setOnAction(event -> {
            gastoEnEdicion = gastos.getSelectionModel().getSelectedItem();

            if (gastoEnEdicion != null) {
                mostrarFormulario(true);
                tituloFormulario.setText("EDITAR REGISTRO");
                fieldImporte.setText(gastoEnEdicion.getImporte().toString());
                fieldIniciativa.setText(gastoEnEdicion.getConceptoGasto());
                fieldTipoCoste.setText(gastoEnEdicion.getTipoCoste());
                cmboEstado.setValue(gastoEnEdicion.getEstado());

                if (gastoEnEdicion.getIdPartida() != null) {
                    for (Partida partida : cmboPartida.getItems()) {
                        if (partida.getId().equals(gastoEnEdicion.getIdPartida())) {
                            cmboPartida.setValue(partida);
                            break;
                        }
                    }
                }
                if (gastoEnEdicion.getIdAccionForm() != null) {
                    for (AccionForm accionForm : cmboAccionForm.getItems()) {
                        if (accionForm.getId() != null && accionForm.getId().equals(gastoEnEdicion.getIdAccionForm())) {
                            cmboAccionForm.setValue(accionForm);
                            break;
                        }
                    }
                } else {
                    cmboAccionForm.getSelectionModel().selectFirst();
                }
            } else {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Por favor, selecciona una fila de la tabla primero.");
            }
        });


        btnGuardar.setOnAction(event -> {
            try {
                BigDecimal importeNumerico = new BigDecimal(fieldImporte.getText());
                String concepto = fieldIniciativa.getText();
                String tipoCoste = fieldTipoCoste.getText();
                Estado estado = cmboEstado.getValue();
                Partida partidaSeleccionada = cmboPartida.getValue();
                AccionForm accionSeleccionada = cmboAccionForm.getValue();
                Integer idUsuario = GestorController.usuarioActual.getId();
                Integer idPartida = null;
                Integer idAccionForm = null;

                if (partidaSeleccionada != null) {
                    idPartida = partidaSeleccionada.getId();
                }
                if (accionSeleccionada != null) {
                    idAccionForm = accionSeleccionada.getId();
                }


                if (gastoEnEdicion == null) {
                    Gasto nuevoGasto = new Gasto(null, idPartida, idAccionForm, idUsuario, importeNumerico, tipoCoste, concepto, estado, "", "", "");
                    int resultadoValidacion = ContabilidadController.validarTipoImporte(nuevoGasto);
                    if (resultadoValidacion == 0) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El importe no puede estar vacío.");
                        return;
                    }
                    if (resultadoValidacion == -1) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Aviso de Devolución",
                                "Has introducido un importe negativo. Esto significa que estás registrando una devolución o abono.Si es correcto, puedes ignorar este aviso.");
                    }
                    ContabilidadController.registrarGasto(nuevoGasto);
                } else {
                    gastoEnEdicion.setImporte(importeNumerico);
                    gastoEnEdicion.setConceptoGasto(concepto);
                    gastoEnEdicion.setTipoCoste(tipoCoste);
                    gastoEnEdicion.setEstado(estado);
                    gastoEnEdicion.setIdPartida(idPartida);
                    gastoEnEdicion.setIdAccionForm(idAccionForm);

                    int resultadoValidacion = ContabilidadController.validarTipoImporte(gastoEnEdicion);
                    if (resultadoValidacion == 0) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error", "El importe no puede estar vacío.");
                        return;
                    }
                    if (resultadoValidacion == -1) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Aviso de Devolución",
                                "Has introducido un importe negativo. Esto significa que estás registrando una devolución o abono. Si es correcto, puedes ignorar este aviso.");
                    }
                    ContabilidadController.actualizarGasto(gastoEnEdicion.getId(), gastoEnEdicion);
                    gastoEnEdicion = null;
                }
                limpiarFormulario();
                cargarTabla();
                gastos.refresh();
                mostrarFormulario(false);
                AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Confirmación", "Cambios realizados con éxito.");

            } catch (Exception e) {
                System.err.println("Error al guardar: " + e.getMessage());
                AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Revisa que los datos introducidos sean correctos.");
            }
        });

        btnCancelar.setOnAction(event -> {
            if (!fieldIniciativa.getText().isEmpty() || !fieldImporte.getText().isEmpty()) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("AVISO", "Vas a salir del formulario sin guardar. ¿Estás seguro?");
                if (!confirmar) {
                    return;
                }
            }
            limpiarFormulario();
            mostrarFormulario(false);
        });


        btnBorrar.setOnAction(event -> {
            Gasto gastoBorrar = gastos.getSelectionModel().getSelectedItem();
            if (gastoBorrar != null) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("AVISO", "Se va a proceder a borrar el gasto seleccionado. ¿Quieres continuar?");
                if (confirmar) {
                    Integer idGastoBorrar = gastoBorrar.getId();
                    try {
                        ContabilidadController.borrarGasto(idGastoBorrar);
                        cargarTabla();
                        gastos.refresh();
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Gasto borrado correctamente.");
                        limpiarFormulario();
                        mostrarFormulario(false);

                    } catch (SQLException e) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Acción denegada",
                                "Revisa que este Gasto no tenga asociada ninguna Factura.\nDe ser así, debe eliminarse esta imputación primero.");
                    }
                }
            } else {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "No se ha seleccionado ningún Gasto.");
            }
        });

    }
}

