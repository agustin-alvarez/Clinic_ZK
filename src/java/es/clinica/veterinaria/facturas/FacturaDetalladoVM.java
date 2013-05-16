
package es.clinica.veterinaria.facturas;

import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserCredentialManager;
import es.clinica.veterinaria.user.UserDAO;
import es.clinica.veterinaria.ventas.Venta;
import es.clinica.veterinaria.ventas.VentaDAO;
import es.clinica.veterinaria.ventas_linea.VentaLinea;
import es.clinica.veterinaria.ventas_linea.VentaLineaDAO;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Messagebox;

/**
 * @author SaRCo
 */
public class FacturaDetalladoVM {
    
    private List<VentaLinea> ventalineas = new ArrayList<VentaLinea>();
    private VentaLineaDAO eventDao = new VentaLineaDAO();
    private VentaDAO ventaDao = new VentaDAO();
    private VentaLineaDAO ventalinDao = new VentaLineaDAO();
    private UserDAO userDao = new UserDAO();
    private FacturaDAO facturaDao = new FacturaDAO();
    private VentaLinea newEvent = new VentaLinea();
    private VentaLinea selectedEvent = new VentaLinea();
    private Venta selectedVenta = new Venta();
    private Factura selectedFactura = new Factura();
    private User selectedVet = new User();
    Session s = Sessions.getCurrent();
    private float costeventalineas;
    private float iva;
    
    @Init
    public void initSetup(@ContextParam(ContextType.VIEW) Component view, 
                          @ExecutionArgParam("selectedFactura") Factura selectedFactura) {
        
        Selectors.wireComponents(view, this, false);
        this.selectedFactura = selectedFactura;
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

    public Factura getSelectedFactura() {
        return selectedFactura;
    }

    public void setSelectedFactura(Factura selectedFactura) {
        this.selectedFactura = selectedFactura;
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
            return eventDao.findAll("SELECT zk_venta_linea.id, " +
                                "zk_venta_linea.id_venta, " + 
                                "zk_venta_linea.id_producto, "+
                                "zk_venta_linea.cantidad, "+
                                "zk_venta_linea.fecha, "+
                                "zk_venta_linea.tipo, "+
                                "zk_venta_linea.pvp, "+
                                "zk_venta_linea.iva, "+
                                "zk_venta.id, "+
                                "zk_factura.id "+
                           "FROM zk_venta_linea " +
                                " INNER JOIN zk_venta ON zk_venta.id = zk_venta_linea.id_venta " +
                                " INNER JOIN zk_factura ON zk_factura.id = zk_venta.factura " +
                                " WHERE zk_factura.id = " + this.selectedFactura.getId()   );
    }

    public void setVentalineas(List<VentaLinea> ventalineas) {
        this.ventalineas = ventalineas;
    }

    public List<User> getVeterinarios() {
        return userDao.findAll("select * from zk_usuario where tipo = 2");
    }

    @Command("updatenumero")
    @NotifyChange("ventalineas")
    public void updatenumero(){
        String anno = new SimpleDateFormat("yyyy").format(this.selectedFactura.getFecha());
        List<Factura> list = facturaDao.findAll("SELECT * FROM zk_factura WHERE year(fecha)='"+ anno +"' AND numero=" + this.selectedFactura.getNumero());
        if(list.size() > 0) {
            Messagebox.show("Ya existe una Factura con número "+ this.selectedFactura.getNumero()+" para el año " + anno, 
                            "Aviso", Messagebox.OK, Messagebox.ERROR);
        }
        else{
            this.selectedFactura.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
            facturaDao.updatenumero(this.selectedFactura);
        }
    }
    
    @Command("updatefecha")
    @NotifyChange("ventalineas")
    public void updatefecha(){
        this.selectedFactura.setEmpleado(UserCredentialManager.getIntance(s).getUser() );
        facturaDao.updatefecha(this.selectedFactura);
    }
    
    @Command("delete")
    @NotifyChange({"events", "ventalineas", "costeventalineas", "iva"})
    public void delete() {
        //shouldn't be able to delete with selectedEvent being null anyway
        //unless trying to hack the system, so just ignore the request
        if(this.selectedEvent != null) {
            int id = ventalinDao.find_idVenta(this.selectedEvent);
            
            List<Venta> list = new VentaDAO().findAll("select * from zk_venta where id=" + id);
            if(list.size() == 1) {
                Venta venta = list.get(0);
                venta.setFactura(null);
                venta.setFacturado(false);
                ventaDao.updateFactura(venta);
                this.selectedEvent = null;
            }
        }
    }
    
    @Command
    public void addProducto()
    {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("selectedCliente", selectedVenta );
        Executions.createComponents("../mascotas/mascota-asignar.zul", null, map);
    }
    
    
    //Recoge las ventas seleccionadas desde 
    @GlobalCommand
    @NotifyChange({"selectedEvent", "ventalineas", "costeventalineas", "iva"})
    public void refreshVentas(@BindingParam("returnVenta") Set<Venta> items)
    {
        System.out.println("Tamaño items: " + items.size());
        Iterator <Venta> it = items.iterator();
        while(it.hasNext()){
            Venta item = it.next();
            item.setFacturado(true);
            item.setFactura(selectedFactura);
            this.selectedFactura.asignarVenta(item);
            
            ventaDao.updateFactura(item);
            
            Iterator <VentaLinea> linea = item.getVenta_lineas().iterator();
            while(linea.hasNext()){
                ventalineas.add(linea.next());
            }
        }
    }
    
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

    public float getIva() {
        iva = 0;
        float costetotal, costeiva;
        Iterator <VentaLinea> it = this.getVentalineas().iterator();
        
        while(it.hasNext()){
            costetotal = 0; costeiva = 0;
            VentaLinea ln = it.next();
            costetotal = (float) (ln.getPvp() * ln.getCantidad() * (1+(ln.getIva() * 0.01)));
            costeiva = (float) (ln.getPvp() * ln.getCantidad() );
            iva += costetotal - costeiva;
        }
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    @Command
    public void crearFactura() throws FileNotFoundException, IOException{
        this.selectedFactura.crearFactura();
    }
    
    @Command
    public void descargarFactura() throws FileNotFoundException, IOException {
        try{
            this.selectedFactura.descargarFactura();
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
//            StringBuilder sb = new StringBuilder(this.selectedFactura.getRuta());
//
//            sb.delete(0, 14);
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
//                Messagebox.show("Fichero no generado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//            }
//        }catch (FileNotFoundException e){
//            Messagebox.show("Factura no generada", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
//        }
//    }
}
