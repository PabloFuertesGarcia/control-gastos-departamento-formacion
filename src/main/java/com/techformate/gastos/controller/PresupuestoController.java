package com.techformate.gastos.controller;

import com.techformate.gastos.controller.dao.PartidaDAO;
import com.techformate.gastos.model.Partida;
import com.techformate.gastos.model.RegistroFinanciero;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PresupuestoController {


    public static void registrarPartida(Partida partida) {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarInsercionPartida(partida);
        System.out.println("Partida insertada en el Budget");
    }

    public static List<Partida> obtenerTodas() {
        PartidaDAO partidaDAO = new PartidaDAO();
        return partidaDAO.realizarConsultaTodasPartidas();
    }

    public static void actualizarPartida(Integer id, Partida partida) {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarActualizacionPartida(id, partida);
    }

    public static void borrarPartida(Integer id){
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarBorradoPartida(id);
    }
}
