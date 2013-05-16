package es.clinica.veterinaria.poblaciones;

import java.util.Date;

/**
 * Poblacion.
 * 
 * @author Agustin Alvarez
 */

//import java.util.Date;

public class Poblacion {
    private int _id;
    private int _provincia;
    private String _poblacion;

    public Poblacion () {}
    
    public Poblacion(int _id, int _provincia, String _poblacion) {
        this._id = _id;
        this._provincia = _provincia;
        this._poblacion = _poblacion;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }
    
    public String getPoblacion() {
        return _poblacion;
    }

    public void setPoblacion(String _poblacion) {
        this._poblacion = _poblacion;
    }

    public int getProvincia() {
        return _provincia;
    }

    public void setProvincia(int _provincia) {
        this._provincia = _provincia;
    }

   
}
