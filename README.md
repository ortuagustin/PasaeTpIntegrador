# Patrones de Arquitecturas de Software de Aplicaciones Enterprise

## Trabajo Práctico Integrador

El objetivo del sistema es proveer una plataforma **simplificada** para analizar datos genómicos.
Para ello el sistema deberá administrar 5 roles:

* **Administrador**: gestiona patologías, fenotipos y usuarios. Un fenotipo tiene un nombre y un tipo que puede ser: numérico o categórico (en este caso se deberán indicar los posibles valores). Una patología tiene un nombre y agrupa una lista de fenotipos.

* **Registrante**: Registra la información de nuevos pacientes. La información incluye el genotipo y los fenotipos. El genotipo será un archivo con un formato determinado. Para completar los fenotipos deberá seleccionar primero la patología, el sistema la presentará todos los campos asociados a esa patología y el registrante deberá completar cada uno de ellos. El paciente queda asociado al registrante y solo podrá ser modificado o eliminado por él.

  El archivo con el genotipo tiene el siguiente formato:

  ```
    rsxxx (representa la posición) -
    Alelo(letra)RecibidoMama -
    Alelo(letra)RecibidoPapa
  ```

  Ejemplo: `rs12223 - AA`

* **Científico**: Lanza análisis sobre la base de datos acumulada  y administra “asociaciones genotipo-fenotipo”. Para ello deberá:
  * Seleccionar las patologías
  * Filtrar los pacientes que quiera incluir en el estudio.
  * Indicar los campos y las condiciones por las cuales quiere filtrar.
  * Restringir las posiciones del genoma que se quieran incluir en el análisis.

El resultado de un análisis lo puede convertir en una nueva asociación en estado borrador. Cuando desee podrá pasarlo a estado publicada.

* **Médico clínico**: Podrá visualizar todas las asociaciones en estado publicado y correrlas para el genotipo de un paciente nuevo, obteniendo los resultados para cada uno de los fenotipos seleccionados.

* **Epidemiólogo**: Podrá visualizar frecuencias genotípicas y fenotípicas con gráficos amables.

En todos los casos los usuarios deberán loguearse para poder operar y sus operacion estarán restringidas según su rol.

Debe manejar un log utilizando AOP. Las operaciones deberán ser transaccionales.

## Como correr

1- Correr TpIntegradorApplication.java.
2- Desde la carpeta "frontend-react" correr "npm run start"
3- Backend corriendo en localhost:8080
3- Frontend corriendo en localhost:8091
