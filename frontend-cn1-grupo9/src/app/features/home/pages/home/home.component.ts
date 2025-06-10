import {Component} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'app-home',
    standalone: true,
    imports: [CommonModule, NgbModule],
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {

    products = [
        {
            id: '1',
            title: 'iPhone 15 Pro',
            description: 'El último iPhone con chip A17 Pro',
            imageUrl: 'https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=400&h=300&fit=crop',
            price: 1299,
            ownerId: 'user1',
            promotion: {
                discount: 15,
                endsAt: new Date('2024-12-31')
            }
        },
        {
            id: '2',
            title: 'MacBook Air M3',
            description: 'Potencia y portabilidad en perfecta armonía',
            imageUrl: 'https://images.unsplash.com/photo-1541807084-5c52b6b3adef?w=400&h=300&fit=crop',
            price: 1499,
            ownerId: 'user2'
        },
        {
            id: '3',
            title: 'AirPods Pro',
            description: 'Cancelación de ruido activa',
            imageUrl: 'https://images.unsplash.com/photo-1588423771073-b8903fbb85b5?w=400&h=300&fit=crop',
            price: 249,
            ownerId: 'user3',
            promotion: {
                discount: 20,
                endsAt: new Date('2024-11-30')
            }
        },
        {
            id: '4',
            title: 'iPad Pro',
            description: 'El iPad más avanzado hasta ahora',
            imageUrl: 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=400&h=300&fit=crop',
            price: 899,
            ownerId: 'user4'
        },
        {
            id: '5',
            title: 'Apple Watch Ultra',
            description: 'Diseñado para aventuras extremas',
            imageUrl: 'https://images.unsplash.com/photo-1434493789847-2f02dc6ca35d?w=400&h=300&fit=crop',
            price: 799,
            ownerId: 'user5'
        },
        {
            id: '6',
            title: 'HomePod mini',
            description: 'Sonido increíble en cualquier lugar',
            imageUrl: 'https://images.unsplash.com/photo-1589492477829-5e65395b66cc?w=400&h=300&fit=crop',
            price: 99,
            ownerId: 'user6',
            promotion: {
                discount: 10,
                endsAt: new Date('2024-12-15')
            }
        }
    ];


    getDiscountedPrice(product: any): number {
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

    buyProduct(product: any): void {
        // TODO: Implement purchase logic
        console.log('Comprando producto:', product.title);
        alert(`¡Producto "${product.title}" agregado al carrito!`);
    }

}