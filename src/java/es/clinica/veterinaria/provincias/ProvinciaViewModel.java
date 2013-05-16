package es.clinica.veterinaria.provincias;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;

public class ProvinciaViewModel {
	
	private ProvinciaDAO eventDao = new ProvinciaDAO();
	private Provincia selectedEvent, newEvent = new Provincia();
//        public String[] provincias = getProvincias();
        
	public Provincia getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Provincia selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
	public Provincia getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Provincia newEvent) {
		this.newEvent = newEvent;
	}

	public List<Provincia> getEvents() {
		return eventDao.findAll();
	}
        
        public String[] getProvincias() {
            
            List<Provincia> allEvents = eventDao.findAll();
            int i=0;
            String pv[] = new String[allEvents.size()];
            
            for (Iterator<Provincia> it = allEvents.iterator(); it.hasNext();) 
            {
                Provincia prov = it.next();
                pv[i] = prov.getProvincia();
                i++;
            }
//            ListModel dictModel= new SimpleListModel(pv);
//            return dictModel;
            return pv;
        }
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
		this.newEvent.setId(UUID.randomUUID().variant());
		eventDao.insert(this.newEvent);
		this.newEvent = new Provincia();
	}
	
	@Command("update")
	@NotifyChange("events")
	public void update() {
		eventDao.update(this.selectedEvent);
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
