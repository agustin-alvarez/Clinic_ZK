package es.clinica.veterinaria.poblaciones;

import java.util.List;
import java.util.UUID;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class PoblacionViewModel {
	
	private PoblacionDAO eventDao = new PoblacionDAO();
	private Poblacion selectedEvent, newEvent = new Poblacion();
//        public String[] provincias = getProvincias();
        
	public Poblacion getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Poblacion selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
	public Poblacion getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Poblacion newEvent) {
		this.newEvent = newEvent;
	}

	public List<Poblacion> getEvents() {
		return eventDao.findAll();
	}
        
//        public String[] getProvincias() {
//            
//            List<Poblacion> allEvents = eventDao.findAll();
//            int i=0;
//            String pv[] = new String[allEvents.size()];
//            
//            for (Iterator<Poblacion> it = allEvents.iterator(); it.hasNext();) 
//            {
//                Poblacion prov = it.next();
//                pv[i] = prov.getProvincia();
//                i++;
//            }
////            ListModel dictModel= new SimpleListModel(pv);
////            return dictModel;
//            return pv;
//        }
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
		this.newEvent.setId(UUID.randomUUID().variant());
		eventDao.insert(this.newEvent);
		this.newEvent = new Poblacion();
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
