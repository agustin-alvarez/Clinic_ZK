/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.user;

import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.provincias.Provincia;
import java.util.Date;

/**
 *
 * @author SaRCo
 */
public class User {
    private int _id;
    private String _user;
    private String _password;
    private int _tipo;
    private Date _fecha_alta;
    private String _nombre;
    private String _apellidos;
    private String _nif;
    private String _direccion;
    private Poblacion _poblacion;
    private Provincia _provincia;
    private int _telefono;
    private int _movil;
    private String _email;
    private String _nss;
    

    public User(){}

    public User(int _id, String _user, String _password, 
                int _tipo, Date _fecha_alta, String _nombre, 
                String _apellidos, String _nif, String _direccion, 
                Poblacion _poblacion, Provincia _provincia, int _telefono, 
                int _movil, String _email, String _nss) {
        this._id = _id;
        this._user = _user;
        this._password = _password;
        this._tipo = _tipo;
        this._fecha_alta = _fecha_alta;
        this._nombre = _nombre;
        this._apellidos = _apellidos;
        this._nif = _nif;
        this._direccion = _direccion;
        this._poblacion = _poblacion;
        this._provincia = _provincia;
        this._telefono = _telefono;
        this._movil = _movil;
        this._email = _email;
        this._nss = _nss;
    }
    
    public Date getFecha_alta() {
        return _fecha_alta;
    }

    public void setFecha_alta(Date _fecha_alta) {
        this._fecha_alta = _fecha_alta;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String _password) {
        this._password = _password;
    }

    public int getTipo() {
        return _tipo;
    }

    public void setTipo(int _tipo) {
        this._tipo = _tipo;
    }

    public String getUser() {
        return _user;
    }

    public void setUser(String _user) {
        this._user = _user;
    }

    public String getApellidos() {
        return _apellidos;
    }

    public void setApellidos(String _apellidos) {
        this._apellidos = _apellidos;
    }

    public String getNif() {
        return _nif;
    }

    public void setNif(String _nif) {
        this._nif = _nif;
    }

    public String getDireccion() {
        return _direccion;
    }

    public void setDireccion(String _direccion) {
        this._direccion = _direccion;
    }

    public Poblacion getPoblacion() {
        return _poblacion;
    }

    public void setPoblacion(Poblacion _ciudad) {
        this._poblacion = _ciudad;
    }

    public Provincia getProvincia() {
        return _provincia;
    }

    public void setProvincia(Provincia _provincia) {
        this._provincia = _provincia;
    }

    public int getTelefono() {
        return _telefono;
    }

    public void setTelefono(int _telefono) {
        this._telefono = _telefono;
    }

    public int getMovil() {
        return _movil;
    }

    public void setMovil(int _movil) {
        this._movil = _movil;
    }

    public String getEmail() {
        return _email;
    }

    public void setEmail(String _email) {
        this._email = _email;
    }

    public String getNss() {
        return _nss;
    }

    public void setNss(String _nss) {
        this._nss = _nss;
    }
    
}
