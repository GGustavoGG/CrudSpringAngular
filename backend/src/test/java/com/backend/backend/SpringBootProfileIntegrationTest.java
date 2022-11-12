package com.backend.backend;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        BackendApplication.class,
        H2TestProfileJPAConfig.class})
@ActiveProfiles("test")
public class SpringBootProfileIntegrationTest {
}
