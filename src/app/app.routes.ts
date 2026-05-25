import { Routes } from '@angular/router';
import { ProductDetails } from './product-details/product-details';
import { ProductEdit } from './product-edit/product-edit';
import { ProductForm } from './product-form/product-form';
import { ProductList } from './product-list/product-list';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'product-list',
    pathMatch: 'full',
  },
  {
    path: 'product-list',
    component: ProductList,
  },
  {
    path: 'product-details/:id',
    component: ProductDetails,
  },
  {
    path: 'product-create',
    component: ProductForm,
  },
  {
    path: 'product-edit/:id',
    component: ProductEdit,
  },
  {
    path: '**',
    redirectTo: 'product-list',
  },
];

export { routes };
