/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.ficheros;

import java.util.Date;

/**
 *
 * @author SaRCo
 */
public class Fichero {
    private int _id;
    private int _id_externo;
    private int _tipo;
    private String _ruta;
    private Date _fecha;

    public Fichero() {}
    
    public Fichero(int _id, int _id_externo, int _tipo, String _ruta, Date _fecha) {
        this._id = _id;
        this._id_externo = _id_externo;
        this._tipo = _tipo;
        this._ruta = _ruta;
        this._fecha = _fecha;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getId_externo() {
        return _id_externo;
    }

    public void setId_externo(int _id_externo) {
        this._id_externo = _id_externo;
    }

    public int getTipo() {
        return _tipo;
    }

    public void setTipo(int _tipo) {
        this._tipo = _tipo;
    }

    public String getRuta() {
        return _ruta;
    }

    public void setRuta(String _ruta) {
        this._ruta = _ruta;
    }
    
    public String getNombre() {
        StringBuilder sb = new StringBuilder(this.getRuta());
        String recortar = "../uploads/historiales/";
        sb.delete(0, 23);
        return sb + "";
    }

    public Date getFecha() {
        return _fecha;
    }

    public void setFecha(Date _fecha) {
        this._fecha = _fecha;
    }
    
    public String getExtension(){
        String extension[] = {".jpg", ".jpeg" , ".gif" , ".png", ".pdf", ".doc", ".docx", ".xls", "xlsx"};
        
        if(this._ruta.toLowerCase().endsWith(extension[0])){
            return _ruta;
        }
        else if(this._ruta.toLowerCase().endsWith(extension[1])){
            return _ruta;
        }
        else if(this._ruta.toLowerCase().endsWith(extension[2])){
            return _ruta;
        }
        else if(this._ruta.toLowerCase().endsWith(extension[3])){
            return _ruta;
        }
        else if(this._ruta.toLowerCase().endsWith(extension[4])){//PDF
            return "../images/PDF.png";
        }
        else if(this._ruta.toLowerCase().endsWith(extension[5])){ //DOC
            return "../images/DOC.png";
        }
        else if(this._ruta.toLowerCase().endsWith(extension[6])){ //DOCX
            return "../images/DOC.png";
        }
        else if(this._ruta.toLowerCase().endsWith(extension[7])){ //XLS
            return "../images/XLS.png";
        }
        else if(this._ruta.toLowerCase().endsWith(extension[8])){ //XLS
            return "../images/XLS.png";
        }
        else {  //DEFAULT
            return "../images/DEFAULT.png";
        }
    }
}