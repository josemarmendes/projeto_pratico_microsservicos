package br.edu.ifma.microsservices.msgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class MsgatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(MsgatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder){
		return builder
				.routes()
				.route("msclientes", r -> r.path("/clientes/**")
						.uri("lb://msclientes"))
				.route("mscartoes", r -> r.path("/cartoes/**")
						.uri("lb://mscartoes"))
				.route("msavaliadorcredito", r -> r.path("/avaliacoes-credito/**")
						.uri("lb://msavaliadorcredito"))
				.build();
	}

}
