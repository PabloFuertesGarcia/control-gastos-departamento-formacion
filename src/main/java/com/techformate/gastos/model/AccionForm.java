package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccionForm {

    private Integer id;
    private String denominacion;
    private String habilidad;

    @Override
    public String toString() {
        return denominacion;
    }

}
