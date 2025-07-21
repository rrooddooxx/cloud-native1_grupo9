import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ReactiveFormsModule} from '@angular/forms';
import {Product} from '../../../../core/models';
import {ProductsService} from '../../services/products.service';
import {SalesService, SaleRequest} from '../../../../core/services/sales.service';
import {PurchaseModalComponent, PurchaseData} from '../../../../shared/components/purchase-modal/purchase-modal.component';

interface ProductViewModel {
    id: number;
    title: string;
    description: string;
    imageUrl: string;
    price: number;
    ownerId: string;
    promotion?: {
        discount: number;
        endsAt: Date;
    };
}

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommonModule, NgbModule, ReactiveFormsModule, PurchaseModalComponent],
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    products: ProductViewModel[] = [];
    originalProducts: Product[] = [];
    selectedProduct: Product | null = null;
    isModalVisible = false;
    private productsService = inject(ProductsService);
    private salesService = inject(SalesService);


    ngOnInit() {
        this.loadProducts();
    }

    getDiscountedPrice(product: ProductViewModel): number {
        if (!product.promotion) return product.price;
        return product.price * (1 - product.promotion.discount / 100);
    }

    getFinalPrice(product: ProductViewModel): number {
        return this.getDiscountedPrice(product);
    }

    formatPrice(price: number): string {
        return new Intl.NumberFormat('es-CL', {
            style: 'currency',
            currency: 'CLP'
        }).format(price);
    }

    isPromotionValid(promotion: any): boolean {
        return promotion && new Date(promotion.endsAt) > new Date();
    }

    buyProduct(product: ProductViewModel): void {
        const fullProduct = this.findFullProduct(product.id);
        if (fullProduct) {
            this.selectedProduct = fullProduct;
            this.isModalVisible = true;
        }
    }

    onModalClose(): void {
        this.isModalVisible = false;
        this.selectedProduct = null;
    }

    onPurchase(purchaseData: PurchaseData): void {
        const saleRequest: SaleRequest = {
            customerEmail: purchaseData.customerEmail,
            customerId: purchaseData.customerId,
            price: purchaseData.price,
            productId: purchaseData.productId,
            quantity: purchaseData.quantity
        };

        this.salesService.createSale(saleRequest).subscribe({
            next: (response) => {
                console.log('Compra exitosa:', response);
                alert(`¡Compra realizada exitosamente!\nProducto: ${purchaseData.productTitle}\nCantidad: ${response.quantity}\nTotal: $${response.price}`);
                this.onModalClose();
            },
            error: (error) => {
                console.error('Error en la compra:', error);
                alert('Error al procesar la compra. Por favor, intente nuevamente.');
            }
        });
    }

    private findFullProduct(productId: number): Product | null {
        return this.originalProducts.find(p => p.id === productId) || null;
    }

    private loadProducts() {
        this.productsService.getProducts().subscribe({
            next: (products) => {
                this.originalProducts = products;
                this.products = this.mapToViewModel(products)
            },
            error: (error) => {
                console.error('Error loading products:', error);
                this.products = [];
                this.originalProducts = [];
            }
        });
    }

    private mapToViewModel(products: Product[]): ProductViewModel[] {
        console.log(products)

        return products.map(product => ({
            id: product.id,
            title: product.title,
            description: product.description,
            imageUrl: product.imageUrl,
            price: product.originalPrice,
            ownerId: product.ownerId,
            promotion: product.hasDiscount ? {
                discount: product.discount,
                endsAt: new Date(product.discountEndsAt!)
            } : undefined
        }));
    }

}
