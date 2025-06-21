package br.ufscar.dc.dsw.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.ILojaDAO;
import br.ufscar.dc.dsw.dao.IClienteDAO;


@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private ILojaDAO lojaDao;

    @Autowired
    private IClienteDAO clienteDao;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if (lojaDao == null || clienteDao == null || email == null) {
            return true;
        }

        boolean emailExisteLoja = lojaDao.getLojaByEmail(email) != null;
        boolean emailExisteCliente = clienteDao.getClienteByEmail(email) != null;

        return !(emailExisteLoja || emailExisteCliente);

    }
}