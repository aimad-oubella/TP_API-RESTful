import { CurrencyPipe, SlicePipe } from '@angular/common';
import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { ProductModel } from '../models/product.model';

@Component({
  selector: 'app-product',
  imports: [CurrencyPipe, SlicePipe, RouterLink],
  templateUrl: './product.html',
  styleUrl: './product.css',
})
export class Product {
  product = input.required<ProductModel>();
}
