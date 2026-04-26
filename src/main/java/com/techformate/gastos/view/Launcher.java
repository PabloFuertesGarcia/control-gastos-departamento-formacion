package com.techformate.gastos.view;

import com.techformate.gastos.controller.ContabilidadController;
import com.techformate.gastos.controller.PresupuestoController;
import com.techformate.gastos.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
            System.out.println("3-Añadir Factura");
            System.out.println("32-Mostrar Facturas");
            System.out.println("33-Actualizar Factura");
            System.out.println("34-Borrar Factura");

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
                case 12 -> {System.out.println("Mostrar partidas");
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
                //******************************************************************************************************
                //******************************************************************************************************

                case 2 -> {System.out.println("2-Añadir Gasto");
                    Gasto gastoNuevo = construirGasto();
                    ContabilidadController.registrarGasto(gastoNuevo);
                    System.out.println("Gasto añadido a la BBDD");
                }
                //------------------------------------------------------------------------------------------------------
                case 22 -> {System.out.println("22-Mostrar Gastos");
                    ContabilidadController.obtenerTodasGastos();
                }
                //------------------------------------------------------------------------------------------------------
                case 23 -> {System.out.println("23-Actualizar Gasto");
                    ContabilidadController.obtenerTodasGastos();
                    System.out.println("Id del Gasto a actualizar:");
                    Integer idActualizar = sc.nextInt();
                    Gasto gastoActualizado = construirGasto();
                    ContabilidadController.actualizarGasto(idActualizar, gastoActualizado);
                }
                //------------------------------------------------------------------------------------------------------
                case 24 -> {System.out.println("24-Borrar gastos");
                    ContabilidadController.obtenerTodasGastos();
                    System.out.println("\nId del Gasto a borrar:");
                    Integer idBorrar = sc.nextInt();
                    ContabilidadController.borrarGasto(idBorrar);
                }
                //******************************************************************************************************
                //******************************************************************************************************

                case 3 -> {
                    System.out.println("3-Añadir Factura");
                    Factura facturaNueva = construirFactura();
                    BigDecimal restante = facturaNueva.getImporteTotal();
                    ArrayList<Imputacion> imputacionesTemporales = new ArrayList<>();

                    do {
                        System.out.println("Elige el Gasto al que se imputa esa factura:");
                        ContabilidadController.obtenerTodasGastos();
                        Integer idGasto = sc.nextInt();

                        System.out.println("Cuanto importe de la factura va a ese Gasto");
                        BigDecimal importeImputado = sc.nextBigDecimal();

                        if (importeImputado.compareTo(restante)>0){
                            System.out.println("No puedes imputar " + importeImputado + " porque solo te quedan " + restante);
                        }

                        Imputacion nuevaImputacion = new Imputacion(idGasto,null,importeImputado);
                        imputacionesTemporales.add(nuevaImputacion);

                        restante = restante.subtract(importeImputado);

                    } while (restante.compareTo(BigDecimal.valueOf(0)) >0);

                    ContabilidadController.registrarFacturaImputada(facturaNueva,imputacionesTemporales);
                }
                //------------------------------------------------------------------------------------------------------
                case 32 -> {
                    System.out.println("32-Mostrar Facturas");
                    ContabilidadController.obtenerTodasFacturas();
                }
                //------------------------------------------------------------------------------------------------------
                case 33 -> {System.out.println("33-Actualizar Factura");
                    ContabilidadController.obtenerTodasFacturas();
                    System.out.println("Id de la Factura a actualizar:");
                    Integer idActualizar = sc.nextInt();
                    Factura facturaActualizada = construirFactura();
                    ContabilidadController.actualizarFactura(idActualizar,facturaActualizada);
                }
                //------------------------------------------------------------------------------------------------------
                case 34 -> {System.out.println("24-Borrar factura");
                    ContabilidadController.obtenerTodasFacturas();
                    System.out.println("\nId de la Factura a borrar:");
                    Integer idBorrar = sc.nextInt();
                    ContabilidadController.borrarFactura(idBorrar);
                }
                //******************************************************************************************************
                //******************************************************************************************************

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
        PresupuestoController.obtenerTodas();
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

    public static Factura construirFactura(){
        Scanner sc = new Scanner(System.in);

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
        return facturaNueva;
    }
    
}
