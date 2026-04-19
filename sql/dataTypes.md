TABLA		ATRIBUTO		SQL DATA TYPE			JAVA TYPE
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
usuarios		
		id_usuario		INT AUTOINCREMENTAL (PK)		Integer
		email 			VARCHAR(255)			String
		nombre 			VARCHAR(20)			String
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

partida
		id_partida		INT AUTOINCREMENTAL (PK)		Integer
		id_sociedadInterna		INT (FK)				Integer

		importe 			DECIMAL(10,2)			BigDecimal
		concepto 			VARCHAR(100)			String
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------		

gasto
		id_gasto 			INT AUTOINCREMENTAL (PK)		Integer	
		id_partida 		INT (FK)				Integer
		id_accionForm		INT (FK)				Integer
		id_usuario		INT (FK)				Integer

		importe 			DECIMAL(10,2)			BigDecimal
		tipoCoste			VARCHAR(255)			String
		conceptoGasto		VARCHAR(255)			String
		estado			VARCHAR(100)			String
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

accionForm
		id_accionForm		INT AUTOINCREMENTAL (PK)		Integer	

		habilidad			VARCHAR(100)			String
		denominacion		VARCHAR(255)			String
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

imputacion (Tabla intermedia)

		id_gasto 			INT (FK)				Integer	
		id_factura		INT (FK)				Integer

		importeFactGasto 		DECIMAL(10,2)			BigDecimal
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

factura

		id_factura		INT AUTOINCREMENTAL (PK)		Integer
		id_sociedadProveedor	INT (FK)				Integer	
		id_sociedadInterna		INT (FK)				Integer

		numFactura		VARCHAR	(100)			String
		fechaEmision		DATE				LocalDate
		importe 			DECIMAL(10,2)			BigDecimal
----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

sociedad
		id_sociedad		INT AUTOINCREMENTAL (PK)		Integer
		tipoSociedad		VARCHAR(20)			String
		nombreSociedad 		VARCHAR(255)			String
		CIF			VARCHAR(50)			String
		