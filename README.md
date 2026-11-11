# transaction-execution-api-challange
Repositorio para API responsable de la ejecucion de transacciones financieras

## Versiones

| Version | Descripcion                                                          |
|---------|----------------------------------------------------------------------|
| 1.0.0   | Version inicial de API para la ejecucion de transaccione financieras |

## Endpoints

| **Endpoint**          | **Metodo HTTP** | **Descripcion**                                                                                                     |
|------------------------|-----------------|---------------------------------------------------------------------------------------------------------------------|
| `/transactions`        | GET             | Obtiene lista de transaccion, que pueden ser filtradas por estatus, identificador de cuenta, y tipo de transaccion. |
| `/transactions`        | POST            | Registra nueva transaccion, validando con proveedor externo                                                         |

## Documentacion de API

Este proyecto utiliza la dependencia de OpenAPI para generar documentacion de la API de manera automatica, lo que facilita la comprension de los endpoints y su uso.
Una vez que la aplicacion se encuentra corriendo, se puede acceder a la documentacion de la API en el siguiente enlace:

```
http://localhost:8080/swagger-ui.html
```
## Descripcion
Microservicio desarrollado en **Java** con **Spring Boot** que permite la ejecución de transacciones financieras. 
La API expone un endpoint para recibir solicitudes de transacciones y procesarlas de manera segura y eficiente.

## Requerimientos
- **Java 17** Version minima
- **Maven** para manejo de dependencia
- **PostgreSQL** como base de datos
- **Docker** para la simulacion del proveedor externo con WireMock

## Uso de IA 
En este proyecto se hizo uso de herramientas de IA como lo es Copilot, ChatGPT, en donde estas herramientas ayudaron 
a detectar errores de sintaxis asi como detectar casos no considerados dentro del flujo de una transaccion. Tambien al momento
de realizar pruebas y detectar que el resultado no era el correcto, se utilizaron para detectar en menor tiempo el problema y solucionarlo.

## Dependencias Utilizadas
- **Spring Boot**: Framework para el desarrollo, que permite crear aplicaciones de manera rapida y sencilla, ademas de contar con una gran cantidad de dependencias que facilitan el desarrollo.
- **Spring Data JPA**: Al utilizar postgres como base de datos, se aprovecho la flexibilidad que provee JPA para realizar consultas 
e implementar filtros de busqueda
- **OpenFeign**: Al utilizar esta depedencia para la comunicacion con servicios externos se logra tener una implementacion mas limpia 
asi como una mejor gestion de errores y excepciones.
- **Resilience4j**: En combinacion de OpenFeign, permite implementar patrones de resiliencia como circuit breaker
- y retry para mejorar la tolerancia a fallos en la comunicacion con servicios externos.
- **PostgreSQL**: Se utilizo como base de datos, al ser una base de datos relacional, permite tener un mejor control de las transacciones y la integridad de los datos. 
Y en conjunto con Spring Data JPA permite realizar consultas complejas de manera sencilla.
- **SpringDoc OpenAPI**: Esta dependencia permite generar documentacion de la API de manera automatica, lo que facilita la comprension de los endpoints y su uso.
- **Lombok**: Gracias a las anotaciones de esta dependencia se evita tener codigo repetitivo y se mejora la legibilidad del codigo.
Aplicando su uso en casos como contructores, gettes y setters

## Ejecucion de Servicio

### PRE-REQUISITOS
- Contar con Base de datos PostgreSQL configurada local o remotamente, y tener las credenciales de acceso a la misma.
- Contar con version Java 17 o superior instalada en el sistema.

Una vez clonado / descargado el proyecto, navegar a la ubicacion mediante terminal y ejecutar el siguiente comando para compilar el proyecto:

```bash
mvn clean install

```
### CONFIGURACION DE PUERTO
El servicio tiene configurado el puerto 8080, en caso de que requiera utilizar otro, lo puede cambiar en el archivo `application.yaml` ubicado en `src/main/resources/` modificando la propiedad `server.port`.

### CONFIGURACION DE CREDENCIALES BD
Antes de iniciar el servicio, es necesario configurar las credenciales de la base de datos en el archivo `application.yaml` ubicado en `src/main/resources/`. 
Asegurarse de actualizar las propiedades `spring.datasource.url`, `spring.datasource.username`, y `spring.datasource.password` con los valores correctos.

### EJECUCION DEL SERVICIO
Para iniciar el servicio, ejecutar el siguiente comando en la terminal desde la ubicacion del proyecto:

```bash
mvn spring-boot:run
```
## SIMULACION DE PROVEEDOR EXTERNO

Para el funcionamiento correcto del endpoint POST /transactions, es necesario contar con un proveedor externo que simule la ejecucion de transacciones financieras.
En este caso se uso WireMock, al permitir definir la peticion y respuesta que se requiere simular, permitiendo realizar pruebas de manera sencilla y rapida.

El pueto configurado para WireMock es el 8081, en caso de que requiera utilizar otro, lo puede cambiar en el archivo `docker-compose.yaml` modificando la propiedad `ports`.
Asi como en application.yaml modificando la propiedad `provider.account.url`.

Una vez que se cuente con la instalacion de Docker, estando en la ubicacion del proyecto, ejecutar el siguiente comando para levantar el contenedor de WireMock:

```bash
docker compose up -d
```

Para validar que se encuentra corriendo el contenedor, ejecutar el siguiente comando:

```bash
docker ps
```

En caso de que se requiera parar el contenedor, ejecutar los siguientes comandos:

```bash
docker stop mock-provider
```

```bash
docker  docker rm mock-provider
```
