# Sistema de Compras REST API

Sistema completo para la gestión de compras en comercios, desarrollado con Spring Boot (backend) y Angular (frontend), utilizando Microsoft SQL Server Express como base de datos.

## 📋 Características

- ✅ API REST completa para gestión de compras
- ✅ Interfaz web moderna con Angular y Bootstrap
- ✅ Base de datos relacional con Microsoft SQL Server Express
- ✅ Filtros avanzados por comercio, fecha y medio de pago
- ✅ Operaciones CRUD completas
- ✅ Validación de datos
- ✅ Documentación técnica completa

## 🏗️ Arquitectura

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.2.0
- **Java**: Versión 17
- **Base de datos**: Microsoft SQL Server Express
- **ORM**: JPA/Hibernate
- **API**: REST con Spring Web
- **Validación**: Bean Validation

### Frontend (Angular)
- **Framework**: Angular 17 (Standalone Components)
- **UI**: Bootstrap 5.3
- **HTTP Client**: Angular HttpClient
- **Forms**: Template-driven forms

### Base de Datos
- **Motor**: Microsoft SQL Server Express
- **Instancia**: LAPTOP-LH6S993U\SQLEXPRESS
- **Esquema**: 2 tablas (Comercio, Compra) con relación 1:N

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java JDK 17 o superior
- Node.js 18 o superior
- Microsoft SQL Server Express 2019+
- Maven 3.6+

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd sistema-compras-rest
```

### 2. Configurar la Base de Datos
1. Ejecutar el script SQL en Microsoft SQL Server Management Studio:
   ```sql
   -- Archivo: database/script.sql
   ```
2. Verificar la conexión con la instancia `LAPTOP-LH6S993U\SQLEXPRESS`

### 3. Configurar el Backend
1. Navegar al directorio backend:
   ```bash
   cd backend
   ```
2. Actualizar las credenciales de base de datos en `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_password
   ```
3. Compilar y ejecutar:
   ```bash
   mvn clean install
   java -jar target/sistema-compras-backend-1.0-SNAPSHOT.jar
   ```
   El backend estará disponible en `http://localhost:8080`

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
   ng serve
   ```
   El frontend estará disponible en `http://localhost:4200`

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

### Compras
- `GET /api/compras` - Listar todas las compras
- `GET /api/compras/{id}` - Obtener compra por ID
- `POST /api/compras` - Crear nueva compra
- `PUT /api/compras/{id}` - Actualizar compra
- `DELETE /api/compras/{id}` - Eliminar compra

### Filtros
- `GET /api/compras/comercio/{comercioId}` - Compras por comercio
- `GET /api/compras/comercio/{comercioId}/filtrar?fechaInicio=...&fechaFin=...&medioPago=...` - Filtros combinados

## 🎨 Interfaz de Usuario

La aplicación web incluye:
- **Lista de compras** con tabla responsive
- **Filtros de búsqueda** por comercio, fecha y medio de pago
- **Formulario modal** para crear/editar compras
- **Validación en tiempo real** de formularios
- **Confirmaciones** para operaciones de eliminación
- **Indicadores visuales** para diferentes medios de pago

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

---

**Nota**: Asegúrate de tener Microsoft SQL Server Express ejecutándose y accesible antes de iniciar la aplicación.