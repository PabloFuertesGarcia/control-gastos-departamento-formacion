package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Imputacion {
    private Integer idGasto;
    private Integer idFactura;
    private BigDecimal importeFacturaGasto;

}
