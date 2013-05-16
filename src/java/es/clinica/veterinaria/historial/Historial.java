package es.clinica.veterinaria.historial;

/**
 * Clase Historial.
 * 
 * @author Agustin Alvarez
 */

import es.clinica.veterinaria.citas.Cita;
import es.clinica.veterinaria.ficheros.Fichero;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.pesos.Peso;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.ventas.Venta;
import java.sql.Time;
import java.util.Date;
import java.util.HashSet;

public class Historial {  
    private int _id;
    private Date _fecha;
    private Time _hora;
    private Mascota _mascota;
    private User _id_veterinario;
    private int _tipo;
    private Venta _venta;
    private String _anamnesis;
    private String _diagnostico;
    private String _tratamiento;
    private Peso _peso;
    private HashSet<Fichero> _ficheros = new HashSet<Fichero>();
    
    public Historial() {}

    public Historial(int _id, Date _fecha, Time _hora, Mascota _id_mascota, 
                     User _id_veterinario, int _tipo, Venta _id_venta, String _anamnesis,
                     String _diagnostico, String _tratamiento, Peso _peso) {
        this._id = _id;
        this._fecha = _fecha;
        this._hora = _hora;
        this._mascota = _id_mascota;
        this._id_veterinario = _id_veterinario;
        this._tipo = _tipo;
        this._venta = _id_venta;
        this._anamnesis = _anamnesis;
        this._diagnostico = _diagnostico;
        this._tratamiento = _tratamiento;
        this._peso = _peso;
    }

    public Mascota getMascota() {
        return _mascota;
    }

    public void setMascota(Mascota _id_mascota) {
        this._mascota = _id_mascota;
    }

    public Venta getVenta() {
        return _venta;
    }

    public void setVenta(Venta _id_venta) {
        this._venta = _id_venta;
    }

    public Time getHora() {
        return _hora;
    }

    public void setHora(Time _hora) {
        this._hora = _hora;
    }

    public void setId(int id){
        this._id = id;
    }
    public int getId(){
        return _id;
    }

    public Date getFecha() {
        return _fecha;
    }

    public void setFecha(Date fecha) {
        this._fecha = fecha;
    }

    public User getId_veterinario() {
        return _id_veterinario;
    }

    public void setId_veterinario(User id_veterinario) {
        this._id_veterinario = id_veterinario;
    }
    
    public int getTipo() {
        return _tipo;
    }

    public void setTipo(int _tipo) {
        this._tipo = _tipo;
    }

    public String getAnamnesis() {
        return _anamnesis;
    }

    public void setAnamnesis(String _anamnesis) {
        this._anamnesis = _anamnesis;
    }

    public String getDiagnostico() {
        return _diagnostico;
    }

    public void setDiagnostico(String _diagnostico) {
        this._diagnostico = _diagnostico;
    }

    public String getTratamiento() {
        return _tratamiento;
    }

    public void setTratamiento(String _tratamiento) {
        this._tratamiento = _tratamiento;
    }

    public Peso getPeso() {
        return _peso;
    }

    public void setPeso(Peso _peso) {
        this._peso = _peso;
    }

    public HashSet<Fichero> getFicheros() {
        return _ficheros;
    }

    public void setFicheros(HashSet<Fichero> _ficheros) {
        this._ficheros = _ficheros;
    }
    
    public boolean addFichero(Fichero _fichero){
        return this._ficheros.add(_fichero);
    }
    
    public boolean deleteFichero(Fichero _fichero){
        if(this._ficheros.contains(_fichero)){
            return this._ficheros.remove(_fichero);
        }
        else{
            return false;
        }
    }
    
}
