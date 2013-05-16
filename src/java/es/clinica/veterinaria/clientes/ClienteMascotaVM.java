package es.clinica.veterinaria.clientes;

import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
import es.clinica.veterinaria.razas.Raza;
import es.clinica.veterinaria.razas.RazaDAO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;

public class ClienteMascotaVM {
    
//	Combobox cmbEspecie;
//        Combobox cmbRaza;
        
        private List<Mascota> mascotas = new ArrayList<Mascota>();
        private List<Raza> allRazas = new ArrayList<Raza>();
        private MascotaDAO eventDao = new MascotaDAO();
	private EspecieDAO especieDao = new EspecieDAO();
        private RazaDAO razaDao = new RazaDAO();
        
        private Cliente selectedEvent = new Cliente();
	private Mascota newEvent = new Mascota();
        private Mascota selectedMascota = new Mascota();
        private Especie selectedEspecie = new Especie();
        
        public Cliente getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Cliente selectedEvent) {
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

        public Especie getSelectedEspecie() {
		return selectedEspecie;
	}

	public void setSelectedEspecie(Especie selectedEspecie) {
		this.selectedEspecie = selectedEspecie;
	}
        
//        public void selectEspecie() {
//            this.selectedEspecie = cmbEspecie.getSelectedItem().getValue();
//        }

	public List<Mascota> getEvents() {
		return eventDao.findAll();
	}
        
        public List<Especie> getEspecies() {
                return especieDao.findAll();
        }
        
        public void setAllRazas(List<Raza> allRazas) {
		this.allRazas = allRazas;
	}
        
        public List<Raza> getAllRazas() {
//                selectedEspecie = cmbEspecie.getSelectedItem().getValue();
                //return razaDao.findAll();
            //return razaDao.findAll("select * from zk_raza where especie = " + selectedEspecie.getId() );
                return allRazas;
        }
        
//        @NotifyChange("razas")
//        public List<Raza> getRazas(String especie) {
//                return razaDao.findAll("select * from zk_raza where especie= " + especie);
//        }
        /*
        public Mascota getNewMasc() {
		return newMasc;
	}

	public void setNewEvent(Mascota newMasc) {
		this.newMasc = newMasc;
	}
        */
        
        public List<Mascota> getMasc() {
		return eventDao.getClienteMascotas(this.selectedEvent);
	}
	
        
        @Init
        public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("selectedCliente") Cliente selectedCliente) 
        {
            Selectors.wireComponents(view, this, false);
            this.selectedEvent = selectedCliente;
            mascotas = getMasc();
        }
        
	@Command("add")
	@NotifyChange("events")
	public void add() {
		this.newEvent.setCliente(selectedEvent);  //Relacion Mascota-Cliente
                selectedEvent.asignarMascota(newEvent);   //Relacion Cliente-Mascota
                eventDao.insert(this.newEvent);
		this.newEvent = new Mascota();
	}
	
	@Command("update")
	@NotifyChange("events")
	public void update() {
		eventDao.update(this.selectedMascota);
	}
	
	@Command("delete")
	@NotifyChange({"events", "selectedEvent"})
	public void delete() {
		//shouldn't be able to delete with selectedEvent being null anyway
		//unless trying to hack the system, so just ignore the request
		if(this.selectedEvent != null) {
			eventDao.delete(this.selectedMascota);
			this.selectedEvent = null;
		}
	}
        
        @Command
	@NotifyChange("allRazas")
	public void onSelectEspecie() {
		allRazas = new RazaDAO().findAll("select * from zk_raza where especie = " + selectedEspecie.getId() );
	}
        
    @Command
    public void sendmascota() {
        Map args = new HashMap();
//        if(selectedMascota.getNombre() != null && selectedMascota.getId() != '0'){
//            this.newEvent.setMascota(selectedMascota);
//        }
        args.put("returnMascota", this.newEvent);
        BindUtils.postGlobalCommand(null, null, "refreshMascotas", args);
    }
        
}
