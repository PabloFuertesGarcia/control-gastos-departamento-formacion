package com.techformate.gastos.controller.vistas;

import com.techformate.gastos.controller.service.ContabilidadController;
import com.techformate.gastos.model.Factura;
import com.techformate.gastos.model.Gasto;
import com.techformate.gastos.model.Imputacion;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ImputacionesViewController implements Initializable {

    public static Factura facturaEnProceso = null;
    private BigDecimal restante = BigDecimal.ZERO;
    private ObservableList<Imputacion> imputacionesTemporales = FXCollections.observableArrayList();
    private Imputacion imputacionEnEdicion = null;

    @FXML private Label NumFactura;
    @FXML private Button btnAnadir, btnBorrar, btnCancelar, btnEditar, btnGuardar, btnVolver;

    @FXML private TableView<Imputacion> imputaciones;
    @FXML private TableColumn<Imputacion, String> colGastoAsociado;
    @FXML private TableColumn<Imputacion, BigDecimal> colImporte;

    @FXML private VBox contenedorFormulario;
    @FXML private Label tituloFormulario;
    @FXML private ComboBox<Gasto> cmboGastoAsociado;
    @FXML private TextField fieldImporte;
    @FXML private Label lblimporteRestante;

    //MÉTODOS DE CARGA DE DATOS E INTERFAZ VISUAL***********************************************************************

    private void mostrarFormulario(boolean mostrar) {
        contenedorFormulario.setVisible(mostrar);
        contenedorFormulario.setManaged(mostrar);
    }

    private void limpiarFormulario() {
        fieldImporte.clear();
        if (cmboGastoAsociado.getItems() != null) cmboGastoAsociado.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarFormulario(false);

        colGastoAsociado.setCellValueFactory(new PropertyValueFactory<>("conceptoGasto"));
        colImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));

        imputaciones.setItems(imputacionesTemporales);
        if (facturaEnProceso != null) {
            NumFactura.setText(facturaEnProceso.getNumFactura());

            if (facturaEnProceso.getId() != null) {
                List<Imputacion> imputacionesPrevias = ContabilidadController.obtenerImputacionesPorFactura(facturaEnProceso.getId());
                if (imputacionesPrevias != null && !imputacionesPrevias.isEmpty()) {
                    imputacionesTemporales.addAll(imputacionesPrevias);
                }
            }
            BigDecimal dineroYaImputado = BigDecimal.ZERO;
            for (Imputacion imp : imputacionesTemporales) {
                dineroYaImputado = dineroYaImputado.add(imp.getImporte());
            }
            restante = facturaEnProceso.getImporte().subtract(dineroYaImputado);
            actualizarLabelRestante();
        }

        cargarDesplegables();
        actions();
    }

    private void actualizarLabelRestante() {
        lblimporteRestante.setText(restante.toString() + " €");
    }

    public void cargarDesplegables() {
        java.util.List<Gasto> gastosList = ContabilidadController.obtenerTodosGastosJoin();
        if (gastosList != null) {
            cmboGastoAsociado.setItems(FXCollections.observableArrayList(gastosList));
        }
    }

    //MÉTODOS DE ACCIONES **********************************************************************************************

    private void actions() {

        //NAVEGACIÓN

        btnVolver.setOnAction(event -> {
            if (restante.compareTo(BigDecimal.ZERO) > 0) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("ATENCIÓN: Imputación Incompleta",
                        "La factura no se ha guardado porque aún quedan " + restante + " € por imputar.\n\nSi vuelves atrás ahora, se perderán todos los datos y esta factura no se registrará.\n\n¿Estás seguro de que quieres salir?");
                if (!confirmar) {
                    return;
                }
            }
            facturaEnProceso = null;
            NavegacionController.cambiarVista(btnVolver, NavegacionController.Ruta.FACTURAS);
        });

        //INTERACCIÓN CON EL CONTENIDO

        btnAnadir.setOnAction(event -> {
            if (restante.compareTo(BigDecimal.ZERO) == 0) {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Aviso", "Ya has imputado todo el importe de esta factura.");
                return;
            }
            tituloFormulario.setText("AÑADIR IMPUTACIÓN");
            mostrarFormulario(true);
            imputacionEnEdicion = null;
            limpiarFormulario();
        });

        btnEditar.setOnAction(event -> {
            imputacionEnEdicion = imputaciones.getSelectionModel().getSelectedItem();

            if (imputacionEnEdicion != null) {
                tituloFormulario.setText("EDITAR IMPUTACIÓN");
                mostrarFormulario(true);
                fieldImporte.setText(imputacionEnEdicion.getImporte().toString());
                for (Gasto gasto : cmboGastoAsociado.getItems()) {
                    if (gasto.getId().equals(imputacionEnEdicion.getIdGasto())) {
                        cmboGastoAsociado.setValue(gasto);
                        break;
                    }
                }
            } else {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Por favor, selecciona una fila de la tabla primero.");
            }
        });

        btnGuardar.setOnAction(event -> {
            try {
                BigDecimal importeNuevo = new BigDecimal(fieldImporte.getText().replace(",", "."));
                Gasto gastoSeleccionado = cmboGastoAsociado.getValue();

                if (gastoSeleccionado == null) {
                    AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Debes seleccionar un Gasto.");
                    return;
                }

                if (imputacionEnEdicion == null) {
                    if (importeNuevo.compareTo(restante) > 0) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Exceso", "No puedes imputar " + importeNuevo + "€ porque solo te quedan " + restante + "€");
                        return;
                    }

                    Imputacion nueva = new Imputacion(gastoSeleccionado.getId(), null, importeNuevo);
                    imputacionesTemporales.add(nueva);
                    restante = restante.subtract(importeNuevo);

                } else {
                    BigDecimal diferencia = importeNuevo.subtract(imputacionEnEdicion.getImporte());
                    if (diferencia.compareTo(restante) > 0) {
                        AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Exceso", "No puedes aumentar el importe en " + diferencia + "€ porque solo tienes " + restante + "€ disponibles.");
                        return;
                    }
                    imputacionEnEdicion.setIdGasto(gastoSeleccionado.getId());
                    imputacionEnEdicion.setImporteFacturaGasto(importeNuevo);
                    restante = restante.subtract(diferencia);
                    imputaciones.refresh();
                    imputacionEnEdicion = null;
                }

                actualizarLabelRestante();
                limpiarFormulario();
                mostrarFormulario(false);

                if (restante.compareTo(BigDecimal.ZERO) == 0) {
                    boolean confirmar = AvisosUsuario.mostrarConfirmacion("Completado", "Has repartido todo el importe. ¿Deseas registrar la factura definitivamente en la base de datos?");
                    if (confirmar) {
                        if (facturaEnProceso.getId() == null || facturaEnProceso.getId() == 0) {
                            ContabilidadController.registrarFacturaImputada(facturaEnProceso, imputacionesTemporales);
                            AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Factura guardada correctamente en el sistema.");
                        } else {
                            ContabilidadController.actualizarFacturaImputada(facturaEnProceso, imputacionesTemporales);
                            AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Exito", "Factura actualizada correctamente.");
                        }
                        facturaEnProceso = null;
                        NavegacionController.cambiarVista(btnGuardar, NavegacionController.Ruta.FACTURAS);
                    }
                }

            } catch (Exception e) {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Revisa que los datos introducidos sean correctos.");
            }
        });

        btnBorrar.setOnAction(event -> {
            Imputacion imputacionSeleccionada = imputaciones.getSelectionModel().getSelectedItem();

            if (imputacionSeleccionada != null) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("AVISO","¿Seguro que quieres borrar esta imputación? El importe volverá a estar sin asignar.");
                if (confirmar) {
                    imputacionesTemporales.remove(imputacionSeleccionada);
                    restante = restante.add(imputacionSeleccionada.getImporte());
                    actualizarLabelRestante();
                    limpiarFormulario();
                    mostrarFormulario(false);
                }
            } else {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "No se ha seleccionado ninguna imputación.");
            }
        });

        btnCancelar.setOnAction(event -> {
            limpiarFormulario();
            mostrarFormulario(false);
        });
    }

}
