package com.prgrms.zoozoobank.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

//    @Bean
//    public GroupedOpenApi getCustomersApi() {
//        return GroupedOpenApi
//                .builder()
//                .group("customers")
//                .pathsToMatch("/api/customers/**")
//                .build();
//    }

    @Bean
    public GroupedOpenApi getAccountsApi() {
        return GroupedOpenApi
                .builder()
                .group("accounts")
                .pathsToMatch("/api/accounts/**")
                .build();
    }

//    @Bean
//    public GroupedOpenApi getBankBranchApi() {
//        return GroupedOpenApi
//                .builder()
//                .group("accounts")
//                .pathsToMatch("/api/bank-branches/**")
//                .build();
//    }

    @Bean
    public OpenAPI getOpenApi() {
        return new OpenAPI().components(new Components())
                .info(getInfo());
    }

    private Info getInfo() {
        return new Info()
                .version("1.0.0")
                .description("BANKING REST API DOC")
                .title("BANKING");
    }
}
