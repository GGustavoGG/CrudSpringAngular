package com.backend.backend.resource;

import com.backend.backend.builder.CidadeBuilder;
import com.backend.backend.dto.CidadeDTO;
import com.backend.backend.util.BaseIntUtil;
import com.backend.backend.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@Transactional
public class CidadeResourceTest  extends BaseIntUtil {
    private final String URL = "/api/cidades";

    @Autowired
    private MappingJackson2HttpMessageConverter messageConverter;

    @Autowired
    private CidadeBuilder cidadeBuilder;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void salvaCidadeTeste() throws Exception {
        CidadeDTO cidadeConstruida = cidadeBuilder.constroiCidade(null);
        CidadeDTO cidadeSalva = messageConverter.getObjectMapper().readValue(getMockMvc().perform(MockMvcRequestBuilders.post(URL).contentType(TestUtil.APPLICATION_JSON_UTF8).content(TestUtil.convertObjectToJsonBytes(cidadeConstruida))).andExpect(status().isCreated()).andReturn().getResponse().getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeSalva.getId());

        Assert.assertEquals(cidadeSalva.getNome(), cidadeConstruida.getNome());
        Assert.assertEquals(cidadeSalva.getQtdHabitantes(), cidadeConstruida.getQtdHabitantes());
        Assert.assertEquals(cidadeSalva.getEstado(), cidadeConstruida.getEstado());
    }

    @Test
    public void atualizaCidadeTeste() throws Exception {
        CidadeDTO cidadeConstruida = cidadeBuilder.constroiCidade(null);
        CidadeDTO cidadeSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
                .perform(MockMvcRequestBuilders.post(URL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(cidadeConstruida)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeSalva.getId());

        Assert.assertEquals(cidadeSalva.getNome(), cidadeConstruida.getNome());
        Assert.assertEquals(cidadeSalva.getQtdHabitantes(), cidadeConstruida.getQtdHabitantes());
        Assert.assertEquals(cidadeSalva.getEstado(), cidadeConstruida.getEstado());

        cidadeSalva = cidadeBuilder.editaCidade(cidadeSalva);
        String URL_FINAL = String.format("%s/%d", URL, cidadeSalva.getId());

        CidadeDTO cidadeAtualizada = messageConverter.getObjectMapper().readValue(getMockMvc()
                    .perform(MockMvcRequestBuilders.put(URL_FINAL)
                    .contentType(TestUtil.APPLICATION_JSON_UTF8)
                    .content(TestUtil.convertObjectToJsonBytes(cidadeSalva)))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeAtualizada.getId());
        Assert.assertNotEquals(cidadeConstruida.getNome(), cidadeAtualizada.getNome());
        Assert.assertNotEquals(cidadeConstruida.getEstado(), cidadeAtualizada.getEstado());
        Assert.assertNotEquals(cidadeConstruida.getQtdHabitantes(), cidadeAtualizada.getQtdHabitantes());
    }

    @Test
    public void salvaCidadeBadRequestTeste() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.post(URL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(cidadeBuilder.constroiCidadeNula())))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void atualizaCidadeBadRequestTeste() throws Exception {
        CidadeDTO cidadeConstruida = cidadeBuilder.constroiCidade(null);
        CidadeDTO cidadeSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(cidadeConstruida)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeSalva.getId());

        Assert.assertEquals(cidadeSalva.getNome(), cidadeConstruida.getNome());
        Assert.assertEquals(cidadeSalva.getQtdHabitantes(), cidadeConstruida.getQtdHabitantes());
        Assert.assertEquals(cidadeSalva.getEstado(), cidadeConstruida.getEstado());

        CidadeDTO cidadeNula = cidadeBuilder.constroiCidadeNula();
        String URL_FINAL = String.format("%s/%d", URL, cidadeSalva.getId());
        getMockMvc().perform(MockMvcRequestBuilders.put(URL_FINAL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(cidadeNula)))
        .andExpect(status().isBadRequest());
    }

    @Test
    public void atualizaCidadeBadRequestNaoEncontradoTeste() throws Exception {
        int id = 99999;
        String URL_FINAL = String.format("%s/%d", URL, id);
        getMockMvc().perform(MockMvcRequestBuilders.put(URL_FINAL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(TestUtil.convertObjectToJsonBytes(cidadeBuilder.constroiCidade(1000L))))
        .andExpect(status().isNotFound());
    }

    @Test
    public void buscaCidadeNaoEncontradaTeste() throws Exception {
        Long id = Long.valueOf(99999);
        String URL_FINAL = String.format("%s/%d", URL, id);
        getMockMvc().perform(MockMvcRequestBuilders.get(URL_FINAL)
        .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isNotFound());
    }

    @Test
    public void buscaCidadeTeste() throws Exception {
        CidadeDTO cidadeConstruida = cidadeBuilder.constroiCidade(null);
        CidadeDTO cidadeSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
                .perform(MockMvcRequestBuilders.post(URL)
                        .contentType(TestUtil.APPLICATION_JSON_UTF8)
                        .content(TestUtil.convertObjectToJsonBytes(cidadeConstruida)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeSalva.getId());

        Assert.assertEquals(cidadeSalva.getNome(), cidadeConstruida.getNome());
        Assert.assertEquals(cidadeSalva.getQtdHabitantes(), cidadeConstruida.getQtdHabitantes());
        Assert.assertEquals(cidadeSalva.getEstado(), cidadeConstruida.getEstado());

        String URL_FINAL = String.format("%s/%d",URL, cidadeSalva.getId());

        CidadeDTO cidadeBuscada = messageConverter.getObjectMapper().readValue(getMockMvc()
            .perform(MockMvcRequestBuilders.get(URL_FINAL)
                .contentType(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeBuscada.getId());
        Assert.assertEquals(cidadeSalva.getNome(),cidadeBuscada.getNome());
        Assert.assertEquals(cidadeSalva.getEstado(), cidadeBuscada.getEstado());
        Assert.assertEquals(cidadeSalva.getQtdHabitantes(), cidadeBuscada.getQtdHabitantes());
    }

    @Test
    public void listaCidadesCriadasTestes() throws Exception {
        CidadeDTO cidadeConstruida = cidadeBuilder.constroiCidade(null);
        CidadeDTO cidadeSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidadeConstruida))
        )
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeSalva.getId());
        Assert.assertEquals(cidadeConstruida.getNome(), cidadeSalva.getNome());
        Assert.assertEquals(cidadeConstruida.getEstado(), cidadeSalva.getEstado());
        Assert.assertEquals(cidadeConstruida.getQtdHabitantes(), cidadeSalva.getQtdHabitantes());

        List cidades = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.get(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString(), List.class);

        Assert.assertEquals(Long.valueOf(cidades.size()), entityManager.createQuery("select count(c) from Cidade c").getSingleResult());
    }

    @Test
    public void apagaCidadeTeste() throws Exception {
        CidadeDTO cidadeConstruida = cidadeBuilder.constroiCidade(null);
        CidadeDTO cidadeSalva = messageConverter.getObjectMapper().readValue(getMockMvc()
        .perform(MockMvcRequestBuilders.post(URL)
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidadeConstruida)))
        .andExpect(status().isCreated())
        .andReturn()
        .getResponse()
        .getContentAsString(), CidadeDTO.class);

        Assert.assertNotNull(cidadeSalva.getId());
        Assert.assertEquals(cidadeConstruida.getNome(), cidadeSalva.getNome());
        Assert.assertEquals(cidadeConstruida.getEstado(), cidadeSalva.getEstado());
        Assert.assertEquals(cidadeConstruida.getQtdHabitantes(), cidadeSalva.getQtdHabitantes());

        long quantidadeCidades = (Long) entityManager.createQuery("select count(c) from Cidade c").getSingleResult();
        String URL_FINAL = String.format("%s/%d", URL, cidadeSalva.getId());

        getMockMvc().perform(MockMvcRequestBuilders.delete(URL_FINAL)).andExpect(status().isNoContent());

        Assert.assertEquals(quantidadeCidades - 1, entityManager.createQuery("select count(c) from Cidade c").getSingleResult());
    }

    @Test
    public void apagaCidadeNotFoundTest() throws Exception {
        String URL_FINAL = String.format("%s/%d", URL, 9999);
        getMockMvc().perform(MockMvcRequestBuilders.delete(URL_FINAL)).andExpect(status().isNotFound());
    }
}
