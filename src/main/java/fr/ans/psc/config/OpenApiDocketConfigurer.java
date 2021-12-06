package fr.ans.psc.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class OpenApiDocketConfigurer {

    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("Note API")
                        .description("A CRUD API to demonstrate Springfox 3 integration")
                        .version("0.0.1-SNAPSHOT")
                        .license("MIT")
                        .licenseUrl("https://opensource.org/licenses/MIT%22")
                        .build())
                        .tags(new Tag("Note", "Endpoints for CRUD operations on notes"))
                        .select().apis(RequestHandlerSelectors.any())
                        .build();

    }
}
