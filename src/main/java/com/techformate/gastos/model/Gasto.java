package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Gasto implements RegistroFinanciero{
    private Integer id;
    private Integer idUsuario;

    private Integer idPartida;
    private Integer idAccionForm;
    private Integer idSociedadInterna;

    private BigDecimal importeGasto;
    private String tipoGasto;
    private String conceptoGasto;

    private String estado;

    @Override
    public String getConceptoVisual() {
        return "";
    }

    @Override
    public BigDecimal getImporte() {
        return importeGasto;
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
