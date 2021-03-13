package com.palilu.mineSweeper.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pmendoza
 * @since 2021-03-08
 */
@SpringBootTest(classes = {SwaggerConfig.class})
public class SwaggerConfigTest {

    @Autowired
    private SwaggerConfig swaggerConfig;

    @Test
    public void test() {
        Docket api = swaggerConfig.api();
        assertEquals(DocumentationType.SWAGGER_2, api.getDocumentationType());
    }
}
