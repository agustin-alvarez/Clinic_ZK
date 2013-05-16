package es.clinica.veterinaria.clientes;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class ClienteValidator extends AbstractValidator {

	public void validate(ValidationContext ctx) {
		String nif = (String)ctx.getProperties("nif")[0].getValue();
                String nombre = (String)ctx.getProperties("nombre")[0].getValue();
		//Integer priority = (Integer)ctx.getProperties("priority")[0].getValue();
		String apellidos = (String)ctx.getProperties("apellidos")[0].getValue();
                String direccion = (String)ctx.getProperties("direccion")[0].getValue();
//                Integer telefono = (Integer)ctx.getProperties("telefono")[0].getValue();
		
                if(nif == null || "".equals(nif)) {
                    this.addInvalidMessage(ctx, "NIF", "Debes introducir un NIF");
                }
                
                if(nombre == null || "".equals(nombre)) {
                    this.addInvalidMessage(ctx, "nombre", "Debes introducir un nombre");
                }
                
                if(apellidos == null || "".equals(apellidos)) {
                    this.addInvalidMessage(ctx, "apellidos", "Debes introducir los apellidos");
                }
                
                if(direccion == null || "".equals(direccion)) {
                    this.addInvalidMessage(ctx, "direccion", "Debes introducir la dirección");
                }
                
//                if(telefono == null || telefono < 600000000) {
//                    this.addInvalidMessage(ctx, "telefono", "Debes introducir un teléfono");
//                }
                
		
		//if(ctx.getProperties("fecha_alta")[0].getValue() == null)
		//	this.addInvalidMessage(ctx, "fecha_alta", "You must specify a date");
		
		//if(priority == null || priority < 1 || priority > 10)
		//	this.addInvalidMessage(ctx, "priority", "You must give a priority > 0 && < 10");
	}
}
