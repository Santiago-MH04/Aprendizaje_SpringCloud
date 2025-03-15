package org.santiago.springcloud.msvcoauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class}) //Esta aplicación no tiene persistencia en este proyecto, y Commons usa una clase que la requiere (Product)
public class MsvcOAuth2Application {
	public static void main(String[] args) {
		SpringApplication.run(MsvcOAuth2Application.class, args);
	}
}
