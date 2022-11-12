import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { TabViewModule } from 'primeng/tabview';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { HttpClientModule } from "@angular/common/http";
import {CidadeModule} from './modules/cidade/cidade.module';
import {PessoaModule} from './modules/pessoa/pessoa.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    TabViewModule,
    BrowserAnimationsModule,
    HttpClientModule,
    CidadeModule,
    PessoaModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

