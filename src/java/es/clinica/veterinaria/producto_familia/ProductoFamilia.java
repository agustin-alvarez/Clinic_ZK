/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.producto_familia;

/**
 *
 * @author SaRCo
 */
public class ProductoFamilia {
    
    private int _id;
    private String _nombre;
    private String _descripcion;
    private boolean _tratamiento;

    public ProductoFamilia() {}
    
    public ProductoFamilia(int _id, String _nombre, String _descripcion, boolean _tratamiento) {
        this._id = _id;
        this._nombre = _nombre;
        this._descripcion = _descripcion;
        this._tratamiento = _tratamiento;
    }

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public boolean isTratamiento() {
        return _tratamiento;
    }

    public void setTratamiento(boolean _tratamiento) {
        this._tratamiento = _tratamiento;
    }
}
