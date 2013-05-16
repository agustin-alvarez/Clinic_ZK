/*
 * FacturaViewModel.java
 * @author SaRCo
 */
package es.clinica.veterinaria.facturas;

import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.user.UserCredentialManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

public class FacturaViewModel {
	
    private FacturaDAO eventDao = new FacturaDAO();
    private Factura selectedEvent, newEvent = new Factura();
    private Cliente selectedCliente;
    private String filterNumero="", filterCliente = "", filterEmpleado =  "", filterFecha = "";
    private ListModelList<Factura> facturas, events;
    Session s = Sessions.getCurrent();
        
    public Factura getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Factura selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public Factura getNewEvent() {
        return newEvent;
    }

    public void setNewEvent(Factura newEvent) {
        this.newEvent = newEvent;
    }

    public List<Factura> getEvents() {
        if(events == null) {
            events = new ListModelList<Factura>();//new ListModelList<Factura>(eventDao.findAll());
        }
        return events;
    }

    public ListModelList<Factura> getFacturas() {
        if(facturas == null) {
            facturas = new ListModelList<Factura>();//new ListModelList<Factura>(eventDao.findAll());
        }
        return facturas;
    }
    
    public String getFilterNumero() {
        return filterNumero;
    }

    @NotifyChange
    public void setFilterNumero(String filterNumero) {
        this.filterNumero = filterNumero;
    }
    
    public String getFilterCliente() {
        return filterCliente;
    }

    @NotifyChange
    public void setFilterCliente(String filterCliente) {
        this.filterCliente = filterCliente;
    }

    public String getFilterEmpleado() {
        return filterEmpleado;
    }

    @NotifyChange
    public void setFilterEmpleado(String filterEmpleado) {
        this.filterEmpleado = filterEmpleado;
    }

    public String getFilterFecha() {
        return filterFecha;
    }

    @NotifyChange
    public void setFilterFecha(String filterFecha) {
        this.filterFecha = filterFecha;
    }
    
    @Command("add")
    @NotifyChange("facturas")
    public void add() {
            this.newEvent.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
            this.newEvent.setFecha(new Date());
            this.newEvent.setCliente(selectedCliente);
            Map map = eventDao.insert(this.newEvent);
            Factura factura = this.newEvent;
            
            this.newEvent = new Factura();
            this.selectedEvent = this.newEvent;
            this.newEvent = new Factura();
            
            int last_id = (Integer) map.get("last_id");
            int last_num = (Integer) map.get("last_num");
            if(last_id != 0) {
                factura.setId(last_id);
                factura.setNumero(last_num);
                this.showFacturaLinea(factura);
            }
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
    public void showFacturaLinea(){
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedFactura", selectedEvent );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("factura.zul", center, map);
    }
    
    public void showFacturaLinea(Factura selFactura){
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedFactura", selFactura);
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("factura.zul", center, map);
    }
    
    @GlobalCommand
    @NotifyChange("selectedCliente")
    public void refreshCliente(@BindingParam("returnCliente") Cliente cli) {
        this.selectedCliente = cli;
    }
    
    // Buscador para hacer el filtrado
    @Command
    @NotifyChange("facturas")
    public void doSearch()
    {
        facturas.clear();
        List<Factura> allEvents;// = eventDao.findAll();

        if((filterNumero   == null || "".equals(filterNumero))    &&
           (filterFecha    == null || "".equals(filterFecha))     &&
           (filterCliente  == null || "".equals(filterCliente))   &&
           (filterEmpleado == null || "".equals(filterEmpleado))) {
                //facturas.addAll(allEvents);
        }
        else
        {
            allEvents = eventDao.findAll();
            for (Iterator<Factura> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents)
            {
                Factura clie = it.next();
                String numero = clie.getNumero() + "";
                if((numero.toLowerCase().startsWith(filterNumero.toLowerCase())   )    &&
                   (clie.getCliente().getFullname().toLowerCase().startsWith(filterCliente.toLowerCase())   )    &&
                   (clie.getEmpleado().getNombre().toLowerCase().startsWith(filterEmpleado.toLowerCase())   )    &&
                    (clie.getFecha().toString().toLowerCase().startsWith(filterFecha.toLowerCase())  )) {
                        facturas.add(clie);
                }
            }
        }
    }
    /* FIN FILTER*/
    
    @Command
    public void crearFactura() throws FileNotFoundException, IOException{
       if(this.selectedEvent == null){
           System.out.println("selectedEvent es nulo");
       }
       else{
           System.out.println("selectedEvent-cliente: "+ this.selectedEvent.getCliente().getNombre());
       }
        this.selectedEvent.crearFactura();
    }
    
    @Command
    public void descargarFactura() throws FileNotFoundException, IOException {
        try{
            this.selectedEvent.descargarFactura();
        }catch (FileNotFoundException e){
            Messagebox.show("Factura no encontrada", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
        }catch (IOException e){
            Messagebox.show("Factura no encontrada", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
        
//    @Command
//    public void descargarFactura() throws FileNotFoundException, IOException {
//        try{
//            Desktop desktop = Executions.getCurrent().getDesktop();
//            StringBuilder sb = new StringBuilder(this.selectedEvent.getRuta());
//
//            sb.delete(0, 14);
//            System.out.println("SB: " + sb);
//            String realpath = desktop.getWebApp().getRealPath("/facturaspdf");
//
//            String ruta = realpath + sb;
//            File f = new File(ruta);
//            byte[] buffer = new byte [(int) f.length()]; 
//            FileInputStream fs = new FileInputStream(f);
//            fs.read( buffer ); 
//            fs.close();
//
//            ByteArrayInputStream is = new ByteArrayInputStream(buffer);
//            AMedia amedia = new AMedia(f, null, null);
//
//            if (is != null){
//                Filedownload.save(amedia);
//            }
//            else{
//                Messagebox.show("Fichero no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//            }
//        }catch (FileNotFoundException e){
//            Messagebox.show("Factura no encontrada", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
//    }
}

