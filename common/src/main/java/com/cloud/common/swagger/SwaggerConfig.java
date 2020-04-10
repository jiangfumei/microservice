package com.cloud.common.swagger;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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


@Slf4j
@Configuration//加载该类配置
@EnableSwagger2//启用Swagger2
public class SwaggerConfig {

    @Value("${swagger.title}")
    private String title;

    @Value("${swagger.description}")
    private String description;

    @Value("${swagger.version}")
    private String version;

    @Value("${swagger.termsOfServiceUrl}")
    private String termsOfServiceUrl;

    @Value("${swagger.contact.name}")
    private String name;

    @Value("${swagger.contact.url}")
    private String url;

    @Value("${swagger.contact.email}")
    private String email;


    /*
    .apis方法用于指定生成注解的范围，有以下四种取值逻辑
①RequestHandlerSelectors.any()，为所有接口都生成API文档，这种方式不必在接口上加任何注解，但是生成的文档没有任何注释，可读性不高；
②RequestHandlerSelectors.basePackage(xx.xx)，为指定包下的controller生成接口文档
③RequestHandlerSelectors.withClassAnnotation(Api.class)，为有@api注解的接口生成api文档
④RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)，为有@ApiOperation注解的方法生成API文档。
    */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.didispace.web"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(name, url, email))
                .version(version)
                .build();
    }

}