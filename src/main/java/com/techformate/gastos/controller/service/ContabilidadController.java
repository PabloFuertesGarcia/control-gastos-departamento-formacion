package com.techformate.gastos.controller.service;

import com.techformate.gastos.controller.dao.*;
import com.techformate.gastos.model.*;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ContabilidadController {


    public static int validarTipoImporte(RegistroFinanciero registro) {
        BigDecimal importe = registro.getImporte();
        if (importe == null) return 0;
        if (importe.compareTo(BigDecimal.ZERO) < 0) {
            return -1;
        }
        return 1;
    }
    //-----------------------------------------------------------------------------------------------

    public static void registrarGasto(Gasto gasto) {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarInsercionGasto(gasto);
    }

    public static List<Gasto> obtenerTodosGastosJoin() {
        GastoDAO gastoDAO = new GastoDAO();
        return gastoDAO.realizarConsultaTodosGastosJoin();
    }

    public static void actualizarGasto(Integer id, Gasto gasto) {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarActualizaciongasto(id, gasto);
    }

    public static void borrarGasto(Integer id) throws SQLException{
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarBorradoGasto(id);
    }
    //-----------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------

    public static void registrarFacturaImputada(Factura factura, ObservableList<Imputacion> imputacionesTemporales) {
        FacturaDAO facturaDAO = new FacturaDAO();
        ImputacionDAO imputacionDAO = new ImputacionDAO();

        GestorDB gestorDB = new GestorDB();
        Connection conexionCompartida = null;

        try {
            conexionCompartida = gestorDB.getConnection();
            if (gestorDB.conexionAbierta()) {

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


    public static List<Factura> obtenerTodasFacturasJoin() {
        FacturaDAO facturaDAO = new FacturaDAO();
        return facturaDAO.realizarConsultaTodasFacturasJoin();
    }


    public static void actualizarFacturaImputada(Factura factura, ObservableList<Imputacion> imputacionesTemporales) {
        FacturaDAO facturaDAO = new FacturaDAO();
        ImputacionDAO imputacionDAO = new ImputacionDAO();

        facturaDAO.realizarActualizacionFactura(factura.getId(), factura);
        imputacionDAO.realizarBorradoTodasPorFactura(factura.getId());
        GestorDB gestorDB = new GestorDB();
        Connection conexion = null;
        try {
            conexion = gestorDB.getConnection();
            if (conexion != null) {
                for (Imputacion imp : imputacionesTemporales) {
                    imp.setIdFactura(factura.getId());
                    imputacionDAO.realizarInsercionImputacion(imp, conexion);
                }
            }
        } finally {
            if (conexion != null) {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void borrarFactura(Integer id) {
        FacturaDAO facturaDAO = new FacturaDAO();
        facturaDAO.realizarBorradoFactura(id);
    }

//-----------------------------------------------------------------------------------------------

    public static List<Imputacion> obtenerImputacionesPorFactura(Integer idFactura) {
        ImputacionDAO imputacionDAO = new ImputacionDAO();
        return imputacionDAO.realizarConsultaImputacionesPorFactura(idFactura);
    }

}
