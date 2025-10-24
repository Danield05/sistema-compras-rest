import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

interface Comercio {
  id: number;
  nombre: string;
  lugar: string;
}

interface Compra {
  id: number;
  fecha: string;
  medioPago: string;
  comprador: string;
  montoTotal: number;
  comercio: Comercio;
}

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Sistema de Compras';

  compras: Compra[] = [];
  comercios: Comercio[] = [];

  // Filtros
  filtroComercioId: string = '';
  filtroMedioPago: string = '';
  filtroFechaInicio: string = '';
  filtroFechaFin: string = '';

  // Para el modal
  compraActual: Compra = this.nuevaCompra();
  esEdicion: boolean = false;

  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarComercios();
    this.cargarCompras();
  }

  cargarComercios(): void {
    // Por simplicidad, hardcodeamos los comercios ya que no hay endpoint para obtenerlos
    this.comercios = [
      { id: 1, nombre: 'Tienda Central', lugar: 'Centro Ciudad' },
      { id: 2, nombre: 'Supermercado Norte', lugar: 'Zona Norte' },
      { id: 3, nombre: 'Minimarket Sur', lugar: 'Zona Sur' }
    ];
  }

  cargarCompras(): void {
    this.http.get<Compra[]>(`${this.apiUrl}/compras`).subscribe({
      next: (data) => {
        this.compras = data;
      },
      error: (error) => {
        console.error('Error al cargar compras:', error);
        alert('Error al cargar las compras');
      }
    });
  }

  filtrarCompras(): void {
    if (!this.filtroComercioId) {
      this.cargarCompras();
      return;
    }

    let url = `${this.apiUrl}/compras/comercio/${this.filtroComercioId}/filtrar`;

    const params: any = {};
    if (this.filtroFechaInicio) params.fechaInicio = this.filtroFechaInicio;
    if (this.filtroFechaFin) params.fechaFin = this.filtroFechaFin;
    if (this.filtroMedioPago) params.medioPago = this.filtroMedioPago;

    this.http.get<Compra[]>(url, { params }).subscribe({
      next: (data) => {
        this.compras = data;
      },
      error: (error) => {
        console.error('Error al filtrar compras:', error);
        alert('Error al filtrar las compras');
      }
    });
  }

  limpiarFiltros(): void {
    this.filtroComercioId = '';
    this.filtroMedioPago = '';
    this.filtroFechaInicio = '';
    this.filtroFechaFin = '';
    this.cargarCompras();
  }

  prepararNuevaCompra(): void {
    this.compraActual = this.nuevaCompra();
    this.esEdicion = false;
  }

  editarCompra(compra: Compra): void {
    this.compraActual = { ...compra };
    this.esEdicion = true;
    // Mostrar modal manualmente
    const modal = document.getElementById('compraModal');
    if (modal) {
      (window as any).bootstrap.Modal.getOrCreateInstance(modal).show();
    }
  }

  guardarCompra(): void {
    if (this.esEdicion) {
      this.http.put<Compra>(`${this.apiUrl}/compras/${this.compraActual.id}`, this.compraActual).subscribe({
        next: (data) => {
          this.cargarCompras();
          this.cerrarModal();
          alert('Compra actualizada exitosamente');
        },
        error: (error) => {
          console.error('Error al actualizar compra:', error);
          alert('Error al actualizar la compra');
        }
      });
    } else {
      this.http.post<Compra>(`${this.apiUrl}/compras`, this.compraActual).subscribe({
        next: (data) => {
          this.cargarCompras();
          this.cerrarModal();
          alert('Compra creada exitosamente');
        },
        error: (error) => {
          console.error('Error al crear compra:', error);
          alert('Error al crear la compra');
        }
      });
    }
  }

  eliminarCompra(id: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta compra?')) {
      this.http.delete(`${this.apiUrl}/compras/${id}`).subscribe({
        next: () => {
          this.cargarCompras();
          alert('Compra eliminada exitosamente');
        },
        error: (error) => {
          console.error('Error al eliminar compra:', error);
          alert('Error al eliminar la compra');
        }
      });
    }
  }

  private nuevaCompra(): Compra {
    return {
      id: 0,
      fecha: new Date().toISOString().slice(0, 16),
      medioPago: '',
      comprador: '',
      montoTotal: 0,
      comercio: { id: 0, nombre: '', lugar: '' }
    };
  }

  private cerrarModal(): void {
    const modal = document.getElementById('compraModal');
    if (modal) {
      (window as any).bootstrap.Modal.getOrCreateInstance(modal).hide();
    }
  }

  getBadgeClass(medioPago: string): string {
    switch (medioPago) {
      case 'Efectivo': return 'bg-success';
      case 'Tarjeta': return 'bg-primary';
      case 'Plazos': return 'bg-warning';
      default: return 'bg-secondary';
    }
  }
}