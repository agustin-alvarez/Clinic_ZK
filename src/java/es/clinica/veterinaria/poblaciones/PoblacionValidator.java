package es.clinica.veterinaria.poblaciones;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class PoblacionValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		String provincia = (String)ctx.getProperties("provincia")[0].getValue();
		//Integer priority = (Integer)ctx.getProperties("priority")[0].getValue();
		
                if(provincia == null || "".equals(provincia))
			this.addInvalidMessage(ctx, "provincia", "Debes introducir una provincia válida");
		//if(ctx.getProperties("fecha_alta")[0].getValue() == null)
		//	this.addInvalidMessage(ctx, "fecha_alta", "You must specify a date");
		
		//if(priority == null || priority < 1 || priority > 10)
		//	this.addInvalidMessage(ctx, "priority", "You must give a priority > 0 && < 10");
	}
}
