package com.techformate.gastos.controller;

import com.techformate.gastos.controller.dao.FacturaDAO;
import com.techformate.gastos.controller.dao.GastoDAO;
import com.techformate.gastos.controller.dao.GestorDB;
import com.techformate.gastos.controller.dao.ImputaciónDAO;
import com.techformate.gastos.model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContabilidadController {

    //-----------------------------------------------------------------------------------------------

    public static void registrarGasto(Gasto gasto) {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarInsercionGasto(gasto);
    }

    public static void obtenerTodasGastos() {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarConsultaTodosGastos();
    }

    public static void actualizarGasto(Integer id, Gasto gasto) {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarActualizaciongasto(id, gasto);
    }

    public static void borrarGasto(Integer id) {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarBorradoGasto(id);
    }
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------

    public static void registrarFacturaImputada(Factura factura, ArrayList<Imputacion> imputacionesTemporales) {
        FacturaDAO facturaDAO = new FacturaDAO();
        ImputaciónDAO imputacionDAO = new ImputaciónDAO();

        GestorDB gestorDB = new GestorDB();
        Connection conexionCompartida = null;

        try {
            conexionCompartida = gestorDB.getConnection();
            if (gestorDB.conexionAbierta()) {

                //apagamos para rollback en caso de error
                conexionCompartida.setAutoCommit(false);

                Integer idFactura = facturaDAO.realizarInsercionFactura(factura, conexionCompartida);
                System.out.println("Factura insertado en la BBDD");

                for (int i = 0; i < imputacionesTemporales.size(); i++) {
                    Imputacion imputacionEditando = imputacionesTemporales.get(i);
                    imputacionEditando.setIdFactura(idFactura);
                    imputacionDAO.realizarInsercionImputacion(imputacionEditando, conexionCompartida);
                }

                conexionCompartida.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (conexionCompartida != null) {
                try {
                    conexionCompartida.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conexionCompartida != null) {
                try {
                    conexionCompartida.setAutoCommit(true);
                    conexionCompartida.close();
                } catch (SQLException exx) {
                    exx.printStackTrace();
                }
            }
        }
    }


//-----------------------------------------------------------------------------------------------

    public static void obtenerTodasFacturas() {
        FacturaDAO facturaDAO = new FacturaDAO();
        facturaDAO.realizarConsultaTodasFacturas();
    }

    public static void actualizarFactura(Integer id, Factura factura) {
        FacturaDAO facturaDAO = new FacturaDAO();
        facturaDAO.realizarActualizacionFactura(id, factura);
    }

    public static void borrarFactura(Integer id) {
        FacturaDAO facturaDAO = new FacturaDAO();
        facturaDAO.realizarBorradoFactura(id);
    }

//-----------------------------------------------------------------------------------------------

}
