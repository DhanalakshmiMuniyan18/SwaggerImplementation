package com.ideas2it.groceryshop.swaggerConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    private static final String BEARER_AUTH = "Bearer";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ideas2it.groceryshop"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo()).securitySchemes(securitySchemes()).securityContexts(List.of(securityContext()));
    }

    /**
     * It provides the information about the API.
     *
     * @return ApiInfo object is being returned.
     */
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "GroceryShop",
                "This api provides Users to purchase a grocery items",
                "1.0",
                "T&C",
                new Contact("dhana", "www.ideas2it.com", "dhanalakshmi@ideas2it.com"),
                "", "", Collections.emptyList());
    }

    /**
     * It creates a list of security schemes that are to be used.
     *
     * @return A list of security schemes.
     */
    private List<SecurityScheme> securitySchemes() {
        return List.of(new ApiKey(BEARER_AUTH, "Authorization", "header"));
    }

    /**
     * This function creates a security context that uses the bearerAuthReference() function to create a security
     * reference that is used to secure all paths
     *
     * @return A SecurityContext object.
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(List.of(bearerAuthReference())).forPaths(PathSelectors.ant("/**")).build();
    }

    /**
     * It creates a security reference with the name "Bearer" and an empty array of authorization scopes
     *
     * @return A SecurityReference object.
     */
    private SecurityReference bearerAuthReference() {
        return new SecurityReference(BEARER_AUTH, new AuthorizationScope[0]);
    }
}

