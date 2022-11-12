package com.backend.backend.resource;

import com.backend.backend.dto.CidadeDTO;
import com.backend.backend.dto.PessoaDTO;
import com.backend.backend.model.Cidade;
import com.backend.backend.service.CidadeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cidades")
@AllArgsConstructor
public class CidadeResource {

    //Injeção de depêndencias
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity listar(){
        return ResponseEntity.ok().body(cidadeService.ListarCidades());
    }

    @PostMapping
    public ResponseEntity<CidadeDTO> criar (@Valid @RequestBody CidadeDTO cidade){
        CidadeDTO cidadeCriada = cidadeService.criarCidade(cidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(cidadeCriada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remover (@PathVariable Long id) {
        cidadeService.deletaCidade(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTO> buscaCidade(@PathVariable Long id){
        try{
            CidadeDTO cidade = cidadeService.buscaCidade(id);
            return ResponseEntity.ok().body(cidade);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid CidadeDTO cidade) {
        CidadeDTO cidadeSalva = cidadeService.atualizarCidade(id, cidade);
        return ResponseEntity.ok().body(cidadeSalva);
    }
}
