import { Component, ViewChild, ChangeDetectionStrategy, OnInit } from '@angular/core';
import { Processo } from '../../models/processo.model';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from '@angular/material/button';
import { Sort, MatSortModule } from '@angular/material/sort';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';
import { MatSort } from '@angular/material/sort';
import { ObjRequest } from '../../models/objRequest.model';
import { ProcessoService } from '../../servicies/processo.service';

@Component({
  selector: 'app-processos',
  imports: [
    MatTableModule, MatPaginatorModule, MatIconModule, 
    MatFormFieldModule, MatInputModule, ReactiveFormsModule,
    MatButtonModule, MatSortModule, MatSlideToggleModule, FormsModule
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './processos.component.html',
  styleUrl: './processos.component.css'
})
export class ProcessosComponent implements OnInit {
  dataSource = new MatTableDataSource<any>([]);
  displayedColumns: string[] = ['id', 'npu', 'municipio', 'uf', 'dataCadastro', 'dataVisualizacao', 'acoes'];
  sortedData!: Processo[];
  listaProcessos!: Processo[];
  listaPaginada: boolean = false;
  paramPaginacao!: ObjRequest;
  lengthPage: number = 0;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private processoService: ProcessoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarParamPaginacao();
    this.loadContatosPaginado();
  }

  carregarParamPaginacao(){
    this.paramPaginacao = new ObjRequest();
    this.paramPaginacao.page = 0;
    this.paramPaginacao.size = 5;
    this.paramPaginacao.sortBy = "id";
    this.paramPaginacao.sortDir = "desc";
    this.paramPaginacao.filtro = "";
  }

  loadContatosPaginado(): void {
    const vm = this;
    vm.processoService.getPagination(vm.paramPaginacao).then(processos => {
      vm.carregarDataSourcePaginado(processos);
    });
  }

  carregarDataSourcePaginado(jsonPaginate:any){  
    this.dataSource.data = jsonPaginate.processoDto;
    this.lengthPage = jsonPaginate.total;
    this.paginator.length = jsonPaginate.total;
    this.paginator.pageIndex = jsonPaginate.param.page;
  }

  criarProcesso(){}

  editarProcesso(processo:Processo){}

  excluirContato(id:number){}

  applyFilterPaginado(event: Event) {}

  tabelaRenderizada($event:any){}

  sortDataPaginado($event:any){}

  formatarData(data?: string): string {
    if (!data) return '';
    return new Date(data).toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit', second: '2-digit' });
  }

  eventoPaginacao($event: PageEvent){}
}
