import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PromotionsService, PromotionResponse } from '../../core/services/promotions.service';
import { ProductsService } from '../home/services/products.service';
import { Product } from '../../core/models';

@Component({
  selector: 'app-promotions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="promotions-container">
      <h2>Create Promotions</h2>
      
      <div class="promotion-form">
        <div class="form-group">
          <label for="productSelect">Select Product:</label>
          <select id="productSelect" [(ngModel)]="selectedProductId" class="form-control">
            <option value="">-- Select a Product --</option>
            <option *ngFor="let product of products" [value]="product.id">
              {{product.title}} - ${{product.originalPrice}}
            </option>
          </select>
        </div>
        
        <button 
          (click)="createPromotion()" 
          [disabled]="!selectedProductId || isLoading"
          class="btn btn-primary">
          {{isLoading ? 'Creating...' : 'Create Promotion'}}
        </button>
      </div>

      <div *ngIf="promotionResult" class="promotion-result">
        <div [ngClass]="getResultClass()">
          <h4>{{promotionResult.status === 'success' ? 'Success!' : 'Error'}}</h4>
          <p>{{promotionResult.message}}</p>
          <div *ngIf="promotionResult.promotion" class="promotion-details">
            <pre>{{promotionResult.promotion | json}}</pre>
          </div>
        </div>
      </div>

      <div *ngIf="promotionHistory.length > 0" class="promotion-history">
        <h3>Recent Promotions</h3>
        <div class="history-list">
          <div *ngFor="let result of promotionHistory" class="history-item" [ngClass]="getResultClass(result)">
            <strong>Product ID: {{result.productId}}</strong>
            <span>{{result.message}}</span>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .promotions-container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
    }

    .promotion-form {
      background: #f8f9fa;
      padding: 20px;
      border-radius: 8px;
      margin-bottom: 20px;
    }

    .form-group {
      margin-bottom: 15px;
    }

    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }

    .form-control {
      width: 100%;
      padding: 8px 12px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-size: 14px;
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

    .btn:disabled {
      background-color: #6c757d;
      cursor: not-allowed;
    }

    .promotion-result {
      margin: 20px 0;
    }

    .success {
      background-color: #d4edda;
      border: 1px solid #c3e6cb;
      color: #155724;
      padding: 15px;
      border-radius: 4px;
    }

    .error {
      background-color: #f8d7da;
      border: 1px solid #f5c6cb;
      color: #721c24;
      padding: 15px;
      border-radius: 4px;
    }

    .promotion-details {
      margin-top: 10px;
      background: #fff;
      padding: 10px;
      border-radius: 4px;
      border: 1px solid #ddd;
    }

    .promotion-history {
      margin-top: 30px;
    }

    .history-item {
      padding: 10px;
      margin: 5px 0;
      border-radius: 4px;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    pre {
      white-space: pre-wrap;
      word-wrap: break-word;
    }
  `]
})
export class PromotionsComponent implements OnInit {
  products: Product[] = [];
  selectedProductId: number | null = null;
  isLoading = false;
  promotionResult: PromotionResponse | null = null;
  promotionHistory: PromotionResponse[] = [];

  constructor(
    private promotionsService: PromotionsService,
    private productsService: ProductsService
  ) {}

  ngOnInit() {
    this.loadProducts();
  }

  loadProducts() {
    this.productsService.getProducts().subscribe({
      next: (products) => {
        this.products = products;
      },
      error: (error) => {
        console.error('Error loading products:', error);
      }
    });
  }

  createPromotion() {
    if (!this.selectedProductId) return;

    this.isLoading = true;
    this.promotionResult = null;

    this.promotionsService.createPromotion(this.selectedProductId).subscribe({
      next: (result) => {
        this.promotionResult = result;
        this.promotionHistory.unshift(result);
        this.isLoading = false;
        if (result.status === 'success') {
          this.selectedProductId = null;
        }
      },
      error: (error) => {
        this.promotionResult = {
          productId: this.selectedProductId!,
          promotion: null,
          status: 'error',
          message: 'Failed to create promotion: ' + error.message
        };
        this.promotionHistory.unshift(this.promotionResult);
        this.isLoading = false;
      }
    });
  }

  getResultClass(result?: PromotionResponse): string {
    const status = result?.status || this.promotionResult?.status;
    return status === 'success' ? 'success' : 'error';
  }
}