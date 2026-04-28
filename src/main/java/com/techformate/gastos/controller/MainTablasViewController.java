package com.techformate.gastos.controller;

import com.techformate.gastos.model.Partida;
import com.techformate.gastos.model.Sociedad;
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
import java.util.ResourceBundle;

public class MainTablasViewController implements Initializable {

    private Partida partidaEnEdicion = null;

    @FXML
    private TableView<Partida> partidas;
    @FXML
    private TableColumn<Partida, Integer> sociedadInterna;
    @FXML
    private TableColumn<Partida, Integer> importe;
    @FXML
    private TableColumn<Partida, Integer> iniciativa;
    @FXML
    private TableColumn<Partida, Integer> tipoGasto;

    @FXML
    private VBox contenedorFormulario;
    @FXML
    private Label tituloFormulario;

    @FXML
    private Button btnAnadir;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCancelar;


    @FXML
    private ComboBox<Sociedad> cmboSociedadInterna;
    @FXML
    private TextField fieldImporte;
    @FXML
    private TextField fieldTipoGasto;
    @FXML
    private TextField fieldIniciativa;

    public void cargarTabla() {
        List<Partida> partidasList = PresupuestoController.obtenerTodas();
        if (partidas != null) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mostrarFormulario(false);
        sociedadInterna.setCellValueFactory(new PropertyValueFactory<>("idSociedadInterna"));
        importe.setCellValueFactory(new PropertyValueFactory<>("importe"));
        tipoGasto.setCellValueFactory(new PropertyValueFactory<>("tipoGasto"));
        iniciativa.setCellValueFactory(new PropertyValueFactory<>("iniciativa"));

        cargarTabla();
        cargarCmbSociedades();
        actions();
    }

    private void actions() {

        btnAnadir.setOnAction(event -> {
            System.out.println("Btn anadir pulsado");
            tituloFormulario.setText("AÑADIR REGISTRO");
            mostrarFormulario(true);
            partidaEnEdicion = null;
            fieldImporte.clear();
            fieldTipoGasto.clear();
            cmboSociedadInterna.getSelectionModel().clearSelection();
            fieldIniciativa.clear();
        });

        btnEditar.setOnAction(event -> {
            mostrarFormulario(true);
            tituloFormulario.setText("EDITAR REGISTRO");


            partidaEnEdicion = partidas.getSelectionModel().getSelectedItem();

            if (partidaEnEdicion != null) {
                fieldImporte.setText(partidaEnEdicion.getImporte().toString());
                fieldTipoGasto.setText(partidaEnEdicion.getTipoGasto());

                for (Sociedad sociedad : cmboSociedadInterna.getItems()) {
                    if (sociedad.getId().equals(partidaEnEdicion.getIdSociedadInterna())) {
                        cmboSociedadInterna.setValue(sociedad);
                    }
                }
                fieldIniciativa.setText(partidaEnEdicion.getIniciativa());
            } else {
                System.out.println("Por favor, selecciona una fila de la tabla primero.");
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
                    PresupuestoController.registrarPartida(nuevaPartida);
                } else {
                    partidaEnEdicion.setImporte(importeNumerico);
                    partidaEnEdicion.setTipoGasto(tipoGasto);
                    partidaEnEdicion.setIdSociedadInterna(idSociedad);
                    partidaEnEdicion.setIniciativa(iniciativa);

                    PresupuestoController.actualizarPartida(partidaEnEdicion.getId(), partidaEnEdicion);
                    partidaEnEdicion = null;
                }

                fieldImporte.clear();
                fieldTipoGasto.clear();
                fieldIniciativa.clear();
                cmboSociedadInterna.getSelectionModel().clearSelection();

                cargarTabla();
                partidas.refresh();

                mostrarFormulario(false);

            } catch (Exception e) {
                System.err.println("Error al guardar: Revisa que los datos sean correctos.");
                e.printStackTrace();
            }
        });

        btnCancelar.setOnAction(event -> {
            fieldImporte.clear();
            fieldTipoGasto.clear();
            fieldIniciativa.clear();
            cmboSociedadInterna.getSelectionModel().clearSelection();

            mostrarFormulario(false);
        });


        btnBorrar.setOnAction(event -> {

            Partida partidaBorrar = partidas.getSelectionModel().getSelectedItem();

            if (partidaBorrar != null) {
                Integer idPartidaBorrar = partidaBorrar.getId();
                PresupuestoController.borrarPartida(idPartidaBorrar);
                cargarTabla();
                System.out.println("Partida borrada correctamente");
            } else {
                System.out.println("No se ha seleccionado ninguna Partida");
            }
            partidas.refresh();
        });
    }
}
