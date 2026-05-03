package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class Factura implements RegistroFinanciero {
    private Integer id;
    private Integer idSociedadProveedor;
    private String nombreSociedadProveedor;

    private Integer idSociedadInterna;
    private String nombreSociedadInterna;

    private String numFactura;
    private LocalDate fechaEmision;
    private BigDecimal importe;

    @Override
    public String getConceptoVisual() {
        return "Factura: " + this.numFactura;
    }

}
