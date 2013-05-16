package es.clinica.veterinaria.clientes;

/**
 * Clase Cliente.
 * 
 * @author Agustin Alvarez
 */

import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.provincias.Provincia;
import java.util.Date;
import java.util.HashSet;

public class Cliente {  
    private int id;
    private String nif;
    private String nombre;
    private String apellidos;
    private String direccion;
    private Poblacion ciudad;
    private Provincia provincia;
    private int telefono;
    private int telefono2;
    private String email;
    private Date fechaalta;
    private int codigopostal;
    private HashSet<Mascota> mascotas = new HashSet<Mascota>(); 

    public Cliente() {}

    public Cliente(int id, String nif, String nombre, String apellidos, Date fecha_alta, String direccion, Poblacion ciudad, Provincia provincia, int telefono, int telefono2, String email, int codigopostal) 
    {
        this.id     = id;
        this.nif        = nif;
        this.nombre     = nombre;
        this.apellidos  = apellidos;
        this.direccion  = direccion;
        this.ciudad     = ciudad;
        this.provincia  = provincia;
        this.telefono   = telefono;
        this.telefono2  = telefono2;
        this.email      = email;
        this.fechaalta = fecha_alta;
        this.codigopostal = codigopostal;
    }
    
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void setNif(String snif){
        this.nif = snif;
    }
    public String getNif() {
        return nif;
    }
    public void setCiudad(Poblacion sciudad){
        this.ciudad = sciudad;
    }
    public Poblacion getCiudad(){
        return ciudad;
    }
    public void setProvincia(Provincia sprovincia){
        this.provincia = sprovincia;
    }
    public Provincia getProvincia(){
        return provincia;
    }
    public void setNombre(String snombre){
        this.nombre = snombre;
    }
    public String getNombre() {
        return nombre;
    }
    public void setApellidos(String sapellido){
        this.apellidos = sapellido;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setDireccion(String sdireccion){
        this.direccion = sdireccion;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setTelefono(int stelefono){
        this.telefono = stelefono;
    }
    public int getTelefono() {
        return telefono;
    }
    public void setTelefono2(int stelefono2){
        this.telefono2 = stelefono2;
    }
    public int getTelefono2() {
        return telefono2;
    }
    public void setEmail(String semail){
        this.email = semail;
    }
    public String getEmail() {
        return email;
    }
    public Date getFechaalta(){
        return fechaalta;
    }
    public void setFechaalta(Date sfecha){
        this.fechaalta = sfecha;
    }
    
    public String getFullname(){
        return this.nombre + " " + this.apellidos;
    }
    
    public void asignarMascota(Mascota mascota){
        if(!mascotas.contains(mascota)){
            mascotas.add(mascota);
        }
    }
    
    public void eliminarMascota(Mascota mascota){
        mascotas.remove(mascota);
    }

    public int getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(int codigopostal) {
        this.codigopostal = codigopostal;
    }
    
    public void mostrarNombre(){
         System.out.print(nombre);
    }
}
