import { Component, OnInit, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Category } from '../models/category.model';
import { CategoryService } from '../services/category.service';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-product-form',
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './product-form.html',
  styleUrl: './product-form.css',
})
export class ProductForm implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly productService = inject(ProductService);
  private readonly categoryService = inject(CategoryService);
  private readonly router = inject(Router);

  categories = signal<Category[]>([]);
  backendErrors = signal<Record<string, string[]>>({});
  generalError = signal('');
  submitting = signal(false);

  productForm = this.fb.nonNullable.group({
    title: ['', [Validators.required, Validators.maxLength(255)]],
    description: ['', [Validators.required]],
    price: [0, [Validators.required, Validators.min(0.01), Validators.pattern(/^\d{1,8}(\.\d{1,2})?$/)]],
    image: [''],
    categoryId: [null as number | null, [Validators.required]],
  });

  ngOnInit(): void {
    this.categoryService.findAll().subscribe({
      next: (categories) => this.categories.set(categories),
      error: () => this.generalError.set('Impossible de charger les categories.'),
    });
  }

  createProduct(): void {
    this.backendErrors.set({});
    this.generalError.set('');

    if (this.productForm.invalid) {
      this.productForm.markAllAsTouched();
      return;
    }

    this.submitting.set(true);

    this.productService.create(this.productForm.getRawValue()).subscribe({
      next: () => this.router.navigate(['/product-list']),
      error: (error) => {
        this.backendErrors.set(error.error?.errors ?? {});
        this.generalError.set(error.error?.message ?? 'Erreur lors de la creation du produit.');
        this.submitting.set(false);
      },
    });
  }

  fieldInvalid(field: keyof typeof this.productForm.controls): boolean {
    const control = this.productForm.controls[field];
    return control.invalid && (control.dirty || control.touched);
  }
}
