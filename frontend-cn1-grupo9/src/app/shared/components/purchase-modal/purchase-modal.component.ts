import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../../core/models/product';
import { PromotionsService } from '../../../core/services/promotions.service';
import { SalesService, SaleRequest } from '../../../core/services/sales.service';

export interface PurchaseData {
  productId: number;
  productTitle: string;
  price: number;
  quantity: number;
  customerId: string;
  customerEmail: string;
}

@Component({
  selector: 'app-purchase-modal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './purchase-modal.component.html',
  styleUrls: ['./purchase-modal.component.scss']
})
export class PurchaseModalComponent {
  @Input() product: Product | null = null;
  @Input() isVisible: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Output() purchase = new EventEmitter<PurchaseData>();

  purchaseForm: FormGroup;
  isCreatingPromotion = false;
  promotionMessage = '';

  constructor(
    private fb: FormBuilder, 
    private promotionsService: PromotionsService,
    private salesService: SalesService
  ) {
    this.purchaseForm = this.fb.group({
      quantity: [1, [Validators.required, Validators.min(1)]],
      customerId: ['', [Validators.required]],
      customerEmail: ['', [Validators.required, Validators.email]]
    });
  }

  onClose(): void {
    this.close.emit();
  }

  onPurchase(): void {
    if (this.purchaseForm.valid && this.product) {
      const formValue = this.purchaseForm.value;
      const saleData: SaleRequest = {
        customerEmail: formValue.customerEmail,
        customerId: formValue.customerId,
        price: this.product.finalPrice * formValue.quantity,
        productId: this.product.id,
        quantity: formValue.quantity
      };
      
      this.salesService.createSale(saleData).subscribe({
        next: (response) => {
          console.log('Sale successful:', response);
          this.onClose();
        },
        error: (error) => {
          console.error('Sale failed:', error);
        }
      });
    }
  }

  getTotalPrice(): number {
    if (!this.product) return 0;
    const quantity = this.purchaseForm.get('quantity')?.value || 1;
    return this.product.finalPrice * quantity;
  }

  onCreatePromotion(): void {
    if (!this.product) return;
    
    this.isCreatingPromotion = true;
    this.promotionMessage = '';
    
    this.promotionsService.createPromotion(this.product.id).subscribe({
      next: (result) => {
        this.isCreatingPromotion = false;
        this.promotionMessage = result.message;
      },
      error: (error) => {
        this.isCreatingPromotion = false;
        this.promotionMessage = 'Error creating promotion: ' + error.message;
      }
    });
  }
}