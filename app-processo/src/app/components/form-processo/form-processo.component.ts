import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCardModule} from '@angular/material/card';
import {FormBuilder,FormGroup,Validators,ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from '@angular/material/button';
import { Processo } from '../../models/processo.model';
import { ProcessoService } from '../../servicies/processo.service';
import { BrasilUfs } from '../../models/BrasilUfs.model';
@Component({
  selector: 'app-form-processo',
  imports: [
    MatFormFieldModule, MatInputModule, MatButtonModule,
    MatSelectModule, MatCardModule, ReactiveFormsModule
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './form-processo.component.html',
  styleUrl: './form-processo.component.css'
})
export class FormProcessoComponent implements OnInit {
  processo!: Processo;
  isEdit = false;
  processoForm!: FormGroup;
  brasilUfs:BrasilUfs = new BrasilUfs();
  constructor(
    private fb: FormBuilder,
    private processoService: ProcessoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.processoForm = this.fb.group({
      npu: ['', [Validators.required, Validators.maxLength(25)]],
      municipio: ['', [Validators.required]],
      uf: ['', [Validators.required, Validators.minLength(2)]],
    });
    this.carregarDados();
  }

  async carregarDados(){
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      await this.processoService.getById(+id).then(data => this.processo = data);
      this.processoForm.patchValue(this.processo);
      this.processoForm.get('uf')?.setValue(this.processo.uf);
    }
  }

  salvar(): void {
    const formValues = this.processoForm.value;
    this.processo = formValues;
    this.processo.usuario_id = (this.processoService.getUsuarioLoguin()).id;
    if (this.isEdit) {
      this.processo.id = this.processoService.processo?.id;
      this.processoService.update(this.processo.id!, this.processo).then(() => this.router.navigate(['/processos']));
    } else {
      this.processoService.create(this.processo).then(() => this.router.navigate(['/processos']));
    }
  }

  cancelar(){
    this.router.navigate(['/processos']);
  }
}
