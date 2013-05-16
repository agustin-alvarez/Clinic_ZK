package es.clinica.veterinaria.historial;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class HistorialValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		String nombre = (String)ctx.getProperties("nombre")[0].getValue();
		//Integer priority = (Integer)ctx.getProperties("priority")[0].getValue();
		String apellidos = (String)ctx.getProperties("apellidos")[0].getValue();
		if(nombre == null || "".equals(nombre))
			this.addInvalidMessage(ctx, "nombre", "You must enter a name");		
		
		//if(ctx.getProperties("fecha_alta")[0].getValue() == null)
		//	this.addInvalidMessage(ctx, "fecha_alta", "You must specify a date");
		
		//if(priority == null || priority < 1 || priority > 10)
		//	this.addInvalidMessage(ctx, "priority", "You must give a priority > 0 && < 10");
	}
}
