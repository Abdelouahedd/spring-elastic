package org.ae.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableElasticsearchRepositories
@EnableSwagger2
public class SpringElasticApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringElasticApplication.class, args);
    }

}
