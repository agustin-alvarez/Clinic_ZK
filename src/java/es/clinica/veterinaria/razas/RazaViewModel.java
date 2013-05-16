package es.clinica.veterinaria.razas;

import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class RazaViewModel {
	
	private RazaDAO eventDao = new RazaDAO();
        private EspecieDAO especieDao = new EspecieDAO();
	private Raza selectedEvent, newEvent = new Raza();
//        public String[] provincias = getProvincias();
        
	public Raza getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Raza selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
	public Raza getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Raza newEvent) {
		this.newEvent = newEvent;
	}

	public List<Raza> getEvents() {
		return eventDao.findAll();
	}
        
        public List<Especie> getEspecies() {
                return especieDao.findAll();
        }
	
	@Command("add")
	@NotifyChange("events")
	public void add() {
            boolean encontrado = false;
            for(Raza raza : getEvents()) {
                if(raza.getRaza().equals(this.newEvent.getRaza())){
                    encontrado = true;
                }
            }
            if(!encontrado){
                eventDao.insert(this.newEvent);
                this.newEvent.getEspecie().asignarRaza(newEvent);
            }
            else{
                Messagebox.show("La Raza "+this.newEvent.getRaza()+" ya se encuentra en el sistema.", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            this.newEvent = new Raza();
	}
                
	@Command("update")
	@NotifyChange({"events", "selectedEvent"})
	public void update() {
		if(!eventDao.update(this.selectedEvent)){
                    Messagebox.show("La Raza no se ha actualizado.", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
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

