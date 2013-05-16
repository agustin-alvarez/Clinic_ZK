/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.pedido_linea;

import es.clinica.veterinaria.pedidos.Pedido;
import es.clinica.veterinaria.pedidos.PedidoDAO;
import es.clinica.veterinaria.productos.Producto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class PedidoLineaViewModel {

    private List<PedidoLinea> pedidolineas = new ArrayList<PedidoLinea>();
    private PedidoLineaDAO eventDao = new PedidoLineaDAO();
    private PedidoDAO pedidoDao = new PedidoDAO();

    private Pedido selectedPedido = new Pedido();
    private PedidoLinea newEvent = new PedidoLinea();
    private PedidoLinea selectedEvent = new PedidoLinea();


    public PedidoLinea getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(PedidoLinea selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Pedido getSelectedPedido() {
            return selectedPedido;
    }

    public void setSelectedPedido(Pedido selectedPedido) {
            this.selectedPedido = selectedPedido;
    }

    public PedidoLinea getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(PedidoLinea newEvent) {
            this.newEvent = newEvent;
    }

    public List<PedidoLinea> getEvents() {
            return eventDao.findAll();
    }

    public List<PedidoLinea> getPedidolineas() {
        if(this.selectedPedido != null) {
            return eventDao.findAll("select * from zk_pedido_linea where id_pedido = " + this.selectedPedido.getId());
        }
       else {
            return new ArrayList<PedidoLinea>();
        }
    }

    public void setPedidolineas(List<PedidoLinea> pedidolineas) {
        this.pedidolineas = pedidolineas;
    }

    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("selectedPedido") Pedido selectedPedido) 
    {
        Selectors.wireComponents(view, this, false);
        this.selectedPedido = selectedPedido;
        pedidolineas = getPedidolineas();
    }

    @Command("add")
    @NotifyChange({"events", "pedidolineas"})
    public void add() {
            this.newEvent.setPedido(selectedPedido);                //Relacion PedidoLinea-Pedido
            this.newEvent.setProducto(selectedEvent.getProducto()); //Relacion PedidoLinea-Producto
            selectedPedido.asignarPedidoLinea(newEvent);            //Relacion Pedido-PedidoLinea
            eventDao.insert(this.newEvent);
            this.newEvent = new PedidoLinea();
            
            refreshPage();
            
    }

    @Command("update")
    @NotifyChange({"events", "pedidolineas"})
    public void update() {
        if(this.selectedEvent != null) {
            if(!eventDao.update(this.selectedEvent)){
                Messagebox.show("Modificación no realizada", "Aviso", Messagebox.OK, Messagebox.ERROR);
            }
        }
//        this.selectedEvent = null;
    }
    
    @Command("updatepedido")
    @NotifyChange("selectedPedido")
    public void updatepedido(){
        if(!pedidoDao.update(this.selectedPedido)){
            Messagebox.show("Modificación no realizada", "Aviso", Messagebox.OK, Messagebox.ERROR);
        }
    }

    @Command("delete")
    @NotifyChange({"events", "pedidolineas"})
    public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                    eventDao.delete(this.selectedEvent);
                    this.selectedEvent = null;
//                    refreshPage();
            }
            else{
                this.selectedEvent = null;
                Messagebox.show("Seleccione un producto para eliminar", "Aviso", Messagebox.OK, Messagebox.ERROR);
            }
            
    }
    
//    @Command
//    public void addProducto()
//    {
//        final HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("selectedCliente", selectedPedido );
//        //Window win = (Window) page.getFellow("win");
//        //Borderlayout bl = (Borderlayout) Path.getComponent("/main");
//        //Center center = bl.getCenter();
//        //center.getChildren().clear();
//        Executions.createComponents("../mascotas/mascota-asignar.zul", null, map);
//    }
    
    @GlobalCommand
    @NotifyChange({"selectedEvent", "pedidolineas"})
    public void refreshvalues(@BindingParam("returnProducto") Set <Producto> items)
    {
        Iterator <Producto> it = items.iterator();
        while(it.hasNext()){
            Producto item = it.next();
            PedidoLinea ped = new PedidoLinea();  
            ped.setProducto(item);                //Relacion PedidoLinea-Producto
            ped.setPedido(selectedPedido);        //Relacion PedidoLinea-Pedido
            ped.setCantidad(1);
            if(selectedPedido.asignarPedidoLinea(ped)) {
                eventDao.insert(ped);
            }
        }
    }
    
        public void refreshPage() {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedPedido", selectedPedido );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("pedido-lineas.zul", center, map);
    }
//    @Command
//    @NotifyChange("allRazas")
//    public void onSelectEspecie()
//    {
//            allRazas = new RazaDAO().findAll("select * from zk_raza where especie = " + selectedEspecie.getId() );
//    }
    
    @Command
    public void send() {
        Map args = new HashMap();
        args.put("returnProveedor", this.selectedPedido.getProveedor());
        BindUtils.postGlobalCommand(null, null, "refreshProveedor", args);
    }
}
