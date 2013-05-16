
package es.clinica.veterinaria.mascotas;
import es.clinica.veterinaria.citas.Cita;
import es.clinica.veterinaria.citas.CitaDAO;
import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import es.clinica.veterinaria.ficheros.Fichero;
import es.clinica.veterinaria.ficheros.FicheroDAO;
import es.clinica.veterinaria.historial.Historial;
import es.clinica.veterinaria.historial.HistorialDAO;
import es.clinica.veterinaria.mascota_vacuna.MascotaVacuna;
import es.clinica.veterinaria.mascota_vacuna.MascotaVacunaDAO;
import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.poblaciones.PoblacionDAO;
import es.clinica.veterinaria.provincias.Provincia;
import es.clinica.veterinaria.provincias.ProvinciaDAO;
import es.clinica.veterinaria.razas.Raza;
import es.clinica.veterinaria.razas.RazaDAO;
import es.clinica.veterinaria.servicio_familia.ServicioFamilia;
import es.clinica.veterinaria.user.UserCredentialManager;
import es.clinica.veterinaria.vacunas.Vacuna;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

/**
 * @author SaRCo
 */
public class MascotaDatosVM {
            
    String activo;
    Session s = Sessions.getCurrent();
    
    private List<Cita>    citas    = new ArrayList<Cita>();
    private List<Mascota> mascotas = new ArrayList<Mascota>();
//    private List<Venta>   ventas   = new ArrayList<Venta>();
    private List<Historial> historiales = new ArrayList<Historial>();
    private List<MascotaVacuna> vacunas = new ArrayList<MascotaVacuna>();
    private List<Raza>    allRazas = new ArrayList<Raza>();
    private List<Poblacion> allPoblaciones = new ArrayList<Poblacion>();
    
    private MascotaDAO eventDao = new MascotaDAO();
    private MascotaDAO mascoDao = new MascotaDAO();
    private EspecieDAO especieDao = new EspecieDAO();
    private RazaDAO razaDao = new RazaDAO();
    private CitaDAO citaDao = new CitaDAO();
    private MascotaVacunaDAO mascvacDao = new MascotaVacunaDAO();
//    private VentaDAO ventaDao = new VentaDAO();
    private HistorialDAO histDao = new HistorialDAO();
    private ProvinciaDAO provDao = new ProvinciaDAO();
    private PoblacionDAO poblDao = new PoblacionDAO();

    private Mascota newEvent = new Mascota();
    private Mascota newMasc  = new Mascota();
    private Cita newCita = new Cita();
    private Historial newHistorial = new Historial();
    private MascotaVacuna newVacuna = new MascotaVacuna();
    
    private Mascota selectedEvent;// = new Mascota();
    private Especie auxEspecie = new Especie();
    private Raza auxRaza = new Raza();
    private Mascota selectedMascota = new Mascota();
    private Cita selectedCita;// = new Cita();
    private Especie selectedEspecie = new Especie();
//    private Venta selectedVenta = new Venta();
    private Provincia selectedProvincia = new Provincia();
    private Historial selectedHistorial;// = new Historial();
    private MascotaVacuna selectedVacuna;// = new MascotaVacuna();
    
    private Mascota auxMascota = new Mascota();
    
    
    /*
     * Inicio de la carga 
     */
    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                          @ExecutionArgParam("selectedMascota") Mascota selectedMascota) 
    {
        Selectors.wireComponents(view, this, false);
        this.selectedEvent = selectedMascota;
        auxEspecie = selectedMascota.getEspecie();
        auxRaza = selectedMascota.getRaza();
        auxMascota = selectedMascota;
//        System.out.println("Cliente: " + selectedMascota.getCliente().getFullname() );
//        citas = getCitas();
//        ventas = getVentas();
//        historiales = getHistoriales();
    }
    
    public Mascota getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Mascota selectedEvent) {
            this.selectedEvent = selectedEvent;
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
    public Mascota getSelectedMascota() {
            return selectedMascota;
    }

    public void setSelectedMascota(Mascota selectedMascota) {
            this.selectedMascota = selectedMascota;
    }

    public Historial getSelectedHistorial() {
        return selectedHistorial;
    }

    public void setSelectedHistorial(Historial selectedHistorial) {
        this.selectedHistorial = selectedHistorial;
    }
    
//    public Venta getSelectedVenta() {
//            return selectedVenta;
//    }
//
//    public void setSelectedVenta(Venta selectedVenta) {
//            this.selectedVenta = selectedVenta;
//    }
    
    public Cita getSelectedCita() {
            return selectedCita;
    }

    public void setSelectedCita(Cita selectedCita) {
            this.selectedCita = selectedCita;
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
            return allRazas;
    }

    
    public List<Cita> getCitas() {
            return citaDao.findAll("select * from zk_cita where id_mascota=" + selectedEvent.getId());
    }
    
//    public List<Venta> getVentas() {
//            return ventaDao.findAll("select * from zk_venta where id_cliente=" + selectedEvent.getId());
//    }

    public List<Historial> getHistoriales() {
            return histDao.findAll("select * from zk_historial where id_mascota=" + selectedEvent.getId() + " ORDER BY fecha DESC");
    }
    
    public List<MascotaVacuna> getVacunas() {
            return mascvacDao.findAll("select * from zk_mascota_vacuna where mascota=" + selectedEvent.getId() + " ORDER BY fecha DESC");
    }
    
    @Command("add")
    @NotifyChange("events")
    public void add() { //Añadir nuevo cliente
//            this.newEvent.setId(UUID.randomUUID().variant());
//            this.newEvent.setCliente(selectedEvent);  //Relacion Mascota-Cliente
            if(!eventDao.insert(this.newEvent)){
                Messagebox.show("Mascota no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.newEvent = new Mascota();
    }

    @Command("update")
    @NotifyChange({"events", "selectedEvent"})
    public void update() {
            if((this.selectedEvent.getEspecie() == null) ) {
                this.selectedEvent.setEspecie(this.auxEspecie);
            }
            
            if((this.selectedEvent.getRaza() == null)) {
                this.selectedEvent.setRaza(auxRaza);
            }

            if(!eventDao.update(this.selectedEvent)){
                Messagebox.show("Mascota no actualizada", "Error", Messagebox.OK, Messagebox.ERROR);
                this.selectedEvent = this.auxMascota;
            }
    }

    @Command("delete")
    @NotifyChange({"events", "selectedEvent"})
    public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                if(!eventDao.delete(this.selectedEvent)){
                    Messagebox.show("Mascota no eliminada", "Error", Messagebox.OK, Messagebox.ERROR);
                }
                this.selectedEvent = null;
            }
    }

    /*
     * Mascotas
     */
    
    @Command("addmasc")
    @NotifyChange("eventsmasc")
    public void addmmasc() {
//            this.newMasc.setId(UUID.randomUUID().variant());
//            this.newMasc.setCliente(selectedEvent);  //Relacion Mascota-Cliente
//            selectedEvent.asignarMascota(newMasc);   //Relacion Cliente-Mascota
            if(!mascoDao.insert(this.newMasc)){
                Messagebox.show("Mascota no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.newMasc = new Mascota();
    }

    @Command("updatemasc")
    @NotifyChange("eventsmasc")
    public void updatemasc() {
            if(!mascoDao.update(this.selectedMascota)){
                Messagebox.show("Mascota no actualizada", "Error", Messagebox.OK, Messagebox.ERROR);
            }
    }

    @Command("deletemasc")
    @NotifyChange({"eventsmasc", "selectedMascota"})
    public void deletemasc() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedMascota != null) {
                if(!mascoDao.delete(this.selectedMascota)){
                    Messagebox.show("Mascota no eliminada", "Error", Messagebox.OK, Messagebox.ERROR);
                }
                this.selectedMascota = null;
            }
    }
    
    @Command
    public void asignarMascota()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        Executions.createComponents("../mascotas/mascota-asignar.zul", null, map);
    }
    
    @Command
    @NotifyChange("allRazas")
    public void onSelectEspecie()
    {
            allRazas = new RazaDAO().findAll("select * from zk_raza where especie = " + selectedEspecie.getId() );
    }
        
            //Seleccionador de Provincia, para filtrar las poblaciones de dicha provincia
    @Command
    @NotifyChange("allPoblaciones")
    public void onSelectProvincia()
    {
            allPoblaciones = new PoblacionDAO().findAll("select * from zk_poblacion where provincia = " + selectedProvincia.getId() );
    }
    
    /*
     * Citas
     */
    
    @Command
    public void asignarCita()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        Executions.createComponents("../clientes/cliente-nueva-cita.zul", null, map);
    }
    
    /*
     * Historiales
     */
    
    @Command
    @NotifyChange("events")
    public void addhistorial() {
        this.newHistorial.setMascota(this.getSelectedEvent());
        this.newHistorial.setId_veterinario(UserCredentialManager.getIntance(s).getUser());
        if(histDao.insert(this.newHistorial)<0){
            Messagebox.show("Historial no añadido", "Error", Messagebox.OK, Messagebox.ERROR);
        }
        this.newHistorial = new Historial();
    }

    @Command
    @NotifyChange({"events","historiales"})
    public void updatehistorial() {
        if(!histDao.update(this.selectedHistorial)){
            Messagebox.show("Historial no actualizado", "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command
    @NotifyChange({"historiales", "events", "selectedEvent"})
    public void deletehistorial() {
        //shouldn't be able to delete with selectedEvent being null anyway
        //unless trying to hack the system, so just ignore the request
        if(this.selectedHistorial != null) {
            if(!histDao.delete(this.selectedHistorial)){
                Messagebox.show("Historial no eliminado", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.selectedHistorial = null;
        }
    }
    
    @GlobalCommand
    @NotifyChange("historiales")
    public void refreshHistorial(@BindingParam("returnHistorial") Historial hist)
    {
        hist.setFecha(new Date());
        int id = histDao.insert(hist);
        
        if(!hist.getFicheros().isEmpty() && id > 0){
            Iterator <Fichero> it = hist.getFicheros().iterator();
            while(it.hasNext()){
                Fichero file = it.next();
                file.setId_externo(id);
                if(new FicheroDAO().insert(file) > 0){
                    System.out.println("Nuevo>>Fichero insertado!!");
                }
                
            }
        }
            
        this.newHistorial = new Historial();
    }
    
    @GlobalCommand
    @NotifyChange("historiales")
    public void refreshHistorialModificado(@BindingParam("returnHistorial") Historial hist) {
        if(!histDao.update(hist)){
            Messagebox.show("Historial no actualizado", "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    @Command
    public void abrirHistorial(){
        Map map = new HashMap();
        map.put("selectedMascota", this.selectedEvent);
        map.put("selectedHistorial", this.selectedHistorial);
        Executions.createComponents("mascota-historial-modificar.zul", null, map);
    }
    
/* CITAS */
    @Command("deletecita")
    @NotifyChange({"citas", "selectedEvent"})
    public void deletecita() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedCita != null) {
                    citaDao.delete(this.selectedCita);
                    this.selectedCita = null;
            }
    }
        
    @Command("deletevacuna")
    @NotifyChange("vacunas")
    public void deletevacuna() {
        //shouldn't be able to delete with selectedEvent being null anyway
        //unless trying to hack the system, so just ignore the request
        if(this.selectedVacuna != null) {
            if(!mascvacDao.delete(this.selectedVacuna)){
                Messagebox.show("Vacuna no eliminada", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.selectedVacuna = null;
        }
    }
    
    public MascotaVacuna getSelectedVacuna() {
        return selectedVacuna;
    }

    public void setSelectedVacuna(MascotaVacuna selectedVacuna) {
        this.selectedVacuna = selectedVacuna;
    }
    
    
    /* Se manda desde mascota-vacuna-nueva.zul */
    @GlobalCommand
    @NotifyChange({"vacunas", "citas"})
    public void refreshVacunas(@BindingParam("returnVacuna") Set<Vacuna> items)
    {
//        System.out.println("Tamaño: " + items.size());
        Iterator <Vacuna> it = items.iterator();
        while(it.hasNext()){
            Vacuna item = it.next();
//            System.out.println("Vacuna: " + item.getNombre());
            this.newVacuna.setMascota(selectedEvent);
            this.newVacuna.setVacuna(item);
            this.newVacuna.setVeterinario(UserCredentialManager.getIntance(s).getUser());
            if(!mascvacDao.insert(this.newVacuna)){
                Messagebox.show("Vacuna no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            
            if(item.getDias() > 0 ){
                this.newCita.setMascota(selectedEvent);
                this.newCita.setCliente(selectedEvent.getCliente());
                this.newCita.setEmpleado(UserCredentialManager.getIntance(s).getUser());
                this.newCita.setEstado(2); //Pendiente
                this.newCita.setInforme(item.getNombre());
                
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_YEAR, item.getDias());

                this.newCita.setFecha(cal.getTime());
                this.newCita.setHora("09:00:00");
                ServicioFamilia serv = new ServicioFamilia();
                serv.setId(1);
                this.newCita.setServicio(serv);
                
                if(!citaDao.insert(newCita)){
                    Messagebox.show("Cita no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
                }
                this.newCita = new Cita();
            }
            
            this.newVacuna = new MascotaVacuna();
        }
    }
    
    /* Se manda desde mascota-vacuna-nueva.zul */
    @GlobalCommand
    @NotifyChange({"vacunas", "citas"})
    public void refreshCitas(@BindingParam("returnCita") Cita item)
    {
            this.newCita = item;
            this.newCita.setCliente(selectedEvent.getCliente());
            this.newCita.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
            if(!citaDao.insert(this.newCita)){
                Messagebox.show("Cita no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.newCita = new Cita();
    }

    @GlobalCommand
    @NotifyChange("selectedEvent")
    public void refreshModificarPropietario(@BindingParam("returnCliente") Cliente cli) {
        this.selectedEvent.setCliente(cli);
    }
    
    @NotifyChange
    public void setActivo(String activo) {
        this.activo = activo;
        if("false".equals(activo)) {
            this.selectedEvent.setFechabaja(new Date());
        }
        else {
            this.selectedEvent.setFechabaja(null);
        }
    }

    @NotifyChange
    public String getActivo() {
//        System.out.println("isActivo1: " + activo);
        if(this.selectedEvent.getFechabaja() == null) {
            activo = "true";
        }
        else {//if(this.selectedEvent.getFechabaja() == null){
            activo = "false";
        }
        
        return activo;
    }
    
    @Command
    @NotifyChange("selectedEvent")
    public void cancelar() {
        this.selectedEvent = this.auxMascota;
    }
    
}
