import { Routes } from '@angular/router';

export const userRoutes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./pages/dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'products',
    loadComponent: () => import('./pages/my-products/my-products.component').then(m => m.MyProductsComponent)
  },
  {
    path: 'products/add',
    loadComponent: () => import('./pages/add-product/add-product.component').then(m => m.AddProductComponent)
  },
  {
    path: 'purchases',
    loadComponent: () => import('./pages/my-purchases/my-purchases.component').then(m => m.MyPurchasesComponent)
  },
  {
    path: 'sales',
    loadComponent: () => import('./pages/my-sales/my-sales.component').then(m => m.MySalesComponent)
  }
];