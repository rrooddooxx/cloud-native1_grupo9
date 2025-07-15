import {Routes} from '@angular/router';
import {MsalGuard} from "@azure/msal-angular";

export const routes: Routes = [
    {
        path: '',
        redirectTo: '/home',
        pathMatch: 'full'
    },
    {
        path: 'home',
        loadChildren: () => import('./features/home/home.routes').then(m => m.homeRoutes),

    },
    {
        path: 'user',
        loadChildren: () => import('./features/user/user.routes').then(m => m.userRoutes),
        canActivate: [MsalGuard]
    },
    {
        path: 'auth',
        loadChildren: () => import('./core/auth/auth.routes').then(m => m.authRoutes)
    },
    {
        path: 'promotions',
        loadComponent: () => import('./features/promotions/promotions.component').then(c => c.PromotionsComponent),
        canActivate: [MsalGuard]
    },
    {
        path: 'inventory',
        loadComponent: () => import('./features/inventory/inventory.component').then(c => c.InventoryComponent),
        canActivate: [MsalGuard]
    },
    {
        path: '**',
        redirectTo: '/home'
    }
];
