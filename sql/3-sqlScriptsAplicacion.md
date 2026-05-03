CONSULTAS EJECUTADAS EN LA APLICACIÓN (JAVA):

    INSERT INTO acciones_form (denominacion, habilidad) VALUES (?, ?);

    SELECT * FROM acciones_form;

    UPDATE acciones_form SET denominacion = ?, habilidad = ? WHERE id_accion_form = ?;

    DELETE FROM acciones_form WHERE id_accion_form = ?;

    INSERT INTO facturas (id_sociedad_interna, id_sociedad_proveedor, num_factura, fecha_emision, importe) VALUES (?, ?, ?, ?, ?);

    SELECT f.*, si.nombre_sociedad AS nombre_sociedad_interna, sp.nombre_sociedad AS nombre_sociedad_proveedor
    FROM facturas f
    LEFT JOIN sociedades si ON si.id_sociedad = f.id_sociedad_interna
    LEFT JOIN sociedades sp ON sp.id_sociedad = f.id_sociedad_proveedor;

    UPDATE facturas SET id_sociedad_interna = ?, id_sociedad_proveedor = ?, num_factura = ?, fecha_emision = ?, importe = ? WHERE id_factura = ?;

    DELETE FROM facturas WHERE id_factura = ?;

    INSERT INTO gastos (id_partida, id_accion_form, id_usuario, importe, tipo_coste, concepto_gasto, estado) VALUES (?, ?, ?, ?, ?, ?, ?);

    SELECT g.*, a.id_accion_form, a.denominacion, p.id_partida, p.iniciativa, u.id_usuario, u.nombre
    FROM gastos g
    LEFT JOIN acciones_form a ON a.id_accion_form = g.id_accion_form
    LEFT JOIN partidas p ON p.id_partida = g.id_partida
    LEFT JOIN usuarios u ON u.id_usuario = g.id_usuario;

    UPDATE gastos SET id_partida = ?, id_accion_form = ?, id_usuario = ?, importe = ?, tipo_coste = ?, concepto_gasto = ?, estado = ? WHERE id_gasto = ?;

    DELETE FROM gastos WHERE id_gasto = ?;

    INSERT INTO imputaciones (id_gasto, id_factura, importe_factura_gasto) VALUES (?, ?, ?);

    SELECT i.id_gasto, i.id_factura, i.importe_factura_gasto, g.concepto_gasto, f.num_factura
    FROM imputaciones i
    INNER JOIN gastos g ON g.id_gasto = i.id_gasto
    INNER JOIN facturas f ON f.id_factura = i.id_factura;

    SELECT i.id_gasto, i.id_factura, i.importe_factura_gasto, g.concepto_gasto, f.num_factura
    FROM imputaciones i
    INNER JOIN gastos g ON g.id_gasto = i.id_gasto
    INNER JOIN facturas f ON f.id_factura = i.id_factura
    WHERE i.id_factura = ?;

    UPDATE imputaciones SET importe_factura_gasto = ? WHERE id_gasto = ? AND id_factura = ?;

    DELETE FROM imputaciones WHERE id_gasto = ? AND id_factura = ?;

    INSERT INTO partidas (id_sociedad_interna, importe, tipo_gasto, iniciativa) VALUES (?, ?, ?, ?);

    SELECT * FROM partidas;

    SELECT sociedades.nombre_sociedad, partidas.*
    FROM sociedades
    RIGHT JOIN partidas ON sociedades.id_sociedad = partidas.id_sociedad_interna;

    UPDATE partidas SET id_sociedad_interna = ?, importe = ?, tipo_gasto = ?, iniciativa = ? WHERE id_partida = ?;

    DELETE FROM partidas WHERE id_partida = ?;

    SELECT * FROM sociedades;

    SELECT id_usuario, email, nombre FROM usuarios WHERE email = ? AND contrasena = ?;




