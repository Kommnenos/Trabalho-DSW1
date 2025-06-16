package br.ufscar.dc.dsw.DCMotors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.dao.IClienteDAO;

@SpringBootApplication(scanBasePackages = "br.ufscar.dc.dsw")
public class DcMotorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcMotorsApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

