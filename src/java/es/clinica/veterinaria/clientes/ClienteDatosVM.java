/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.clientes;

import es.clinica.veterinaria.citas.Cita;
import es.clinica.veterinaria.citas.CitaDAO;
import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.poblaciones.PoblacionDAO;
import es.clinica.veterinaria.provincias.Provincia;
import es.clinica.veterinaria.provincias.ProvinciaDAO;
import es.clinica.veterinaria.razas.Raza;
import es.clinica.veterinaria.razas.RazaDAO;
import es.clinica.veterinaria.user.UserCredentialManager;
import es.clinica.veterinaria.ventas.Venta;
import es.clinica.veterinaria.ventas.VentaDAO;
import es.clinica.veterinaria.ventas.VentaViewModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class ClienteDatosVM {
        
//    Combobox cmbEspecie;
//    Combobox cmbRaza;
    Session s = Sessions.getCurrent();
    private List<Cita>    citas    = new ArrayList<Cita>();
    private List<Mascota> mascotas = new ArrayList<Mascota>();
    private List<Venta>   ventas   = new ArrayList<Venta>();
    private List<Raza>    allRazas = new ArrayList<Raza>();
    private List<Poblacion> allPoblaciones = new ArrayList<Poblacion>();
    
    private ClienteDAO eventDao = new ClienteDAO();
    private MascotaDAO mascoDao = new MascotaDAO();
    private EspecieDAO especieDao = new EspecieDAO();
//    private RazaDAO razaDao = new RazaDAO();
    private CitaDAO citaDao = new CitaDAO();
    private VentaDAO ventaDao = new VentaDAO();
    private ProvinciaDAO provDao = new ProvinciaDAO();
    private PoblacionDAO poblDao = new PoblacionDAO();

    private Cliente newEvent = new Cliente();
    private Mascota newMasc  = new Mascota();
    private Cita newCita = new Cita();
    private Venta newVenta = new Venta();
    
    private Cliente selectedEvent = new Cliente();
    private Cita selectedCita = new Cita();
    private Mascota selectedMascota = new Mascota();
    private Especie selectedEspecie = new Especie();
    private Venta selectedVenta = new Venta();
    private Provincia selectedProvincia = new Provincia();
    
    private Cliente auxCliente = new Cliente();
    

    
    
    /*
     * Inicio de la carga 
     */
    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("selectedCliente") Cliente selectedCliente) 
    {
        Selectors.wireComponents(view, this, false);
        this.selectedEvent = selectedCliente;
        this.auxCliente = selectedCliente;
//        mascotas = getMasc();
//        citas = getCitas();
//        ventas = getVentas();
    }
    
    public Cliente getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Cliente selectedEvent) {
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
    
    public Venta getSelectedVenta() {
            return selectedVenta;
    }

    public void setSelectedVenta(Venta selectedVenta) {
            this.selectedVenta = selectedVenta;
            System.out.println("SelectedVenta: " + this.selectedVenta.getCliente().getFullname());
    }
    
    public Cita getSelectedCita() {
            return selectedCita;
    }

    public void setSelectedCita(Cita selectedCita) {
            this.selectedCita = selectedCita;
            System.out.println("SelectedCita: " + this.selectedCita.getCliente().getFullname());
    }

    public Cliente getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(Cliente newEvent) {
            this.newEvent = newEvent;
    }

    public Especie getSelectedEspecie() {
            return selectedEspecie;
    }

    public void setSelectedEspecie(Especie selectedEspecie) {
            this.selectedEspecie = selectedEspecie;
    }

    public List<Cliente> getEvents() {
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

    public List<Mascota> getMasc() {
            return mascoDao.getClienteMascotas(this.selectedEvent);
    }
    
    public List<Mascota> getMascotas() {
        return mascoDao.findAll("select * from zk_mascota where id_cliente=" + selectedEvent.getId());
    }
        
    public List<Cita> getCitas() {
        return citaDao.findAll("select * from zk_cita where id_cliente=" + selectedEvent.getId());
    }
    
    public List<Venta> getVentas() {
            return ventaDao.findAll("select * from zk_venta where id_cliente=" + selectedEvent.getId());
    }

    @Command("add")
    @NotifyChange("events")
    public void add() { //Añadir nuevo cliente

            if(!eventDao.insert(this.newEvent)){
                Messagebox.show("Datos del cliente no insertados", "Error", Messagebox.OK, Messagebox.ERROR);
            }
            this.newEvent = new Cliente();
    }

    @Command("update")
    @NotifyChange({"events", "selectedEvent"})
    public void update() {
            if(!eventDao.update(this.selectedEvent)){
                Messagebox.show("Datos del cliente no actualizados", "Error", Messagebox.OK, Messagebox.ERROR);
                this.selectedEvent = this.auxCliente;
            }
    }

    @Command("delete")
    @NotifyChange({"events", "selectedEvent"})
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

    /*
     * Mascotas
     */
    
    @Command("addmasc")
    @NotifyChange("mascotas")
    public void addmmasc() {
            this.newMasc.setCliente(selectedEvent);  //Relacion Mascota-Cliente
            selectedEvent.asignarMascota(newMasc);   //Relacion Cliente-Mascota
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
    @NotifyChange("mascotas")
    public void asignarMascota()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedEvent );
        Executions.createComponents("../clientes/cliente-nueva-mascota.zul", null, map);
    }

    /* Se manda desde cliente-nueva-mascota.zul */
    @GlobalCommand
    @NotifyChange("mascotas")
    public void refreshMascotas(@BindingParam("returnMascota") Mascota item)
    {
        this.newMasc = item;
        this.newMasc.setCliente(selectedEvent);

        if(!mascoDao.insert(this.newMasc)){
            Messagebox.show("Mascota no añadida", "Error", Messagebox.OK, Messagebox.ERROR);
        }
        else{
            selectedEvent.asignarMascota(newMasc);   //Relacion Cliente-Mascota
        }
        this.newMasc = new Mascota();
    }
    
    @Command
    @NotifyChange("allRazas")
    public void onSelectEspecie()
    {
            allRazas = new RazaDAO().findAll("select * from zk_raza where especie = " + selectedEspecie.getId() );
    }
        
    @Command
    public void showMascota()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedMascota", selectedMascota );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("../mascotas/mascota.zul", center, map);
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
    
    /* Se manda desde cliente-nueva-cita.zul */
    @GlobalCommand
    @NotifyChange("citas")
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
    
    /*
     * Ventas
     */
    
    @Command("asignarVenta")
    @NotifyChange("ventas")
    public void asignarVenta() {
            this.newVenta.setVendedor(UserCredentialManager.getIntance(s).getUser() );
            this.newVenta.setFecha(new Date());
            int id = ventaDao.insert(this.newVenta);
            Venta venta = this.newVenta;
            this.newVenta = new Venta();
            if(id != 0) {
                venta.setId(id);
                new VentaViewModel().showVentaLinea(venta);
            }else{
                Messagebox.show("Venta no creada", "Error", Messagebox.OK, Messagebox.ERROR);
            }
    }
    
    @Command
    public void showVentaLinea() {
        new VentaViewModel().showVentaLinea(this.selectedVenta);
    }
    
    @Command
    @NotifyChange("selectedEvent")
    public void cancelar() {
//        Messagebox.show("Modificación cancelada", "Aviso", Messagebox.CANCEL, Messagebox.ON_CANCEL);
        this.selectedEvent = this.auxCliente;
    }
}
