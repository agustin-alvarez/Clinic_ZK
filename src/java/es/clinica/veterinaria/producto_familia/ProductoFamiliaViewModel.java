/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.producto_familia;

import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class ProductoFamiliaViewModel {
	
	private ProductoFamiliaDAO eventDao = new ProductoFamiliaDAO();
	private ProductoFamilia selectedEvent, newEvent = new ProductoFamilia();
//        public String[] provincias = getProvincias();
        
	public ProductoFamilia getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(ProductoFamilia selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
	public ProductoFamilia getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(ProductoFamilia newEvent) {
		this.newEvent = newEvent;
	}

	public List<ProductoFamilia> getEvents() {
		return eventDao.findAll();
	}
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
            boolean encontrado = false;
            for(ProductoFamilia esp : getEvents()) {
                if(esp.getNombre().equals(this.newEvent.getNombre())){
                    encontrado = true;
                }
            }
            if(!encontrado){
                eventDao.insert(this.newEvent);
            }
            else{
                Messagebox.show("La familia producto "+this.newEvent.getNombre()+" ya se encuentra en el sistema.", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            this.newEvent = new ProductoFamilia();
	}
	
	@Command("update")
	@NotifyChange({"events", "selectedEvent"})
	public void update() {
		if(!eventDao.update(this.selectedEvent)){
                    Messagebox.show("La familia producto "+this.selectedEvent.getNombre()+" no se ha podido modificar.", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
                }
                this.selectedEvent = null;
	}
	
	@Command("delete")
	@NotifyChange({"events", "selectedEvent"})
	public void delete() {
		//shouldn't be able to delete with selectedEvent being null anyway
		//unless trying to hack the system, so just ignore the request
		if(this.selectedEvent != null) {
			eventDao.delete(this.selectedEvent);
			this.selectedEvent = null;
		}
	}
}

