package es.clinica.veterinaria.vacunas;

import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import es.clinica.veterinaria.razas.Raza;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class VacunaViewModel {
	
	private VacunaDAO eventDao = new VacunaDAO();
        private EspecieDAO especieDao = new EspecieDAO();
	private Vacuna selectedEvent, auxEvent, newEvent = new Vacuna();
        
	public Vacuna getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Vacuna selectedEvent) {
                this.auxEvent = selectedEvent;
		this.selectedEvent = selectedEvent;
	}
	
	public Vacuna getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Vacuna newEvent) {
		this.newEvent = newEvent;
	}

        public Vacuna getAuxEvent() {
            return auxEvent;
        }

        public void setAuxEvent(Vacuna auxEvent) {
            this.auxEvent = auxEvent;
        }
        

	public List<Vacuna> getEvents() {
		return eventDao.findAll();
	}
        
        public List<Especie> getEspecies() {
                return especieDao.findAll();
        }
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
            boolean encontrado = false;
            for(Vacuna vacuna : getEvents()) {
                if(vacuna.getNombre().equals(this.newEvent.getNombre()) && (vacuna.getEspecie().getId() == this.newEvent.getEspecie().getId())){
                    encontrado = true;
                }
            }
            if(!encontrado){
                eventDao.insert(this.newEvent);
            }
            else{
                Messagebox.show("La vacuna "+this.newEvent.getNombre()+" para la especie "+ this.newEvent.getEspecie().getEspecie()+" ya se encuentra en el sistema.", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            
            this.newEvent = new Vacuna();
	}
                
	@Command("update")
	@NotifyChange({"events", "selectedEvent"})
	public void update() {
            if(!eventDao.update(this.selectedEvent)) {
                Messagebox.show("La vacuna no se ha modificado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
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

