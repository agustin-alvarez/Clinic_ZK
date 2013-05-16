package es.clinica.veterinaria.provincias;

import java.util.Date;

/**
 * Provincia.
 * 
 * @author Agustin Alvarez
 */

//import java.util.Date;

public class Provincia {
    private int _id;
    private String _provincia;

    public Provincia () {}
    
    public Provincia(int _id, String _provincia) {
        this._id = _id;
        this._provincia = _provincia;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getProvincia() {
        return _provincia;
    }

    public void setProvincia(String _provincia) {
        this._provincia = _provincia;
    }

   
}
