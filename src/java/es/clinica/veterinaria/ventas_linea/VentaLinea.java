/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.ventas_linea;

import es.clinica.veterinaria.productos.Producto;
import es.clinica.veterinaria.servicios.Servicio;
import es.clinica.veterinaria.ventas.Venta;
import java.sql.Time;
import java.util.Date;

/**
 *
 * @author SaRCo
 */
public class VentaLinea {
    
    private int _id;
    private Venta _venta;
    private Producto _producto;
    private int _cantidad;
    private Date _fecha;
    private Time _hora;
    private Servicio _servicio;
    private int _tipo; //1)Producto 2)Servicio
    private float _pvp;
    private String _descripcion;
    private int _iva;

    public VentaLinea () {}

    public VentaLinea(int _id, Venta _venta, Producto _producto, 
                      Servicio _servicio, int _cantidad, Date _fecha, 
                      Time _hora, int _tipo, float _pvp, String _descripcion, int _iva) {
        this._id = _id;
        this._venta = _venta;
        this._producto = _producto;
        this._cantidad = _cantidad;
        this._fecha = _fecha;
        this._hora = _hora;
        this._servicio = _servicio;
        this._tipo = _tipo;
        this._pvp = _pvp;
        this._descripcion = _descripcion;
        this._iva = _iva;
    }
    
    public int getCantidad() {
        return _cantidad;
    }

    public void setCantidad(int _cantidad) {
        this._cantidad = _cantidad;
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

    public Producto getProducto() {
        return _producto;
    }

    public void setProducto(Producto _producto) {
        this._producto = _producto;
    }

    public Venta getVenta() {
        return _venta;
    }

    public void setVenta(Venta _venta) {
        this._venta = _venta;
    }
    
    public float getPrecio(){
        float pt = 0;
        if(this._tipo == 1) {
            pt = this.getProducto().getPrecio() ;
        }
        else if(this._tipo == 2) {
            pt = this.getServicio().getPrecio() ;
        }
        return pt;
    }
    
//    public float getPreciototal(){
//        float pt = 0;
//        if(this._tipo == 1) {
//            pt = (float) (this.getProducto().getPvp() * this._cantidad * (1+(this.getProducto().getIva().getValor() * 0.01))) ;
//            System.out.println("IVA: " + (1+(this.getProducto().getIva().getValor()*0.01)));
//        }
//        else if(this._tipo == 2) {
//            pt = (float) (this.getServicio().getPrecio() * this._cantidad * (1+(this.getServicio().getIva().getValor() * 0.01)));
//            System.out.println("IVA: " + (1+(this.getServicio().getIva().getValor() * 0.01)));
//        }
//        return pt;
//    }
    public float getPreciototal(){
        float pt = 0;
            pt = (float) (this.getPvp() * this._cantidad * (1+(this.getIva() * 0.01))) ;
        return pt;
    }
    
     public float getPreciototalNoIVA(){
//        float pt = 0;
            return (float) (this.getPvp() * this._cantidad) ;
//        return pt;
    }

    public Servicio getServicio() {
        return _servicio;
    }

    public void setServicio(Servicio _servicio) {
        this._servicio = _servicio;
    }

    public int getTipo() {
        return _tipo;
    }

    public void setTipo(int _tipo) {
        this._tipo = _tipo;
    }
    
    public String getNombre(){
        String nombre = "";
        if(this._tipo == 1) {
            nombre = this.getProducto().getNombre() ;
        }
        else if(this._tipo == 2) {
            nombre = this.getServicio().getNombre() ;
        }
        return nombre;
    }
    
    public String getCodigo(){
        String nombre = "";
        if(this._tipo == 1) {
            nombre = this.getProducto().getCodigo() ;
        }
        else if(this._tipo == 2) {
            nombre = this.getServicio().getCodigo() ;
        }
        return nombre;
    }

    public void setDescripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }
    
    public String getDescripcion(){
        String nombre = "";
        if(this._tipo == 1) {
            nombre = this.getProducto().getDescripcion() ;
        }
        else if(this._tipo == 2) {
            nombre = this.getServicio().getDescripcion() ;
        }
        return nombre;
    }

    public void setPvp(float _pvp) {
        this._pvp = _pvp;
    }
    
    public float getPvp(){
        if(_pvp != 0){
            return _pvp;
        }
        else{
            float pvp = 0;
            if(this._tipo == 1) {
                pvp = this.getProducto().getPvp() ;
            }
            else if(this._tipo == 2) {
                pvp = this.getServicio().getPrecio() ;
            }
            return pvp;
        }
    }

    public int getIva() {
        if(_iva != 0){
            return _iva;
        }else{
            int iva = 0;
            if(this._tipo == 1) {
                iva = this.getProducto().getIva().getValor() ;
            }
            else if(this._tipo == 2) {
                iva = this.getServicio().getIva().getValor() ;
            }
            return iva;
        }
    }

    public void setIva(int _iva) {
        this._iva = _iva;
    }
    
}
