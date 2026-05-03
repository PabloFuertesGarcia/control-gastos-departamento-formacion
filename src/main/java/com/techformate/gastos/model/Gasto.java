package com.techformate.gastos.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor

public class Gasto implements RegistroFinanciero{

    private Integer id, idUsuario, idPartida, idAccionForm, idSociedadInterna;
    private String conceptoGasto, nombre, iniciativa, denominacionAccion, tipoCoste;
    private BigDecimal importe;
    private Estado estado;

    public Gasto(Integer id, Integer idPartida, Integer idAccionForm, Integer idUsuario, BigDecimal importe, String tipoCoste, String conceptoGasto, Estado estado, String iniciativa, String denominacionAccion, String nombre) {
        this.id = id;
        this.idPartida = idPartida;
        this.idAccionForm = idAccionForm;
        this.idUsuario = idUsuario;
        this.importe = importe;
        this.tipoCoste = tipoCoste;
        this.conceptoGasto = conceptoGasto;
        this.estado = estado;
        this.iniciativa = iniciativa;
        this.denominacionAccion = denominacionAccion;
        this.nombre = nombre;
    }

    @Override
    public String getConceptoVisual() {
        return "Gasto: " + this.conceptoGasto;
    }

    @Override
    public BigDecimal getImporte() {
        return importe;
    }

    @Override
    public String toString() {
        String denominacionValue = null;
        if (denominacionAccion == null){
            denominacionValue = "Sin AF";
        }else denominacionValue = "AF: "+denominacionAccion;
        return denominacionValue + " | "+
                "Iniciativa: '" + iniciativa + " | "+
                importe +" €"
                ;
    }
}
