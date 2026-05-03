package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Imputacion implements RegistroFinanciero{
    private Integer idGasto;
    private String conceptoGasto;
    private Integer idFactura;
    private String numFactura;
    private BigDecimal importeFacturaGasto;

    public Imputacion(Integer idGasto, Integer idFactura, BigDecimal importeFacturaGasto) {
        this.idGasto = idGasto;
        this.idFactura = idFactura;
        this.importeFacturaGasto = importeFacturaGasto;
    }

    @Override
    public String getConceptoVisual() {
        return "Imputación (Fac. " + this.numFactura + ")";
    }

    @Override
    public BigDecimal getImporte() {
        return this.importeFacturaGasto;
    }

    public void setIdGasto(Integer idGasto) {
        this.idGasto = idGasto;
    }

    public void setConceptoGasto(String conceptoGasto) {
        this.conceptoGasto = conceptoGasto;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public void setImporteFacturaGasto(BigDecimal importeFacturaGasto) {
        this.importeFacturaGasto = importeFacturaGasto;
    }
}
