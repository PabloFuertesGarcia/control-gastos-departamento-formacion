*Gestor de gastos - Departamento de formación*

Descripción del proyecto:

Este proyecto consiste en el desarrollo de una aplicación multiplataforma, para la gestión y control financiero de un departamento de formación de una empresa.
El sistema permitirá centralizar la gestión de costes asociados a los distintos programas, clases, proveedores y ponentes, facilitando el control de honorarios, presupuestos y facturas.
Va destinada a profesionales de Recursos Humanos, tanto de mayor responsabilidad (encargados del control presupuestario) como de menor nivel (frecuentemente encargados de la inserción de datos/registro de los gastos y tramitación de facturas).

Actualmente, la gestión de costes en formación suele ser fragmentada, con varias personas involucradas que llevan programas y gastos distintos y numerosos, por lo que se requiere un seguimiento muy efectivo.
Esta aplicación resuelve:

- Fragmentación/integridad de datos:
	Centraliza proveedores, cursos y facturas en una base de datos relacional, más robusta que otras herramientas frecuentes como archivos Excel.
	Además, asegura la normalidad de los datos insertados y establece una metodología de gestión más clara.

- Descontrol presupuestario:
	Permite comparar el presupuesto asignado frente al gasto en tiempo real.


Nota para la evaluación:
Los entregables de cada asignatura se podrán encontrar en las siguientes carpetas del proyecto:


    Bases de Datos: 

        Carpeta /diagrams: diseño (Diagrama E/R), modelo relacional y README explicando el diseño.

        Carpeta /sql: los distintos scripts SQL de creación, inserción de datos y consultas .


    Programación y MPO: 

        Carpeta /src: código fuente Java de la aplicación, organizado por paquetes.

        README del proyecto - A continuación se puede ver cómo se ha estructurado y el funcionamiento del código, y las mejoras implementadas para el módulo de MPO (marcadas como "******** MEJORA MPO - ")


    Lenguajes de Marcas:

        Carpeta /xml: archivo de datos (.xml), esquema de validación (.xsd) y evidencias de validación del sistema.


    Sistemas Informáticos:

        Carpeta /docs/sistemas: informe técnico del entorno de ejecución, requisitos de hardware y manual de instalación .


    Itinerario Personal para la Empleabilidad I:

        Carpeta /docs/empleabilidad: perfil profesional, investigación del sector, presentación del proyecto y el portfolio básico .

---------------------------------------------------------------------------
---------------------------------------------------------------------------

- Módulo Programación -  

FUNCIONALIDAD

La plicación está diseñada para ser el motor financiero del departamento. Permite al usuario registrar el presupuesto inicial, y sobre él prever los gastos que va a tener en distintas formaciones y, finalmente, registrar las facturas reales cruzando los datos para saber exactamente en qué se ha consumido el dinero y evitar descuadres.

Para iniciar la aplicación, primero se deben configurar las credenciales del usuario en la BD, la cual en el momento sólo puede hacerse mediante inserts directamente en un gestor como PHPmyAdmin. 

Al ejecutarse la aplicación (clase principal Launcher.java), el usuario inicia sesión con las credenciales y puede trabajar con las distintas tablas de la BD, gestionando Partidas, Gastos y Facturas, gracias a los botones disponibles en las pantallas interfaz de JAVAFX, que ejecutan distintas operaciones CRUD hacia la BD:

- Añadir registros.
- Editar registros existentes.
- Borrar registros.

 Además, cuenta con funcionalidades avanzadas exportación de datos a XML (solo si es Admin) y un sistema de imputaciones que controla matemáticamente el saldo restante de las facturas.


ESTRUCTURA DEL CODIGO:

Se ha separado la estructura del proyecto según las responsabilidades y los procesos mediante paquetes, siguiendo el patrón Model-View-Controller:

    1. model: Clases modelo de los elementos. 

        Por un lado estan las entidades / objetos con la que se maneja la información: Partida, Factura, Gasto, Imputacion (del gasto a la Factura), AccionForm (Acción formativa), Sociedad y Usuario. Con la librería Lombok se han implementado métodos getter y setters.

        Además, están las clases de:

            -  Estado (enum de los distintos estados de un registro para facilitar la lógica - Comprometido, Consumido y Contabilizado).

            - ExportacionPartidas: Es un contenedor que actúa como el elemento raíz para transformar los objetos Partida desde Java a un archivo XML mediante JAXB, para que los datos se validen correctamente contra el esquema XSD.

******** MEJORA MPO - FUNCIONALIDAD EXTRA: clase Registro financiero::
            Las entidades Partida, Factura, Gasto tienen una lógica de negocio parecida en su atributo más importante: el importe, el cual cuando es positivo significa que es un gasto, y cuando es negativo un ingreso.
            Para evitar equivocaciones del usuario, se ha implementado un sistema de validación utilizando polimorfismo mediante esta interfaz, que obliga a implementar "getImporte()" para poder tratar los objetos de la misma manera, lo q ue ha permitido crear un único método centralizado en la clase ContabilidadController: "validarTipoImporte(RegistroFinanciero)", que evalúa dinámicamente si el dinero es positivo o negativo. Más adelante se detalla la lógica implementada en este método (sección controller).

            
---------------------------------------------------------------------------

    2. view: Interfaz de usuario (archivos FXML).

---------------------------------------------------------------------------

    3. controller: dentro de este paquete, se ha subdividir aún más según la especialización dentro de la lógica de la aplicación:

        controller.dao: 

            Conexión a la base de datos (mySQL) y consultas SQL de cada entidad con su Tabla correspondiente en la Base de datos. Aquellas tablas que tienen claves foráneas cambian en la forma que tienen de acceder y devolver la aplicación, usando JOINs y así poder visualizar no los IDs, sino los nombres correctos de las entidades que aparezcan en la interfaz visual.

        controller.service - lógica de negocio:

            3.1. PresupuestoController
                Se encarga de todo lo relacionado con el presupuesto inicial asignado al departamento. Organiza las peticiones de la pantalla de partidas para que el DAO pueda guardar o leer la información correctamente.

                MODULO LENGUAJE DE MARCAS: EXPORTACIÓN XML: Aquí se gestiona la salida de datos hacia archivos externos usando la clase de exportación para convertir la lista de partidas de Java a un archivo XML, validando que el archivo final sea como pide el esquema de validación XSD.
                
                Es una funcionalidad que sólo pueden realizar los usuarios logeados que tengan rol de Admin (1), al igual que el poder borrar Partidas (se ocultan los botones correspondientes).

            3.2. ContabilidadController
                Es el motor principal para los gastos del día a día y las facturas de los proveedores. Controla el registro y la modificación de gastos y facturas.

********** MEJORA MPO - VALIDACIÓN DE IMPORTE: 
                Gracias al uso de la interfaz Registro Financiero, este servicio tiene un método que puede revisar cualquier objeto de tipo financiero (Gasto, Factura o Partida) para ver si el importe es correcto. Las reglas de negocio aplicadas son:

                    En el módulo de Partidas (presupuesto), el sistema bloquea importes negativos, ya que una partida no puede ser menor que 0.

                    En Facturas y Gastos, el sistema acepta el importe negativo, pero lanza un aviso al usuario de que el registro será tratado como un abono o devolución.

********** MEJORA MPO - TRANSACCIONES CON ROLLBACK: 
                En el proceso de imputar facturas, este controlador asegura si algo falla mientras se guardan los datos, el sistema hace un rollback para cancelar todo y que la base de datos quede con errores o dinero mal imputado o pendiente de imputar.

            3.3. GestorController
                Se ocupa de la seguridad y de que los datos comunes estén disponibles en todas las pantallas.

                    Control de sesión: Guarda quién es el usuario que ha entrado al sistema para saber quién registra un Gasto en caso de añadir uno nuevo. Esta lógica se podría implementar a cada cambio en la BD en un futuro, lo cual facilitaría las auditorías.

                    Carga de desplegables: Para que el usuario no tenga que escribir nombres a mano y cometer errores, este servicio rellena los ComboBox de las pantallas con los datos reales de sociedades y cursos que hay en la BD.

        controller.vistas: 

            Controladores de las distintas interfaces de usuario y los eventos (JAVAFX).

******** MEJORAS MPO:

        Para evitar duplicidad de código, se han creado las siguientes clases estáticas:

            AvisosUsuario: Centraliza la creación de ventanas de alerta y confirmaciones.

            NavegacionController: centraliza la lógica de cambio de pantallas de FXML mediante un Enum de rutas, lo que facilita el mantenimiento de la aplicación.
