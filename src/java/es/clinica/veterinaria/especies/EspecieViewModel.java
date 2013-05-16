
package es.clinica.veterinaria.especies;

import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class EspecieViewModel {

	private EspecieDAO eventDao = new EspecieDAO();
	private Especie selectedEvent, newEvent = new Especie();
//        public String[] provincias = getProvincias();

	public Especie getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Especie selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

	public Especie getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Especie newEvent) {
		this.newEvent = newEvent;
	}

	public List<Especie> getEvents() {
		return eventDao.findAll();
	}


	@Command("add")
	@NotifyChange({"events", "newEvent"})
	public void add() {
            boolean encontrado = false;
            for(Especie esp : getEvents()) {
                if(esp.getEspecie().equals(this.newEvent.getEspecie())){
                    encontrado = true;
                }
            }
            if(!encontrado){
                eventDao.insert(this.newEvent);
            }
            else{
                Messagebox.show("La especie "+this.newEvent.getEspecie()+" ya se encuentra en el sistema.", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            this.newEvent = null;
            this.newEvent = new Especie();
	}

	@Command("update")
	@NotifyChange({"events", "selectedEvent"})
	public void update() {

            if(!eventDao.update(this.selectedEvent)){
                Messagebox.show("La especie no se actualizó", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
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

