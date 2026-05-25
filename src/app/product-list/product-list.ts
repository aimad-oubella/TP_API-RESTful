import { Component, OnInit, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Category } from '../models/category.model';
import { ProductModel } from '../models/product.model';
import { Product } from '../product/product';
import { CategoryService } from '../services/category.service';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-product-list',
  imports: [Product, FormsModule],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList implements OnInit {
  private readonly productService = inject(ProductService);
  private readonly categoryService = inject(CategoryService);

  products = signal<ProductModel[]>([]);
  categories = signal<Category[]>([]);
  selectedCategoryId: number | null = null;
  page = signal(0);
  size = signal(6);
  totalPages = signal(0);
  totalElements = signal(0);
  loading = signal(false);
  errorMessage = signal('');

  ngOnInit(): void {
    this.loadCategories();
    this.loadProducts();
  }

  loadProducts(page = this.page()): void {
    this.loading.set(true);
    this.errorMessage.set('');

    this.productService.findAll(page, this.size(), this.selectedCategoryId).subscribe({
      next: (response) => {
        this.products.set(response.content);
        this.page.set(response.number);
        this.totalPages.set(response.totalPages);
        this.totalElements.set(response.totalElements);
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('Impossible de charger les produits. Verifiez que le backend est demarre.');
        this.loading.set(false);
      },
    });
  }

  onCategoryChange(): void {
    this.loadProducts(0);
  }

  previousPage(): void {
    if (this.page() > 0) {
      this.loadProducts(this.page() - 1);
    }
  }

  nextPage(): void {
    if (this.page() + 1 < this.totalPages()) {
      this.loadProducts(this.page() + 1);
    }
  }

  private loadCategories(): void {
    this.categoryService.findAll().subscribe({
      next: (categories) => this.categories.set(categories),
      error: () => this.errorMessage.set('Impossible de charger les categories.'),
    });
  }
}
