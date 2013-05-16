
package es.clinica.veterinaria.clientes;

import es.clinica.veterinaria.citas.Cita;
import es.clinica.veterinaria.citas.CitaDAO;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
import es.clinica.veterinaria.servicio_familia.ServicioFamilia;
import es.clinica.veterinaria.servicio_familia.ServicioFamiliaDAO;
import es.clinica.veterinaria.user.UserCredentialManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

/**
 * @author SaRCo
 */
public class ClienteCitaVM {

        private List<Cita> citas = new ArrayList<Cita>();
        private CitaDAO eventDao = new CitaDAO();
        private ServicioFamiliaDAO servDao = new ServicioFamiliaDAO();
        private MascotaDAO mascDao = new MascotaDAO();
        Session s = Sessions.getCurrent();

        private Cliente selectedEvent = new Cliente();
        private Mascota selectedMascota = new Mascota();
	private Cita newEvent = new Cita();
        private Cita selectedCita = new Cita();
        private Date selectedFecha = new Date();

        private List<String> horas = new ArrayList<String>();

        @Init
        public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                              @ExecutionArgParam("selectedCliente") Cliente selectedCliente, 
                              @ExecutionArgParam("selectedMascota") Mascota selectedMascota)
        {
            Selectors.wireComponents(view, this, false);
            if(selectedCliente != null) {
                this.selectedEvent = selectedCliente;
            }
            if(selectedMascota != null) {
                this.selectedMascota = selectedMascota;
            }
//            citas = getCitas();
        }
        
        public Cliente getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Cliente selectedEvent) {
		this.selectedEvent = selectedEvent;
	}

        public Cita getSelectedCita() {
		return selectedCita;
	}

	public void setSelectedCita(Cita selectedCita) {
		this.selectedCita = selectedCita;
	}

        public Date getSelectedFecha() {
            return selectedFecha;
        }

        public void setSelectedFecha(Date selectedFecha) {
            this.selectedFecha = selectedFecha;
        }

        public Mascota getSelectedMascota() {
            return selectedMascota;
        }

        public void setSelectedMascota(Mascota selectedMascota) {
            this.selectedMascota = selectedMascota;
        }

	public Cita getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Cita newEvent) {
		this.newEvent = newEvent;
	}

	public List<Cita> getEvents() {
		return eventDao.findAll();
	}

        public List<Cita> getCitas() {
		return eventDao.findAll("select * from zk_cita where id_cliente=" + selectedEvent.getId());
	}

        public List<ServicioFamilia> getServicios() {
                return servDao.findAll();
        }

        public List<Mascota> getMascotas() {
                return mascDao.findAll("select * from zk_mascota where id_cliente=" + selectedEvent.getId());
        }

        public List<String> getHoras() {
            return horas;
        }

	@Command("add")
	@NotifyChange("events")
	public void add() {
		//this.newEvent.setId(UUID.randomUUID().variant());
                //selectedEvent.asignarCita(newEvent);   //Relacion Cliente-Mascota
                this.newEvent.setCliente(selectedEvent);
                this.newEvent.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
                eventDao.insert(this.newEvent);
		this.newEvent = new Cita();
	}

	@Command("update")
	@NotifyChange({"events","selectedCita"})
	public void update() {
		eventDao.update(this.selectedCita);
	}

	@Command("delete")
	@NotifyChange({"events", "selectedCita"})
	public void delete() {
		//shouldn't be able to delete with selectedEvent being null anyway
		//unless trying to hack the system, so just ignore the request
		if(this.selectedEvent != null) {
			eventDao.delete(this.selectedCita);
			this.selectedEvent = null;
		}
	}


    //Seleccionador de Provincia, para filtrar las poblaciones de dicha provincia
    @Command
    @NotifyChange("horas")
    public void onSelectFecha()
    {
        Calendar cfecha = Calendar.getInstance();
        Calendar finfecha = Calendar.getInstance();
        List<Cita> allCitas = new ArrayList<Cita>();

//        Date selFecha = this.getSelectedFecha();
        this.horas.clear();

        String sfecha = new SimpleDateFormat("yyyy-MM-dd").format(this.selectedFecha);
        String anyo = new SimpleDateFormat("yyyy").format(this.selectedFecha);
        String mes = new SimpleDateFormat("MM").format(this.selectedFecha);
        String dia = new SimpleDateFormat("dd").format(this.selectedFecha);

        cfecha.set(Integer.parseInt(anyo), Integer.parseInt(mes), Integer.parseInt(dia), 9, 00, 00);
        finfecha.set(Integer.parseInt(anyo), Integer.parseInt(mes), Integer.parseInt(dia), 20, 55, 00);

        allCitas = new CitaDAO().findAll("select * from zk_cita where fecha = '" + sfecha + "'" );


        for(Calendar calit = cfecha; calit.compareTo(finfecha) < 0; calit.add(Calendar.MINUTE, 5))
        {
            String min;
            String hor;
            String hora_min;

            if(cfecha.get(Calendar.MINUTE) == 0) {
                min = "00";
            }else if(cfecha.get(Calendar.MINUTE) == 5){
                min = "05";
            }else{
                min = cfecha.get(Calendar.MINUTE) + "";
            }
            if(cfecha.get(Calendar.HOUR_OF_DAY) == 9) {
                hor = "09";
            }else {
                hor = cfecha.get(Calendar.HOUR_OF_DAY) + "";
            }
                hora_min = hor + ":" + min ;

            if(allCitas.isEmpty())
            {
                this.horas.add(hora_min);
            }
            else
            {
                boolean encontrado = false;
                for (Iterator<Cita> it = allCitas.iterator(); it.hasNext();)
                {
                    Cita cita = it.next();
                    String hms = hora_min + ":00";
                    if(cita.getHora().equals(hms) && cita.getEstado() != 0 ) {
                        encontrado = true;
                     }
                }
                if(!encontrado) {
                    this.horas.add(hora_min);
                }
            }
        }
    }
    
    @Command
    public void sendmascota() {
        Map args = new HashMap();
        if(selectedMascota.getNombre() != null && selectedMascota.getId() != '0'){
            this.newEvent.setMascota(selectedMascota);
        }
        System.out.println("Mascota: " + this.newEvent.getMascota().getNombre());
        System.out.println("Hora: " + this.newEvent.getHora().toString());
        System.out.println("Fecha: " + this.newEvent.getFecha().toString());
        System.out.println("Servicio: " + this.newEvent.getServicio().getNombre());
        
        args.put("returnCita", this.newEvent);
        BindUtils.postGlobalCommand(null, null, "refreshCitas", args);
    }

//    @GlobalCommand
//    @NotifyChange("selectedCita")
//    public void refreshCliente(@BindingParam("returnCliente") Cliente cli)
//    {
//
//        this.newEvent.setCliente(cli);
//        this.newEvent.setFecha(new Date());
//        this.selectedEvent = this.newEvent;
//    }
}
