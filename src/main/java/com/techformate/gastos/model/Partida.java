package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Partida {
    private Integer id;
    private Integer idSociedadInterna;
    private BigDecimal importe;
    private String tipoGasto;
    private String iniciativa;

}
