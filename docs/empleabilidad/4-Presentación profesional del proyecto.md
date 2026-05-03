# 📊 Presentación del Proyecto: 

*Gestor de Gastos de Formación (L&D)*

Se trata de una aplicación de escritorio desarrollada íntegramente en Java, diseñada para centralizar y automatizar el control financiero de las acciones formativas de una empresa. Actúa como un módulo de gestión que permite administrar partidas presupuestarias, imputar gastos, registrar facturas y gestionar la relación entre sociedades y acciones formativas.

### **2. ¿Qué problema resuelve?**
Históricamente, en los departamentos de Personas, el seguimiento del presupuesto de formación se hace mediante hojas de cálculo desconectadas, lo que genera puntos ciegos entre lo que RRHH aprueba y lo que Finanzas acaba pagando. 

Este proyecto resuelve ese caos garantizando la integridad de los datos. A través de la base de datos relacional y el código, el sistema impide errores humanos críticos (por ejemplo, es imposible eliminar una partida presupuestaria si ya tiene gastos o facturas asociadas a ella). Pasa de un control manual propenso a errores a un sistema estructurado, seguro, y totalmente trazable.

### **3. ¿Para quién está pensado?**
Está diseñado específicamente para perfiles de Learning & Development y HR Business Partners. 
Es una herramienta técnica pensada para usuarios de negocio; les permite tener control total sobre sus presupuestos y exportar informes estructurados sin necesidad de entrar a complejos ERPs contables, pero saltando las limitaciones de las hojas de cálculo.

### **4. ¿Qué tecnologías utiliza?**
El proyecto se ha construido utilizando un stack robusto y escalable, gestionado a través de *Maven*:
*   *Lógica y Backend:* Java 21 aplicando Programación Orientada a Objetos (POO).
*   *Arquitectura:* Patrón MVC estricto, separando claramente la capa de acceso a datos, la lógica de negocio, y la interfaz de usuario, facilitando el mantenimiento continuado de la herramienta.
*   *Persistencia de Datos:* Base de datos relacional MySQL ,  scripts separados para un despliegue rápido aún siendo ejecutado en un pc local. Conexión mediante JDBC.
*   *Interfaz de Usuario (UI):* Entorno gráfico desarrollado con JavaFX.
*   *Interoperabilidad:* Exportación automática de presupuestos a formato XML con validación estricta de la estructura mediante esquemas XSD.

------------------------------------------------------------------------------------------------------------------------------

### **5. Qué he aprendido desarrollándolo**

Lo que más me ha aportado ha sido entender el ciclo de vida completo del software y la importancia de una buena planificación y una arquitectura ordenada.

He aprendido a evolucionar un código plano hacia un modelo MVC real. También, la importancia de tener un buen sistema de capturación y depuración de errores para dar un feedback útil y real al usuario y al gestor de la herramienta.

Finalmente, he podido reforzar mis conocimientos en diseño de bases de datos relacionales y la persistencia de datos, gracias a la integración de tecnologías que he ido aprendiendo de forma separada durante el curso.