# Diagrama de Despliegue - Sistema de Compras

## Arquitectura General

El sistema sigue una arquitectura de tres capas: **Cliente Web**, **Servidor de Aplicaciones** y **Base de Datos**, con comunicación REST entre capas.

```
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│ Cliente Web     │       │ Backend API     │       │ Base de Datos   │
│ (Angular SPA)   │◄─────►│ (Spring Boot)   │◄─────►│ (SQL Server)    │
│                 │       │                 │       │                 │
│ - HTML/CSS/JS   │ HTTP  │ - REST API      │ JDBC  │ - Tablas        │
│ - Bootstrap UI  │       │ - DTOs          │       │ - Relaciones    │
│ - RxJS          │       │ - Validación    │       │ - Índices       │
└─────────────────┘       └─────────────────┘       └─────────────────┘
       │                           │                           │
       └───────────────────────────┼───────────────────────────┘
                                   │
                    Puerto 4200    │    Puerto 8082
```

## Componentes del Sistema

### 1. Cliente Frontend (Angular SPA)
- **Tecnología**: Angular 17 con Standalone Components
- **Puerto**: 4200 (desarrollo), 80/443 (producción)
- **Arquitectura**: Single Page Application (SPA)
- **Funcionalidades**:
  - **Interfaz de usuario**: Bootstrap 5.3 responsive
  - **Gestión de compras**: CRUD completo con formularios
  - **Filtros avanzados**: Por comercio, fecha y medio de pago
  - **Validación**: Template-driven forms con feedback visual
  - **Estado**: Gestión reactiva con RxJS
  - **Comunicación**: HTTP Client con interceptores

### 2. Backend API (Spring Boot)
- **Tecnología**: Spring Boot 3.2.0 con Java 17
- **Puerto**: 8082 (configurado)
- **Arquitectura**: Capas Controller-Service-Repository
- **Funcionalidades**:
  - **API REST**: Endpoints para compras y comercios
  - **DTOs**: Transferencia de datos optimizada
  - **Validación**: Bean Validation con Hibernate Validator
  - **CORS**: Configurado para desarrollo local
  - **Manejo de errores**: Respuestas HTTP apropiadas
  - **Documentación**: Endpoints detallados (Swagger opcional)

### 3. Base de Datos (SQL Server Express)
- **Tecnología**: Microsoft SQL Server Express 2019+
- **Instancia**: LAPTOP-LH6S993U\SQLEXPRESS
- **Base de datos**: SistemaCompras (creada automáticamente)
- **Características**:
  - **Esquema**: 2 tablas con relación 1:N
  - **Integridad**: Foreign keys y constraints
  - **Índices**: Optimizados para consultas frecuentes
  - **Datos iniciales**: Script SQL incluido
  - **Conexión**: JDBC con configuración de seguridad

## Diagrama de Despliegue Detallado

```
┌─────────────────┐
│   Navegador     │
│   Web Browser   │
│                 │
│ - Angular App   │
│ - Bootstrap CSS │
└─────────────────┘
         │
         │ HTTP/HTTPS (4200)
         ▼
┌─────────────────┐
│   Servidor Web  │
│   (Nginx/Apache)│
│                 │
│ - Archivos      │
│   estáticos     │
└─────────────────┘
         │
         │ HTTP/REST (8080)
         ▼
┌─────────────────┐
│ Backend Spring  │
│ Boot Application│
│                 │
│ - Controllers   │
│ - Services      │
│ - Repositories  │
│ - Entities      │
└─────────────────┘
         │
         │ JDBC
         ▼
┌─────────────────┐
│ Microsoft SQL   │
│ Server Express  │
│                 │
│ - Base de datos │
│ - Tablas        │
│ - Índices       │
└─────────────────┘
```

## Requisitos de Despliegue

### Requisitos de Hardware
- **RAM**: Mínimo 4GB, Recomendado 8GB
- **Disco**: Mínimo 10GB de espacio libre
- **CPU**: Procesador de 2 núcleos o superior

### Requisitos de Software
- **Sistema Operativo**: Windows 10/11
- **Java**: JDK 17 o superior
- **Node.js**: Versión 18 o superior (para desarrollo)
- **Microsoft SQL Server Express**: 2019 o superior
- **Navegador Web**: Chrome, Firefox, Edge (últimas versiones)

## Pasos de Despliegue

### 1. Base de Datos
```sql
-- Ejecutar script en Microsoft SQL Server Management Studio
-- Archivo: database/script.sql
```

### 2. Backend (Spring Boot)
```bash
cd backend
mvn clean install
mvn spring-boot:run
# O alternativamente:
java -jar target/sistema-compras-backend-1.0-SNAPSHOT.jar
```

### 3. Frontend (Angular)
```bash
cd frontend
npm install
ng build --configuration=production
# Copiar archivos de dist/ a servidor web
```

### 4. Configuración de Producción
- Configurar variables de entorno para base de datos
- Configurar servidor web (Nginx/Apache) para servir archivos estáticos
- Configurar proxy reverso para API
- Configurar HTTPS/SSL

## Variables de Entorno

### Backend (.env o application-prod.properties)
```
spring.datasource.url=jdbc:sqlserver://LAPTOP-LH6S993U\\SQLEXPRESS;databaseName=SistemaCompras;encrypt=true;trustServerCertificate=false
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password_segura
server.port=8080
```

### Frontend (environment.prod.ts)
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://tu-dominio.com/api'
};
```

## Monitoreo y Logs

- **Backend**: Logs en console/file con Spring Boot logging
- **Frontend**: Console logs en desarrollo, servicios de monitoreo en producción
- **Base de datos**: SQL Server logs y monitoring tools

## Seguridad

- Validación de entrada en backend
- CORS configurado para dominio específico
- Conexión segura a base de datos (encrypt=true)
- HTTPS en producción
- Sanitización de datos en frontend