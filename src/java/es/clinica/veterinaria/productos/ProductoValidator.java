package es.clinica.veterinaria.productos;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class ProductoValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		String codigo = (String)ctx.getProperties("codigo")[0].getValue();
		//Integer priority = (Integer)ctx.getProperties("priority")[0].getValue();
		
                String nombre = (String)ctx.getProperties("nombre")[0].getValue();
		if(nombre == null || "".equals(nombre))
			this.addInvalidMessage(ctx, "nombre", "Debes introducir un nombre");		
		
                if(codigo == null || "".equals(codigo))
			this.addInvalidMessage(ctx, "codigo", "Debes introducir un código");
		//if(ctx.getProperties("fecha_alta")[0].getValue() == null)
		//	this.addInvalidMessage(ctx, "fecha_alta", "You must specify a date");
		
		//if(priority == null || priority < 1 || priority > 10)
		//	this.addInvalidMessage(ctx, "priority", "You must give a priority > 0 && < 10");
	}
}
