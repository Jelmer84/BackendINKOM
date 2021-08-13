package INKOM.Backend;

import INKOM.Backend.domain.ProfilePicture;
import INKOM.Backend.domain.Role;
import INKOM.Backend.domain.User;
import INKOM.Backend.payload.response.EventSummary;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private ApiInfo apiInfo() {
        return new ApiInfo("INKOM Backend",
                "INKOM stock taking API",
                "1.0",
                "Terms of service",
                new Contact("INKOM.Backend", "INKOM.Backend", "INKOM.Backend"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                //.apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("INKOM.Backend"))
                //.paths(PathSelectors.any())
                .paths(PathSelectors.ant("/api/**"))

                .build()
                .ignoredParameterTypes(EventSummary.class)
                //.ignoredParameterTypes(Jwt.class)
                .additionalModels(typeResolver.resolve(ProfilePicture.class))
                .additionalModels(typeResolver.resolve(Role.class))
                .additionalModels(typeResolver.resolve(User.class));
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}