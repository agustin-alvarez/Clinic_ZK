/*
 * Obsoleto
 */
package es.clinica.veterinaria.clientes;

/**
 *
 * @author SaRCo
 */
public class ClienteFilter {
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
