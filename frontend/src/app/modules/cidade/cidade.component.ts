import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { CidadeService } from 'src/app/services/cidade.service';
import { Cidade } from "../../models/Cidade";
import { BlockUI, NgBlockUI } from 'ng-block-ui';
import { finalize } from 'rxjs/operators';


@Component({
  selector: 'app-cidade',
  templateUrl: './cidade.component.html',
  styleUrls: ['./cidade.component.css'],
  providers: [MessageService]
})
export class CidadeComponent implements OnInit {
  @BlockUI() blockUI?: NgBlockUI;

  constructor(private service: CidadeService, private MessageService: MessageService) {
    this.atualizarCidade = false;
  }

  atualizarCidade: boolean;

  cidades: Cidade[] = [];

  cidade = new Cidade();

  ngOnInit(): void {
    this.findAll();
  }

  findAll(): void {
    this.blockUI?.start();
    this.service.findAll().pipe(finalize(() => { this.blockUI?.stop() })).subscribe((resposta) => {
      this.cidades = resposta;
    })
  }

  create(): void {
    this.blockUI?.start();
    this.service.create(this.cidade).pipe(finalize(() => { this.blockUI?.stop() })).subscribe((resposta) => {
      this.showSuccessMessage('Cidade Cadastrada com Sucesso');
      this.limpar();
      this.findAll();
    }, erro => {
      this.showErrorMessage('Erro ao cadastrar cidade');
    })
  }

  limpar(): void {
    this.cidade = new Cidade();
  }
  delete(id: any): void {
    this.blockUI?.start();
    this.service.delete(id).pipe(finalize(() => { this.blockUI?.stop() })).subscribe((resposta) => {
      if (resposta === null) {
        this.findAll();
        this.showSuccessMessage("Cidade delatada com sucesso")
      } else {
        this.showErrorMessage("Erro ao deletar cidade");
      }
    });
  }

  atualizar(){
    this.blockUI?.start();
    this.service.update(this.cidade).pipe(finalize(() => { this.blockUI?.stop() })).subscribe((resposta) => {
      if(resposta !== null){
        this.showSuccessMessage("Pessoa Atualizada com sucesso"); 
        this.cancelarAtualizacao();
        this.findAll();
      }else{
        this.showErrorMessage("Erro ao atualizar cidade");
      }
    }, erro => {
      this.showErrorMessage("Erro ao atualizar cidade");
    })
  }

  requisitaAtualizacao(id: any){
    this.blockUI?.start();
    this.atualizarCidade = true;
    this.service.findById(id).pipe(finalize(() => { this.blockUI?.stop() })).subscribe((resposta) => {
      if(resposta !== null){
        this.showInfoMessage("Edite os dados e clique em atualizar"); 
        this.cidade = resposta;
      }else{
        this.showErrorMessage("Erro ao requisitar atualização de cidade");
      }
    }, erro => {
      this.showErrorMessage("Erro ao requisitar atualização de cidade");
    })
  }

  cancelarAtualizacao(){
    this.limpar();
    this.atualizarCidade = false;
  }

  showSuccessMessage(message: string): void {
    this.MessageService.add({ severity: 'success', summary: 'Sucesso', detail: message });
  }

  showErrorMessage(message: string): void {
    this.MessageService.add({ severity: 'error', summary: 'Erro', detail: message });
  }

  showInfoMessage(message: string): void {
    this.MessageService.add({ severity: 'info', summary: 'Info', detail: message });
  }
}

