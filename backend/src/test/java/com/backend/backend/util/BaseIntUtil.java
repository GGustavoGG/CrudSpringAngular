package com.backend.backend.util;

import com.backend.backend.BackendApplication;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.persistence.EntityManager;
import java.io.IOException;

@SpringBootTest(classes = {BackendApplication.class})
@WebAppConfiguration
@TestConfiguration
public class BaseIntUtil {

    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private EntityManager entityManager;

    /**
     * Atribuir o valor do <code>pWebApplicationContext</code> no atribuito <code>webApplicationContext</code> da Classe.
     *
     * @param pWebApplicationContext Parâmetro de Entrada
     */
    @Autowired
    public void setWebApplicationContext(WebApplicationContext pWebApplicationContext) {
        webApplicationContext = pWebApplicationContext;
    }

    /**
     * Obter o valor do atributo <code>entityManager</code> da classe.
     *
     * @return O atributo <code>entityManager</code>.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Atribuir o valor do <code>pEntityManager</code> no atribuito <code>entityManager</code> da Classe.
     *
     * @param pEntityManager Parâmetro de Entrada
     */
    @Autowired
    private void setEntityManager(EntityManager pEntityManager) {
        entityManager = pEntityManager;
    }

    /**
     * Retorna o valor do atributo <code>mockMvc</code> do Objeto.
     *
     * @return O atributo <code>mockMvc</code>
     */
    protected MockMvc getMockMvc() {
        return mockMvc;
    }


    protected byte[] toJson(Object object) throws IOException {
        return TestUtil.convertObjectToJsonBytes(object);
    }

    protected void emFlushClear() {
        entityManager.flush();
        entityManager.clear();
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

}

