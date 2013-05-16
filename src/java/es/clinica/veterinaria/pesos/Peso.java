package es.clinica.veterinaria.pesos;

import es.clinica.veterinaria.mascotas.Mascota;
import java.util.Date;

/**
 * @author SaRCo
 */
public class Peso {
    private int _id;
    private Mascota _mascota;
    private float _valor;
    private Date _fecha;

    public Peso() {}
    
    public Peso(int _id, float _valor, Date _fecha, Mascota _mascota) {
        this._id = _id;
        this._mascota = _mascota;
        this._valor = _valor;
        this._fecha = _fecha;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Mascota getMascota() {
        return _mascota;
    }

    public void setMascota(Mascota _mascota) {
        this._mascota = _mascota;
    }

    public float getValor() {
        return _valor;
    }

    public void setValor(float _valor) {
        this._valor = _valor;
    }

    public Date getFecha() {
        return _fecha;
    }

    public void setFecha(Date _fecha) {
        this._fecha = _fecha;
    }
}
