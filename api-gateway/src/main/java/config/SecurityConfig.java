package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context. annotation. Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    //Security Config class for the webflux

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {

        /*the discovery service doesn't need any extra authentication to be set up. It can be accessed just by adding
        permit all feature to URI "/eureka/**"*/
        //TODO: We can't reach http://localhost:8080/eureka/web address due to authorization issue. Above comment not working for me.
        serverHttpSecurity.csrf()
                .disable()
                .authorizeExchange(exchange -> exchange.pathMatchers("/eureka/**")
                .permitAll()
                .anyExchange()
                .authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);

        return serverHttpSecurity.build();
    }
}
