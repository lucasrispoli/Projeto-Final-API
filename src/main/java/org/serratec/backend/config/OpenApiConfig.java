package org.serratec.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private final String devUrl = "http://localhost:8080";
    private final String prodUrl = "https://sua-api-em-producao.com";

    @Bean
    public OpenAPI myOpenAPI() {
        // Configuração dos servidores
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor de desenvolvimento");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("URL do servidor de produção");

        // Contato
        Contact contact = new Contact();
        contact.setEmail("dragonstore@gmail.com");
        contact.setName("Dragon Store");
        contact.setUrl("http://www.localhost:8080");

        // Licença
        License apacheLicense = new License()
                .name("Apache License")
                .url("https://www.apache.org/licenses/LICENSE-2.0");

        // Informações da API
        Info info = new Info()
                .title("Projeto Final API - Residência de Software - SERRATEC")
                .version("1.0")
                .contact(contact)
                .description("API de e-commerce de uma loja de jogos eletrônicos.")
                .termsOfService("http://www.localhost:8080/termos")
                .license(apacheLicense);

        // Configuração de segurança (Bearer Token)
        final String securitySchemeName = "bearerAuth";
        SecurityScheme securityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer))
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes(securitySchemeName, securityScheme));
    }
}