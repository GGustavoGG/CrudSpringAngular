package com.backend.backend.builder;

import com.backend.backend.dto.CidadeDTO;
import com.backend.backend.dto.mapper.CidadeMapper;
import com.backend.backend.model.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Collection;

@Component
public class CidadeBuilder extends ConstrutorDeEntidade<Cidade> {

    @Override
    protected Cidade construirEntidade(){
        return null;
    }

    @Override
    protected Cidade persistir(Cidade cidade){
        return null;
    }

    @Override
    protected Cidade obterPorId(Long id){
        return null;
    }

    @Override
    protected Collection<Cidade> obterTodos(){
        return null;
    }

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CidadeMapper cidadeMapper;

    public CidadeDTO constroiCidade(Long id) {
        CidadeDTO cidadeDTO = new CidadeDTO();
        cidadeDTO.setId(id);
        cidadeDTO.setNome("Porto Seguro");
        cidadeDTO.setQtdHabitantes(98000L);
        cidadeDTO.setEstado("Bahia");
        return cidadeDTO;
    }

    public CidadeDTO constroiCidadeNula() {
        CidadeDTO cidadeDTO = new CidadeDTO();
        cidadeDTO.setId(null);
        cidadeDTO.setNome(null);
        cidadeDTO.setEstado(null);
        cidadeDTO.setQtdHabitantes(null);
        return cidadeDTO;
    }

    public CidadeDTO editaCidade(CidadeDTO cidade){
        cidade.setNome("Fortaleza");
        cidade.setEstado("Ceará");
        cidade.setQtdHabitantes(2500000L);
        return cidade;
    }

    public CidadeDTO retornaCidadeSalva(String nome){
        Cidade cidade = new Cidade();;
        cidade.setNome(nome);
        cidade.setQtdHabitantes(666666L);
        cidade.setEstado("Acre");
        this.entityManager.persist(cidade);
        this.entityManager.flush();
        return cidadeMapper.cidadeToCidadeDTO(cidade);
    }
}
