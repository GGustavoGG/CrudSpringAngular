package com.backend.backend.service;

import com.backend.backend.dto.CidadeDTO;
import com.backend.backend.dto.mapper.CidadeMapper;
import com.backend.backend.model.Cidade;
import com.backend.backend.repository.CidadeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CidadeService {
    //Injeções de depêndencia
    private CidadeRepository cidadeRepository;
    private CidadeMapper cidadeMapper;

    public List<CidadeDTO> ListarCidades() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidadeMapper.listCidadeToListCidadeDTO(cidades);
    }

    public CidadeDTO criarCidade(CidadeDTO cidade){
        Cidade cidadeSalva = cidadeRepository.save(cidadeMapper.cidadeDTOToCidade(cidade));
        return cidadeMapper.cidadeToCidadeDTO(cidadeSalva);
    }

    public void deletaCidade(Long id){
        cidadeRepository.deleteById(id);
    }

    public CidadeDTO buscaCidade(Long id) throws Exception {
        Optional<Cidade> cidade = cidadeRepository.findById(id);
        if(cidade.isPresent()){
            return cidadeMapper.cidadeToCidadeDTO(cidade.get());
        }else{
            throw new Exception();
        }
    }

    public CidadeDTO atualizarCidade(Long id, CidadeDTO cidade){
        Cidade cidadeSalva = this.cidadeRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(cidadeMapper.cidadeDTOToCidade(cidade), cidadeSalva, "id");
        this.cidadeRepository.save(cidadeSalva);
        return cidadeMapper.cidadeToCidadeDTO(cidadeSalva);
    }
}
