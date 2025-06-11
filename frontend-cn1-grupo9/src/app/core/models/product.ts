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