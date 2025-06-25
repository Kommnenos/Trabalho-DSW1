package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import br.ufscar.dc.dsw.security.ClienteDetailsServiceImpl;
import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UsuarioDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((authz) -> authz
						.requestMatchers("/", "/home", "/error", "/login/**", "/js/**").permitAll()
						.requestMatchers("/css/**", "/image/**", "/webjars/**").permitAll()
						.requestMatchers("/cliente/cadastrar", "/cliente/salvar").permitAll()
						.requestMatchers("/loja/cadastrar", "/loja/salvar").permitAll()
						.requestMatchers( "/veiculo/listarTodos", "/veiculo/download/**").permitAll()
						.requestMatchers("/admin/**" ).hasRole("ADMIN")
						.requestMatchers("/veiculo/cadastrar", "/veiculo/salvar" ).hasRole("LOJA")
						.requestMatchers("/veiculo/**").hasRole("LOJA")
						.requestMatchers("/proposta/listar").hasAnyRole("USER","LOJA")
						.requestMatchers("/proposta/cadastrar/**", "/proposta/cliente/**").hasRole("USER")
						.requestMatchers("/proposta/aceitar/**").hasRole("LOJA")
						.requestMatchers("/proposta/loja/**").hasRole("LOJA")
						.requestMatchers("/proposta/**" ).hasRole("USER")

						.anyRequest().authenticated())

				.formLogin((form) -> form
						.loginPage("/login")
						.permitAll())
				.logout((logout) -> logout
						.logoutSuccessUrl("/").permitAll());

		return http.build();
	}
}