package es.clinica.veterinaria.servicio_familia;

import es.clinica.veterinaria.especies.Especie;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class ServicioFamiliaViewModel {
	
	private ServicioFamiliaDAO eventDao = new ServicioFamiliaDAO();
	private ServicioFamilia selectedEvent, newEvent = new ServicioFamilia();
//        public String[] provincias = getProvincias();
        
	public ServicioFamilia getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(ServicioFamilia selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
	public ServicioFamilia getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(ServicioFamilia newEvent) {
		this.newEvent = newEvent;
	}

	public List<ServicioFamilia> getEvents() {
		return eventDao.findAll();
	}
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
            boolean encontrado = false;
            
            for(ServicioFamilia serv : getEvents()) {
                if(serv.getNombre().equals(this.newEvent.getNombre())){
                    encontrado = true;
                }
            }
            if(!encontrado){
                eventDao.insert(this.newEvent);
            }
            else{
                Messagebox.show("La familia del servicio "+this.newEvent.getNombre()+" ya se encuentra en el sistema.", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            this.newEvent = new ServicioFamilia();
	}
	
	@Command("update")
	@NotifyChange({"events", "selectedEvent"})
	public void update() {
		eventDao.update(this.selectedEvent);
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

