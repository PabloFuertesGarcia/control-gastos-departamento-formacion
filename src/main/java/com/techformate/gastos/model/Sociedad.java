package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Sociedad {
    private Integer id;
    private String tipoSociedad;
    private String nombreSociedad;
    private String cIF;



}
