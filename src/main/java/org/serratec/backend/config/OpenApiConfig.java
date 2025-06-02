package org.serratec.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${dominio.openapi.dev-url}")
    private String devUrl;
    @Value("${dominio.openapi.prod-url}")
    private String prodUrl;

    @Bean
    OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor de desenvolvimento");
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("URL do servidor de produção");
        Contact contact = new Contact();
        contact.setEmail("dragonstore@gmail.com");
        contact.setName("Dragon Store");
        contact.setUrl("http://www.localhost:8080");
        License apacheLicense = new License().name("Apache 	License")
                .url("https://www.apache.org/licenses/LICENSE-2.0");
        Info info = new Info().title("Projeto Final API - Residência de Software - SERRATEC").version("1.0").contact(contact)
                .description("API de e-commerce de uma loja de jogos eletrônicos.").termsOfService("http://www.localhost:8080/termos")
                .license(apacheLicense);
        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }

}
