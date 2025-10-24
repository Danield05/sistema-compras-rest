-- Script de creación de base de datos para Microsoft SQL Server Express
-- DBMS: Microsoft SQL Server Express
-- Servidor: LAPTOP-LH6S993U\SQLEXPRESS

-- Crear base de datos
CREATE DATABASE SistemaCompras;
GO

-- Usar la base de datos
USE SistemaCompras;
GO

-- Crear tabla Comercio
CREATE TABLE Comercio (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    nombre NVARCHAR(255) NOT NULL,
    lugar NVARCHAR(255) NOT NULL
);

-- Crear tabla Compra
CREATE TABLE Compra (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    fecha DATETIME NOT NULL,
    medio_pago NVARCHAR(50) NOT NULL CHECK (medio_pago IN ('Efectivo', 'Tarjeta', 'Plazos')),
    comprador NVARCHAR(255) NOT NULL,
    monto_total DECIMAL(10,2) NOT NULL,
    comercio_id BIGINT NOT NULL,
    FOREIGN KEY (comercio_id) REFERENCES Comercio(id)
);

-- Insertar datos de ejemplo
INSERT INTO Comercio (nombre, lugar) VALUES
('Tienda Central', 'Centro Ciudad'),
('Supermercado Norte', 'Zona Norte'),
('Minimarket Sur', 'Zona Sur');

INSERT INTO Compra (fecha, medio_pago, comprador, monto_total, comercio_id) VALUES
('2024-01-15 10:30:00', 'Efectivo', 'Juan Pérez', 150.50, 1),
('2024-01-16 14:20:00', 'Tarjeta', 'María García', 200.00, 2),
('2024-01-17 09:15:00', 'Plazos', 'Carlos López', 300.75, 1),
('2024-01-18 16:45:00', 'Efectivo', 'Ana Rodríguez', 75.25, 3);

-- Crear índices para mejorar rendimiento en consultas
CREATE INDEX idx_compra_fecha ON Compra(fecha);
CREATE INDEX idx_compra_medio_pago ON Compra(medio_pago);
CREATE INDEX idx_compra_comercio_id ON Compra(comercio_id);

GO