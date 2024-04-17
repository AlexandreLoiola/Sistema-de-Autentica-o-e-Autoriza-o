package com.AlexandreLoiola.Access.Management;

import com.AlexandreLoiola.AccessManagement.AccessManagementApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = AccessManagementApplication.class)
class AccessManagementApplicationTests {

	@MockBean
	private SecurityAutoConfiguration securityAutoConfiguration;

	@Test
	void contextLoads() {
		String[] args = {};
		AccessManagementApplication.main(args);
	}

}
