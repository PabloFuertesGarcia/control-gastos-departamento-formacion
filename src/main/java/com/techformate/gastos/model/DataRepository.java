package com.techformate.gastos.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

public class DataRepository {
    // Los hacemos estáticos para que sean los mismos en toda la App
    public static Map<Integer, Usuario> tablaUsuarios = new HashMap<>();
    public static Map<Integer, Sociedad> tablaSociedades = new HashMap<>();
    public static Map<Integer, Partida> tablaPartidas = new HashMap<>();
    public static Map<Integer, Gasto> tablaGastos = new HashMap<>();
    public static Map<Integer, Factura> tablafacturas = new HashMap<>();
    public static List<Imputacion> tablaImputaciones = new ArrayList<>();
    public static Map<Integer, AccionForm> tablaAcciones = new HashMap<>();


    // Este método lo llamaremos UNA SOLA VEZ al arrancar
    public static void cargarDatosFicticios() {
        tablaUsuarios.put(1, new Usuario(1, "pablofuertespr@gmail.com", "Pablo"));
        tablaUsuarios.put(2, new Usuario(2, "elena.martin@innovatech.es", "Elena"));

        tablaSociedades.put(1, new Sociedad(1, "Interna", "TechFormate S.L.", "B11223344"));
        tablaSociedades.put(2, new Sociedad(2, "Proveedor", "AWS EMEA", "N0000000A"));

        tablaAcciones.put(1, new AccionForm(1, "Liderazgo","Leadership Program"));

        tablaPartidas.put(1, new Partida(1, 1, new BigDecimal("4500.00"), "Formación técnica", "Microservicios Java"));

        tablaGastos.put(1, new Gasto(1, 1, 1, 1, 1, new BigDecimal("1200.00"), "Matrícula", "Bootcamp Java", "Comprometido"));

        tablaImputaciones.add(new Imputacion(1, 1, new BigDecimal("1200.00")));

        System.out.println("Datos ficticios cargados en memoria.");
    }

}