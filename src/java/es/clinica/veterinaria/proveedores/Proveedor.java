package es.clinica.veterinaria.proveedores;

/**
 * Event.
 * 
 * @author Agustin Alvarez
 */

import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.provincias.Provincia;
import java.util.Date;

public class Proveedor {
    private int id;
    private String nif;
    private String nombre;
    private String direccion;
    private Poblacion poblacion;
    private Provincia provincia;
    private String telefono;
    private String telefono2;
    private String movil;
    private String fax;
    private String email;
    private Date fecha_alta;
    private Date fecha_baja;
    private String observaciones;
    private boolean activo;
    private String contacto;

    public Proveedor() {}

    public Proveedor(int id, String nif, String nombre, String direccion, 
                     Poblacion poblacion, Provincia provincia, String telefono, 
                     String telefono2, String movil, String fax, String email, 
                     Date fecha_alta, Date fecha_baja, String observaciones, 
                     boolean activo, String contacto) {
        this.id = id;
        this.nif = nif;
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.provincia = provincia;
        this.telefono = telefono;
        this.telefono2 = telefono2;
        this.movil = movil;
        this.fax = fax;
        this.email = email;
        this.fecha_alta = fecha_alta;
        this.fecha_baja = fecha_baja;
        this.observaciones = observaciones;
        this.activo = activo;
        this.contacto = contacto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Poblacion getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Poblacion poblacion) {
        this.poblacion = poblacion;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
}
