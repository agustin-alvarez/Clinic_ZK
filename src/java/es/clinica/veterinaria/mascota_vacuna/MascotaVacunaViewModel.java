/*
 * MascotaVacunaViewModel.java
 * @author SaRCo
 */
package es.clinica.veterinaria.mascota_vacuna;

import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserCredentialManager;
import es.clinica.veterinaria.vacunas.Vacuna;
import es.clinica.veterinaria.vacunas.VacunaDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

public class MascotaVacunaViewModel {
	
    private MascotaVacunaDAO eventDao = new MascotaVacunaDAO();
    private VacunaDAO vacunaDao = new VacunaDAO();
    private MascotaVacuna selectedEvent, newEvent = new MascotaVacuna();
    private Mascota selectedMascota;
    private MascotaVacuna selectedVacuna;
    private User selectedVet;
    private Set <Vacuna> selectedVacunas;
    private List<Vacuna> _vacunamasc;
    Session s = Sessions.getCurrent();

    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                          @ExecutionArgParam("selectedMascota") Mascota selectedMascota, 
                          @ExecutionArgParam("selectedVacuna") MascotaVacuna selectedHistorial) 
    {
        Selectors.wireComponents(view, this, false);
        
        if(selectedMascota != null) //Si la venta es nula, entonces es Venta Rápida
        {   
            this.selectedMascota = selectedMascota;
            this.selectedVet =  UserCredentialManager.getIntance(s).getUser();
            if(selectedHistorial != null)
            {
                this.selectedEvent = selectedVacuna;
            }
//            else{
//                this.newEvent.setId_veterinario(this.selectedVet);
//                this.newEvent.setPeso(new Peso());
//            }
        }
    }
    
    public MascotaVacuna getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(MascotaVacuna selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Mascota getSelectedMascota() {
        return selectedMascota;
    }

    public void setSelectedMascota(Mascota selectedMascota) {
        this.selectedMascota = selectedMascota;
    }

    public Set<Vacuna> getSelectedVacunas() {
        return selectedVacunas;
    }

    public void setSelectedVacunas(Set<Vacuna> selectedVacunas) {
        this.selectedVacunas = selectedVacunas;
    }
    
    public MascotaVacuna getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(MascotaVacuna newEvent) {
            this.newEvent = newEvent;
    }

    public List<MascotaVacuna> getEvents() {
            return eventDao.findAll();
    }
    
    public List<Vacuna> getVacunas() {
        return vacunaDao.findAll("SELECT * FROM zk_vacuna WHERE especie="+ selectedMascota.getEspecie().getId() );
    }

    @Command("add")
    @NotifyChange("events")
    public void add() {
            eventDao.insert(this.newEvent);
            this.newEvent = new MascotaVacuna();
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
    
    @Command
    public void send() {
        Map args = new HashMap();
        args.put("returnVacuna", this.selectedVacunas);
        BindUtils.postGlobalCommand(null, null, "refreshVacunas", args);
    }
}

