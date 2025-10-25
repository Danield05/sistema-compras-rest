# Sistema de Compras REST API

Sistema completo para la gestión de compras en comercios, desarrollado con Spring Boot 3.2.0 (backend) y Angular 17 (frontend), utilizando Microsoft SQL Server Express como base de datos. Incluye operaciones CRUD completas, filtros avanzados, validación de datos y arquitectura en capas.

## 📥 Repositorio

**URL del repositorio**: https://github.com/Danield05/sistema-compras-rest.git


##  Características

- ✅ **API REST completa** para gestión de compras y comercios con DTOs
- ✅ **Interfaz web moderna** con Angular 17 (Standalone Components) y Bootstrap 5.3
- ✅ **Base de datos relacional** con Microsoft SQL Server Express y JPA/Hibernate
- ✅ **Filtros avanzados** por comercio, fecha y medio de pago con consultas optimizadas
- ✅ **Operaciones CRUD completas** para compras y comercios con validación
- ✅ **Validación de datos** con Bean Validation y manejo de errores
- ✅ **Arquitectura en capas** (Controller, Service, Repository, Entity) con inyección de dependencias
- ✅ **DTOs** para transferencia de datos optimizada y conversión automática
- ✅ **Configuración CORS** para desarrollo frontend y seguridad
- ✅ **Documentación técnica completa** con diagramas ER, despliegue y requirements.txt
- ✅ **Gestión de estado** en frontend con formularios reactivos y template-driven
- ✅ **Manejo de errores** y feedback visual al usuario

## 🏗️ Arquitectura

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.2.0 con Spring Boot Starter Parent
- **Java**: OpenJDK 17 (Temurin recomendado)
- **Base de datos**: Microsoft SQL Server Express 2019+ con instancia LAPTOP-LH6S993U\SQLEXPRESS
- **ORM**: JPA/Hibernate con Spring Data JPA
- **API**: REST con Spring Web MVC y HttpMessageConverters
- **Validación**: Bean Validation 3.0 con Hibernate Validator
- **Dependencias**: Web, Data JPA, Validation, DevTools, SQL Server JDBC, Jackson
- **Configuración**: CORS habilitado, UTF-8 encoding, Hibernate DDL auto-update
- **Arquitectura**: Capas Controller-Service-Repository con inyección de dependencias

### Frontend (Angular)
- **Framework**: Angular 17 con Standalone Components (sin NgModules)
- **UI**: Bootstrap 5.3 con componentes responsive y accesibles
- **HTTP Client**: Angular HttpClient con interceptores y manejo de errores
- **Forms**: Template-driven forms con validación en tiempo real
- **Routing**: Angular Router con lazy loading (preparado para expansión)
- **Dependencias**: RxJS 7.8, Zone.js 0.14, Angular Localize, TypeScript 5.2
- **Build**: Angular CLI 17.x con webpack y optimización de producción
- **TypeScript**: 5.2.x con configuración estricta y path mapping

### Base de Datos
- **Motor**: Microsoft SQL Server Express 2019+
- **Instancia**: LAPTOP-LH6S993U\SQLEXPRESS (configurable)
- **Base de datos**: SistemaCompras (creada automáticamente por Hibernate)
- **Esquema**: 2 tablas (Comercio, Compra) con relación 1:N y foreign key
- **Configuración**: Usuario sa con contraseña Admin123456 (configurable)
- **Índices**: Optimizados para fecha, medio_pago y comercio_id (FK)
- **Datos iniciales**: 3 comercios y 4 compras de ejemplo (script SQL incluido)
- **Conexión**: JDBC con encrypt=true y trustServerCertificate=false para seguridad

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java JDK 17 o superior
- Node.js 18 o superior
- Microsoft SQL Server Express 2019+
- Maven 3.6+

### 1. Clonar el repositorio
```bash
git clone https://github.com/Danield05/sistema-compras-rest.git
cd sistema-compras-rest
```

### 2. Configurar la Base de Datos
1. Ejecutar el script SQL en Microsoft SQL Server Management Studio:
   ```sql
   -- Ruta: database/script.sql
   ```
2. Verificar la conexión con la instancia `LAPTOP-LH6S993U\SQLEXPRESS`

### 3. Configurar el Backend
1. Navegar al directorio backend:
    ```bash
    cd backend
    ```
2. Actualizar las credenciales de base de datos en `src/main/resources/application.properties`:
    ```properties
    spring.datasource.username=sa
    spring.datasource.password=Admin123456
    spring.datasource.url=jdbc:sqlserver://LAPTOP-LH6S993U\\SQLEXPRESS;databaseName=SistemaCompras;encrypt=true;trustServerCertificate=false
    ```
3. Compilar y ejecutar:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
    El backend estará disponible en `http://localhost:8082` (puerto configurado)

### 4. Configurar el Frontend
1. Navegar al directorio frontend:
    ```bash
    cd frontend
    ```
2. Instalar dependencias:
    ```bash
    npm install
    ```
3. Ejecutar en modo desarrollo:
    ```bash
    npm start
    # o
    ng serve --port 4200
    ```
    El frontend estará disponible en `http://localhost:4200`

### 5. Verificar Instalación
- Backend: `http://localhost:8082/api/compras` (debe retornar JSON)
- Frontend: `http://localhost:4200` (interfaz web funcional)
- Base de datos: Verificar tablas creadas en SQL Server Management Studio

## 📊 Modelo de Datos

### Entidad Comercio
- `id`: BIGINT (PK, IDENTITY)
- `nombre`: NVARCHAR(255) NOT NULL
- `lugar`: NVARCHAR(255) NOT NULL

### Entidad Compra
- `id`: BIGINT (PK, IDENTITY)
- `fecha`: DATETIME NOT NULL
- `medio_pago`: NVARCHAR(50) NOT NULL (Efectivo/Tarjeta/Plazos)
- `comprador`: NVARCHAR(255) NOT NULL
- `monto_total`: DECIMAL(10,2) NOT NULL
- `comercio_id`: BIGINT (FK -> Comercio.id)

## 🔗 Endpoints de la API

#### Compras (CompraDTO para entrada/salida)
- `GET /api/compras` - Listar todas las compras (retorna CompraDTO[])
- `GET /api/compras/{id}` - Obtener compra por ID (retorna Compra)
- `POST /api/compras` - Crear nueva compra (recibe CompraDTO, retorna CompraDTO)
- `PUT /api/compras/{id}` - Actualizar compra (recibe CompraDTO, retorna CompraDTO)
- `DELETE /api/compras/{id}` - Eliminar compra (204 No Content)

#### Comercios
- `GET /api/comercios` - Listar todos los comercios
- `GET /api/comercios/{id}` - Obtener comercio por ID
- `POST /api/comercios` - Crear nuevo comercio
- `PUT /api/comercios/{id}` - Actualizar comercio
- `DELETE /api/comercios/{id}` - Eliminar comercio

#### Filtros Avanzados para Compras
- `GET /api/compras/comercio/{comercioId}` - Compras por comercio específico
- `GET /api/compras/comercio/{comercioId}/fecha?fechaInicio=YYYY-MM-DD&fechaFin=YYYY-MM-DD` - Filtro por rango de fechas
- `GET /api/compras/comercio/{comercioId}/medio-pago/{medioPago}` - Filtro por medio de pago (Efectivo/Tarjeta/Plazos)
- `GET /api/compras/comercio/{comercioId}/filtrar?fechaInicio=...&fechaFin=...&medioPago=...` - Filtros combinados

## 🎨 Interfaz de Usuario

La aplicación web incluye:
- **Lista de compras** con tabla responsive y paginación
- **Filtros de búsqueda** por comercio, fecha y medio de pago
- **Formulario modal** para crear/editar compras con validación
- **Gestión de comercios** integrada
- **Validación en tiempo real** de formularios
- **Confirmaciones** para operaciones de eliminación
- **Indicadores visuales** para diferentes medios de pago (Efectivo/Tarjeta/Plazos)
- **Interfaz responsive** con Bootstrap 5.3
- **Manejo de errores** y feedback al usuario

## 📁 Estructura del Proyecto

```
sistema-compras-rest/
├── backend/                          # Proyecto Spring Boot
│   ├── src/main/java/com/sistema/compras/
│   │   ├── controller/               # Controladores REST
│   │   ├── entity/                   # Entidades JPA
│   │   ├── repository/               # Repositorios JPA
│   │   └── service/                  # Servicios de negocio
│   ├── src/main/resources/
│   │   └── application.properties    # Configuración
│   └── pom.xml                       # Dependencias Maven
├── frontend/                         # Proyecto Angular
│   ├── src/
│   │   ├── app/                      # Componentes Angular
│   │   ├── index.html                # HTML principal
│   │   ├── main.ts                   # Bootstrap de la app
│   │   └── styles.css                # Estilos globales
│   ├── angular.json                  # Configuración Angular CLI
│   ├── package.json                  # Dependencias npm
│   └── tsconfig.json                 # Configuración TypeScript
├── database/                         # Scripts de base de datos
│   └── script.sql                    # Creación de BD y datos iniciales
├── docs/                             # Documentación
│   ├── diagrama-er.md                # Diagrama Entidad-Relación
│   └── diagrama-despliegue.md        # Diagrama de despliegue
└── README.md                         # Este archivo
```

## 🔧 Configuración de Producción

### Backend
- Configurar variables de entorno para credenciales de BD
- Configurar logging apropiado
- Implementar HTTPS
- Configurar CORS para dominio específico

### Frontend
- Ejecutar `ng build --configuration=production`
- Configurar servidor web (Nginx/Apache) para archivos estáticos
- Configurar proxy reverso para API calls

### Base de Datos
- Configurar backups automáticos
- Monitoreo de rendimiento
- Configurar usuarios con permisos mínimos necesarios

## 🧪 Pruebas

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm test
```

## 📝 Documentación Adicional

- [Diagrama Entidad-Relación](docs/diagrama-er.md)
- [Diagrama de Despliegue](docs/diagrama-despliegue.md)
- [Script de Base de Datos](database/script.sql)
- [Dependencias del Proyecto](requirements.txt)

## 📦 Dependencias

### Backend (Java/Maven)
- **Spring Boot Starter Parent** 3.2.0 (gestión de versiones)
- **Spring Boot Starter Web** 3.2.0 (API REST)
- **Spring Boot Starter Data JPA** 3.2.0 (ORM y base de datos)
- **Spring Boot Starter Validation** 3.2.0 (validación de datos)
- **Microsoft SQL Server JDBC Driver** (conector BD)
- **Spring Boot DevTools** 3.2.0 (desarrollo)
- **Jackson Core/Annotations** (serialización JSON)

### Frontend (Node.js/npm)
- **@angular/core** ^17.0.0 (framework principal)
- **@angular/common** ^17.0.0 (directivas comunes)
- **@angular/forms** ^17.0.0 (formularios)
- **@angular/router** ^17.0.0 (navegación)
- **@angular/platform-browser** ^17.0.0 (rendering)
- **bootstrap** ^5.3.0 (UI framework)
- **rxjs** ~7.8.0 (programación reactiva)
- **zone.js** ~0.14.2 (change detection)
- **typescript** ~5.2.0 (lenguaje)
- **@angular/cli** ^17.0.0 (herramientas de desarrollo)

## 🤝 Contribución

1. Fork el proyecto
2. Crear rama para feature (`git checkout -b feature/AmazingFeature`)
3. Commit cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abrir Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## 📞 Soporte

Para soporte técnico o preguntas:
- Revisar la documentación en la carpeta `docs/`
- Verificar logs de aplicación
- Validar configuración de base de datos

## 🔧 Solución de Problemas Comunes

### Error: "Port 8082 was already in use"
**Problema**: El puerto 8082 está ocupado por otra aplicación.

**Solución**:
```bash
# Verificar qué proceso usa el puerto
netstat -ano | findstr :8082

# Matar el proceso (reemplaza XXXX con el PID)
taskkill /PID XXXX /F

# O cambiar el puerto en application.properties
server.port=8083
```

### Error: "There is already an object named 'comercio' in the database"
**Problema**: Las tablas ya existen en la base de datos.

**Solución**: Configurar Hibernate para no recrear el esquema:
```properties
# En application.properties
spring.jpa.hibernate.ddl-auto=validate
```

### URLs de Acceso
- **Aplicación Frontend**: http://localhost:4200
- **API Backend**: http://localhost:8082/api
- **Documentación API**: http://localhost:8082/swagger-ui.html (si está habilitado)

### Verificación de Servicios
```bash
# Verificar backend
curl http://localhost:8082/api/compras

# Verificar frontend (desde navegador)
http://localhost:4200
```

---

**Nota**: Asegúrate de tener Microsoft SQL Server Express ejecutándose y accesible antes de iniciar la aplicación.