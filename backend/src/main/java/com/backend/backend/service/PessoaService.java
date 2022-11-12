package com.backend.backend.service;

import com.backend.backend.dto.PessoaDTO;
import com.backend.backend.dto.mapper.PessoaMapper;
import com.backend.backend.model.Pessoa;
import com.backend.backend.repository.PessoaRepository;
import com.backend.backend.service.builder.PessoaPdfService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaService {
    //Injeção de depêndencia
    private PessoaRepository pessoaRepository;
    private PessoaMapper pessoaMapper;


    public List<PessoaDTO> listarPessoas(){
        List<Pessoa> pessoas = pessoaRepository.findAll();
        return pessoaMapper.listPessoaToListPessoaDTO(pessoas);
    }

    public PessoaDTO criarPessoa(PessoaDTO pessoa) throws MethodArgumentNotValidException {
        boolean isValid = ValidaCpfService.test(pessoaMapper.pessoaDTOToPessoa(pessoa).getCpf());
        if (isValid){
            Pessoa pessoaSalva = pessoaRepository.save(pessoaMapper.pessoaDTOToPessoa(pessoa));
            return pessoaMapper.pessoaToPessoaDTO(pessoaSalva);
        }else {
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(pessoa, "CPF Inválido");
            throw new MethodArgumentNotValidException(null, result);
        }
    }

    public PessoaDTO buscarPessoa(Long id) throws Exception {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        if (pessoa.isPresent()){
            return pessoaMapper.pessoaToPessoaDTO(pessoa.get());
        }else {
            throw new Exception();
        }
    }

    public void deletarPessoa(Long id){
        pessoaRepository.deleteById(id);
    }

    public PessoaDTO atualizarPessoa(Long id, PessoaDTO pessoa){
        Pessoa pessoaSalva = this.pessoaRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        BeanUtils.copyProperties(pessoaMapper.pessoaDTOToPessoa(pessoa),pessoaSalva, "id");
        this.pessoaRepository.save(pessoaSalva);
        return pessoaMapper.pessoaToPessoaDTO(pessoaSalva);
    }


}
