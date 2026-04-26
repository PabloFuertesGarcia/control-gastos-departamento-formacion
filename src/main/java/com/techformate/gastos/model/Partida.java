package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Partida implements RegistroFinanciero{
    private Integer id;
    private Integer idSociedadInterna;
    private BigDecimal importe;
    private String tipoGasto;
    private String iniciativa;

    @Override
    public String getConceptoVisual() {
        return "";
    }

    @Override
    public BigDecimal getAsignadosaEste() {
        return null;
    }

    @Override
    public BigDecimal getRestante() {
        return RegistroFinanciero.super.getRestante();
    }
}
