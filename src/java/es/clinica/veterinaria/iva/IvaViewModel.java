/*
 * IvaViewModel.java
 * @author SaRCo
 */
package es.clinica.veterinaria.iva;

import java.util.List;
import java.util.UUID;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class IvaViewModel {
	
	private IvaDAO eventDao = new IvaDAO();
	private Iva selectedEvent, newEvent = new Iva();
        
	public Iva getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Iva selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
	public Iva getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Iva newEvent) {
		this.newEvent = newEvent;
	}

	public List<Iva> getEvents() {
		return eventDao.findAll();
	}
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
		this.newEvent.setId(UUID.randomUUID().variant());
		eventDao.insert(this.newEvent);
		this.newEvent = new Iva();
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

