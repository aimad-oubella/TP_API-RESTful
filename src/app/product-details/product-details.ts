import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProductModel } from '../models/product.model';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-product-details',
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './product-details.html',
  styleUrl: './product-details.css',
})
export class ProductDetails implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly productService = inject(ProductService);

  product = signal<ProductModel | null>(null);
  errorMessage = signal('');

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.productService.findById(id).subscribe({
      next: (product) => this.product.set(product),
      error: (error) => this.errorMessage.set(error.error?.message ?? 'Produit introuvable.'),
    });
  }
}
