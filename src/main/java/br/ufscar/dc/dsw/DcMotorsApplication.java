package br.ufscar.dc.dsw;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.impl.EmailService;
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
	public CommandLineRunner demo(IClienteDAO clienteDAO, BCryptPasswordEncoder encoder, EmailService emailService){
		return args -> {
			if(clienteDAO.getClienteByEmail("admin") == null) {
				Usuario admin1 = new Usuario();
				admin1.setNome("Administrador");
				admin1.setEmail("admin");
				admin1.setSenha(encoder.encode("admin"));
				IUsuarioDAO.save(admin1);
			}

		};
	}

}

