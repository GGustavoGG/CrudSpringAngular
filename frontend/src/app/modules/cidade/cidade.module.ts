import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CidadeComponent } from './cidade.component';
import {ButtonModule} from 'primeng/button';
import {TableModule} from 'primeng/table';
import {FormsModule} from '@angular/forms';
import {InputTextModule} from 'primeng/inputtext';
import {DropdownModule} from 'primeng/dropdown';
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { HttpClientModule } from "@angular/common/http";
import {ToastModule} from 'primeng/toast';
import { BlockUIModule } from 'ng-block-ui';


@NgModule({
    declarations: [
        CidadeComponent
    ],
    imports: [ 
        CommonModule,
        ButtonModule,
        TableModule,
        FormsModule,
        InputTextModule,
        DropdownModule,
        BrowserAnimationsModule,
        HttpClientModule,
        ToastModule,
        BlockUIModule.forRoot(),
    ],
    exports: [
        CidadeComponent, 
    ],
    providers: []
})

export class CidadeModule {}