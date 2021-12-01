package fr.ans.psc.config;


import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class WebConfig {

    private final BuildProperties buildProperties;

    public WebConfig(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("psc-api-maj-v" + buildProperties.getVersion())
                .description("API de mise à jour unitaire des Professionnels et structures de Santé")
                .termsOfServiceUrl("https://esante.gouv.fr")
                .contact(new Contact("ANS", "https://esante.gouv.fr", "esignsante@esante.gouv.fr"))
                .build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("fr.ans.psc.api"))
                .paths(PathSelectors.any())
                .build();
    }
}
