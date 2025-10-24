# Diagrama de Despliegue - Sistema de Compras

## Arquitectura General

```
[Cliente Web Browser] <--- HTTP/HTTPS ---> [Servidor Web Angular]
                                               |
                                               | HTTP/REST API
                                               v
[Backend Spring Boot] <--- JDBC ---> [Microsoft SQL Server Express]
```

## Componentes del Sistema

### 1. Cliente Frontend (Angular)
- **Tecnología**: Angular 17 (Standalone Components)
- **Puerto**: 4200 (desarrollo), 80/443 (producción)
- **Funcionalidades**:
  - Interfaz de usuario para gestión de compras
  - Formularios de creación/edición de compras
  - Filtros de búsqueda por comercio, fecha y medio de pago
  - Tabla responsive para mostrar compras
  - Modales para operaciones CRUD

### 2. Backend API (Spring Boot)
- **Tecnología**: Spring Boot 3.2.0 con Java 17
- **Puerto**: 8080
- **Funcionalidades**:
  - API REST para operaciones CRUD de compras
  - Validación de datos
  - Manejo de errores
  - CORS habilitado para frontend
  - Documentación de API (Swagger opcional)

### 3. Base de Datos
- **Tecnología**: Microsoft SQL Server Express
- **Instancia**: LAPTOP-LH6S993U\SQLEXPRESS
- **Base de datos**: SistemaCompras
- **Características**:
  - Tablas: Comercio, Compra
  - Relación 1:N (Comercio -> Compras)
  - Índices para optimización de consultas

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