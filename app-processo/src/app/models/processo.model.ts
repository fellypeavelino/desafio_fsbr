import { DocumentoPdf } from "./documentoPdf.model";

export interface Processo {
    id?: number;
    npu: string;
    municipio: string;
    uf: string;
    dataCadastro?: string;
    dataVisualizacao?: string;
    usuario_id:number;
    documentosDto: DocumentoPdf[];
}