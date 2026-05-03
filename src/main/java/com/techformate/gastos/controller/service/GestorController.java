package com.techformate.gastos.controller.service;

import com.techformate.gastos.controller.dao.AccionFormDAO;
import com.techformate.gastos.controller.dao.SociedadesDAO;
import com.techformate.gastos.controller.dao.UsuarioDAO;
import com.techformate.gastos.model.*;

import java.util.List;
import java.util.Map;

public class GestorController {

    //-----------------------------------------------------------------------------------------------
    //USUARIOS
    //-----------------------------------------------------------------------------------------------

    public static Usuario usuarioActual = null;

   public static boolean iniciarSesion(String email, String contrasena) {
       UsuarioDAO usuarioDAO = new UsuarioDAO();
       Usuario usuarioEncontrado = usuarioDAO.usuarioAutentificar(email, contrasena);

       if (usuarioEncontrado != null) {
           usuarioActual = usuarioEncontrado;
           return true;
       }else return false;
   }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    //-----------------------------------------------------------------------------------------------
    //SOCIEDADES
    //-----------------------------------------------------------------------------------------------

    public static List<Sociedad> obtenerSociedadesInternas() {
        SociedadesDAO sociedadesDAO = new SociedadesDAO();
        return sociedadesDAO.realizarConsultaSociedadesInternas();
    }

    public static List<Sociedad> obtenerSociedadesProveedores() {
        SociedadesDAO sociedadesDAO = new SociedadesDAO();
        return sociedadesDAO.realizarConsultaSociedadesProveedores();
    }

    //-----------------------------------------------------------------------------------------------
    //ACCIONES FORMATIVAS
    //-----------------------------------------------------------------------------------------------

    public static List<AccionForm> obtenerTodasAccionesForm() {
        AccionFormDAO accionFormDAO = new AccionFormDAO();
        return accionFormDAO.realizarConsultaTodasAcciones();
    }

    // METODOS NO IMPLEMENTADOS EN EL MVP - Los dejo para una futura ampliación

    /*public static void registrarAccionForm(AccionForm accionForm) {
        AccionFormDAO accionFormDAO = new AccionFormDAO();
        accionFormDAO.realizarInsercionAccion(accionForm);
    }

        public static void actualizarAccionForm(Integer id, AccionForm accionForm) {
        AccionFormDAO accionFormDAO = new AccionFormDAO();
        accionFormDAO.realizarActualizacionAccionForm(id, accionForm);
    }

    public static void borrarAccionForm(Integer id) {
        AccionFormDAO accionFormDAO = new AccionFormDAO();
        accionFormDAO.realizarBorradoAccionForm(id);
    }

     */
}
