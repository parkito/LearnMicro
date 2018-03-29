package com.parkito.learnmicro.monolith;

import com.google.common.base.Predicates
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableSwagger2
@Configuration
@SpringBootApplication
class UserApplication {

    @Bean
    fun restTemplate() = RestTemplate();

    @Bean
    fun docket() = Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
            .build();
}

fun main(args: Array<String>) {
    SpringApplication.run(UserApplication::class.java, *args);
}

