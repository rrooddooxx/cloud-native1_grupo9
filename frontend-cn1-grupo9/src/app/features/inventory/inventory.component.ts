import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryService, Inventory } from '../../core/services/inventory.service';

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="inventory-container">
      <h2>Inventory Dashboard</h2>
      
      <div *ngIf="isLoading" class="loading">
        Loading inventory data...
      </div>

      <div *ngIf="!isLoading && inventory.length === 0" class="empty-state">
        No inventory data available.
      </div>

      <div *ngIf="!isLoading && inventory.length > 0" class="inventory-grid">
        <div class="inventory-card" *ngFor="let item of inventory" [ngClass]="getStatusClass(item.status)">
          <div class="card-header">
            <h3>{{item.productTitle}}</h3>
            <span class="product-id">ID: {{item.productId}}</span>
          </div>
          
          <div class="card-body">
            <div class="stock-info">
              <div class="stock-item">
                <label>Total Stock:</label>
                <span class="stock-value">{{item.stock}}</span>
              </div>
              
              <div class="stock-item">
                <label>Reserved:</label>
                <span class="reserved-value">{{item.reserved}}</span>
              </div>
              
              <div class="stock-item">
                <label>Available:</label>
                <span class="available-value">{{item.available}}</span>
              </div>
            </div>
            
            <div class="status-badge" [ngClass]="getStatusClass(item.status)">
              {{getStatusLabel(item.status)}}
            </div>
          </div>
        </div>
      </div>

      <div *ngIf="error" class="error-message">
        <h4>Error Loading Inventory</h4>
        <p>{{error}}</p>
        <button (click)="loadInventory()" class="btn btn-primary">Retry</button>
      </div>
    </div>
  `,
  styles: [`
    .inventory-container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
    }

    .loading, .empty-state {
      text-align: center;
      padding: 40px;
      font-size: 16px;
      color: #666;
    }

    .inventory-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }

    .inventory-card {
      background: white;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
      overflow: hidden;
      transition: transform 0.2s;
    }

    .inventory-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.15);
    }

    .card-header {
      background: #f8f9fa;
      padding: 15px;
      border-bottom: 1px solid #dee2e6;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .card-header h3 {
      margin: 0;
      font-size: 18px;
      color: #333;
    }

    .product-id {
      font-size: 12px;
      color: #666;
      background: #e9ecef;
      padding: 4px 8px;
      border-radius: 4px;
    }

    .card-body {
      padding: 20px;
    }

    .stock-info {
      margin-bottom: 15px;
    }

    .stock-item {
      display: flex;
      justify-content: space-between;
      margin-bottom: 8px;
    }

    .stock-item label {
      font-weight: 500;
      color: #555;
    }

    .stock-value {
      font-weight: bold;
      color: #28a745;
    }

    .reserved-value {
      font-weight: bold;
      color: #ffc107;
    }

    .available-value {
      font-weight: bold;
      color: #007bff;
    }

    .status-badge {
      padding: 6px 12px;
      border-radius: 20px;
      font-size: 12px;
      font-weight: bold;
      text-align: center;
      text-transform: uppercase;
    }

    .status-badge.available {
      background-color: #d4edda;
      color: #155724;
    }

    .status-badge.low-stock {
      background-color: #fff3cd;
      color: #856404;
    }

    .status-badge.out-of-stock {
      background-color: #f8d7da;
      color: #721c24;
    }

    .inventory-card.available {
      border-left: 4px solid #28a745;
    }

    .inventory-card.low-stock {
      border-left: 4px solid #ffc107;
    }

    .inventory-card.out-of-stock {
      border-left: 4px solid #dc3545;
    }

    .error-message {
      background: #f8d7da;
      border: 1px solid #f5c6cb;
      color: #721c24;
      padding: 20px;
      border-radius: 4px;
      text-align: center;
    }

    .btn {
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
    }

    .btn-primary {
      background-color: #007bff;
      color: white;
    }
  `]
})
export class InventoryComponent implements OnInit {
  inventory: Inventory[] = [];
  isLoading = true;
  error: string | null = null;

  constructor(private inventoryService: InventoryService) {}

  ngOnInit() {
    this.loadInventory();
  }

  loadInventory() {
    this.isLoading = true;
    this.error = null;

    this.inventoryService.getAllInventory().subscribe({
      next: (data) => {
        this.inventory = data;
        this.isLoading = false;
      },
      error: (error) => {
        this.error = 'Failed to load inventory data: ' + error.message;
        this.isLoading = false;
      }
    });
  }

  getStatusClass(status: string): string {
    return status.toLowerCase().replace('_', '-');
  }

  getStatusLabel(status: string): string {
    return status.replace('_', ' ');
  }
}