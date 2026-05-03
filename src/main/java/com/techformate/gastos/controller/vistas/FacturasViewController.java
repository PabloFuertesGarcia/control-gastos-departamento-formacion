package com.techformate.gastos.controller.vistas;

import com.techformate.gastos.controller.service.ContabilidadController;
import com.techformate.gastos.controller.service.GestorController;
import com.techformate.gastos.model.Factura;
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
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FacturasViewController implements Initializable {

    private Factura facturaEnEdicion = null;

    @FXML private TableView<Factura> facturas;
    @FXML private TableColumn<Factura, LocalDate> colFecha;
    @FXML private TableColumn<Factura, String> colNumFactura, colProveedor, colSociedadInterna;
    @FXML private TableColumn<Factura, BigDecimal> colImporte;

    @FXML private Button btnAnadir, btnBorrar, btnCancelar, btnEditar, btnGuardar, btnIrFacturas, btnIrGastos, btnIrPartidas, btnVolver;

    @FXML private VBox contenedorFormulario;
    @FXML private Label tituloFormulario;
    @FXML private ComboBox<Sociedad> cmboProveedor, cmboSociedadInterna;
    @FXML private TextField fieldImporte, fieldNumFactura;

    @FXML private DatePicker dPFecha;


    //MÉTODOS DE CARGA DE DATOS E INTERFAZ VISUAL***********************************************************************

    public void cargarTabla() {
        List<Factura> facturasList = ContabilidadController.obtenerTodasFacturasJoin();
        if (facturasList != null) {
            ObservableList<Factura> observableFacturas = FXCollections.observableArrayList(facturasList);
            facturas.setItems(observableFacturas);
        }
    }

    public void cargarDesplegables() {
        cargarCmbSociedades();
    }

    public void cargarCmbSociedades() {
        List<Sociedad> sociedadesInternasList = GestorController.obtenerSociedadesInternas();
        if (sociedadesInternasList != null) {
            ObservableList<Sociedad> observableSociedadesInternas = FXCollections.observableArrayList(sociedadesInternasList);
            cmboSociedadInterna.setItems(observableSociedadesInternas);
        }

        List<Sociedad> sociedadesProveedoresList = GestorController.obtenerSociedadesProveedores();
        if (sociedadesProveedoresList != null) {
            ObservableList<Sociedad> observableSociedadesProveedores = FXCollections.observableArrayList(sociedadesProveedoresList);
            cmboProveedor.setItems(observableSociedadesProveedores);
        }
    }

    private void mostrarFormulario(boolean mostrar) {
        contenedorFormulario.setVisible(mostrar);
        contenedorFormulario.setManaged(mostrar);
        if (mostrar) {
            btnGuardar.setText("SIGUIENTE (Imputar)");
        }
    }

    private void limpiarFormulario() {
        fieldImporte.clear();
        dPFecha.setValue(null);
        fieldNumFactura.clear();
        if(cmboSociedadInterna.getItems() != null) cmboSociedadInterna.getSelectionModel().clearSelection();
        if(cmboProveedor.getItems() != null) cmboProveedor.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mostrarFormulario(false);
        colSociedadInterna.setCellValueFactory(new PropertyValueFactory<>("nombreSociedadInterna"));
        colProveedor.setCellValueFactory(new PropertyValueFactory<>("nombreSociedadProveedor"));
        colNumFactura.setCellValueFactory(new PropertyValueFactory<>("numFactura"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaEmision"));
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

        btnIrGastos.setOnAction(event -> {
            NavegacionController.cambiarVista(btnIrGastos, NavegacionController.Ruta.GASTOS);
        });

        btnIrFacturas.setDisable(true);

        btnVolver.setOnAction(actionEvent -> {
            GestorController.cerrarSesion();
            NavegacionController.cambiarVista(btnVolver, NavegacionController.Ruta.LOGIN);
        });

        //INTERACCIÓN CON EL CONTENIDO

        btnAnadir.setOnAction(event -> {
            tituloFormulario.setText("AÑADIR REGISTRO");
            facturaEnEdicion = null;
            limpiarFormulario();
            mostrarFormulario(true);
        });

        btnEditar.setOnAction(event -> {
            facturaEnEdicion = facturas.getSelectionModel().getSelectedItem();

            if (facturaEnEdicion != null) {
                tituloFormulario.setText("EDITAR REGISTRO");
                mostrarFormulario(true);

                fieldImporte.setText(facturaEnEdicion.getImporte().toString());
                fieldNumFactura.setText(facturaEnEdicion.getNumFactura());
                dPFecha.setValue(facturaEnEdicion.getFechaEmision());

                if (facturaEnEdicion.getIdSociedadInterna() != null) {
                    for (Sociedad sociedad : cmboSociedadInterna.getItems()) {
                        if (sociedad.getId().equals(facturaEnEdicion.getIdSociedadInterna())) {
                            cmboSociedadInterna.setValue(sociedad);
                            break;
                        }
                    }
                }

                if (facturaEnEdicion.getIdSociedadProveedor() != null) {
                    for (Sociedad sociedad : cmboProveedor.getItems()) {
                        if (sociedad.getId().equals(facturaEnEdicion.getIdSociedadProveedor())) {
                            cmboProveedor.setValue(sociedad);
                            break;
                        }
                    }
                }

            } else {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Por favor, selecciona una fila de la tabla primero.");
            }
        });


        btnGuardar.setOnAction(event -> {
            try {
                BigDecimal importe = new BigDecimal(fieldImporte.getText().replace(",", "."));
                String numFactura = fieldNumFactura.getText();
                LocalDate fecha = dPFecha.getValue();
                Sociedad sociedadInterna = cmboSociedadInterna.getValue();
                Sociedad sociedadProveedor = cmboProveedor.getValue();

                if (sociedadInterna == null || sociedadProveedor == null || fecha == null) {
                    AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "Debes completar todos los campos y sociedades.");
                    return;
                }

                if (facturaEnEdicion == null) {
                    Factura nuevaFactura = new Factura(null, sociedadProveedor.getId(), sociedadProveedor.getNombreSociedad(), sociedadInterna.getId(), sociedadInterna.getNombreSociedad(), numFactura, fecha, importe);
                    ImputacionesViewController.facturaEnProceso = nuevaFactura;
                } else {
                    facturaEnEdicion.setIdSociedadInterna(sociedadInterna.getId());
                    facturaEnEdicion.setNombreSociedadInterna(sociedadInterna.getNombreSociedad());
                    facturaEnEdicion.setIdSociedadProveedor(sociedadProveedor.getId());
                    facturaEnEdicion.setNombreSociedadProveedor(sociedadProveedor.getNombreSociedad());
                    facturaEnEdicion.setNumFactura(numFactura);
                    facturaEnEdicion.setFechaEmision(fecha);
                    facturaEnEdicion.setImporte(importe);
                    ImputacionesViewController.facturaEnProceso = facturaEnEdicion;
                }
                int resultado = ContabilidadController.validarTipoImporte(ImputacionesViewController.facturaEnProceso);
                if (resultado == 0) {
                    AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error de Importe", "El importe no es válido.");
                    return;
                }
                if (resultado == -1) {
                    AvisosUsuario.mostrarAlerta(Alert.AlertType.INFORMATION, "Aviso de Devolución",
                            "Has introducido un importe negativo. Esto significa que estás registrando una devolución o abono. Si es correcto, puedes ignorar este aviso.");
                }
                NavegacionController.cambiarVista(btnGuardar, NavegacionController.Ruta.IMPUTACIONES);

            } catch (Exception e) {
                System.err.println("Error al procesar: " + e.getMessage());
                AvisosUsuario.mostrarAlerta(Alert.AlertType.ERROR, "Error", "Revisa que los datos introducidos sean correctos.");
            }
        });

        btnCancelar.setOnAction(event -> {
            if (!fieldNumFactura.getText().isEmpty() || !fieldImporte.getText().isEmpty() || dPFecha.getValue() != null) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("AVISO", "Vas a salir del formulario sin guardar. ¿Estás seguro?");
                if (!confirmar) {
                    return;
                }
            }
            limpiarFormulario();
            mostrarFormulario(false);
        });


        btnBorrar.setOnAction(event -> {
            Factura facturaBorrar = facturas.getSelectionModel().getSelectedItem();
            if (facturaBorrar != null) {
                boolean confirmar = AvisosUsuario.mostrarConfirmacion("AVISO", "Se va a proceder a borrar la factura seleccionada. ¿Quieres continuar?");
                if (confirmar) {
                    ContabilidadController.borrarFactura(facturaBorrar.getId());
                    cargarTabla();
                    facturas.refresh();
                    System.out.println("Factura borrada correctamente");
                    limpiarFormulario();
                    mostrarFormulario(false);
                }
            } else {
                AvisosUsuario.mostrarAlerta(Alert.AlertType.WARNING, "Aviso", "No se ha seleccionado ninguna Factura.");
            }
        });


    }
}
