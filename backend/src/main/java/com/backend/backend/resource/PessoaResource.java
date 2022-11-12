package com.backend.backend.resource;

import com.backend.backend.dto.PessoaDTO;
import com.backend.backend.service.PessoaService;
import com.backend.backend.service.builder.PessoaPdfService;
import com.backend.backend.util.ExportacaoUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/pessoas")
@AllArgsConstructor
public class PessoaResource {

    //Injeção de depêndencias
    private PessoaService pessoaService;
    private PessoaPdfService pessoaPdfService;
    private ServletContext servletContext;

    @GetMapping
    public ResponseEntity listar(){
        return ResponseEntity.ok().body(pessoaService.listarPessoas());
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> criar (@Valid @RequestBody PessoaDTO pessoa) throws MethodArgumentNotValidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.criarPessoa(pessoa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> buscaPessoa(@PathVariable Long id){
        try {
            PessoaDTO pessoa = pessoaService.buscarPessoa(id);
            return ResponseEntity.ok().body(pessoa);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity remover(@PathVariable Long id){
        pessoaService.deletarPessoa(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid PessoaDTO pessoa) {
        return ResponseEntity.ok().body(pessoaService.atualizarPessoa(id, pessoa));
    }

    @GetMapping("/{id}/imprimir")
    public ResponseEntity<InputStreamResource> criaRelatorio(@PathVariable Long id) throws Exception {
        byte[] arquivo = this.pessoaPdfService.constroiPdf(id);
        return ExportacaoUtil.output(arquivo, "relatorio");
    }
}
