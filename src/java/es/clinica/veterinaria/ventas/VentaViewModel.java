/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.ventas;
import es.clinica.veterinaria.albaranes.AlbaranPdf;
import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.user.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.*;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author SaRCo
 */
public class VentaViewModel {

    private VentaDAO eventDao = new VentaDAO();
    private Venta selectedEvent, newEvent = new Venta();
    private Cliente selectedCliente;
    private String filterCliente = "",
                   filterEmpleado =  "",
                   filterFecha = "", 
                   filterFacturado = "", 
                   filterVeterinario = "", 
                   filterHora = "";
    private ListModelList<Venta> ventas;
    Session s = Sessions.getCurrent();
    private Media albaran;
    
    public Venta getSelectedEvent() {
            return selectedEvent;
    }

    public void setSelectedEvent(Venta selectedEvent) {
            this.selectedEvent = selectedEvent;
    }

    public Cliente getSelectedCliente() {
        return selectedCliente;
    }

    public void setSelectedCliente(Cliente selectedCliente) {
        this.selectedCliente = selectedCliente;
    }

    public Venta getNewEvent() {
            return newEvent;
    }

    public void setNewEvent(Venta newEvent) {
            this.newEvent = newEvent;
    }

    public List<Venta> getEvents() {
            return eventDao.findAll();
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

    public String getFilterHora() {
        return filterHora;
    }

    @NotifyChange
    public void setFilterHora(String filterHora) {
        this.filterHora = filterHora;
    }
    
    public String getFilterFacturado() {
        return filterFacturado;
    }
    
    @NotifyChange
    public void setFilterFacturado(String filterFacturado) {
        this.filterFacturado = filterFacturado;
    }

    public String getFilterVeterinario() {
        return filterVeterinario;
    }

    @NotifyChange
    public void setFilterVeterinario(String filterVeterinario) {
        this.filterVeterinario = filterVeterinario;
    }
    
    
    @NotifyChange("ventas")
    public ListModelList<Venta> getVentas() {
        if(ventas == null) {
            ventas = new ListModelList<Venta>();//new ListModelList<Venta>(eventDao.findAll());
        }
        return ventas;
    }
    
    @Command("add")
    @NotifyChange("ventas")
    public void add() {
            this.newEvent.setVendedor(UserCredentialManager.getIntance(s).getUser() );
            this.newEvent.setFecha(new Date());
            int id = eventDao.insert(this.newEvent);
            Venta venta = this.newEvent;
            this.newEvent = new Venta();
            if(id != 0) {
                venta.setId(id);
                this.showVentaLinea(venta);
            }
    }
        
    @Command("update")
    @NotifyChange("ventas")
    public void update() {
            eventDao.update(this.selectedEvent);
    }

    @Command("delete")
    @NotifyChange({"ventas", "selectedEvent"})
    public void delete() {
            //shouldn't be able to delete with selectedEvent being null anyway
            //unless trying to hack the system, so just ignore the request
            if(this.selectedEvent != null) {
                    eventDao.delete(this.selectedEvent);
                    this.selectedEvent = null;
            }
    }
    
    @Command
    public void showVentaLinea()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        if(selectedEvent != null) {
            map.put("selectedVenta", selectedEvent );
        }
        else {
            map.put("selectedVenta", newEvent);
        }
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("../ventas/venta-lineas.zul", center, map);
    }
    
    public void showVentaLinea(Venta venta)
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedVenta", venta );
        //Window win = (Window) page.getFellow("win");
        Borderlayout bl = (Borderlayout) Path.getComponent("/main");
        Center center = bl.getCenter();
        center.getChildren().clear();
        Executions.createComponents("../ventas/venta-lineas.zul", center, map);
    }
    
    @GlobalCommand
    @NotifyChange("selectedCliente")
    public void refreshCliente(@BindingParam("returnCliente") Cliente cli)
    {
        
        this.newEvent.setCliente(cli);
        this.newEvent.setFecha(new Date());
//        venta.setVenta(selectedVenta);
        this.selectedCliente = cli;
//        this.add();  //Que al elegir el cliente vaya directamente a linea de venta
    }
    
    // Buscador para hacer el filtrado
    @Command
    @NotifyChange("ventas")
    public void doSearch()
    {
        ventas.clear();
        List<Venta> allEvents; // = eventDao.findAll();

        if((filterHora         == null || "".equals(filterHora))         &&
           (filterFacturado    == null || "".equals(filterFacturado))    &&
           (filterVeterinario  == null || "".equals(filterVeterinario))  &&
           (filterFecha        == null || "".equals(filterFecha))        &&
           (filterCliente      == null || "".equals(filterCliente))      &&
           (filterEmpleado     == null || "".equals(filterEmpleado))) {
                //ventas.addAll(allEvents); //Para mostrar todo
        }
        else
        {
            allEvents = eventDao.findAll(); //Quitar para mostrar todo
            for (Iterator<Venta> it = allEvents.iterator(); it.hasNext();) //for(Mascota masc : allEvents)
            {
                String facturado, veterinario, cliente;
                Venta clie = it.next();
                if(clie.isFacturado()){
                    facturado = "Si";
                }
                else{
                    facturado = "No";
                }
                if(clie.getVeterinario() == null){
                    veterinario = "";
                }
                else{
                    veterinario = clie.getVeterinario().getNombre();
                }
                
                if(clie.getCliente().getFullname() == null){
                    cliente = clie.getCliente().getNombre();
                }
                else{
                    cliente = clie.getCliente().getFullname();
                }
                
                
//                System.out.println("Veterinario:" + veterinario);
//                System.out.println("Facturado:" + facturado);
//                System.out.println("Cliente:" + cliente);
//                System.out.println("Vendedor:" + clie.getVendedor().getNombre());
//                System.out.println("Hora:" + clie.getHora().toString());
//                System.out.println("Fecha:" + clie.getFecha().toString());
                
                if( (veterinario.toLowerCase().startsWith(filterVeterinario.toLowerCase())   )    &&
                    (facturado.toLowerCase().startsWith(filterFacturado.toLowerCase())  )   &&
                    (cliente.toLowerCase().startsWith(filterCliente.toLowerCase())   )    &&
                    (clie.getVendedor().getNombre().toLowerCase().startsWith(filterEmpleado.toLowerCase())  )   &&
                    (clie.getHora().toString().toLowerCase().startsWith(filterHora.toLowerCase())  )   &&
                    (clie.getFecha().toString().toLowerCase().startsWith(filterFecha.toLowerCase())  )) {
                        
                        ventas.add(clie);
                }
            }
        }
    }
    /* FIN FILTER*/
    
    @Command
    @NotifyChange("ventas")
    public void crearAlbaran() throws FileNotFoundException, IOException{
        AlbaranPdf albaran = new AlbaranPdf();
        albaran.setVenta(this.selectedEvent);
        albaran.createPdf();
        this.selectedEvent.descargarAlbaran();
//        FirstPdf.main();
//        MyFirstTable.main();
    }
    
    @Command
    public void descargarAlbaran() throws FileNotFoundException, IOException {
        try{
            this.selectedEvent.descargarAlbaran();
        }catch (FileNotFoundException e){
            Messagebox.show("Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
        }catch (IOException e) {
            Messagebox.show("Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        
//        try{
//            if(this.selectedEvent.getAlbaran() == null){
//                Messagebox.show("Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//            }
//            else{
//                File f = new File(this.selectedEvent.getAlbaran() );
//                byte[] buffer = new byte[ (int) f.length() ];
//                FileInputStream fs = new FileInputStream(f);
//                fs.read( buffer ); 
//                fs.close();
//
//                ByteArrayInputStream is = new ByteArrayInputStream(buffer);
//                AMedia amedia =new AMedia("albaran-" + this.selectedEvent.getFecha() + "-" + this.selectedEvent.getId(), "pdf", "application/pdf", is);
//
//                if (is != null){
//                    Filedownload.save(amedia);
//                }
//                else{
//                    Messagebox.show("Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//                }
//            }
//        }catch (FileNotFoundException e){
//            Messagebox.show("Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//        }catch (IOException e) {
//            Messagebox.show("Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
//        
    }
    
}
