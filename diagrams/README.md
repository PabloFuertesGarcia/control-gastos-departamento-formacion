*Diseño de la BBDD de la APP:*

A continuación, se describe la gestión de los gastos del departamento actualmente, para identificar los distintos datos que manejará la aplicación, su organización en tablas y las relaciones:

Antes del fin del año actual y comienzo del inicio del año siguiente, los distintos responsables del departamento estructuran el Presupuesto completo del departamento, analizando los gastos del año vigente y los programas que vendrán en el próximo. 

Este presupuesto, actualmente, es una hoja de Excel denominada 'Budget', donde registran el importe de la partida, la sociedad a la que corresponde (según los trabajadores que asistirán, o a quién pertenece el gasto si no es una formación en sí, como materiales, herramientas, licencias, etc.), y el tipo de gasto (DEI, formacion técnica, formación de soft skills, o formación de Líderes).

Durante el año, a medida que se van organizando las Acciones formativas y los Grupos, se va teniendo de cada partida del Budget una aproximación del gasto más real, según Nº asistentes y presupuestos de los proveedores externos si se requieren. 

- Este gasto se va anotando en una hoja de excel denominada 'Control de gastos', con el estado de 'Comprometido', que significa que se ha apalabrado pero no ejecutado.
    El importe se resta de la partida del Budget a la que corresponde. Coinciden la Sociedad y el tipo de gasto. 
    
- Según suceda la formación, el estado pasa a 'Consumido'.
    
- Finalmente, una vez se ha emitido y pagada la factura; y aparece en el cierre contable del departamento financiero, los usuarios de Formación pasan su estado a 'Contabilizado'.
    
Estos 3 estados, junto con la información de la Sociedad a la que carga el gasto, restándose de la partida presupuestaria correspondiente, aportan la foto completa de los gastos del departamento, necesario para el control.

----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

DISEÑO DE LA SOLUCIÓN Y PLANIFICACIÓN DE LA BASE DE DATOS PARA LA APLICACIÓN JAVA:

Para digitalizar este proceso y evitar los descuadres típicos de las hojas de cálculo, la base de datos dividirá la información en cuatro grandes conceptos secuenciales:

    1. PARTIDA
    Sustituye a la pestaña 'Budget', y supone la planificación inicial. 
    Representa el presupuesto aprobado a principio de año para una Sociedad y un Tipo de Gasto concretos. 
    Es la "hucha" original que marca el tope de gasto.

    2. GASTO
    Sustituye a la pestaña 'Control de Gastos'. 
    Es el acto de comprometer o prever un coste específico concreto, vinculado a una Partida (de la cual resta saldo de forma dinámica) y es el portador de los tres estados clave: Comprometido, Consumido o Contabilizado. Puede estar vinculado también a una Acción Formativa, lo que facilitará el análisis posterior.

    3. FACTURA
    Representa el documento legal emitido por el proveedor reclamando el cobro por un servicio/producto. 
    La Factura en sí misma es inamovible: tiene un número de registro, una fecha y un importe total cerrado.

    4. IMPUTACION (El cruce contable)
    Es una tabla intermedia, y supone el núcleo financiero de la aplicación. Representa la intersección exacta entre la previsión (el Gasto) y la realidad (la Factura). 
    Dado que una misma factura puede englobar varios conceptos distintos, o un mismo gasto puede pagarse en varias facturas fraccionadas, el sistema requiere de un gran nivel de detalle para que los números cuadren, fomentando el desglose detallado de los Gastos:


Además, el sistema cuenta con entidades auxiliares para mantener la integridad de los datos:

    5. USUARIO: personal de RRHH que opera el sistema.

    6. SOCIEDAD: diferenciadas entre internas (empresas del Grupo) y proveedores.

    7. ACCIONES FORMATIVAS: Catálogo de cursos.



LOGICA Y NECESIDADES A TENER EN CUENTA:

    - En el Budget, no es necesaria tanta exactitud con el desglose, permitiendo planificar partidas generales.

    - Durante el registro de Gastos, el usuario ahí sí debe desglosar bien por cada concepto específico de una formación, independientemente de cómo se vaya a facturar. Este registro nace en estado Comprometido.

    - Posteriormente, cuando se recibe una Factura que engloba varios de esos conceptos, el sistema permite realizar una imputación línea a línea. El usuario distribuye el importe total del documento fiscal, vinculando qué porción económica exacta corresponde a cada Gasto previamente registrado.

    - Este cruce actualizará automáticamente los Gastos afectados al estado Contabilizado. El resultado es una trazabilidad sobre en qué conceptos exactos se ha consumido el presupuesto, manteniendo al mismo tiempo la flexibilidad de soportar imputaciones directas al 100% si el usuario opta por registrar Gastos genéricos no desglosados.




El siguiente cuadro detalla cómo se han transformado las entidades del Diagrama E/R a tablas SQL, identificando los tipos de datos para mapearlos en la aplicación Java (JDBC).
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
TABLA		ATRIBUTO		SQL DATA TYPE				JAVA TYPE
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
usuarios		

		id_usuario              INT AUTOINCREMENTAL (PK)		Integer
		email                   VARCHAR(255)					String
		nombre                  VARCHAR(20)						String
        contrasena              VARCHAR(255)                    String
        rol                     INT                             Integer
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

partidas

		id_partida			    INT AUTOINCREMENTAL (PK)		Integer
		id_sociedad_interna  	INT (FK)						Integer
		importe 			    DECIMAL(10,2)					BigDecimal
		iniciativa 			    VARCHAR(100)					String
        tipo_gasto              VARCHAR(255)                    String
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		

gastos

		id_gasto 			    INT AUTOINCREMENTAL (PK)		Integer	
		id_partida 			    INT (FK)						Integer
		id_accionForm		    INT (FK)						Integer
		id_usuario			    INT (FK)						Integer
		importe 		    	DECIMAL(10,2)					BigDecimal
		tipo_coste			    VARCHAR(255)					String
		concepto_gasto		    VARCHAR(255)					String
		estado				    VARCHAR(100)					String
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

acciones_form

		id_accion_form		    INT AUTOINCREMENTAL (PK)		Integer	
		habilidad			    VARCHAR(100)					String
		denominacion		    VARCHAR(255)					String
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

imputaciones (Tabla intermedia)

		id_gasto 				INT (FK)					    Integer	
		id_factura				INT (FK)					    Integer
		importe_factura_gasto 	DECIMAL(10,2)				    BigDecimal
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

facturas

		id_factura				INT AUTOINCREMENTAL (PK)	    Integer
		id_sociedad_proveedor	INT (FK)					    Integer	
		id_sociedad_interna		INT (FK)					    Integer
		num_factura				VARCHAR	(100)				    String
		fecha_emision			DATE						    LocalDate
		importe 				DECIMAL(10,2)				    BigDecimal
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

sociedades

		id_sociedad				INT AUTOINCREMENTAL (PK)	    Integer
		tipo_sociedad			VARCHAR(20)					    String
		nombre_sociedad 		VARCHAR(255)				    String
		cif						VARCHAR(50)					    String
		