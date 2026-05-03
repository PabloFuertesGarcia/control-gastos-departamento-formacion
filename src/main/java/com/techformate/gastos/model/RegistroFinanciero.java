package com.techformate.gastos.model;

import java.math.BigDecimal;

public interface RegistroFinanciero {

    /* Es una interfaz creada para poder tratar los objetos de igual forma en un mismo metodo
    de suma de importes en ContabilidadController.
     */
    String getConceptoVisual();
    BigDecimal getImporte();
}
