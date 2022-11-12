package com.backend.backend.resource;

import com.backend.backend.builder.PessoaBuilder;
import com.backend.backend.dto.CidadeDTO;
import com.backend.backend.dto.PessoaDTO;
import com.backend.backend.util.BaseIntUtil;
import com.backend.backend.util.TestUtil;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@Transactional
public class PessoaResourceTest  extends BaseIntUtil {
    private final String URL = "/api/pessoas";

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Autowired
    private PessoaBuilder pessoaBuilder;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void salvaPessoaTeste() throws Exception {
        PessoaDTO pessoaConstruida = pessoaBuilder.constroiPessoa(null);
        PessoaDTO pessoaSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.post(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pessoaConstruida)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), PessoaDTO.class);

        Assert.assertNotNull(pessoaSalva.getId());
        Assert.assertEquals(pessoaConstruida.getNome(), pessoaSalva.getNome());
        Assert.assertEquals(pessoaConstruida.getApelido(), pessoaSalva.getApelido());
        Assert.assertEquals(pessoaConstruida.getCpf(), pessoaSalva.getCpf());
        Assert.assertEquals(pessoaConstruida.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertEquals(pessoaConstruida.getTimeCoracao(), pessoaSalva.getTimeCoracao());
    }

    @Test
    public void salvaPessoaNulaTeste() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaBuilder.constroiPessoaNula())))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void salvaPessoaCidadeInvalidaTeste() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaBuilder.constroiPessoaCidadeInvalida())))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void salvaPessoaCpfInvalidoTeste() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaBuilder.constroiPessoaCpfInvalido())))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void editaPessoaTeste() throws Exception {
        PessoaDTO pessoaConstruida = pessoaBuilder.constroiPessoa(null);
        PessoaDTO pessoaSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaConstruida)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), PessoaDTO.class);

        Assert.assertNotNull(pessoaSalva.getId());
        Assert.assertEquals(pessoaConstruida.getNome(), pessoaSalva.getNome());
        Assert.assertEquals(pessoaConstruida.getApelido(), pessoaSalva.getApelido());
        Assert.assertEquals(pessoaConstruida.getCpf(), pessoaSalva.getCpf());
        Assert.assertEquals(pessoaConstruida.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertEquals(pessoaConstruida.getTimeCoracao(), pessoaSalva.getTimeCoracao());


        pessoaConstruida = pessoaBuilder.editaPessoa(pessoaConstruida);
        String URL_FINAL = String.format("%s/%d",URL, pessoaSalva.getId());

        PessoaDTO pessoaEditada = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.put(URL_FINAL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaConstruida)))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(), PessoaDTO.class);

        Assert.assertNotNull(pessoaEditada.getId());
        Assert.assertNotEquals(pessoaEditada.getNome(), pessoaSalva.getNome());
        Assert.assertNotEquals(pessoaEditada.getApelido(), pessoaSalva.getApelido());
        Assert.assertNotEquals(pessoaEditada.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertNotEquals(pessoaEditada.getTimeCoracao(), pessoaSalva.getTimeCoracao());
        Assert.assertNotEquals(pessoaEditada.getCpf(), pessoaSalva.getCpf());
        Assert.assertNotEquals(pessoaEditada.getCidade(), pessoaSalva.getCidade());
    }

    @Test
    public void editaPessoaBadRequestTeste() throws Exception {
        PessoaDTO pessoaConstruida = pessoaBuilder.constroiPessoa(null);
        PessoaDTO pessoaSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(pessoaConstruida)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(), PessoaDTO.class);

        Assert.assertNotNull(pessoaSalva.getId());
        Assert.assertEquals(pessoaConstruida.getNome(), pessoaSalva.getNome());
        Assert.assertEquals(pessoaConstruida.getApelido(), pessoaSalva.getApelido());
        Assert.assertEquals(pessoaConstruida.getCpf(), pessoaSalva.getCpf());
        Assert.assertEquals(pessoaConstruida.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertEquals(pessoaConstruida.getTimeCoracao(), pessoaSalva.getTimeCoracao());

        PessoaDTO pessoaNula = pessoaBuilder.constroiPessoaNula();
        String URL_FINAL = String.format("%s/%d", URL, pessoaSalva.getId());
        getMockMvc().perform(MockMvcRequestBuilders.put(URL_FINAL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaNula)))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void editaPessoaNaoEncontradoTeste() throws Exception {
        int id = 999999;
        String URL_FINAL = String.format("%s/%d", URL, id);
        getMockMvc().perform(MockMvcRequestBuilders.put(URL_FINAL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaBuilder.constroiPessoa(null))))
        .andExpect(status().isNotFound());
    }

    @Test
    public void buscaPessoaNaoEncontradoTeste() throws Exception {
        int id = 999999;
        String URL_FINAL = String.format("%s/%d", URL, id);
        getMockMvc().perform(MockMvcRequestBuilders.get(URL_FINAL))
                .andExpect(status().isNotFound());
    }

    @Test
    public void buscaPessoaTeste() throws Exception {
        PessoaDTO pessoaConstruida = pessoaBuilder.constroiPessoa(null);
        PessoaDTO pessoaSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaConstruida)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), PessoaDTO.class);

        Assert.assertNotNull(pessoaSalva.getId());
        Assert.assertEquals(pessoaConstruida.getNome(), pessoaSalva.getNome());
        Assert.assertEquals(pessoaConstruida.getApelido(), pessoaSalva.getApelido());
        Assert.assertEquals(pessoaConstruida.getCpf(), pessoaSalva.getCpf());
        Assert.assertEquals(pessoaConstruida.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertEquals(pessoaConstruida.getTimeCoracao(), pessoaSalva.getTimeCoracao());

        String URL_FINAL = String.format("%s/%d", URL, pessoaSalva.getId());
        PessoaDTO pessoaBuscada = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.get(URL_FINAL))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(), PessoaDTO.class);

        Assert.assertEquals(pessoaBuscada.getId(), pessoaSalva.getId());
        Assert.assertEquals(pessoaBuscada.getNome(), pessoaSalva.getNome());
        Assert.assertEquals(pessoaBuscada.getApelido(), pessoaSalva.getApelido());
        Assert.assertEquals(pessoaBuscada.getTimeCoracao(), pessoaSalva.getTimeCoracao());
        Assert.assertEquals(pessoaBuscada.getCpf(), pessoaSalva.getCpf());
        Assert.assertEquals(pessoaBuscada.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertEquals(pessoaBuscada.getCidade(), pessoaSalva.getCidade());
    }

    @Test
    public void listaPessoaCriadaTeste() throws Exception {
        PessoaDTO pessoaConstruida = pessoaBuilder.constroiPessoa(null);
        PessoaDTO pessoaSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaConstruida)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), PessoaDTO.class);

        Assert.assertNotNull(pessoaSalva.getId());
        Assert.assertEquals(pessoaConstruida.getNome(), pessoaSalva.getNome());
        Assert.assertEquals(pessoaConstruida.getApelido(), pessoaSalva.getApelido());
        Assert.assertEquals(pessoaConstruida.getCpf(), pessoaSalva.getCpf());
        Assert.assertEquals(pessoaConstruida.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertEquals(pessoaConstruida.getTimeCoracao(), pessoaSalva.getTimeCoracao());

        List pessoas = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.get(URL))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(), List.class);

        Assert.assertEquals(Long.valueOf(pessoas.size()), entityManager.createQuery("select count(city) from Pessoa city").getSingleResult());
    }

    @Test
    public void apagaPessoaTeste() throws Exception {
        PessoaDTO pessoaConstruida = pessoaBuilder.constroiPessoa(null);
        PessoaDTO pessoaSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoaConstruida)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), PessoaDTO.class);

        Assert.assertNotNull(pessoaSalva.getId());
        Assert.assertEquals(pessoaConstruida.getNome(), pessoaSalva.getNome());
        Assert.assertEquals(pessoaConstruida.getApelido(), pessoaSalva.getApelido());
        Assert.assertEquals(pessoaConstruida.getCpf(), pessoaSalva.getCpf());
        Assert.assertEquals(pessoaConstruida.getHobbie(), pessoaSalva.getHobbie());
        Assert.assertEquals(pessoaConstruida.getTimeCoracao(), pessoaSalva.getTimeCoracao());

        Long qtdPessoas = (long) entityManager.createQuery("select count(c) from Pessoa c").getSingleResult();
        String URL_FINAL = String.format("%s/%d", URL, pessoaSalva.getId());

        getMockMvc().perform(MockMvcRequestBuilders.delete(URL_FINAL)).andExpect(status().isNoContent());

        Assert.assertEquals(qtdPessoas - 1, entityManager.createQuery("select count(c) from Pessoa c").getSingleResult());
    }

    @Test
    public void apagaPessoaNotFoundTeste() throws Exception {
        String URL_FINAL = String.format("%s/%d", URL, 999999);
        getMockMvc().perform(MockMvcRequestBuilders.delete(URL_FINAL)).andExpect(status().isNotFound());
    }
}
