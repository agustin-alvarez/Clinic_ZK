/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.ventas_linea;

import es.clinica.veterinaria.albaranes.AlbaranPdf;
import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.clientes.ClienteDAO;
import es.clinica.veterinaria.productos.Producto;
import es.clinica.veterinaria.servicios.Servicio;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserCredentialManager;
import es.clinica.veterinaria.user.UserDAO;
import es.clinica.veterinaria.ventas.Venta;
import es.clinica.veterinaria.ventas.VentaDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.annotation.*;
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
public class VentaLineaViewModel {
    
    private List<VentaLinea> ventalineas = new ArrayList<VentaLinea>();
    private List<User> veterinarios = new ArrayList<User>();
    private VentaLineaDAO eventDao = new VentaLineaDAO();
    private VentaDAO ventaDao = new VentaDAO();
    private UserDAO userDao = new UserDAO();
    private VentaLinea newEvent = new VentaLinea();
    private VentaLinea selectedEvent = new VentaLinea();
    private Venta selectedVenta = new Venta();
    private User selectedVet = new User();
    Session s = Sessions.getCurrent();
    private float costeventalineas;
    
    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("selectedVenta") Venta selectedVenta) 
    {
        Selectors.wireComponents(view, this, false);
        
        if(selectedVenta == null) //Si la venta es nula, entonces es Venta Rápida
        {   
            ClienteDAO newClienteDao = new ClienteDAO();
            VentaDAO newVentaDao = new VentaDAO();
            Venta newVenta = new Venta();
            List<Cliente> cliente = newClienteDao.findAll("SELECT * FROM zk_cliente WHERE id=1");
            newVenta.setCliente(cliente.get(0));
            newVenta.setVendedor(UserCredentialManager.getIntance(s).getUser());
            newVenta.setFecha(new Date());
            if(UserCredentialManager.getIntance(s).getUser().getTipo()== 2)
            {
                selectedVet =  UserCredentialManager.getIntance(s).getUser();
                newVenta.setVeterinario(selectedVet);
            }
            int id = newVentaDao.insert(newVenta);
            newVenta.setId(id);
            
            selectedVenta = newVenta;
        }
        this.selectedVenta = selectedVenta;
        this.selectedVet = selectedVenta.getVeterinario();
//        ventalineas = getVentalineas();
    }
        
    public VentaLinea getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(VentaLinea selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Venta getSelectedVenta() {
        return selectedVenta;
    }

    public void setSelectedVenta(Venta selectedVenta) {
        this.selectedVenta = selectedVenta;
    }

    public User getSelectedVet() {
        return selectedVet;
    }

    public void setSelectedVet(User selectedVet) {
        this.selectedVet = selectedVet;
    }
    
    public VentaLinea getNewEvent() {
        return newEvent;
    }

    public void setNewEvent(VentaLinea newEvent) {
        this.newEvent = newEvent;
    }

    public List<VentaLinea> getEvents() {
        return eventDao.findAll();
    }

    public List<VentaLinea> getVentalineas() {
        return eventDao.findAll("select * from zk_venta_linea where id_venta = " + this.selectedVenta.getId());
    }

    public void setVentalineas(List<VentaLinea> ventalineas) {
        this.ventalineas = ventalineas;
    }

    public List<User> getVeterinarios() {
        return userDao.findAll("select * from zk_usuario where tipo = 2");
    }

    public void setVeterinarios(List<User> veterinarios) {
        this.veterinarios = veterinarios;
    }


    @Command("add")
    @NotifyChange({"events", "ventalineas", "costeventalineas"})
    public void add() {
        this.newEvent.setVenta(selectedVenta);                  //Relacion VentaLinea-Venta
        this.newEvent.setProducto(selectedEvent.getProducto()); //Relacion VentaLinea-Producto
        selectedVenta.asignarVentaLinea(newEvent);              //Relacion Venta-VentaLinea
        eventDao.insert(this.newEvent);
        this.newEvent = new VentaLinea();
    }

    @Command("update")
    @NotifyChange({"events", "ventalineas", "costeventalineas"})
    public void update() {
        if(!eventDao.update(this.selectedEvent)) {
            Messagebox.show("Cambio no realizado", "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    @Command("updateVet")
    @NotifyChange({"events", "ventalineas"})
    public void updateVet() {
        this.selectedVenta.setVeterinario(this.selectedVet);
        if(!ventaDao.updateVeterinario(this.selectedVenta)) {
            Messagebox.show("Cambio no realizado", "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command("delete")
    @NotifyChange({"events", "ventalineas", "costeventalineas"})
    public void delete() {
        //shouldn't be able to delete with selectedEvent being null anyway
        //unless trying to hack the system, so just ignore the request
        if(this.selectedEvent != null) {
            eventDao.delete(this.selectedEvent);
            this.selectedEvent = null;
        }
    }
    
    @Command
    public void addProducto()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedVenta );
        Executions.createComponents("../mascotas/mascota-asignar.zul", null, map);
    }
    
    
    //Recoge los productos seleccionados
    @GlobalCommand
    @NotifyChange({"selectedEvent", "ventalineas", "costeventalineas"})
    public void refreshvalues(@BindingParam("returnProducto") Set<Producto> items)
    {
        System.out.println("Tamaño: " + items.size());
        Iterator <Producto> it = items.iterator();
        while(it.hasNext()){
            Producto item = it.next();
            System.out.println("Productos: " + item.getNombre());
            
            VentaLinea vlinea = new VentaLinea();
            vlinea.setProducto(item);                   //Relacion VentaLinea-Producto
            vlinea.setVenta(selectedVenta);             //Relacion VentaLinea-Venta
            vlinea.setCantidad(1);
            vlinea.setTipo(1);
            if(selectedVenta.asignarVentaLinea(vlinea)) {
                eventDao.insert(vlinea);
            }
        }
    }
    
    @GlobalCommand
    @NotifyChange({"selectedEvent", "ventalineas", "costeventalineas"})
    public void refreshServicios(@BindingParam("returnServicio") Set<Servicio> items)
    {
        System.out.println("Tamaño: " + items.size());
        Iterator <Servicio> it = items.iterator();
        while(it.hasNext()){
            Servicio item = it.next();
            System.out.println("Productos: " + item.getNombre());
            
            VentaLinea vlinea = new VentaLinea();
            vlinea.setServicio(item);                   //Relacion VentaLinea-Producto
            vlinea.setVenta(selectedVenta);             //Relacion VentaLinea-Venta
            vlinea.setCantidad(1);
            vlinea.setTipo(2);
            if(selectedVenta.asignarVentaLinea(vlinea)) {
                eventDao.insert(vlinea);
            }
        }
    }
    
    public void refreshPage() {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedVenta", selectedVenta );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("venta-lineas.zul", center, map);
    }
    
    @GlobalCommand
    @NotifyChange("selectedVenta")
    public void refreshCliente(@BindingParam("returnCliente") Cliente cli)
    {
        this.selectedVenta.setCliente(cli);
        if(!ventaDao.updateCliente(this.selectedVenta))  {
            Messagebox.show("Cambio no realizado", "Error", Messagebox.OK, Messagebox.ERROR);
        }
    }
    
    @Command
    public void crearAlbaran() throws FileNotFoundException, IOException{
        AlbaranPdf albaran = new AlbaranPdf();
        albaran.setVenta(this.selectedVenta);
        albaran.createPdf();
        this.selectedVenta.descargarAlbaran();
    }

//    public float getCosteventalineas() {
//        costeventalineas = 0;
//        Iterator <VentaLinea> it = this.getVentalineas().iterator();
//        while(it.hasNext()){
//            VentaLinea ln = it.next();
//            if(ln.getTipo() == 1) {
//                costeventalineas += ln.getProducto().getPvp() * ln.getCantidad() * (1+(ln.getProducto().getIva().getValor() * 0.01));
//            }
//            else  if(ln.getTipo() == 2) {
//                costeventalineas += ln.getServicio().getPrecio() * ln.getCantidad()* (1+(ln.getServicio().getIva().getValor()* 0.01));
//            }
//        }
//        return costeventalineas;
//    }
    public float getCosteventalineas() {
        costeventalineas = 0;
        Iterator <VentaLinea> it = this.getVentalineas().iterator();
        while(it.hasNext()){
            VentaLinea ln = it.next();
            costeventalineas += ln.getPvp() * ln.getCantidad() * (1+(ln.getIva() * 0.01));
        }
        return costeventalineas;
    }
    

    public void setCosteventalineas(float costeventalineas) {
        this.costeventalineas = costeventalineas;
    }
    
}
