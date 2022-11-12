package com.backend.backend.dto.mapper;

import com.backend.backend.dto.CidadeDTO;
import com.backend.backend.model.Cidade;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CidadeMapper {
    CidadeDTO cidadeToCidadeDTO(Cidade cidade);
    Cidade cidadeDTOToCidade(CidadeDTO cidade);
    List<CidadeDTO> listCidadeToListCidadeDTO(List<Cidade> cidades);
}
