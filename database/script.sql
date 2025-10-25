-- SQLBook: Code
-- Script de creación de base de datos para Microsoft SQL Server Express
-- DBMS: Microsoft SQL Server Express
-- Servidor: LAPTOP-""\SQLEXPRESS

-- =============================================
-- CONFIGURACIÓN INICIAL DE SQL SERVER
-- =============================================

-- 1. Verificar si el usuario sa existe y está habilitado
SELECT name, type_desc, is_disabled
FROM sys.server_principals
WHERE name = 'sa';

-- 2. Si sa está deshabilitado, habilitarlo
IF EXISTS (SELECT name FROM sys.server_principals WHERE name = 'sa' AND is_disabled = 1)
BEGIN
    ALTER LOGIN sa ENABLE;
    PRINT 'Usuario sa habilitado.';
END

-- 3. Establecer contraseña para sa
ALTER LOGIN sa WITH PASSWORD = 'Admin123456';
PRINT 'Contraseña establecida para usuario sa.';

-- 4. Verificar que sa esté habilitado
SELECT name, type_desc, is_disabled
FROM sys.server_principals
WHERE name = 'sa';

-- =============================================
-- ELIMINAR BASE DE DATOS SI EXISTE
-- =============================================
USE master;
GO

-- Método ultra-fuerte para eliminar cualquier rastro
DECLARE @SQL NVARCHAR(MAX) = '';

-- 1. Terminar todas las conexiones a cualquier base de datos que contenga "Sistema"
SELECT @SQL = @SQL + 'KILL ' + CAST(session_id AS NVARCHAR) + '; '
FROM sys.dm_exec_sessions
WHERE database_id IN (
    SELECT database_id FROM sys.databases
    WHERE name LIKE '%Sistema%'
);

IF @SQL <> ''
BEGIN
    EXEC sp_executesql @SQL;
    PRINT 'Todas las conexiones terminadas.';
END

-- 2. Eliminar todas las bases de datos que contengan "Sistema"
SET @SQL = '';
SELECT @SQL = @SQL + 'ALTER DATABASE [' + name + '] SET SINGLE_USER WITH ROLLBACK IMMEDIATE; DROP DATABASE [' + name + ']; '
FROM sys.databases
WHERE name LIKE '%Sistema%';

IF @SQL <> ''
BEGIN
    EXEC sp_executesql @SQL;
    PRINT 'Todas las bases de datos Sistema eliminadas.';
END
ELSE
BEGIN
    PRINT 'No había bases de datos Sistema para eliminar.';
END
GO

-- =============================================
-- CREAR BASE DE DATOS NUEVA
-- =============================================
CREATE DATABASE SistemaCompras;
PRINT 'Base de datos SistemaCompras creada exitosamente.';
GO

-- =============================================
-- CREAR TABLAS
-- =============================================

-- Crear tabla Comercio
EXEC('
USE SistemaCompras;
CREATE TABLE Comercio (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(255) NOT NULL,
    lugar NVARCHAR(255) NOT NULL
);
');
PRINT 'Tabla Comercio creada exitosamente.';
GO

-- Crear tabla Compra
EXEC('
USE SistemaCompras;
CREATE TABLE Compra (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    fecha DATETIME NOT NULL,
    medio_pago NVARCHAR(50) NOT NULL CHECK (medio_pago IN (''Efectivo'', ''Tarjeta'', ''Plazos'')),
    comprador NVARCHAR(255) NOT NULL,
    monto_total DECIMAL(10,2) NOT NULL,
    comercio_id BIGINT NOT NULL,
    FOREIGN KEY (comercio_id) REFERENCES Comercio(id)
);
');
PRINT 'Tabla Compra creada exitosamente.';
GO

-- =============================================
-- INSERTAR DATOS DE EJEMPLO
-- =============================================
EXEC('
USE SistemaCompras;
INSERT INTO Comercio (nombre, lugar) VALUES
(''Tienda Central'', ''Centro Ciudad''),
(''Supermercado Norte'', ''Zona Norte''),
(''Minimarket Sur'', ''Zona Sur'');
');
PRINT 'Datos de Comercio insertados exitosamente.';
GO

EXEC('
USE SistemaCompras;
INSERT INTO Compra (fecha, medio_pago, comprador, monto_total, comercio_id) VALUES
(''2024-01-15 10:30:00'', ''Efectivo'', ''Juan Pérez'', 150.50, 1),
(''2024-01-16 14:20:00'', ''Tarjeta'', ''María García'', 200.00, 2),
(''2024-01-17 09:15:00'', ''Plazos'', ''Carlos López'', 300.75, 1),
(''2024-01-18 16:45:00'', ''Efectivo'', ''Ana Rodríguez'', 75.25, 3);
');
PRINT 'Datos de Compra insertados exitosamente.';
GO

-- =============================================
-- CREAR ÍNDICES
-- =============================================
EXEC('
USE SistemaCompras;
CREATE INDEX idx_compra_fecha ON Compra(fecha);
CREATE INDEX idx_compra_medio_pago ON Compra(medio_pago);
CREATE INDEX idx_compra_comercio_id ON Compra(comercio_id);
');
PRINT 'Índices creados exitosamente.';
GO

-- =============================================
-- VERIFICAR RESULTADOS
-- =============================================
EXEC('
USE SistemaCompras;
SELECT ''Comercios:'' as Info, COUNT(*) as Cantidad FROM Comercio
UNION ALL
SELECT ''Compras:'' as Info, COUNT(*) as Cantidad FROM Compra;
');
PRINT 'Verificación completada exitosamente.';
GO

-- =============================================
-- VERIFICACIÓN FINAL
-- =============================================
SELECT 'Configuración completada exitosamente. El usuario sa tiene contraseña Admin123456' as Estado;
GO