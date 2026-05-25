import { Category } from './category.model';

export interface Product {
  id: number;
  title: string;
  description: string;
  price: number;
  image?: string | null;
  category: Category;
  createdAt?: string;
  updatedAt?: string;
}

export type ProductModel = Product;

export interface ProductRequest {
  title: string;
  description: string;
  price: number;
  image?: string | null;
  categoryId: number | null;
}

export interface PageResponse<T> {
  content: T[];
  number: number;
  size: number;
  totalElements: number;
  totalPages: number;
  first: boolean;
  last: boolean;
}
