import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { PlacaService } from './services/placa.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  resultado: any;
  error: string = '';
  consultaForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private placaService: PlacaService
  ) {
    this.consultaForm = this.fb.group({
      placa: ['', [Validators.required, Validators.pattern(/^[A-Z]{3}-?\d{4}$/)]],
      fechaHora: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.consultaForm.valid) {
      const { placa, fechaHora } = this.consultaForm.value;
      this.placaService.validarPlaca(placa!, fechaHora!)
        .subscribe({
          next: (res) => {
            this.resultado = res;
            this.error = '';
          },
          error: (err) => {
            this.error = err.error || 'Error al conectar con el servidor';
            this.resultado = null;
          }
        });
    }
  }
}