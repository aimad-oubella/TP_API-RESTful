import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { PageResponse, Product, ProductRequest } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${environment.apiUrl}/products`;

  findAll(page = 0, size = 6, categoryId?: number | null): Observable<PageResponse<Product>> {
    let params = new HttpParams()
      .set('page', page)
      .set('size', size);

    if (categoryId) {
      params = params.set('categoryId', categoryId);
    }

    return this.http.get<PageResponse<Product>>(this.apiUrl, { params });
  }

  findById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.apiUrl}/${id}`);
  }

  create(payload: ProductRequest): Observable<Product> {
    return this.http.post<Product>(this.apiUrl, payload);
  }
}
