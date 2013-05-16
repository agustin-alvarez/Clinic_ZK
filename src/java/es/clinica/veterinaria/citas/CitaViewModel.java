
package es.clinica.veterinaria.citas;
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
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

public class CitaViewModel {
	
	private CitaDAO eventDao = new CitaDAO();
	private ServicioFamiliaDAO servDao = new ServicioFamiliaDAO();
        private MascotaDAO mascDao = new MascotaDAO();
	private Cita selectedEvent, newEvent = new Cita();
        private Cita auxCita;
        private Date selectedFecha = new Date();
        Session s = Sessions.getCurrent();
        
        private String filterTipo = "",
                       filterMascota = "",
                       filterCliente = "",
                       filterHora =  "",
                       filterFecha = "",
                       filterEstado = "", 
                       filterEmpleado = "",
                       filterNumero = "";
        
        private List<String> horas = new ArrayList<String>();
        private ListModelList<Cita> citas;
        private ListModelList<Cita> avisocita;
	
        
        @Init
        public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("selectedCita") Cita selectedEvent) 
        {
            Selectors.wireComponents(view, this, false);
            this.selectedEvent = selectedEvent;
            if(selectedEvent != null){
                this.selectedFecha = this.selectedEvent.getFecha();
                this.onSelectFecha();
                this.auxCita = this.selectedEvent;
            }
//            citas = getCitas();
        }
        
	public Cita getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(Cita selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
	
        public Date getSelectedFecha() {
            return selectedFecha;
        }

        public void setSelectedFecha(Date selectedFecha) {
            this.selectedFecha = selectedFecha;
        }
	public Cita getNewEvent() {
		return newEvent;
	}

	public void setNewEvent(Cita newEvent) {
		this.newEvent = newEvent;
	}

        public String getFilterTipo() {
            return filterTipo;
        }

        @NotifyChange
        public void setFilterTipo(String filterTipo) {
            this.filterTipo = filterTipo;
        }

        public String getFilterMascota() {
            return filterMascota;
        }

        @NotifyChange
        public void setFilterMascota(String filterMascota) {
            this.filterMascota = filterMascota;
        }

        public String getFilterCliente() {
            return filterCliente;
        }

        @NotifyChange
        public void setFilterCliente(String filterCliente) {
            this.filterCliente = filterCliente;
        }

        public String getFilterHora() {
            return filterHora;
        }

        @NotifyChange
        public void setFilterHora(String filterHora) {
            this.filterHora = filterHora;
        }

        public String getFilterFecha() {
            return filterFecha;
        }

        @NotifyChange
        public void setFilterFecha(String filterFecha) {
            this.filterFecha = filterFecha;
        }

        public String getFilterEstado() {
            return filterEstado;
        }

        @NotifyChange
        public void setFilterEstado(String filterEstado) {
            this.filterEstado = filterEstado;
        }

        public String getFilterEmpleado() {
            return filterEmpleado;
        }

        @NotifyChange
        public void setFilterEmpleado(String filterEmpleado) {
            this.filterEmpleado = filterEmpleado;
        }
        
        public String getFilterNumero() {
            return filterNumero;
        }

        @NotifyChange
        public void setFilterNumero(String filterNumero) {
            this.filterNumero = filterNumero;
        }
        
	public List<Cita> getEvents() {
		return eventDao.findAll();
	}
        
        public List<ServicioFamilia> getServicios() {
                return servDao.findAll();
        }
        
        public ListModelList<Cita> getCitas() 
        {
            if(citas == null) {
                citas = new ListModelList<Cita>();//new ListModelList<Cita>(getEvents());
            }
            return citas;
        }
        
        public List<String> getHoras() {
            return horas;
        }
        
        public List<Mascota> getMascotas() {
            return mascDao.findAll("select * from zk_mascota where id_cliente=" + selectedEvent.getCliente().getId());
        }
        //Desde el dia anterior (no ha acudido el cliente) - la semana siguiente
        public List<Cita> getAvisoCita(){
            return eventDao.findAll("SELECT * FROM zk_cita WHERE fecha > curdate()-1 AND fecha < curdate()+7 AND estado = 2 ORDER BY fecha ASC");
        }
	
	@Command("add")
	@NotifyChange("citas")
	public void add() {
//		this.newEvent.setId(UUID.randomUUID().variant());
                this.newEvent.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
		if(!eventDao.insert(this.newEvent)){
                    Messagebox.show("Cita no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
                }
		this.newEvent = new Cita();
	}
	
	@Command("update")
	@NotifyChange("citas")
	public void update() {
            if(this.selectedEvent.getFecha() == null){
                this.selectedEvent.setFecha(auxCita.getFecha());
            }
            if(this.selectedEvent.getEmpleado() == null){
                this.selectedEvent.setEmpleado(auxCita.getEmpleado());
            }
            if(this.selectedEvent.getHora() == null){
                this.selectedEvent.setHora(auxCita.getHora());
            }
		if(!eventDao.update(this.selectedEvent)){
                    Messagebox.show("Cita no modificada", "Error", Messagebox.OK, Messagebox.ERROR);
                }
	}
	
        @Command("acudidoCita")
	@NotifyChange({"citas","avisoCita"})
	public void acudidoCita() {
            this.selectedEvent.setEstado(1);
            eventDao.update(this.selectedEvent);
	}
        
        @Command("canceladoCita")
	@NotifyChange({"citas","avisoCita"})
	public void canceladoCita() {
            this.selectedEvent.setEstado(0);
            eventDao.update(this.selectedEvent);
	}
        
        @Command("pendienteCita")
	@NotifyChange({"citas","avisoCita"})
	public void pendienteCita() {
            this.selectedEvent.setEstado(2);
            eventDao.update(this.selectedEvent);
	}
        
        @Command("avisadoCita")
        @NotifyChange({"citas","avisoCita"})
	public void avisadoCita() {
            this.selectedEvent.setEstado(3);
            eventDao.update(this.selectedEvent);
	}
        
	@Command("delete")
	@NotifyChange({"citas", "selectedEvent"})
	public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                eventDao.delete(this.selectedEvent);
                this.selectedEvent = null;
            }
	}
 
    // Buscador para hacer el filtrado
    @Command 
    @NotifyChange("citas")
    public void doSearch() 
    {
        citas.clear();
        List<Cita> allEvents;// = eventDao.findAll();

        if((filterNumero    == null || "".equals(filterNumero))   && 
           (filterEmpleado  == null || "".equals(filterEmpleado)) && 
           (filterFecha     == null || "".equals(filterFecha))    && 
           (filterHora      == null || "".equals(filterHora))     && 
           (filterCliente   == null || "".equals(filterCliente))  &&
           (filterMascota   == null || "".equals(filterMascota))  &&
           (filterEstado    == null || "".equals(filterEstado))   &&
           (filterTipo      == null || "".equals(filterTipo))) {
//                citas.addAll(allEvents);
        }
        else 
        {
            allEvents = eventDao.findAll();
            for (Iterator<Cita> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents) 
            {
                Cita clie = it.next();
                int estado = clie.getEstado();
                String stado, servicio;
                
                if(estado == 0) {
                    stado = "Cancelado";
                }
                else if(estado == 1) {
                    stado = "Acudido";
                }
                else {
                    stado = "Pendiente";
                }
                if(clie.getServicio() == null){
                    servicio = "";
                }
                else{
                    servicio = clie.getServicio().getNombre();
                }
                
//                System.out.println("id: "+ clie.getId()+"");
//                System.out.println("empleado: "+ clie.getEmpleado().getNombre());
//                System.out.println("servicio: "+ servicio);
//                System.out.println("mascota: "+ clie.getMascota().getNombre());
//                System.out.println("cliente: "+ clie.getCliente().getFullname());
//                System.out.println("estado: "+ stado);
//                System.out.println("fecha: "+ clie.getFecha().toString());
//                System.out.println("hora: "+ clie.getHora());
                
                if (((clie.getId()+"").toLowerCase().startsWith(filterNumero.toLowerCase())   )    &&
                    (clie.getEmpleado().getNombre().toLowerCase().startsWith(filterEmpleado.toLowerCase())   )    &&
                    (servicio.toLowerCase().startsWith(filterTipo.toLowerCase())   )    &&
                    (clie.getMascota().getNombre().toLowerCase().startsWith(filterMascota.toLowerCase())   )    &&
                    (clie.getCliente().getFullname().toLowerCase().startsWith(filterCliente.toLowerCase())  )   && 
                    (clie.getHora().toLowerCase().startsWith(filterHora.toLowerCase())  )                       &&
                    (stado.toLowerCase().startsWith(filterEstado.toLowerCase())  )                       &&
                    (clie.getFecha().toString().toLowerCase().startsWith(filterFecha.toLowerCase())  )) {
                        citas.add(clie);
                }
            }
        }
    }    
    /* FIN FILTER*/
    
        //Seleccionador de Provincia, para filtrar las poblaciones de dicha provincia
    @Command
    @NotifyChange({"citas", "horas"})
    public void onSelectFecha()
    {
        Calendar cfecha = Calendar.getInstance();
        Calendar finfecha = Calendar.getInstance();
        List<Cita> allCitas;// = new ArrayList<Cita>();
        
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
                    if(cita.getHora().equals(hms) && cita.getEstado() != 0) {
                        encontrado = true; 
                     }
                }
                if(!encontrado) {
                    this.horas.add(hora_min);
                }
            }
        }         
    }
    
    @Command("modificarCita")
    @NotifyChange("citas")
    public void modificarCita()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCita", selectedEvent );
        Executions.createComponents("../citas/cita-modificar.zul", null, map);
    }
    
    
}
