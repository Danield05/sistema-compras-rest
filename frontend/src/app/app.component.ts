import { Component } from '@angular/core';
import { CompraListComponent } from './compra-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CompraListComponent],
  template: `
    <div class="container mt-4">
      <h1 class="mb-4">Sistema de Compras</h1>
      <app-compra-list></app-compra-list>
    </div>
  `
})
export class AppComponent {
  title = 'sistema-compras';
}