package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Factura {
    private Integer id;
    private Integer idSociedadProveedor;
    private Integer idSociedadInterna;
    private String numFactura;
    private LocalDate fechaEmision;
    private BigDecimal importe;
}
