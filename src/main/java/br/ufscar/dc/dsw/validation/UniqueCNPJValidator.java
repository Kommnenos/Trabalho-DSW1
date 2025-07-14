package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Loja;

@Component
public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, String> {

	@Autowired
	private IUsuarioDAO dao;

	@Override
	public boolean isValid(String CNPJ, ConstraintValidatorContext context) {
		if (dao != null) {
			Loja loja = dao.findByCNPJ(CNPJ);
			return loja == null;
		} else {
			// Durante a execução da classe LivrariaMvcApplication
			// não há injeção de dependência
			return true;
		}

	}
}