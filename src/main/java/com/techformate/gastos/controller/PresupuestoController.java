package com.techformate.gastos.controller;

import com.techformate.gastos.controller.dao.PartidaDAO;
import com.techformate.gastos.model.DataRepository;
import com.techformate.gastos.model.Partida;
import com.techformate.gastos.model.RegistroFinanciero;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class PresupuestoController {

    //Código para calcular el id más alto, eliminar cuando tengamos los DAO
    public static int idMasAltoRegistro(Map registroAnalizar) {
        Optional<Map.Entry<Integer, RegistroFinanciero>> idMasAlto = registroAnalizar.entrySet()
                .stream()
                .max(Map.Entry.comparingByKey());
        Integer id;
        id = idMasAlto.map(integerRegistroFinancieroEntry -> integerRegistroFinancieroEntry.getKey() + 1).orElse(1);
        System.out.println(idMasAlto);
        System.out.println(id);
        return id;
    }

    public static void registrarPartida(Partida partida) {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarInsercionPartida(partida);
        System.out.println("Partida insertada en el Budget");
    }

    public static void obtenerTodas() {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarConsultaTodasPartidas();
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
