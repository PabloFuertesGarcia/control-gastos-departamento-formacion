package com.techformate.gastos.model;


public enum Estado {
    COMPROMETIDO("Comprometido"),
    CONSUMIDO("Consumido"),
    CONTABILIZADO("Contabilizado");

    private final String valor;

    Estado(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return valor;
    }

    public static Estado stringAEnum(String string) {
        for (Estado estado : Estado.values()) {
            if (estado.valor.equalsIgnoreCase(string)) {
                return estado;
            }
        }
        return COMPROMETIDO;
    }

}
