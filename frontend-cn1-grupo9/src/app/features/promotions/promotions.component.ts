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
  templateUrl: './promotions.component.html'
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