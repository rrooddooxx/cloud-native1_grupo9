import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface PromotionResponse {
  productId: number;
  promotion: any;
  status: string;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class PromotionsService {

  constructor(private http: HttpClient) { }

  createPromotion(productId: number): Observable<PromotionResponse> {
    return this.http.post<PromotionResponse>(`${environment.bffUrl}/promotions/create/${productId}`, {});
  }
}