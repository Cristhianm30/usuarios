package com.plaza.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication

public class UsuariosApplication {

	public static void main(String[] args) {SpringApplication.run(UsuariosApplication.class, args);
	}

}
