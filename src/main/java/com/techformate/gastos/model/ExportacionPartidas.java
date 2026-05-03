package com.techformate.gastos.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "presupuesto_existente")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportacionPartidas {

    @XmlElement(name = "partida")
    private List<Partida> listaPartidas;

    public ExportacionPartidas() {
    }

    public ExportacionPartidas(List<Partida> listaPartidas) {
        this.listaPartidas = listaPartidas;
    }
}