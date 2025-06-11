import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardReport } from '../../../core/models';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) { }

  getUserReport(userId: number = 1): Observable<DashboardReport> {
    return this.http.get<DashboardReport>(`${environment.bffUrl}/api/private/users/${userId}/report`);
  }
}