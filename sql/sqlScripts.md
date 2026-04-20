CREATE DATABASE gastosFormacion;

USE gastosFormacion;


CREATE TABLE usuarios(    
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,    
    email VARCHAR(255) UNIQUE NOT NULL,    
    nombre VARCHAR(50) NOT NULL 
);


CREATE TABLE acciones_form(    
    id_accion_form INT PRIMARY KEY AUTO_INCREMENT,    
    habilidad VARCHAR(255),
    denominacion VARCHAR(255) NOT NULL
);


CREATE TABLE sociedades(    
    id_sociedad INT PRIMARY KEY AUTO_INCREMENT,    
    tipo_sociedad VARCHAR(20) NOT NULL,
    nombre_sociedad VARCHAR(255) NOT NULL,
    cif VARCHAR(50) UNIQUE NOT NULL
);


CREATE TABLE partidas(
    id_partida INT PRIMARY KEY AUTO_INCREMENT,
    id_sociedad_interna INT NOT NULL,    
    importe DECIMAL(10,2) NOT NULL,
    tipo_gasto VARCHAR(100) NOT NULL,
    iniciativa VARCHAR(255), -- Añadido para el nivel de detalle del proyecto
    FOREIGN KEY (id_sociedad_interna) REFERENCES sociedades (id_sociedad) ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE facturas(
    id_factura INT PRIMARY KEY AUTO_INCREMENT,
    id_sociedad_interna INT NOT NULL,
    id_sociedad_proveedor INT NOT NULL,
    num_factura VARCHAR(100) UNIQUE NOT NULL,
    fecha_emision DATE,
    importe DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_sociedad_interna) REFERENCES sociedades (id_sociedad) ON DELETE RESTRICT,
    FOREIGN KEY (id_sociedad_proveedor) REFERENCES sociedades (id_sociedad) ON DELETE RESTRICT ON UPDATE CASCADE
);


CREATE TABLE gastos(
    id_gasto INT PRIMARY KEY AUTO_INCREMENT,
    id_partida INT NOT NULL,
    id_accion_form INT, -- Permite NULL si el gasto no requiere curso
    id_usuario INT NOT NULL,
    importe DECIMAL(10,2) NOT NULL,
    tipo_coste VARCHAR(255),
    concepto_gasto VARCHAR(255) NOT NULL,
    estado VARCHAR(100) DEFAULT 'Comprometido', -- Automatizado
    FOREIGN KEY (id_partida) REFERENCES partidas (id_partida) ON DELETE RESTRICT,
    FOREIGN KEY (id_accion_form) REFERENCES acciones_form (id_accion_form) ON DELETE RESTRICT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE RESTRICT    
);


CREATE TABLE imputaciones(
    id_gasto INT NOT NULL,
    id_factura INT NOT NULL,
    importe_factura_gasto DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_gasto, id_factura),

    CONSTRAINT fk_imputacion_gasto
        FOREIGN KEY (id_gasto) 
        REFERENCES gastos (id_gasto) 
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_imputacion_factura
        FOREIGN KEY (id_factura) 
        REFERENCES facturas (id_factura) 
        ON UPDATE CASCADE
        ON DELETE RESTRICT    
);






