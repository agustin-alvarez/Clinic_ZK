package es.clinica.veterinaria.clientes;

import es.clinica.veterinaria.citas.Cita;
import es.clinica.veterinaria.citas.CitaDAO;
import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.poblaciones.PoblacionDAO;
import es.clinica.veterinaria.provincias.Provincia;
import es.clinica.veterinaria.provincias.ProvinciaDAO;
import es.clinica.veterinaria.user.UserCredentialManager;
import java.util.*;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

//import org.zkoss.zul.ListModelList;

public class ClienteViewModel {
    
    Session s = Sessions.getCurrent();
    private ClienteDAO eventDao = new ClienteDAO();
    private CitaDAO citaDao = new CitaDAO();
    private ProvinciaDAO provDao = new ProvinciaDAO();
    private PoblacionDAO poblDao = new PoblacionDAO();
    
    private Cliente selectedEvent, newEvent = new Cliente();
    private Provincia selectedProvincia = new Provincia();
    private Cita newCita = new Cita();
    
    private List<Poblacion> allPoblaciones = new ArrayList<Poblacion>();

    private String filterNombre = "",
                   filterApellidos = "",
                   filterDni = "",
                   filterDireccion = "",
                   filterCiudad = "",
                   filterProvincia =  "",
                   filterFechaalta = "", 
                   filterTelefono = "", 
                   filterTelefono2="",
                   filterCodigopostal ="",
                   filterEmail = "";
    
    private ListModelList<Cliente> clientes;
//    private ListModelList<Provincia> provincias;
        
    public Cliente getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Cliente selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Cliente getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(Cliente newEvent) {
            this.newEvent = newEvent;
    }
    
    public List<Cliente> getEvents() {
            return eventDao.findAll();
    }
    
    public Provincia getSelectedProvincia() {
            return selectedProvincia;
    }

    public void setSelectedProvincia(Provincia selectedProvincia) {
            this.selectedProvincia = selectedProvincia;
    }
    
    public List<Provincia> getProvincias() {
            return provDao.findAll();
    }
    
    public List<Poblacion> getPoblaciones() {
            return poblDao.findAll();
    }
    
    public List<Poblacion> getAllPoblaciones() {
            return allPoblaciones;
    }
    /*
    public List<Mascota> getMasc() {
            return eventDao.getClienteMascotas(this.selectedEvent);
    }
    */

    @Command("add")
    @NotifyChange({"events", "clientes"})
    public void add() {
//            this.newEvent.setId(UUID.randomUUID().variant());
            if(eventDao.insert(this.newEvent)){
                this.newEvent = new Cliente();
                Executions.sendRedirect("cliente-template.zul");
            }
            else{
                Messagebox.show("Cliente no añadido", "Error", Messagebox.OK, Messagebox.ERROR);
                this.newEvent = new Cliente();
            }
            
    }

    @Command("update")
    @NotifyChange({"events", "clientes"})
    public void update() {
            if(!eventDao.update(this.selectedEvent)){
                Messagebox.show("Cliente no actualizado", "Error", Messagebox.OK, Messagebox.ERROR);
            }
    }

    @Command("delete")
    @NotifyChange({"events", "clientes", "selectedEvent"})
    public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                if(!eventDao.delete(this.selectedEvent)){
                    Messagebox.show("Cliente no eliminado", "Error", Messagebox.OK, Messagebox.ERROR);
                }
                this.selectedEvent = null;
            }
    }

    @Command
    public void showMascotas()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("cliente-mascota.zul", center, map);
    }
    
    @Command
    public void showCliente()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("cliente.zul", center, map);
    }
    
    @Command
    public void showCitas()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("cliente-cita.zul", center, map);
    }

    @Command
    public void asignarMascota()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        //Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        //Center center = bl.getCenter();
        //center.getChildren().clear();
        Executions.createComponents("../mascotas/mascota-asignar.zul", null, map);
    }

    @Command
    public void asignarCita()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        //Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        //Center center = bl.getCenter();
        //center.getChildren().clear();
        Executions.createComponents("../clientes/cliente-nueva-cita.zul", null, map);
    }
    
        /* Se manda desde mascota-vacuna-nueva.zul */
    @GlobalCommand
    @NotifyChange({"vacunas", "citas"})
    public void refreshCitas(@BindingParam("returnCita") Cita item)
    {
            this.newCita = item;
            this.newCita.setCliente(selectedEvent);
            this.newCita.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
            if(!citaDao.insert(this.newCita)){
                Messagebox.show("Cita no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.newCita = new Cita();
    }

    @Command("addCita")
    @NotifyChange({"clientes", "events"})
    public void addCita() {
//            this.newCita.setId(UUID.randomUUID().variant());
//		this.newCita.setCliente(selectedEvent);  //Relacion Mascota-Cliente
//                selectedEvent.asignarMascota(newEvent);   //Relacion Cliente-Mascota
            if(!citaDao.insert(this.newCita)){
                Messagebox.show("Cita no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.newCita = new Cita();
    }

    /* FILTRADO */

    public String getFilterNombre() {
        return filterNombre;
    }

    public String getFilterApellidos() {
        return filterApellidos;
    }
    
    public String getFilterDni() {
        return filterDni;
    }
    
    @NotifyChange
    public void setFilterNombre(String filterNombre) {
        this.filterNombre = filterNombre;
    }

    @NotifyChange
    public void setFilterApellidos(String filterApellidos) {
        this.filterApellidos = filterApellidos;
    }

    @NotifyChange
    public void setFilterDni(String filterDni) {
        this.filterDni = filterDni;
    }

    public String getFilterDireccion() {
        return filterDireccion;
    }

    @NotifyChange
    public void setFilterDireccion(String filterDireccion) {
        this.filterDireccion = filterDireccion;
    }

    public String getFilterCiudad() {
        return filterCiudad;
    }

    @NotifyChange
    public void setFilterCiudad(String filterCiudad) {
        this.filterCiudad = filterCiudad;
    }

    public String getFilterProvincia() {
        return filterProvincia;
    }

    @NotifyChange
    public void setFilterProvincia(String filterProvincia) {
        this.filterProvincia = filterProvincia;
    }

    public String getFilterFechaalta() {
        return filterFechaalta;
    }
    
    @NotifyChange
    public void setFilterFechaalta(String filterFechaalta) {
        this.filterFechaalta = filterFechaalta;
    }

    public String getFilterTelefono() {
        return filterTelefono;
    }
    
    @NotifyChange
    public void setFilterTelefono(String filterTelefono) {
        this.filterTelefono = filterTelefono;
    }

    public String getFilterTelefono2() {
        return filterTelefono2;
    }

    @NotifyChange
    public void setFilterTelefono2(String filterTelefono2) {
        this.filterTelefono2 = filterTelefono2;
    }

    public String getFilterEmail() {
        return filterEmail;
    }

    @NotifyChange
    public void setFilterEmail(String filterEmail) {
        this.filterEmail = filterEmail;
    }

    public String getFilterCodigopostal() {
        return filterCodigopostal;
    }

    @NotifyChange
    public void setFilterCodigopostal(String filterCodigopostal) {
        this.filterCodigopostal = filterCodigopostal;
    }
    
    
    // Buscador para hacer el filtrado
    @Command 
    @NotifyChange( "clientes")
    public void doSearch() 
    {
//        System.out.println();
        clientes.clear();
        List<Cliente> allEvents;// = eventDao.findAll();

        if((filterTelefono  == null || "".equals(filterTelefono))   && 
           (filterTelefono2 == null || "".equals(filterTelefono2))  && 
           (filterEmail     == null || "".equals(filterEmail))      &&
           (filterApellidos == null || "".equals(filterApellidos))  && 
           (filterNombre    == null || "".equals(filterNombre))     && 
           (filterDni       == null || "".equals(filterDni))        &&
           (filterDireccion == null || "".equals(filterDireccion))  && 
           (filterCiudad    == null || "".equals(filterCiudad))     && 
           (filterProvincia == null || "".equals(filterProvincia))  &&
           (filterCodigopostal  == null || "".equals(filterCodigopostal))   && 
           (filterFechaalta == null || "".equals(filterFechaalta))) {
                //clientes.addAll(allEvents);
        }
        else 
        {
            allEvents = eventDao.findAll();
            for (Iterator<Cliente> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents) 
            {
                Cliente clie = it.next();
                if (((clie.getTelefono()+"").toLowerCase().startsWith(filterTelefono.toLowerCase()))   &&
                    ((clie.getTelefono2()+"").toLowerCase().startsWith(filterTelefono2.toLowerCase())) && 
                    ((clie.getEmail()+"").toLowerCase().startsWith(filterEmail.toLowerCase()) )        &&
                    (clie.getApellidos().toLowerCase().startsWith(filterApellidos.toLowerCase()) )     &&
                    (clie.getNombre().toLowerCase().startsWith(filterNombre.toLowerCase()) )           && 
                    (clie.getNif().toLowerCase().startsWith(filterDni.toLowerCase())  )                &&
                    (clie.getDireccion().toLowerCase().startsWith(filterDireccion.toLowerCase())   )   &&
                    (clie.getCiudad().getPoblacion().toLowerCase().startsWith(filterCiudad.toLowerCase())  ) && 
                    (clie.getProvincia().getProvincia().toLowerCase().startsWith(filterProvincia.toLowerCase())  ) &&
                    ((clie.getCodigopostal()+"").toLowerCase().startsWith(filterCodigopostal.toLowerCase()))   &&
                    (clie.getFechaalta().toString().toLowerCase().startsWith(filterFechaalta.toLowerCase())  )) {
                        clientes.add(clie);
                }
            }
        }
    }
    
    /* FIN FILTER*/
    
    public ListModelList<Cliente> getClientes() 
    {
        if(clientes == null) {
            clientes = new ListModelList<Cliente>();//new ListModelList<Cliente>(getEvents());
        }

        return clientes;
    }

    
    
    //Seleccionador de Provincia, para filtrar las poblaciones de dicha provincia
    @Command
    @NotifyChange("allPoblaciones")
    public void onSelectProvincia()
    {
            allPoblaciones = new PoblacionDAO().findAll("select * from zk_poblacion where provincia = " + selectedProvincia.getId() );
    }
    
    public void refreshPage() {
//        final HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("selectedPedido", selectedPedido );
            //Window win = (Window) page.getFellow("win");
            Borderlayout bl = (Borderlayout) Path.getComponent("/main");
            Center center = bl.getCenter();
            center.getChildren().clear();
            Executions.createComponents("cliente-lista.zul", center, null);
    }
    
    @Command
    public void send() {
        Map args = new HashMap();
        args.put("returnCliente", this.selectedEvent);
        BindUtils.postGlobalCommand(null, null, "refreshCliente", args);
    }
    
    @Command
    public void modificarPropietario() {
        Map args = new HashMap();
        args.put("returnCliente", this.selectedEvent);
        BindUtils.postGlobalCommand(null, null, "refreshModificarPropietario", args);
    }
}
