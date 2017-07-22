package io.falcon.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("io.falcon.assessment")
@SpringBootApplication
public class FalconAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(FalconAssessmentApplication.class, args);
	}
}
