/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.especies;

import es.clinica.veterinaria.razas.Raza;
import java.util.HashSet;

/**
 *
 * @author SaRCo
 */
public class Especie {
    private int _id;
    private String _especie;
    private HashSet<Raza> razas = new HashSet<Raza>();

    public Especie() {}
    
    public Especie(int _id, String _especie) {
        this._id = _id;
        this._especie = _especie;
    }

    public String getEspecie() {
        return _especie;
    }

    public void setEspecie(String _especie) {
        this._especie = _especie;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }
    
    public void asignarRaza(Raza raza){
        if(!razas.contains(raza))
            razas.add(raza);
    }
    
    public void eliminarRaza(Raza raza){
        razas.remove(raza);
    }
    
    public HashSet<Raza> getRazas() {
        return razas;
    }

    public void setRazas(HashSet<Raza> razas) {
        this.razas = razas;
    }
}
