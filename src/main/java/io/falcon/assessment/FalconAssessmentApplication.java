package io.falcon.assessment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "io.falcon.assessment")
@SpringBootApplication
@EnableAutoConfiguration
public class FalconAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FalconAssessmentApplication.class);
		app.run(args);
	}
}
