import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CompraService, Compra } from './compra.service';

@Component({
  selector: 'app-compra-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  styleUrls: ['./compra-list.component.css'],
  template: `
    <div class="container">
      <h2>Lista de Compras</h2>

      <!-- Filtros y Formulario lado a lado -->
      <div class="top-section">
        <!-- Filtros de búsqueda -->
        <div class="filters">
          <h3>Filtros</h3>
          <div>
            <label for="comercioIdFilter">Comercio ID:</label>
            <input id="comercioIdFilter" type="number" [(ngModel)]="filters.comercioId" name="comercioIdFilter" (ngModelChange)="applyFilters()">
          </div>
          <div>
            <label for="fechaInicio">Fecha Inicio:</label>
            <input id="fechaInicio" type="date" [(ngModel)]="filters.fechaInicio" name="fechaInicio" (ngModelChange)="applyFilters()">
          </div>
          <div>
            <label for="fechaFin">Fecha Fin:</label>
            <input id="fechaFin" type="date" [(ngModel)]="filters.fechaFin" name="fechaFin" (ngModelChange)="applyFilters()">
          </div>
          <div>
            <label for="medioPagoFilter">Medio de Pago:</label>
            <select id="medioPagoFilter" [(ngModel)]="filters.medioPago" name="medioPagoFilter" (ngModelChange)="applyFilters()">
              <option value="">Todos</option>
              <option value="Efectivo">Efectivo</option>
              <option value="Tarjeta">Tarjeta</option>
              <option value="Plazos">Plazos</option>
            </select>
          </div>
          <div>
            <label for="compradorFilter">Comprador:</label>
            <input id="compradorFilter" type="text" [(ngModel)]="filters.comprador" name="compradorFilter" (ngModelChange)="applyFilters()">
          </div>
          <button (click)="clearFilters()">Limpiar Filtros</button>
        </div>

        <!-- Formulario -->
        <div class="form-section">
          <h3>{{ isEditing ? 'Editar Compra' : 'Agregar Nueva Compra' }}</h3>
          <form (ngSubmit)="onSubmit()">
            <div>
              <label for="fecha">Fecha:</label>
              <input id="fecha" type="datetime-local" [(ngModel)]="newCompra.fecha" name="fecha" required>
            </div>
            <div>
              <label for="medioPago">Medio de Pago:</label>
              <select id="medioPago" [(ngModel)]="newCompra.medioPago" name="medioPago" required>
                <option value="Efectivo">Efectivo</option>
                <option value="Tarjeta">Tarjeta</option>
                <option value="Plazos">Plazos</option>
              </select>
            </div>
            <div>
              <label for="comprador">Comprador:</label>
              <input id="comprador" type="text" [(ngModel)]="newCompra.comprador" name="comprador" required>
            </div>
            <div>
              <label for="montoTotal">Monto Total:</label>
              <input id="montoTotal" type="number" step="0.01" [(ngModel)]="newCompra.montoTotal" name="montoTotal" required>
            </div>
            <div>
              <label for="comercioId">Comercio ID:</label>
              <input id="comercioId" type="number" [(ngModel)]="newCompra.comercioId" name="comercioId" required>
            </div>
            <button type="submit">{{ isEditing ? 'Actualizar' : 'Agregar' }}</button>
            <button type="button" (click)="cancelEdit()" *ngIf="isEditing">Cancelar</button>
          </form>
        </div>
      </div>

      <!-- Lista de compras en tabla -->
      <div class="list-section">
        <div *ngIf="compras.length === 0">No hay compras disponibles.</div>
        <table *ngIf="compras.length > 0" class="compras-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Fecha</th>
              <th>Comprador</th>
              <th>Medio de Pago</th>
              <th>Comercio</th>
              <th>Monto Total</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let compra of compras">
              <td>{{ compra.id }}</td>
              <td>{{ compra.fecha | date:'dd/MM/yyyy HH:mm' }}</td>
              <td>{{ compra.comprador }}</td>
              <td>{{ compra.medioPago }}</td>
              <td>{{ compra.comercioNombre }}</td>
              <td>{{ compra.montoTotal | number:'1.2-2' }}</td>
              <td>
                <button (click)="editCompra(compra)" style="background: #ffc107; color: #212529; border: none; padding: 6px 12px; margin-right: 5px; border-radius: 4px; cursor: pointer;">Editar</button>
                <button (click)="deleteCompra(compra.id)" style="background: #dc3545; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer;">Eliminar</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  `
})
export class CompraListComponent implements OnInit {
  compras: Compra[] = [];
  newCompra: Partial<Compra> = {};
  filters: any = {};
  isEditing = false;
  editingId: number | null = null;

  constructor(private compraService: CompraService) { }

  // Función para decodificar caracteres mal codificados
  private decodeString(str: string): string {
    try {
      // Reemplazar secuencias comunes de caracteres mal codificados
      return str.replace(/Ã¡/g, 'á')
                .replace(/Ã©/g, 'é')
                .replace(/Ã­/g, 'í')
                .replace(/Ã³/g, 'ó')
                .replace(/Ãº/g, 'ú')
                .replace(/Ã±/g, 'ñ')
                .replace(/Ã/g, 'í')  // Para otros casos
                .replace(/Â/g, '');  // Remover caracteres extraños
    } catch (e) {
      return str;
    }
  }

  ngOnInit(): void {
    this.loadCompras();
  }

  loadCompras(): void {
    this.compraService.getCompras().subscribe(data => {
      // Decodificar los nombres para corregir problemas de acentos
      this.compras = this.applyLocalFilters(data.map(compra => ({
        ...compra,
        comprador: this.decodeString(compra.comprador),
        comercioNombre: this.decodeString(compra.comercioNombre)
      })));
    });
  }

  applyLocalFilters(compras: Compra[]): Compra[] {
    return compras.filter(compra => {
      if (this.filters.comercioId && compra.comercioId !== this.filters.comercioId) return false;
      if (this.filters.fechaInicio && new Date(compra.fecha) < new Date(this.filters.fechaInicio)) return false;
      if (this.filters.fechaFin && new Date(compra.fecha) > new Date(this.filters.fechaFin)) return false;
      if (this.filters.medioPago && compra.medioPago !== this.filters.medioPago) return false;
      if (this.filters.comprador && !compra.comprador.toLowerCase().includes(this.filters.comprador.toLowerCase())) return false;
      return true;
    });
  }

  applyFilters(): void {
    this.loadCompras();
  }

  clearFilters(): void {
    this.filters = {};
    this.loadCompras();
  }

  onSubmit(): void {
    if (this.newCompra.fecha && this.newCompra.medioPago && this.newCompra.comprador && this.newCompra.montoTotal && this.newCompra.comercioId) {
      if (this.isEditing && this.editingId) {
        this.compraService.updateCompra(this.editingId, this.newCompra).subscribe(() => {
          this.loadCompras();
          this.cancelEdit();
        });
      } else {
        this.compraService.createCompra(this.newCompra as Omit<Compra, 'id'>).subscribe(() => {
          this.loadCompras();
          this.newCompra = {};
        });
      }
    }
  }

  editCompra(compra: Compra): void {
    this.isEditing = true;
    this.editingId = compra.id;
    this.newCompra = {
      ...compra,
      comprador: this.decodeString(compra.comprador),
      comercioNombre: this.decodeString(compra.comercioNombre)
    };
  }

  deleteCompra(id: number): void {
    if (confirm('¿Estás seguro de que quieres eliminar esta compra?')) {
      this.compraService.deleteCompra(id).subscribe(() => {
        this.loadCompras();
      });
    }
  }

  cancelEdit(): void {
    this.isEditing = false;
    this.editingId = null;
    this.newCompra = {};
  }
}