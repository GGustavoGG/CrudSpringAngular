package com.backend.backend.builder;

import java.util.Collection;

public abstract class ConstrutorDeEntidade<E>{

    private CustomizacaoEntidade<E> customizacao;

    public E construir() {
        final E entidade = construirEntidade();
        if (isCustomizado()){
            customizacao.executar(entidade);
        }
        return persistir(entidade);
    }

    public ConstrutorDeEntidade<E> customizar(CustomizacaoEntidade<E> costumizacao) {
        this.customizacao = customizacao;
        return this;
    }

    protected abstract E construirEntidade();

    protected abstract E persistir(E entidade);

    public boolean isCustomizado() {
        return this.customizacao != null;
    }

    protected abstract E obterPorId(Long id);

    protected abstract Collection<E> obterTodos();

    public void setCustomizacao(CustomizacaoEntidade<E> customizacao){
        this.customizacao = customizacao;
    }
}
