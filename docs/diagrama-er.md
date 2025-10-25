# Diagrama Entidad-Relación - Sistema de Compras

## Descripción General

El sistema de compras utiliza un modelo de datos relacional con dos entidades principales: **Comercio** y **Compra**. La relación entre estas entidades es de uno a muchos (1:N), donde un comercio puede tener múltiples compras registradas.

## Entidades

### Comercio
Entidad que representa los establecimientos comerciales donde se realizan las compras.

| Atributo | Tipo | Restricciones | Descripción |
|----------|------|---------------|-------------|
| `id` | BIGINT | PK, IDENTITY, NOT NULL | Identificador único del comercio |
| `nombre` | NVARCHAR(255) | NOT NULL | Nombre del comercio |
| `lugar` | NVARCHAR(255) | NOT NULL | Ubicación del comercio |

### Compra
Entidad que representa las transacciones de compra realizadas en los comercios.

| Atributo | Tipo | Restricciones | Descripción |
|----------|------|---------------|-------------|
| `id` | BIGINT | PK, IDENTITY, NOT NULL | Identificador único de la compra |
| `fecha` | DATETIME | NOT NULL | Fecha y hora de la compra |
| `medio_pago` | NVARCHAR(50) | NOT NULL, CHECK | Método de pago (Efectivo/Tarjeta/Plazos) |
| `comprador` | NVARCHAR(255) | NOT NULL | Nombre de la persona que realizó la compra |
| `monto_total` | DECIMAL(10,2) | NOT NULL | Monto total de la compra |
| `comercio_id` | BIGINT | FK, NOT NULL | Referencia al comercio donde se realizó la compra |

## Relaciones

### Comercio → Compra (1:N)
- **Tipo**: Uno a Muchos
- **Cardinalidad**: Un comercio puede tener múltiples compras
- **Implementación**: Foreign Key `comercio_id` en tabla Compra
- **Integridad**: Restricción de clave foránea con eliminación en cascada

## Diagrama Visual

```
┌─────────────────┐       ┌─────────────────┐
│    Comercio     │       │     Compra      │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │◄──────┤ id (PK)         │
│ nombre          │       │ fecha           │
│ lugar           │       │ medio_pago      │
└─────────────────┘       │ comprador       │
                          │ monto_total     │
                          │ comercio_id (FK)│
                          └─────────────────┘
```

## Restricciones de Integridad

### Validación de Datos
- **medio_pago**: Solo permite valores 'Efectivo', 'Tarjeta', 'Plazos'
- **monto_total**: Debe ser mayor a 0.00
- **fecha**: No puede ser futura (implementado en aplicación)
- **comercio_id**: Debe existir en tabla Comercio

### Índices Optimizados
- Índice en `Compra.fecha` para consultas por fecha
- Índice en `Compra.medio_pago` para filtros por método de pago
- Índice en `Compra.comercio_id` (FK) para joins eficientes

## Consultas Principales Soportadas

1. **Listar todas las compras** con información del comercio
2. **Compras por comercio específico**
3. **Compras por rango de fechas**
4. **Compras por método de pago**
5. **Filtros combinados** (comercio + fecha + medio_pago)

Este modelo proporciona flexibilidad para consultas analíticas y reporting mientras mantiene la integridad referencial de los datos.