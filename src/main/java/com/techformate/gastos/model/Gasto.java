package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Gasto {
    private Integer id;
    private Integer idUsuario;

    private Integer idPartida;
    private Integer idAccionForm;
    private Integer idSociedadInterna;

    private BigDecimal importe;
    private String tipoGasto;
    private String conceptoGasto;

    private String estado;
}
