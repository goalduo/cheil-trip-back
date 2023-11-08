package com.goalduo.cheilTrip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        final ApiInfo apiInfo = new ApiInfoBuilder()
                .title("enjoy trip API")
                .description("enjoy trip API에 관한 설명입니다.")
                .contact(new Contact("SSAFY", "https://edu.ssafy.com","ssafy@email.com"))
                .license("SSAFY License")
                .version("10.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.tripspring"))
//                .paths(PathSelectors.ant("/**/board/**"))
                .paths(PathSelectors.any())
                .build();
    }
}
