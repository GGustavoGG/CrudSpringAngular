package com.backend.backend.builder;

import com.backend.backend.dto.CidadeDTO;
import com.backend.backend.dto.PessoaDTO;
import com.backend.backend.dto.mapper.CidadeMapper;
import com.backend.backend.dto.mapper.PessoaMapper;
import com.backend.backend.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Collection;

@Component
public class PessoaBuilder extends ConstrutorDeEntidade<Pessoa> {

    @Override
    protected Pessoa construirEntidade() {return null;}

    @Override
    protected Pessoa persistir(Pessoa pessoa) {return null;}

    @Override
    protected Pessoa obterPorId(Long id) {return null;}

    @Override
    protected Collection<Pessoa> obterTodos() {return null;}


    @Autowired
    private CidadeBuilder cidadeBuilder;

    @Autowired
    private CidadeMapper cidadeMapper;

    public PessoaDTO constroiPessoa(Long id) {
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setId(id);
        pessoa.setNome("Flavio");
        pessoa.setApelido("Flavin do Pneu");
        pessoa.setTimeCoracao("Vasco");
        pessoa.setHobbie("trocar pneu");
        pessoa.setCpf("494.076.520-25");
        pessoa.setCidade(cidadeMapper.cidadeDTOToCidade(cidadeBuilder.retornaCidadeSalva("Rio Branco")));
        return pessoa;
    }

    public PessoaDTO constroiPessoaNula(){
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setId(null);
        pessoa.setNome(null);
        pessoa.setApelido(null);
        pessoa.setTimeCoracao(null);
        pessoa.setHobbie(null);
        pessoa.setCpf(null);
        pessoa.setCidade(null);
        return pessoa;
    }

    public PessoaDTO editaPessoa(PessoaDTO pessoa){
        pessoa.setNome("Samuel");
        pessoa.setApelido("Shaulin Matador de Porco");
        pessoa.setTimeCoracao("Framengu");
        pessoa.setHobbie("Matar Porco");
        pessoa.setCpf("763.468.600-03");
        pessoa.setCidade(cidadeMapper.cidadeDTOToCidade(cidadeBuilder.retornaCidadeSalva("Rio Branco")));
        return pessoa;
    }

    public PessoaDTO constroiPessoaCpfInvalido(){
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNome("Poze");
        pessoa.setApelido("Pitbull do Funk");
        pessoa.setTimeCoracao("framengu");
        pessoa.setHobbie("ser o pitbull do funk");
        pessoa.setCpf("777.777.777-88");
        pessoa.setCidade(cidadeMapper.cidadeDTOToCidade(cidadeBuilder.retornaCidadeSalva("Acaratinga")));
        return pessoa;
    }

    public PessoaDTO constroiPessoaCidadeInvalida(){
        PessoaDTO pessoa = new PessoaDTO();
        pessoa.setNome("Poze");
        pessoa.setApelido("Pitbull do Funk");
        pessoa.setTimeCoracao("framengu");
        pessoa.setHobbie("ser o pitbull do funk");
        pessoa.setCpf("691.560.040-01");
        pessoa.setCidade(null);
        return pessoa;
    }
}
