/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.razas;

import es.clinica.veterinaria.especies.Especie;

/**
 *
 * @author SaRCo
 */
public class Raza {
    private int _id;
    private Especie _especie;
    private String _raza;
    
    public Raza() {}
    
    public Raza(int _id, Especie _especie, String _raza) {
        this._id = _id;
        this._especie = _especie;
        this._raza = _raza;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getRaza() {
        return _raza;
    }

    public void setRaza(String _raza) {
        this._raza = _raza;
    }

    public Especie getEspecie() {
        return _especie;
    }

    public void setEspecie(Especie _especie) {
        this._especie = _especie;
    }
    
    
}
