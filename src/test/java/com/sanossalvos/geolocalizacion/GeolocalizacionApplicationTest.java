package com.sanossalvos.geolocalizacion;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = GeolocalizacionApplication.class)
@ActiveProfiles("test")
class GeolocalizacionApplicationTest {

	@Test
	void contextLoads() {
	}
}