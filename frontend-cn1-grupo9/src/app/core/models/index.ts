export interface Product {
  id: number;
  title: string;
  description: string;
  imageUrl: string;
  originalPrice: number;
  finalPrice: number;
  ownerId: string;
  discount: number;
  discountEndsAt: string | null;
  hasDiscount: boolean;
}

export interface User {
  id: string;
  name: string;
  email: string;
  avatar?: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface Purchase {
  id: string;
  productId: string;
  buyerId: string;
  sellerId: string;
  amount: number;
  purchaseDate: Date;
  status: 'pending' | 'completed' | 'cancelled';
  product?: Product;
  buyer?: User;
  seller?: User;
}

export interface AuthUser {
  id: string;
  name: string;
  email: string;
  avatar?: string;
  token: string;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export interface CreateProductRequest {
  title: string;
  description: string;
  imageUrl: string;
  price: number;
  promotion?: {
    discount: number;
    endsAt: Date;
  };
}

export interface DashboardReport {
  totalProducts: number;
  totalSales: number;
  totalPurchases: number;
  totalIncomeAmount: number;
  totalIncomeAmountCurrency: string;
}