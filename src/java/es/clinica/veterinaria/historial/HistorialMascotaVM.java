package es.clinica.veterinaria.historial;

import es.clinica.veterinaria.mascotas.Mascota;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
//import org.zkoss.zul.ListModelList;

public class HistorialMascotaVM {
	
        private List<Historial> historiales = new ArrayList<Historial>();
        private HistorialDAO eventDao = new HistorialDAO();
	
        private Mascota selectedEvent = new Mascota();
	private Mascota newEvent = new Mascota();
        private Mascota selectedMascota = new Mascota();
        
        public Mascota getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Mascota selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
        public Mascota getSelectedMascota() {
		return selectedMascota;
	}

	public void setSelectedMascota(Mascota selectedMascota) {
		this.selectedMascota = selectedMascota;
	}
        
	public Mascota getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Mascota newEvent) {
		this.newEvent = newEvent;
	}

	public List<Historial> getEvents() {
		return eventDao.findAll();
	}
        /*
        public Mascota getNewMasc() {
		return newMasc;
	}

	public void setNewEvent(Mascota newMasc) {
		this.newMasc = newMasc;
	}
        */
        
//        public List<Historial> getHist() {
//		return eventDao.getMascotaHistorial(this.selectedEvent);
//	}
	
        
//        @Init
//        public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("selectedMascota") Mascota selectedMascota) 
//        {
//            Selectors.wireComponents(view, this, false);
//            this.selectedEvent = selectedMascota;
//            historiales = getHist();
//        }
        
//	@Command("add")
//	@NotifyChange("events")
//	public void add() {
//		this.newEvent.setId(UUID.randomUUID().variant());
//		this.newEvent.setCliente(selectedEvent);  //Relacion Mascota-Historial
//                selectedEvent.asignarMascota(newEvent);   //Relacion Historial-Mascota
//                eventDao.insert(this.newEvent);
//		this.newEvent = new Mascota();
//	}
	/*
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
        */
        
}
