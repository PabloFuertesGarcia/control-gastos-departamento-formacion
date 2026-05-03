# Módulo de Lenguajes de Marcas - Gestión de Presupuesto mediante XML
El archivo (partidas_presupuesto.xml) representa el Budget aprobado a principio de año para el departamento, extraido directamente de todas las partidas existentes en la tabla partidas de la Base de Datos.

Para garantizar la estructura y que los datos no se manipulen, una buena manera es poder exportar esta foto inicial en un formato estructurado y compatible con la aplicación y la propia BD, permitiendo también que los datos del presupuesto puedan ser enviados al departamento financiero o cargados en otras herramientas sin riesgo de perder la integridad de los datos.

El funcionamiento en la aplicación de esta exportación es mediante una acción en JavaFX dentro del PresupuestoController, usando la librería JAXB (Jakarta XML Binding).

Cuando el responsable de presupuesto pulsa el botón de *Exportar XML*, el sistema busca todas las partidas en la base de datos MySQL, las convierte en objetos Java y las escribe automáticamente.

### 1. Estructura del XML
Cada nodo representa una Partida, que es un dinero reservado para una iniciativa y empresa del Grupo concreta:   

* **ID:** identificador único de cada Partida.    
* **Sociedad:** a qué empresa del grupo pertenece.    
* **Importe:** el saldo inicial aprobado para gastar.   
* **Tipo de Gasto:** la categoría (Técnica, Soft Skills, DEI, etc.).    
* **Iniciativa:** El programa o proyecto específico al que va destinado ese dinero.    

### 2. Validador - XSD
Con el mismo objetivo de garantizar la integridad d elos datos, el XML se valida contra un esquema XSD (esquema_partidas.xsd).

El esquema controla:    

* Tipos de datos: 
Obliga a que el ID sea un entero y el importe un decimal (necesario para cálculos sin errores de otros tipos de datos en Java, en la aplicación.    

* Restricciones:
 Se programa una restricción (minInclusive) para que el importe nunca sea negativo (ya que es dinero reservado).    

* Obligatoriedad: 
Todos los atributos (sociedad, iniciativa, etc.) son requeridos para que la exportación sea válida para los responsables de RRHH.   

#### *- Nota sobre evidencias -*
En la misma carpeta de xml se puede encontrar el archivo XSD, como un ejemplo exportado de un XML, y capturas de su validación correcta. En contraparte, puede encontrarse también una captura simulada de una validación incorrecta.
