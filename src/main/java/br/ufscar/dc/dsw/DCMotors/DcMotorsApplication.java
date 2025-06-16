package br.ufscar.dc.dsw.DCMotors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.ufscar.dc.dsw")
public class DcMotorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcMotorsApplication.class, args);
	}

}
