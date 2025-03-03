package org.santiago.springcloud.msvcusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MsvcUsersApplication {
	public static void main(String[] args) {
		SpringApplication.run(MsvcUsersApplication.class, args);
	}

		//Ajustar otras configuraciones
	@Bean //Para poder inyectar luego, es como el @Produces de Jakarta
	PasswordEncoder passwordEncoder(){
			return new BCryptPasswordEncoder();
		}
}
