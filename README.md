# Examen Final — Programación Web

Aplicación web desarrollada con **Spring Boot 3** como proyecto de examen final del curso de Programación Web (código `1152439`).

## Tecnologías

| Capa | Tecnología |
|---|---|
| Backend | Java 21 · Spring Boot 3.3.5 |
| Persistencia | Spring Data JPA · H2 (base de datos en memoria) |
| Seguridad | Spring Security 6 |
| Plantillas | Thymeleaf + thymeleaf-extras-springsecurity6 |
| Validación | Spring Boot Validation (Bean Validation / Jakarta) |
| Utilidades | Lombok |
| Build | Maven |

## Requisitos previos

- Java 21 o superior
- Maven 3.8+

## Cómo ejecutar

```bash
# Clonar el repositorio
git clone https://github.com/WilliamBalaguera/previo-final-1152439.git
cd previo-final-1152439

# Compilar y ejecutar
./mvnw spring-boot:run
```

La aplicación quedará disponible en `http://localhost:8080`.

### Consola H2

La base de datos H2 corre en memoria. Puedes acceder a su consola en:

```
http://localhost:8080/h2-console
```

> Credenciales por defecto definidas en `application.properties`.

## Estructura del proyecto

```
src/
└── main/
    ├── java/com/universidad/app/   # Código fuente Java
    └── resources/
        ├── templates/              # Plantillas Thymeleaf
        └── application.properties # Configuración
```

## Build

```bash
# Solo compilar
./mvnw clean package

# Ejecutar tests
./mvnw test
```

## Autor

**William Balaguera** — [@WilliamBalaguera](https://github.com/WilliamBalaguera)
