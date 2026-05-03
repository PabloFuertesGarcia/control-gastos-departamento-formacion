package com.techformate.gastos.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)

public class Partida implements RegistroFinanciero{

    @XmlAttribute(name = "id")
    private Integer id;

    @XmlAttribute(name = "idSociedadInterna")
    private Integer idSociedadInterna;

    @XmlAttribute(name = "nombreSociedadInterna")
    private String nombreSociedadInterna;

    @XmlAttribute(name = "importe")
    private BigDecimal importe;

    @XmlAttribute(name = "tipoGasto")
    private String tipoGasto;

    @XmlAttribute(name = "iniciativa")
    private String iniciativa;

    public Partida(Integer id, Integer idSociedadInterna, String nombreSociedadInterna, BigDecimal importe, String tipoGasto, String iniciativa) {
        this.id = id;
        this.idSociedadInterna = idSociedadInterna;
        this.nombreSociedadInterna = nombreSociedadInterna;
        this.importe = importe;
        this.tipoGasto = tipoGasto;
        this.iniciativa = iniciativa;
    }

    public Partida(Integer id, Integer idSociedadInterna, BigDecimal importe, String tipoGasto, String iniciativa) {
        this.id = id;
        this.idSociedadInterna = idSociedadInterna;
        this.importe = importe;
        this.tipoGasto = tipoGasto;
        this.iniciativa = iniciativa;
    }

    @Override
    public String getConceptoVisual() {
        return "Partida: " + this.iniciativa;
    }

    @Override
    public String toString() {
        return iniciativa;
    }
}
