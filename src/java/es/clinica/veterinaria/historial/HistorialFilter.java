/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.historial;

/**
 *
 * @author SaRCo
 */
public class HistorialFilter {
    private String nif = "", nombre="", apellidos="";
    
    public String getNif() {
        return nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif.trim().toLowerCase();
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre.trim().toLowerCase();
    }
    
    public String getApellidos(){
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos.trim().toLowerCase();
    }
}
