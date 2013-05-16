/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.pedidos;

import es.clinica.veterinaria.proveedores.Proveedor;
import es.clinica.veterinaria.proveedores.ProveedorDAO;
import es.clinica.veterinaria.user.UserCredentialManager;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author SaRCo
 */
public class PedidoViewModel {

    private PedidoDAO eventDao = new PedidoDAO();
    private ProveedorDAO provDao = new ProveedorDAO();
    private Pedido selectedEvent, newEvent = new Pedido();
    private Proveedor selectedProveedor;
    private String filterFechaEntrega = "",
                    filterFechaPago = "",
                    filterProveedor = "",
                    filterPagado =  "",
                    filterFecha = "", 
                    filterId = "";
    private ListModelList<Pedido> pedidos;
    Session s = Sessions.getCurrent();


    public Pedido getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Pedido selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Pedido getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(Pedido newEvent) {
            this.newEvent = newEvent;
    }

    public List<Pedido> getEvents() {
            return eventDao.findAll();
    }

    public Proveedor getSelectedProveedor() {
            return selectedProveedor;
    }

    public void setSelectedProveedor(Proveedor selectedProveedor) {
            this.selectedProveedor = selectedProveedor;
    }

    public String getFilterFechaEntrega() {
        return filterFechaEntrega;
    }

    @NotifyChange
    public void setFilterFechaEntrega(String filterFechaEntrega) {
        this.filterFechaEntrega = filterFechaEntrega;
    }

    public String getFilterFechaPago() {
        return filterFechaPago;
    }

    @NotifyChange
    public void setFilterFechaPago(String filterFechaPago) {
        this.filterFechaPago = filterFechaPago;
    }

    public String getFilterProveedor() {
        return filterProveedor;
    }

    @NotifyChange
    public void setFilterProveedor(String filterProveedor) {
        this.filterProveedor = filterProveedor;
    }

    public String getFilterPagado() {
        return filterPagado;
    }

    @NotifyChange
    public void setFilterPagado(String filterPagado) {
        this.filterPagado = filterPagado;
    }

    public String getFilterFecha() {
        return filterFecha;
    }

    @NotifyChange
    public void setFilterFecha(String filterFecha) {
        this.filterFecha = filterFecha;
    }

    public String getFilterId() {
        return filterId;
    }
    
    @NotifyChange
    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }
    
    public ListModelList<Pedido> getPedidos() {
        if(pedidos == null) {
            pedidos = new ListModelList<Pedido>();//new ListModelList<Pedido>(getEvents());
        }
        return pedidos;
    }


    public List<Proveedor> getProveedores() {
            return provDao.findAll();
    }

    @Command("add")
    @NotifyChange("pedidos")
    public void add() {
            this.newEvent.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
            this.newEvent.setFecha(new Date());
            int id =  eventDao.insert(this.newEvent);
            Pedido pedido = this.newEvent;
            this.newEvent = new Pedido();
            this.selectedEvent = this.newEvent;
            this.newEvent = new Pedido();
            if(id != 0) {
                pedido.setId(id);
                this.showPedidoLinea(pedido);
            }
    }
    
    @Command("update")
    @NotifyChange({"pedidos", "selectedEvent"})
    public void update() {
            eventDao.update(this.selectedEvent);
    }

    @Command("delete")
    @NotifyChange({"pedidos", "selectedEvent"})
    public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                    eventDao.delete(this.selectedEvent);
                    this.selectedEvent = null;
            }
    }

    @Command("pagado")
    @NotifyChange("pedidos")
    public void pagado() {
        this.selectedEvent.setPagado(true);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.selectedEvent.setFecha_pago(df.parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        } catch (ParseException ex) {
            Logger.getLogger(PedidoViewModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        eventDao.update(this.selectedEvent);
    }

    @Command("sinpagar")
    @NotifyChange("pedidos")
    public void sinpagar() {
        this.selectedEvent.setPagado(false);
        this.selectedEvent.setFecha_pago(null);
        eventDao.update(this.selectedEvent);
    }

    @Command
    public void showPedidoLinea()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedPedido", this.selectedEvent );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("pedido-lineas.zul", center, map);
    }

    public void showPedidoLinea(Pedido selPedido){
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedPedido", selPedido );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("pedido-lineas.zul", center, map);
    }

    // Buscador para hacer el filtrado
    @Command
    @NotifyChange("pedidos")
    public void doSearch()
    {
        pedidos.clear();
        List<Pedido> allEvents; // = eventDao.findAll();

        if((filterId            == null || "".equals(filterId))             &&
           (filterFecha         == null || "".equals(filterFecha))          &&
           (filterFechaEntrega  == null || "".equals(filterFechaEntrega))   &&
           (filterFechaPago     == null || "".equals(filterFechaPago))      &&
           (filterProveedor     == null || "".equals(filterProveedor))      &&
           (filterPagado        == null || "".equals(filterPagado))) {
                //pedidos.addAll(allEvents);
        }
        else
        {
            allEvents = eventDao.findAll();
            for (Iterator<Pedido> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents)
            {
                Pedido clie = it.next();
                String fecha_entrega,
                       fecha_pago,
                       pagado;
                 
                if(clie.getFecha_entrega() == null) {
                    fecha_entrega = "";
                }else {
                    fecha_entrega = clie.getFecha_entrega().toString();
                }
                if(clie.getFecha_pago() == null) {
                    fecha_pago = "";
                }
                else {
                    fecha_pago = clie.getFecha_pago().toString();
                }
                if(clie.isPagado()) {
                    pagado = "Si";
                }
                else {
                    pagado = "No";
                }
                
                if( ((clie.getId()+"").toLowerCase().startsWith(filterId.toLowerCase())  ) &&
                    (fecha_entrega.toLowerCase().startsWith(filterFechaEntrega.toLowerCase())   )                &&
                    (fecha_pago.toLowerCase().startsWith(filterFechaPago.toLowerCase())  )                      &&
                    (clie.getProveedor().getNombre().toLowerCase().startsWith(filterProveedor.toLowerCase())  ) &&
                    (pagado.toLowerCase().startsWith(filterPagado.toLowerCase())  ) &&
                    (clie.getFecha().toString().toLowerCase().startsWith(filterFecha.toLowerCase())  )) {
                        
                        pedidos.add(clie);
                }
            }
        }
    }
    /* FIN FILTER*/
    
    @GlobalCommand
    @NotifyChange("selectedProveedor")
    public void refreshProveedor(@BindingParam("returnProveedor") Proveedor prov)
    {
        this.newEvent.setProveedor(prov);
        this.newEvent.setFecha(new Date());
//        venta.setVenta(selectedVenta);
        this.selectedProveedor = prov;
//        this.add();  //Que al elegir el cliente vaya directamente a linea de venta
    }
}
