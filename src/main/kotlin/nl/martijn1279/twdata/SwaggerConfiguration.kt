package nl.martijn1279.twdata

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun apiv1(): Docket = Docket(DocumentationType.SWAGGER_2)
            .groupName("V1")
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors
                    .basePackage("nl.martijn1279.twdata.controller.v1"))
            .paths(PathSelectors.regex("/.*"))
            .build()
}
