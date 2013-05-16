
package es.clinica.veterinaria.ventas;

import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.facturas.Factura;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.ventas_linea.VentaLinea;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

/**
 * @author SaRCo
 */
public class Venta {
    
    private int _id;
    private Date _fecha;
    private Time _hora;
    private Cliente _cliente;
    private User _vendedor;
    private User _veterinario;
    private HashSet <VentaLinea>_venta_lineas = new HashSet<VentaLinea>();
    private String _albaran;
    private Factura _factura;
    private boolean _facturado;
    
    public Venta () {}

    public Venta(int _id, Date _fecha, Time _hora, Cliente _cliente, 
            User _vendedor, User _veterinario , String _albaran, Factura _factura, boolean _facturado) {
        this._id = _id;
        this._fecha = _fecha;
        this._hora = _hora;
        this._cliente = _cliente;
        this._vendedor = _vendedor;
        this._veterinario = _veterinario;
        this._albaran = _albaran;
        this._factura = _factura;
        this._facturado = _facturado;
    }

    public Cliente getCliente() {
        return _cliente;
    }

    public void setCliente(Cliente _cliente) {
        this._cliente = _cliente;
    }

    public User getVendedor() {
        return _vendedor;
    }

    public void setVendedor(User _vendedor) {
        this._vendedor = _vendedor;
    }
    
    public Date getFecha() {
        return _fecha;
    }

    public void setFecha(Date _fecha) {
        this._fecha = _fecha;
    }

    public Time getHora() {
        return _hora;
    }

    public void setHora(Time _hora) {
        this._hora = _hora;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public User getVeterinario() {
        return _veterinario;
    }

    public void setVeterinario(User _veterinario) {
        this._veterinario = _veterinario;
    }
    
    public HashSet<VentaLinea> getVenta_lineas() {
        return _venta_lineas;
    }

    public void setVenta_lineas(HashSet<VentaLinea> _venta_lineas) {
        this._venta_lineas = _venta_lineas;
    }

    public boolean asignarVentaLinea(VentaLinea ventalinea) {
        if(!_venta_lineas.contains(ventalinea)) {
                _venta_lineas.add(ventalinea);
                return true;
            }
        else {
            return false;
        }
    }
    
    public void eliminarVentaLinea(VentaLinea ventalinea) {
        _venta_lineas.remove(ventalinea);
    }
    
//    public float getCoste() {
//        float coste_total = 0;
//        Iterator <VentaLinea> it = _venta_lineas.iterator();
//        while(it.hasNext()){
//            VentaLinea ln = it.next();
//            if(ln.getTipo() == 1) {
//                coste_total += ln.getProducto().getPvp() * ln.getCantidad() * (1+(ln.getProducto().getIva().getValor() * 0.01));
//            }
//            else  if(ln.getTipo() == 2) {
//                coste_total += ln.getServicio().getPrecio() * ln.getCantidad()* (1+(ln.getServicio().getIva().getValor()* 0.01));
//            }
//        }
//        return coste_total;
//    }
    
    //Coste total + iva
    public float getCoste() {
        float coste_total = 0;
        Iterator <VentaLinea> it = _venta_lineas.iterator();
        while(it.hasNext()){
            VentaLinea ln = it.next();
            coste_total += ln.getPvp() * ln.getCantidad() * (1+(ln.getIva() * 0.01));
        }
        return coste_total;
    }
    
    public float getIvas() {
        float coste_total = 0;
        Iterator <VentaLinea> it = _venta_lineas.iterator();
        while(it.hasNext()){
            VentaLinea ln = it.next();
            coste_total += (ln.getPvp() * ln.getCantidad() * (1+(ln.getIva() * 0.01))) - (ln.getPvp() * ln.getCantidad());
        }
        return coste_total;
    }
    
    public float getCostesinIva() {
//        return getCoste() - getIvas();
        float coste_total = 0;
        Iterator <VentaLinea> it = _venta_lineas.iterator();
        while(it.hasNext()){
            VentaLinea ln = it.next();
            coste_total += (ln.getPvp() * ln.getCantidad());
        }
        return coste_total;
    }
    
    public int getNum_articulos(){
        int num = 0;
        Iterator <VentaLinea> it = _venta_lineas.iterator();
        while(it.hasNext()){
            VentaLinea ln = it.next();
            num +=  ln.getCantidad();
        }
        return num;
    }
    
    public String getAlbaran() {
        return _albaran;
    }

    public void setAlbaran(String _albaran) {
        this._albaran = _albaran;
    }

    public Factura getFactura() {
        return _factura;
    }

    public void setFactura(Factura _factura) {
        this._factura = _factura;
    }

    public boolean isFacturado() {
        return _facturado;
    }

    public void setFacturado(boolean _facturado) {
        this._facturado = _facturado;
    }
    
    public void descargarAlbaran() throws FileNotFoundException, IOException {
        try{
            if(this.getAlbaran() == null){
                Messagebox.show("Venta: Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
            else{
//                File f = new File(this.getAlbaran() );
//                byte[] buffer = new byte[ (int) f.length() ];
//                FileInputStream fs = new FileInputStream(f);
//                fs.read( buffer ); 
//                fs.close();
//
//                ByteArrayInputStream is = new ByteArrayInputStream(buffer);
//                AMedia amedia = new AMedia("albaran-" + this.getFecha() + "-" + this.getId(), "pdf", "application/pdf", is);
                Desktop desktop = Executions.getCurrent().getDesktop();
                StringBuilder sb = new StringBuilder(this.getAlbaran());
                System.out.println("SB: " + sb);
                
                sb.delete(0, 12);
                System.out.println("SB despues: " + sb);
                String realpath = desktop.getWebApp().getRealPath("/albaranes");

                String ruta = realpath + sb;
                File f = new File(ruta);
                byte[] buffer = new byte [(int) f.length()]; 
                FileInputStream fs = new FileInputStream(f);
                fs.read( buffer ); 
                fs.close();

                ByteArrayInputStream is = new ByteArrayInputStream(buffer);
                AMedia amedia = new AMedia(f, null, null);

                if (is != null){
                    Filedownload.save(amedia);
                }
                else{
                    Messagebox.show("is = null : Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
                }
            }
        }catch (FileNotFoundException e){
            Messagebox.show("FileNotFoundException: Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
        }catch (IOException e) {
            Messagebox.show("IOException: Albarán no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        
    }
}
