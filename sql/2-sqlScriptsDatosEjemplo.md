CONSULTAS PARA INSERTAR DATOS DE EJEMPLO:

    INSERT INTO usuarios (email, nombre, contrasena) VALUES 
    ('pablofuertespr@gmail.com', 'Pablo', 'pablo123'),
    ('elena.martin@innovatech.es', 'Elena', 'elena123'),
    ('carlos.gomez@innovatech.es', 'Carlos', 'carlos123'),
    ('sofia.ruiz@innovatech.es', 'Sofía', 'sofia123'),
    ('javier.lopez@innovatech.es', 'Javier', 'javier123');
    
    Aquí añadí gestión de roles para funcionalidades específicas:

    ALTER TABLE usuarios ADD COLUMN rol INT;
    UPDATE usuarios SET rol = 2;
    UPDATE usuarios SET rol = 1 WHERE email = 'pablofuertespr@gmail.com';


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
