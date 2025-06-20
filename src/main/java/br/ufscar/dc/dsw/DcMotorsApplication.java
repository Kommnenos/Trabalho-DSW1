package br.ufscar.dc.dsw;

import br.ufscar.dc.dsw.domain.Cliente;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.dao.IVeiculoDAO;
import br.ufscar.dc.dsw.dao.IClienteDAO;

@SpringBootApplication
public class DcMotorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DcMotorsApplication.class, args);
	}
	@Bean
	public CommandLineRunner demo(IClienteDAO clienteDAO, BCryptPasswordEncoder encoder){
		return args -> {
			Cliente admin1 = new Cliente();
			admin1.setNome("Administrador");
			admin1.setEmail("admin");
			admin1.setSenha(encoder.encode("admin"));
			admin1.setCPF("012.345.678-90");
			admin1.setRole("ROLE_ADMIN");
			admin1.setEnabled(true);
			clienteDAO.save(admin1);
		};
	}

}

