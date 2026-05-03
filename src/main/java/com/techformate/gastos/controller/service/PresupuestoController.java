package com.techformate.gastos.controller.service;

import com.techformate.gastos.controller.dao.PartidaDAO;
import com.techformate.gastos.model.ExportacionPartidas;
import com.techformate.gastos.model.Partida;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class PresupuestoController {


    public static void registrarPartida(Partida partida) {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarInsercionPartida(partida);
        System.out.println("Partida insertada en el Budget");
    }

    public static List<Partida> obtenerTodasPartidas() {
        PartidaDAO partidaDAO = new PartidaDAO();
        return partidaDAO.realizarConsultaTodasPartidas();
    }

    public static List<Partida> obtenerTodasJoin() {
        PartidaDAO partidaDAO = new PartidaDAO();
        return partidaDAO.realizarConsultaTodasPartidasJoin();
    }

    public static void actualizarPartida(Integer id, Partida partida) {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarActualizacionPartida(id, partida);
    }

    public static void borrarPartida(Integer id) throws SQLException {
        PartidaDAO partidaDAO = new PartidaDAO();
        partidaDAO.realizarBorradoPartida(id);
    }

    //FUNCIONALIDAD LENGUAJE DE MARCAS

    public static boolean exportarPartidasXML() {
        try {
            ExportacionPartidas datos = new ExportacionPartidas(obtenerTodasJoin());
            JAXBContext context = JAXBContext.newInstance(ExportacionPartidas.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "partidas_esquema.xsd");
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File fichero = new File("xml/partidas_presupuesto.xml");
            fichero.getParentFile().mkdirs();
            marshaller.marshal(datos, fichero);
            return true;

        } catch (JAXBException e) {
            System.out.println("Error en el XML / JAXB");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error en la gestión del fichero");
            e.printStackTrace();
        }
        return false;
    }

}
