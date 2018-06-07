package com.parkito.learnmicro.documents;

import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.client.RetryProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableDiscoveryClient
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication
public class DocumentApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .build();
    }

    @Bean
    public RetryOperationsInterceptor configServerRetryInterceptor(RetryProperties properties) {
        return RetryInterceptorBuilder
                .stateless()
                .backOffOptions(properties.getInitialInterval(),
                        properties.getMultiplier(),
                        properties.getMaxInterval())
                .maxAttempts(properties.getMaxAttempts())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(DocumentApplication.class, args);
    }
}
