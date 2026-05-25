import { Component, input } from '@angular/core';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-input',
  imports: [FormsModule],
  templateUrl: './input.html',
  styleUrl: './input.css',
})
export class Input {
  label = input.required<string>();
  name = input.required<string>();
  type = input.required<string>();
  value = input.required<any>();
  errors = input.required<any>();
}
