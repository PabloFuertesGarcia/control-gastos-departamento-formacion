package com.techformate.gastos.model;

import java.math.BigDecimal;

public interface RegistroFinanciero {

    /* Es una interfaz creada para la renderización en las listas de la aplicación.
    Permite obtener los datos de la misma manera y pasárselos a la vista
     */

    String getConceptoVisual();
    BigDecimal getImporte();
    BigDecimal getAsignadosaEste();
    default BigDecimal getRestante(){
        return getImporte().subtract(getAsignadosaEste());
    }

}
