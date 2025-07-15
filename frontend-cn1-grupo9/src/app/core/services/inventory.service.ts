import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Inventory {
  productId: number;
  productTitle: string;
  stock: number;
  reserved: number;
  available: number;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  constructor(private http: HttpClient) { }

  getAllInventory(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(`${environment.bffUrl}/inventory`);
  }

  getInventoryByProductId(productId: number): Observable<Inventory> {
    return this.http.get<Inventory>(`${environment.bffUrl}/inventory/product/${productId}`);
  }
}