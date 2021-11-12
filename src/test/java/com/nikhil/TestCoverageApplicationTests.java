package com.nikhil;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest()
class TestCoverageApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void test() {
		TestCoverageApplication.main(new String[] {
				"--spring.main.web-environment=false"
		});
	}
	

}
