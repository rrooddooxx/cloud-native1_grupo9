# Products Microservice - CN1 Grupo 9 🛍️

Microservicio interno encargado de la gestión de productos en la arquitectura de microservicios del Grupo 9.

## 📋 Propósito

Este microservicio proporciona una API REST para la gestión completa de productos, incluyendo:

- **CRUD de productos**: Crear, leer, actualizar y eliminar productos
- **Gestión de propietarios**: Asociación de productos con usuarios propietarios
- **Sistema de descuentos**: Manejo de descuentos con fechas de vencimiento
- **Almacenamiento de imágenes**: URLs de imágenes de productos

## 🗄️ Conexión a Oracle Database

El servicio utiliza **Oracle Autonomous Database** como base de datos principal:

- **Driver**: Oracle JDBC (`oracle.jdbc.driver.OracleDriver`)
- **Dialecto**: Oracle Hibernate Dialect
- **Conexión**: TNS con Wallet de seguridad
- **Pool de conexiones**: HikariCP con máximo 4 conexiones
- **DDL**: Actualización automática del esquema

### Configuración de Base de Datos
```yaml
datasource:
  url: jdbc:oracle:thin:@cn1g9_high?TNS_ADMIN=./wallet
  username: CN1G9
  hikari:
    maximum-pool-size: 4
    connection-timeout: 30000
```

## 🔒 Microservicio de Red Privada

Este es un **microservicio interno** que:

- ✅ **Solo accesible** dentro de la red privada de contenedores
- ✅ **No expuesto** directamente a internet
- ✅ **Comunicación interna** con otros microservicios del ecosistema
- ✅ **Puerto**: 8080 (configurable via `SERVER_PORT`)

## 🚀 Tecnologías

- **Spring Boot** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **Oracle Database** - Base de datos
- **Lombok** - Reducción de boilerplate
- **Docker** - Contenerización

## 📦 Modelo de Datos

### Product Entity
```java
- id: Integer (PK, Auto-generated)
- title: String (255)
- description: String (1000)
- imageUrl: String (1000)
- price: BigDecimal
- ownerId: String (50)
- discount: Integer
- endsAt: LocalDate
```