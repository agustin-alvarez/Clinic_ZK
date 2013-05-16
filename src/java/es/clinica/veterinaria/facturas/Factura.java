
package es.clinica.veterinaria.facturas;

import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.ventas.Venta;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class Factura {
    private int _id;
    private int _numero;
    private Date _fecha;
    private Cliente _cliente;
    private User _empleado;
    private String _ruta;
    private HashSet <Venta>_ventas = new HashSet<Venta>();
    
    public Factura() {}

    public Factura(int _id, int _numero, Date _fecha, Cliente _cliente, User _empleado, String _ruta) {
        this._id = _id;
        this._numero = _numero;
        this._fecha = _fecha;
        this._cliente = _cliente;
        this._empleado = _empleado;
        this._ruta = _ruta;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getNumero() {
        return _numero;
    }

    public void setNumero(int _numero) {
        this._numero = _numero;
    }

    public Date getFecha() {
        return _fecha;
    }

    public void setFecha(Date _fecha) {
        this._fecha = _fecha;
    }

    public Cliente getCliente() {
        return _cliente;
    }

    public void setCliente(Cliente _cliente) {
        this._cliente = _cliente;
    }

    public User getEmpleado() {
        return _empleado;
    }

    public void setEmpleado(User _empleado) {
        this._empleado = _empleado;
    }

    public String getRuta() {
        return _ruta;
    }

    public void setRuta(String _ruta) {
        this._ruta = _ruta;
    }

    public HashSet<Venta> getVentas() {
        return _ventas;
    }

    public void setVentas(HashSet<Venta> _ventas) {
        this._ventas = _ventas;
    }

    public boolean asignarVenta(Venta venta) {
        if(!_ventas.contains(venta)) {
                _ventas.add(venta);
                return true;
            }
        else {
            return false;
        }
    }
    
    public void eliminarVentaLinea(Venta venta) {
        _ventas.remove(venta);
    }
    
    public float getCoste(){
        return getCostetotal() - getIvas();
    }
    
    public float getCostetotal(){
       float coste = 0;
        Iterator <Venta> it = _ventas.iterator();
        while(it.hasNext()){
            Venta ln = it.next();
            coste += ln.getCoste();
        }
        return coste;
    }
    
    public float getIvas(){
       float coste = 0;
        Iterator <Venta> it = _ventas.iterator();
        while(it.hasNext()){
            Venta ln = it.next();
            coste += ln.getIvas();
        }
        return coste;
    }
    
    public void crearFactura() throws FileNotFoundException, IOException{
        FacturaPdf facturapdf = new FacturaPdf();
        facturapdf.setFactura(this);
        facturapdf.createPdf();
        descargarFactura();
    }
    
    public void descargarFactura() throws FileNotFoundException, IOException {
        try{
            Desktop desktop = Executions.getCurrent().getDesktop();
            StringBuilder sb = new StringBuilder(this.getRuta());

            sb.delete(0, 14);
            System.out.println("SB: " + sb);
            String realpath = desktop.getWebApp().getRealPath("/facturaspdf");

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
                Messagebox.show("Fichero no encontrado", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
            }
        }catch (FileNotFoundException e){
            Messagebox.show("Factura no encontrada", "Aviso", Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
    
}
