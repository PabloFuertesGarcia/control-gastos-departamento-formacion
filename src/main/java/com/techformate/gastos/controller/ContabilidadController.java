package com.techformate.gastos.controller;

import com.techformate.gastos.controller.dao.GastoDAO;
import com.techformate.gastos.model.*;

public class ContabilidadController {


    public static void registrarGasto(Gasto gasto) {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarInsercionGasto(gasto);
        System.out.println("Gasto insertado en la BBDD");
    }

    public static void obtenerTodasGastos() {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarConsultaTodosGastos();
    }

    public static void actualizarGasto(Integer id, Gasto gasto) {
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarActualizaciongasto(id, gasto);
    }

    public static void borrarGasto(Integer id){
        GastoDAO gastoDAO = new GastoDAO();
        gastoDAO.realizarBorradoGasto(id);
    }

    public static void registrarImputación(int id, Imputacion imputacionNueva) {
    }

    public static void registrarFactura(Integer id, Factura facturaNueva) {
    }
}
