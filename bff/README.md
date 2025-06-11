# 🛍️ E-commerce BFF (Backend for Frontend)

**Grupo 9 - Cloud Native 1**

## 📋 Descripción

Este proyecto implementa un **Backend for Frontend (BFF)** para una plataforma de e-commerce, desarrollado como parte de la asignatura Cloud Native 1. El BFF actúa como una capa intermedia entre los microservicios backend y las aplicaciones frontend, proporcionando APIs optimizadas y específicas para cada cliente.

## 🚀 Tecnologías Utilizadas

### Backend
- **Java 21** - Lenguaje de programación principal
- **Spring Boot 3.5.0** - Framework de aplicación
- **Spring Security** - Autenticación y autorización
- **Spring OAuth2** - Manejo de tokens JWT y autenticación OAuth2
- **Lombok** - Reducción de código boilerplate
- **Gradle** - Herramienta de construcción y gestión de dependencias

### Infraestructura
- **Docker** - Containerización de la aplicación
- **Docker Compose** - Orquestación de contenedores
- **Azure AD** - Proveedor de identidad OAuth2

## 🏗️ Arquitectura BFF

El BFF implementa el patrón Backend for Frontend, proporcionando:

- **Agregación de datos** de múltiples microservicios
- **Transformación de datos** adaptados para el frontend
- **Manejo centralizado de autenticación** con OAuth2/JWT
- **APIs públicas y privadas** con diferentes niveles de seguridad
- **Gestión de errores** y logging centralizado

## 📁 Estructura del Proyecto

```
src/main/java/com/grupo9/bff/
├── BffApplication.java          # Clase principal de la aplicación
├── config/
│   ├── HttpClientConfig.java    # Configuración de clientes HTTP
│   └── SecurityConfig.java      # Configuración de seguridad OAuth2
├── products/
│   ├── Product.java             # Entidad de producto
│   ├── ProductDTO.java          # DTO para transferencia de datos
│   ├── ProductsController.java  # API REST para productos
│   └── ProductsService.java     # Lógica de negocio de productos
└── users/
    ├── UserReportController.java # API REST para reportes de usuario
    ├── UserReportDTO.java        # DTO para reportes
    └── UserReportService.java    # Servicio de reportes de usuario
```

## 🔐 Seguridad

- **OAuth2 + JWT** con Azure AD como proveedor de identidad
- **APIs públicas** (`/api/public/*`) - Acceso sin autenticación
- **APIs privadas** (`/api/private/*`) - Requieren token JWT válido
- **CORS** habilitado para aplicaciones frontend

## 🐳 Containerización

```dockerfile
# Multi-stage build
FROM gradle:jdk21-alpine AS builder
FROM bellsoft/liberica-openjre-debian:21 AS runner
```

- **Construcción optimizada** con multi-stage build
- **Imagen base liviana** con OpenJRE 21
- **Puerto 8080** expuesto para la aplicación

## ⚙️ Configuración

### Variables de Entorno
- `PRODUCTS_SERVICE_HOST` - URL del microservicio de productos
- Puerto por defecto: `8080`

### Azure AD OAuth2
- Configurado para integración con Microsoft Azure AD
- Issuer URI: `https://login.microsoftonline.com/[tenant-id]/v2.0`

## 🚀 Ejecución

### Con Docker
```bash
docker build -t bff-ecommerce .
docker run -p 8080:8080 -e PRODUCTS_SERVICE_HOST=http://products-service bff-ecommerce
```

### Con Docker Compose
```bash
docker-compose up
```

### Desarrollo Local
```bash
./gradlew bootRun
```

## 📝 APIs Disponibles

### Productos (Públicas)
- `GET /api/public/products` - Obtener todos los productos
- `GET /api/public/products/{id}` - Obtener producto por ID

### Usuarios (Privadas - Requieren autenticación)
- `GET /api/private/users/{id}/report` - Obtener reporte de usuario

## 👥 Equipo - Grupo 9

Proyecto desarrollado para la asignatura **Cloud Native 1** como parte de la arquitectura de microservicios para e-commerce.

---

*Este BFF forma parte de una arquitectura más amplia que incluye microservicios independientes para productos, usuarios, órdenes y otros dominios del e-commerce.*