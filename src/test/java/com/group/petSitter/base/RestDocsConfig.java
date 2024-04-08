package com.group.petSitter.base;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@TestConfiguration
public class RestDocsConfig {

    @Bean
    public RestDocumentationResultHandler write() {
        return MockMvcRestDocumentation.document(
            "{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint())
        );
    }
}
