package es.clinica.veterinaria.pesos;

import es.clinica.veterinaria.mascotas.Mascota;
import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

/**
 * @author SaRCo
 */
public class PesoViewModel {
	
    private PesoDAO eventDao = new PesoDAO();
    private Peso selectedEvent, newEvent = new Peso();
    private Mascota selectedMascota = new Mascota();
    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                          @ExecutionArgParam("selectedMascota") Mascota selectedMascota) 
    {
        Selectors.wireComponents(view, this, false);
        this.selectedMascota = selectedMascota;
        System.out.println("Mascota: " + this.selectedMascota.getNombre());
    }

    public Peso getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Peso selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Peso getNewEvent() {
        return newEvent;
    }

    public void setNewEvent(Peso newEvent) {
        this.newEvent = newEvent;
    }

    public List<Peso> getEvents() {
        return eventDao.findAll("SELECT * FROM zk_peso WHERE mascota=" + this.selectedMascota.getId() + " ORDER BY fecha DESC");
    }

    @Command("add")
    @NotifyChange("events")
    public void add() {
//            this.newEvent.setId(UUID.randomUUID().variant());
        this.newEvent.setMascota(selectedMascota);
        eventDao.insert(this.newEvent);
        this.newEvent = new Peso();
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

