package com.backend.backend.configuration;

import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ITextConfig {
    @Bean
    public CSSResolver cssResolver() {
        return XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
    }
}
