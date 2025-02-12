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
import { DocumentoPdf } from '../../models/documentoPdf.model';
import { MatIconModule } from '@angular/material/icon';
@Component({
  selector: 'app-form-processo',
  imports: [
    MatFormFieldModule, MatInputModule, MatButtonModule,
    MatSelectModule, MatCardModule, ReactiveFormsModule,
    MatIconModule
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
  listaMunicipios!:any[];
  documentoPdf!: File;
  listaDocs: any[] = [];

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
      documentosDto: [[]]
    });
    this.carregarDados();
  }

  async carregarDados(){
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      await this.processoService.getById(+id).then(data => this.processo = data);
      this.listaDocs = this.processo.documentosDto;
      this.processoForm.patchValue(this.processo);
      this.processoForm.get('uf')?.setValue(this.processo.uf);
      await this.ufSelecionada({value:this.processo.uf});
      this.processoForm.get('municipio')?.setValue(this.processo.municipio);
      await this.processoService.visualizacao(+id);
    }
  }

  async salvar(): Promise<void> {
    const formValues = this.processoForm.value;
    this.processo = { ...this.processo, ...formValues };
    this.processo.usuario_id = this.processoService.getUsuarioLoguin().id;
    this.processo.documentosDto = this.processoForm.value.documentosDto || [];
    if (this.isEdit) {
      this.processo.id = this.processoService.processo?.id;
      this.processoService.update(this.processo.id!, this.processo).then(() => this.router.navigate(['/processos']));
    } else {
      let result = await this.processoService.findByNpu(this.processo.npu);
      if (result.id) {
        alert("Numero do Processo já existe!");
        return;
      }
      let documentosDto = this.processoForm.get('documentosDto')?.value;
      if (documentosDto && documentosDto.length == 0) {
        alert("Necessario Anexar um PDF!");
        return;
      }
      this.processoService.create(this.processo).then(() => this.router.navigate(['/processos']));
    }
  }

  cancelar(){
    this.router.navigate(['/processos']);
  }

  formatNpu(event: any): void {
    let value = event.target.value.replace(/\D/g, '');
    if (value.length > 0) {
      value = value.replace(/^(\d{7})(\d)/, '$1-$2');
      value = value.replace(/^(\d{7})-(\d{2})(\d)/, '$1-$2.$3');
      value = value.replace(/^(\d{7})-(\d{2}).(\d{4})(\d)/, '$1-$2.$3.$4');
      value = value.replace(/^(\d{7})-(\d{2}).(\d{4}).(\d{1})(\d)/, '$1-$2.$3.$4.$5');
      value = value.replace(/^(\d{7})-(\d{2}).(\d{4}).(\d{1}).(\d{2})(\d)/, '$1-$2.$3.$4.$5.$6');
    }
    event.target.value = value;
    this.processoForm.get('npu')?.setValue(value);
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.readAsArrayBuffer(file);
      reader.onload = (e: any) => {
        const byteArray = new Uint8Array(e.target.result);
        const documentoPdf: DocumentoPdf = {
          path: file.name,
          documentoPdf: Array.from(byteArray) 
        };

        this.processoForm.patchValue({
          documentosDto: [documentoPdf]
        });

        console.log("Arquivo carregado:", documentoPdf);
      };
    }
  }
  
  async ufSelecionada($event:any){
    const {value} = $event;
    let list = await this.processoService.carregarMunicipiosPelaUf(value);
    this.listaMunicipios = list;
  }

  async download(id:number){
    let pdfBlob = await this.processoService.download(id);
    const blob = new Blob([pdfBlob], { type: 'application/pdf' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `documento_${id}.pdf`; 
    a.click();
    window.URL.revokeObjectURL(url); 
  }
}
