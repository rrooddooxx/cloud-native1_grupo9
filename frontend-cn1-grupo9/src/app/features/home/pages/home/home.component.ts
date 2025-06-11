import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClient} from '@angular/common/http';
import {Product} from '../../../../core/models';
import {environment} from '../../../../../environments/environment';

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
    imports: [CommonModule, NgbModule],
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
    products: ProductViewModel[] = [];
    private http = inject(HttpClient);


    ngOnInit() {
        this.loadProducts();
    }

    getDiscountedPrice(product: ProductViewModel): number {
        if (!product.promotion) return product.price;
        return product.price * (1 - product.promotion.discount / 100);
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
        // TODO: Implement purchase logic
        console.log('Comprando producto:', product.title);
        alert(`¡Producto "${product.title}" agregado al carrito!`);
    }

    private loadProducts() {
        try {
            this.http.get<Product[]>(`${environment.apiUrl}/public/products`)
                .subscribe({
                    next: (products) => {
                        console.log('prod: ', products)

                        this.products = this.mapToViewModel(products)
                    },
                    error: (error) => console.error('Error loading products:', error)
                });
        } catch (e) {
            this.products = [];
            return;
        }
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
