import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../../../core/models/product';

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
  templateUrl: './purchase-modal.component.html',
  styleUrls: ['./purchase-modal.component.scss']
})
export class PurchaseModalComponent {
  @Input() product: Product | null = null;
  @Input() isVisible: boolean = false;
  @Output() close = new EventEmitter<void>();
  @Output() purchase = new EventEmitter<PurchaseData>();

  purchaseForm: FormGroup;

  constructor(private fb: FormBuilder) {
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
      const purchaseData: PurchaseData = {
        productId: this.product.id,
        productTitle: this.product.title,
        price: this.product.finalPrice,
        quantity: formValue.quantity,
        customerId: formValue.customerId,
        customerEmail: formValue.customerEmail
      };
      this.purchase.emit(purchaseData);
    }
  }

  getTotalPrice(): number {
    if (!this.product) return 0;
    const quantity = this.purchaseForm.get('quantity')?.value || 1;
    return this.product.finalPrice * quantity;
  }
}