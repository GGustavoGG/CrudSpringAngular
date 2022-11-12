package com.backend.backend.dto.mapper;

import com.backend.backend.dto.PessoaDTO;
import com.backend.backend.model.Pessoa;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface PessoaMapper {
    PessoaDTO pessoaToPessoaDTO (Pessoa pessoa);
    Pessoa pessoaDTOToPessoa (PessoaDTO pessoa);
    List<PessoaDTO> listPessoaToListPessoaDTO(List<Pessoa> pessoa);
}
