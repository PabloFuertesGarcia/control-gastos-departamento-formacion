package com.techformate.gastos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Usuario {
    private Integer id;
    private String email;
    private String nombre;
    private String contrasena;
    private Integer rol;

    public Usuario(Integer id, String email, String nombre, Integer rol) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
    }

}
