package controller;

import model.Sociedad;
import model.Usuario;

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
