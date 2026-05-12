package com.sanossalvos.geolocalizacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GeolocalizacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeolocalizacionApplication.class, args);
	}

}
