/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.items;

import es.clinica.veterinaria.iva.Iva;
import java.util.Date;

/**
 *
 * @author SaRCo
 */
public class Item {

    private int _id;
    private String _codigo;
    private String _nombre;
    private String _descripcion;
    private float _precio;
    private Iva _iva;
    private String _observaciones;
    private Date _fecha_alta;
    
    public Item() {}
    
    public Item(int _id, String _codigo, String _nombre, String _descripcion, float _precio, Iva _iva , String _observaciones, Date _fecha_alta) {
        this._id = _id;
        this._codigo = _codigo;
        this._nombre = _nombre;
        this._descripcion = _descripcion;
        this._observaciones = _observaciones;
        this._precio = _precio;
        this._iva = _iva;
        this._fecha_alta = _fecha_alta;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getCodigo() {
        return _codigo;
    }

    public void setCodigo(String _codigo) {
        this._codigo = _codigo;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _servicio) {
        this._nombre = _servicio;
    }

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public float getPrecio() {
        return _precio;
    }

    public void setPrecio(float _precio) {
        this._precio = _precio;
    }

    public Iva getIva() {
        return _iva;
    }

    public void setIva(Iva _iva) {
        this._iva = _iva;
    }

    public String getObservaciones() {
        return _observaciones;
    }

    public void setObservaciones(String _observaciones) {
        this._observaciones = _observaciones;
    }

    public Date getFecha_alta() {
        return _fecha_alta;
    }

    public void setFecha_alta(Date _fecha_alta) {
        this._fecha_alta = _fecha_alta;
    }
    
}
