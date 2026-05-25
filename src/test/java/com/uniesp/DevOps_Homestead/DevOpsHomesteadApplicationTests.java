package com.uniesp.DevOps_Homestead;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class DevOpsHomesteadApplicationTests {

	@Test
	void devePossuirAnotacaoSpringBootApplication() {
		assertTrue(DevOpsHomesteadApplication.class.isAnnotationPresent(SpringBootApplication.class));
	}

}
