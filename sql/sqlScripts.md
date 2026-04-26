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

INSERTAR DATOS DE EJEMPLO:

INSERT INTO usuarios (email, nombre) VALUES 
('pablofuertespr@gmail.com', 'Pablo'),
('elena.martin@innovatech.es', 'Elena'),
('carlos.gomez@innovatech.es', 'Carlos'),
('sofia.ruiz@innovatech.es', 'Sofía'),
('javier.lopez@innovatech.es', 'Javier');


INSERT INTO acciones_form (habilidad, denominacion) VALUES 
('Programación', 'Bootcamp Java Full-Stack'),
('Cloud Computing', 'Certificación AWS Arquitecto'),
('Soft Skills', 'Gestión del tiempo y productividad'),
('Idiomas', 'Inglés B2 para Negocios'),
('Safety', 'Prevención de Riesgos Laborales');


INSERT INTO sociedades (tipo_sociedad, nombre_sociedad, cif) VALUES 
('Interna', 'TechFormate S.L.', 'B11223344'),
('Interna', 'TechFormate Chore España S.A.', 'A22334455'),
('Proveedor', 'Amazon Web Services EMEA', 'N0000000A'),
('Proveedor', 'English Advanced Institute', 'B99887766'),
('Proveedor', 'Ironhack School', 'B55667788'),
('Proveedor', 'Catering Delicias', 'B87654321');


INSERT INTO partidas (id_sociedad_interna, importe, tipo_gasto, iniciativa) VALUES 
(1, 4500.00, 'Formación técnica', 'Especialización en Microservicios con Java'),
(1, 3800.00, 'Formación técnica', 'Desarrollo Frontend Avanzado'),
(1, 5000.00, 'Formación de Partners', 'Gestion Microsoft Cloud'),
(1, 4200.00, 'Formación de Partners', 'Acreditación Partner de Ciberseguridad'),
(1, 2500.00, 'Certificaciones', 'Exámenes oficiales AWS Cloud Practitioner'),
(1, 2000.00, 'Certificaciones', 'Certif Scrum Master PSM I'),
(1, 3000.00, 'Formación técnica', 'Arquitectura de datos y SQL'),
(1, 2800.00, 'Certificaciones', 'Certif en Ciberseguridad');

INSERT INTO partidas (id_sociedad_interna, importe, tipo_gasto, iniciativa) VALUES 
(2, 6000.00, 'Plataformas digitales', 'Suscripción Anual LinkedIn'),
(2, 4500.00, 'Plataformas digitales', 'Licencias Plataforma de Idiomas IA'),
(2, 3500.00, 'Idiomas', 'Clases de Inglés Técnico para Devs'),
(2, 2800.00, 'Idiomas', 'Francés para Soporte al Cliente'),
(2, 3200.00, 'Formación SoftSkills', 'Liderazgo y gestión de equipos - Agile'),
(2, 2500.00, 'Formación SoftSkills', 'Comunicación y productividad'),
(2, 4000.00, 'Plataformas digitales', 'Licencia Iseazy'),
(2, 2000.00, 'Formación SoftSkills', 'Bienestar y Salud Laboral');

INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto) 
VALUES (1, 1, 1, 1200.00, 'Matrícula', 'Inscripción Bootcamp Java Full-Stack');

INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto) 
VALUES (5, 2, 2, 150.00, 'Tasa Examen', 'Examen AWS Practitioner');

INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto) 
VALUES (9, NULL, 4, 5500.00, 'Suscripción', 'Suscripcion LinkedIn 2026');

INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto) 
VALUES (11, 4, 3, 900.00, 'Honorarios', 'Curso (2T) Inglés Business');

INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto) 
VALUES (16, NULL, 5, 450.00, 'Material', 'Soportes pantallas y materiales');

INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto) 
VALUES (14, 3, 2, 800.00, 'Taller Presencial', 'Formación presencial productividad equipos');


INSERT INTO facturas (id_sociedad_interna, id_sociedad_proveedor, num_factura, fecha_emision, importe) 
VALUES (1, 5, '2026-BOOT-001', '2026-04-20', 1200.00);

INSERT INTO facturas (id_sociedad_interna, id_sociedad_proveedor, num_factura, fecha_emision, importe) 
VALUES (1, 3, 'AWS-998877', '2026-04-21', 150.00);

INSERT INTO facturas (id_sociedad_interna, id_sociedad_proveedor, num_factura, fecha_emision, importe) 
VALUES (2, 4, 'EAI-2026-15', '2026-04-22', 900.00);

INSERT INTO facturas (id_sociedad_interna, id_sociedad_proveedor, num_factura, fecha_emision, importe) 
VALUES (2, 6, 'FAC-CAT-442', '2026-04-23', 450.00);


INSERT INTO imputaciones (id_gasto, id_factura, importe_factura_gasto) VALUES (1, 1, 1200.00);
INSERT INTO imputaciones (id_gasto, id_factura, importe_factura_gasto) VALUES (2, 2, 150.00);
INSERT INTO imputaciones (id_gasto, id_factura, importe_factura_gasto) VALUES (4, 3, 900.00);
INSERT INTO imputaciones (id_gasto, id_factura, importe_factura_gasto) VALUES (5, 4, 450.00);




