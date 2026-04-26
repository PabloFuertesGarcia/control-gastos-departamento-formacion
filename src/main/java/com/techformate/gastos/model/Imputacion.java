package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Imputacion implements RegistroFinanciero{
    private Integer idGasto;
    private Integer idFactura;
    private BigDecimal importeFacturaGasto;

    @Override
    public String getConceptoVisual() {
        return "";
    }

    @Override
    public BigDecimal getImporte() {
        return this.getImporteFacturaGasto();
    }

    @Override
    public BigDecimal getAsignadosaEste(){
        return BigDecimal.valueOf(0);
    }

    @Override
    public BigDecimal getRestante(){
        return BigDecimal.valueOf(0);
    }
}
