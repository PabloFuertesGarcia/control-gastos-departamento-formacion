package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Factura {
    private Integer id;
    private Integer idSociedadProveedor;
    private Integer idSociedadInterna;
    private String numFactura;
    private Date fechaEmision;
    private BigDecimal importe;
}
