# Diagrama Entidad-Relación - Sistema de Compras

## Entidades

### Comercio
- **id**: BIGINT (PK, IDENTITY)
- **nombre**: NVARCHAR(255) NOT NULL
- **lugar**: NVARCHAR(255) NOT NULL

### Compra
- **id**: BIGINT (PK, IDENTITY)
- **fecha**: DATETIME NOT NULL
- **medio_pago**: NVARCHAR(50) NOT NULL (Valores: 'Efectivo', 'Tarjeta', 'Plazos')
- **comprador**: NVARCHAR(255) NOT NULL
- **monto_total**: DECIMAL(10,2) NOT NULL
- **comercio_id**: BIGINT (FK -> Comercio.id)

## Relaciones
- Un **Comercio** puede tener muchas **Compras** (1:N)
- Una **Compra** pertenece a un **Comercio** (N:1)

## Diagrama en Texto

```
Comercio (1) -----> (N) Compra
  - id (PK)
  - nombre
  - lugar
                    - id (PK)
                    - fecha
                    - medio_pago
                    - comprador
                    - monto_total
                    - comercio_id (FK)
```

Este modelo permite registrar compras asociadas a comercios específicos y consultarlas por comercio, fecha y medio de pago.