package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SpringSecurity {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
	//Disable csrf as we are communicating through postman client	
		serverHttpSecurity.csrf()
		.disable()
		//How spring should handle the scurity 
		//To exclude security token when we make discovery server static call 
		.authorizeExchange(exchange->exchange.pathMatchers("eureka/**")
		//permit all the call to this url pattern
		.permitAll()
		//for any other they should be authenticated
		.anyExchange().authenticated())
		//We also need to Enable resource server capabilities
		.oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt);
		//This will create an object of type HttpSecurity
		return serverHttpSecurity.build();
	}
}
