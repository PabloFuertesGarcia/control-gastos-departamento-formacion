package com.techformate.gastos.controller;

import com.techformate.gastos.controller.dao.GastoDAO;
import com.techformate.gastos.controller.dao.PartidaDAO;
import com.techformate.gastos.controller.dao.SociedadesDAO;
import com.techformate.gastos.model.Partida;
import com.techformate.gastos.model.Sociedad;
import com.techformate.gastos.model.Usuario;

import java.util.List;
import java.util.Map;

public class GestorController {

    private Map<Integer, Usuario> tablaUsuarios;
    private Map<Integer, Sociedad> tablaSociedades;

    public GestorController(Map<Integer, Usuario> tablaUsuarios, Map<Integer, Sociedad> tablaSociedades) {
        this.tablaUsuarios = tablaUsuarios;
        this.tablaSociedades = tablaSociedades;
    }

    public Map<Integer, Usuario> getTablaUsuarios() {
        return tablaUsuarios;
    }

    public Map<Integer, Sociedad> getTablaSociedades() {
        return tablaSociedades;
    }

    public static List<Sociedad> obtenerTodasSociedades() {
        SociedadesDAO sociedadesDAO = new SociedadesDAO();
        return sociedadesDAO.realizarConsultaTodasSociedades();
    }

    public static List<Sociedad> obtenerSociedadesInternas() {
        SociedadesDAO sociedadesDAO = new SociedadesDAO();
        return sociedadesDAO.realizarConsultaSociedadesInternas();
    }

    public static List<Sociedad> obtenerSociedadesProveedores() {
        SociedadesDAO sociedadesDAO = new SociedadesDAO();
        return sociedadesDAO.realizarConsultaSociedadesProveedores();
    }

    public void crearUsuario(Usuario usuario){
        tablaUsuarios.put(usuario.getId(), usuario);
    }

    public void modificarUsuario(Usuario usuario){
        tablaUsuarios.replace(usuario.getId(),usuario);
    }

    public void crearSociedad(Sociedad sociedad){
        tablaSociedades.put(sociedad.getId(), sociedad);
    }

    public void modificarSociedad(Sociedad sociedad){
        tablaSociedades.replace(sociedad.getId(),sociedad);
    }

}
