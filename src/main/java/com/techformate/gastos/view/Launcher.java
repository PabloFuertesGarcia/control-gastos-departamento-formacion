package com.techformate.gastos.view;

import com.techformate.gastos.controller.ContabilidadController;
import com.techformate.gastos.controller.PresupuestoController;
import com.techformate.gastos.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) {

        //Application.launch(HelloApplication.class, args);

        System.out.println("Bienvenido");
        DataRepository.cargarDatosFicticios();
        Scanner sc = new Scanner(System.in);
        int option = 0;
        while (!(option == 9)) {
            option = 0;
            System.out.println("1-Añadir Partida Budget");
            System.out.println("12-Mostrar partidas");
            System.out.println("13-Actualizar partidas");
            System.out.println("14-Borrar partidas");
            System.out.println();
            System.out.println("2-Añadir Gasto");
            System.out.println("22-Mostrar Gastos");
            System.out.println("23-Actualizar Gastos");
            System.out.println("24-Borrar gastos");
            System.out.println();
            System.out.println("5-Añadir Factura");
            System.out.println("6-Mostrar Facturas");
            System.out.println();
            System.out.println("7-Añadir AccionForm");
            System.out.println("8-Mostrar Acciones Formativas");
            System.out.println();
            System.out.println("9-Salir");
            //------------------------------------------------------------------------------------------------------
            switch (option = sc.nextInt()) {
                case 1 -> {System.out.println("Añadir Partida Budget");

                    Partida partidaNueva = construirPartida();
                    PresupuestoController.registrarPartida(partidaNueva);
                }
                //------------------------------------------------------------------------------------------------------
                case 12 -> {
                    System.out.println("Mostrar partidas");
                    PresupuestoController.obtenerTodas();
                }
                //------------------------------------------------------------------------------------------------------
                case 13 -> {System.out.println("13-Actualizar partidas");
                    PresupuestoController.obtenerTodas();
                    System.out.println("Id de la partida a actualizar:");
                    Integer idActualizar = sc.nextInt();
                    Partida partidaActualizada = construirPartida();
                    PresupuestoController.actualizarPartida(idActualizar, partidaActualizada);
                }
                //------------------------------------------------------------------------------------------------------
                case 14 -> {System.out.println("14-Borrar partida");
                    PresupuestoController.obtenerTodas();
                    System.out.println("\nId de la partida a borrar:");
                    Integer idBorrar = sc.nextInt();
                    PresupuestoController.borrarPartida(idBorrar);
                }
                //------------------------------------------------------------------------------------------------------
                //------------------------------------------------------------------------------------------------------
                case 2 -> {System.out.println("2-Añadir Gasto");
                    Gasto gastoNuevo = construirGasto();
                    ContabilidadController.registrarGasto(gastoNuevo);
                    System.out.println("Partida insertada en el Budget");
                }
                //------------------------------------------------------------------------------------------------------
                case 22 -> {System.out.println("22-Mostrar Gastos");
                    ContabilidadController.obtenerTodasGastos();


                }
                //------------------------------------------------------------------------------------------------------
                case 23 -> {System.out.println("23-Actualizar Gastos");
                }
                case 24 -> {System.out.println("24-Borrar gastos");
                }
                //------------------------------------------------------------------------------------------------------
                //------------------------------------------------------------------------------------------------------
                case 5 -> {
                    System.out.println("5-Añadir Factura");

                    System.out.println("Elegir Sociedad Proveedor (lista:)");
                    for (Sociedad soc : DataRepository.tablaSociedades.values()) {
                        System.out.println(soc);
                    }
                    Integer idSociedadProveedor = sc.nextInt();

                    System.out.println("Elegir Sociedad de cargo (lista:)");
                    for (Sociedad soc : DataRepository.tablaSociedades.values()) {
                        System.out.println(soc);
                    }
                    Integer idSociedadInterna = sc.nextInt();

                    System.out.println("Numero de Factura");
                    String numFactura = sc.next();

                    System.out.println("Introduce la fecha (formato DD/MM/YYYY):");
                    LocalDate fecha = null;
                    String fechaString = sc.next();
                    try {
                        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        fecha = LocalDate.parse(fechaString, formato);
                    } catch (DateTimeParseException e) {
                        System.out.println("Error: Formato de fecha no válido. Usa DD/MM/YYYY.");
                    }

                    System.out.println("Importe");
                    BigDecimal importefactura = sc.nextBigDecimal();

                    Factura facturaNueva = new Factura(null,idSociedadProveedor,idSociedadInterna,numFactura,fecha,importefactura);
                    ContabilidadController.registrarFactura(null, facturaNueva);
                    System.out.println("Partida insertada en el Budget");
                }
                //------------------------------------------------------------------------------------------------------
                case 6 -> {
                    System.out.println("6-Mostrar Facturas");
                    for (Factura factura : DataRepository.tablafacturas.values()) {
                        System.out.println(factura);
                    }
                    System.out.println();
                }
                //------------------------------------------------------------------------------------------------------

                case 7 -> {
                    System.out.println("7-Añadir AccionForm");
                }
                //------------------------------------------------------------------------------------------------------

                case 8 -> {

                    System.out.println("8-Mostrar Acciones Formativas");
                    for (AccionForm accionForm : DataRepository.tablaAcciones.values()) {
                        System.out.println(accionForm);
                    }
                    System.out.println();
                }
                //------------------------------------------------------------------------------------------------------

                case 9 -> {
                    System.out.println("9-Salir");
                    return;
                }
                //------------------------------------------------------------------------------------------------------
                default -> System.out.println("Opción no válida, inténtalo de nuevo.");
            }
        }
    }

    public static Partida construirPartida(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Sociedad de cargo");
        System.out.println("Lista:");
        for (Sociedad soc : DataRepository.tablaSociedades.values()) {
            System.out.println(soc);
        }
        Integer idSociedadInterna = sc.nextInt();

        System.out.println("Importe");
        BigDecimal importe = sc.nextBigDecimal();

        System.out.println("Tipo gasto");
        String tipoGasto = sc.next();

        System.out.println("Iniciativa");
        String iniciativa = sc.next();

        Partida partidaNueva = new Partida(null, idSociedadInterna, importe, tipoGasto, iniciativa);
        return partidaNueva;
    }

    public static Gasto construirGasto(){
        Scanner sc = new Scanner(System.in);

        System.out.println("Elegir usuario (lista):");
        for (Usuario usuario : DataRepository.tablaUsuarios.values()) {
            System.out.println(usuario);
        }
        Integer idUsuario = sc.nextInt();


        System.out.println("Elegir partida (lista):");
        for (Partida partida : DataRepository.tablaPartidas.values()) {
            System.out.println(partida);
        }
        Integer idPartida = sc.nextInt();

        System.out.println("Elegir AccionFormativa (lista):");
        for (AccionForm accionForm : DataRepository.tablaAcciones.values()) {
            System.out.println(accionForm);
        }
        Integer idAccion = sc.nextInt();

        System.out.println("Elegir Sociedad de cargo (lista:)");
        for (Sociedad soc : DataRepository.tablaSociedades.values()) {
            System.out.println(soc);
        }
        Integer idSociedadInterna = sc.nextInt();

        System.out.println("Importe");
        BigDecimal importeGasto = sc.nextBigDecimal();

        System.out.println("Tipo gasto");
        String tipoGasto = sc.next();

        System.out.println("ConceptoGasto");
        String concepto = sc.next();

        Gasto gastoNuevo = new Gasto(null,idUsuario,idPartida,idAccion,idSociedadInterna,importeGasto,tipoGasto,concepto,"Comprometido");
        return gastoNuevo;
    }
}
