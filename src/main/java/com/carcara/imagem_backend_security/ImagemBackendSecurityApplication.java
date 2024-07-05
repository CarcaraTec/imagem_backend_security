package com.carcara.imagem_backend_security;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)

public class ImagemBackendSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImagemBackendSecurityApplication.class, args);
	}
}
