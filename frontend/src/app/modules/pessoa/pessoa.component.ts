import { Component, NgModule, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Cidade } from 'src/app/models/Cidade';
import { CidadeService } from 'src/app/services/cidade.service';
import { PessoaService } from 'src/app/services/pessoa.service';
import { Pessoa } from "../../models/Pessoa";
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { finalize } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { ExportacaoUtil} from '../../util/exportacao.util';

@Component({
  selector: 'app-pessoa',
  templateUrl: './pessoa.component.html',
  styleUrls: ['./pessoa.component.css'],
  providers: [MessageService]
})

export class PessoaComponent implements OnInit {

  @BlockUI() blockUI? : NgBlockUI;

  constructor(private service: PessoaService, private cidadeService: CidadeService, private messageService: MessageService, private http: HttpClient) {
    this.atualizarPessoa = false;
  }

  pessoas: Pessoa[] = [];
  cidades: Cidade[] = [];
  
  atualizarPessoa: boolean;

  pessoaCadastro = new Pessoa();

  ngOnInit(): void {
    this.findAll();
    this.findAllCities();
  }

  findAll(): void {
    this.blockUI?.start();

    this.service.findAll().pipe(finalize(() => {this.blockUI?.stop()})).subscribe((resposta) => {
      this.pessoas = resposta;
    })
  }

  findAllCities() {
    this.blockUI?.start();
    this.cidadeService.findAll().pipe(finalize(() => {this.blockUI?.stop()})).subscribe((resposta) => {
        this.cidades = resposta;
    })
  }

  create() {
    this.blockUI?.start();
    this.service.create(this.pessoaCadastro).pipe(finalize(() => {this.blockUI?.stop()})).subscribe((resposta) => {
      this.findAll();
      this.limpar();
      this.showSuccessMessage('Pessoa Cadastrada com Sucesso');
    }, erro =>{
      this.showErrorMessage('Erro ao cadastrar pessoa');
    })
  }
 
  atualizar(){
    this.blockUI?.start();
    this.service.update(this.pessoaCadastro).pipe(finalize(() => {this.blockUI?.stop()})).subscribe((resposta) =>{
      if(resposta !== null){
        this.showSuccessMessage("Pessoa atualizada com sucesso");
        this.cancelar();
        this.findAll();
      }else{
        this.showErrorMessage('Erro ao atualizar pessoa');  
      }
    }, erro =>{
      this.showErrorMessage('Erro ao atualizar pessoa');
    })
  }

  requisitaAtualizacao(id: any){
    this.blockUI?.start();
    this.atualizarPessoa = true;
    this.service.findById(id).pipe(finalize(() => {this.blockUI?.stop()})).subscribe((resposta) => {
      if(resposta !== null){
        this.pessoaCadastro = resposta;
        this.showInfoMessage("Edite os dados e clique em atualizar")
      }else{
        this.showErrorMessage('Erro ao requisitar atualização');  
      }
    }, erro =>{
      this.showErrorMessage('Erro ao requisitar atualização');
    })
  }
  
  cancelar(){
    this.limpar();
    this.atualizarPessoa = false;
  }

  delete(id: any): void {
    this.blockUI?.start();
    this.service.delete(id).pipe(finalize(() => {this.blockUI?.stop()})).subscribe((resposta) => {
      if(resposta === null){
        this.findAll();
        this.showSuccessMessage("Pessoa deletada com sucesso");
      }else{
        this.showErrorMessage("Erro ao deletar pessoa");
      }
    });
  }

  relatorio(id: any) {
    this.blockUI?.start();
    this.service.relatorio(id).pipe(finalize(() => {this.blockUI?.stop()})).subscribe((resposta) => {
      if(resposta !== null){
        var file = new Blob([resposta], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        ExportacaoUtil.download(fileURL, "Relatorio" + id + '.pdf');
        this.showSuccessMessage("Relatorio Gerado com Sucesso");
      }else{
        this.showErrorMessage("Erro ao gerar relatorio");
      }
    }, erro => {
      this.showErrorMessage("Erro ao gerar Relatorio");
    });
  }


  limpar(){
    this.pessoaCadastro = new Pessoa();
  }

  showSuccessMessage(message: string):void {
    this.messageService.add({severity:'success', summary: 'Sucesso', detail: message});
  }

  showErrorMessage(message: string):void {
    this.messageService.add({severity:'error', summary: 'Erro', detail: message});
  }

  showInfoMessage(message: string): void {
    this.messageService.add({ severity: 'info', summary: 'Info', detail: message });
  }

}

  
  