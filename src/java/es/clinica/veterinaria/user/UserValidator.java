/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.user;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

/**
 *
 * @author SaRCo
 */
public class UserValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		String password = (String)ctx.getProperties("password")[0].getValue();
		//Integer priority = (Integer)ctx.getProperties("priority")[0].getValue();
		
                String nombre = (String)ctx.getProperties("nombre")[0].getValue();
		if(nombre == null || "".equals(nombre)) {
                    this.addInvalidMessage(ctx, "nombre", "Debes introducir un nombre");
                }		
		
                if(password == null || "".equals(password)) {
                    this.addInvalidMessage(ctx, "password", "Debes introducir un password");
                }
		//if(ctx.getProperties("fecha_alta")[0].getValue() == null)
		//	this.addInvalidMessage(ctx, "fecha_alta", "You must specify a date");
		
		//if(priority == null || priority < 1 || priority > 10)
		//	this.addInvalidMessage(ctx, "priority", "You must give a priority > 0 && < 10");
	}
}
