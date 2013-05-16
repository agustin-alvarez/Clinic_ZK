package es.clinica.veterinaria.vacunas;

import es.clinica.veterinaria.especies.Especie;
import java.util.Date;

/**
 * @author SaRCo
 */
public class Vacuna {
    private int _id;
    private String _nombre;
    private String _descripcion;
    private Especie _especie;
    private int _dias;
    private Date _fecha;
    
    public Vacuna() {}

    public Vacuna(int _id, String _nombre, String _descripcion, Especie _especie, int _dias, Date _fecha) {
        this._id = _id;
        this._nombre = _nombre;
        this._descripcion = _descripcion;
        this._especie = _especie;
        this._dias = _dias;
        this._fecha = _fecha;
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

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this._descripcion = _descripcion;
    }

    public Especie getEspecie() {
        return _especie;
    }

    public void setEspecie(Especie _especie) {
        this._especie = _especie;
    }

    public int getDias() {
        return _dias;
    }

    public void setDias(int _dias) {
        this._dias = _dias;
    }

    public Date getFecha() {
        return _fecha;
    }

    public void setFecha(Date _fecha) {
        this._fecha = _fecha;
    }
  
}
