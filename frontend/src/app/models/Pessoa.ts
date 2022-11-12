import { Cidade } from "./Cidade";

export class Pessoa {
    id?: number;
    nome?: string;
    apelido?: string;
    timeCoracao?: string;
    cpf?: string;
    hobbie?: string;
    cidade?: Cidade;

    constructor(){}
}