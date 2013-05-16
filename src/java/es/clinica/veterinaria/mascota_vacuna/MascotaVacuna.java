package es.clinica.veterinaria.mascota_vacuna;

import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.vacunas.Vacuna;
import java.sql.Time;
import java.util.Date;

/**
 * @author SaRCo
 */
public class MascotaVacuna {
    
    private int _id;
    private Mascota _mascota;
    private Vacuna _vacuna;
    private Date _fecha;
    private Time _hora;
    private User _veterinario;

    public MascotaVacuna() {}

    public MascotaVacuna(int _id, Mascota _mascota, Vacuna _vacuna, Date _fecha, Time _hora, User _veterinario) {
        this._id = _id;
        this._mascota = _mascota;
        this._vacuna = _vacuna;
        this._fecha = _fecha;
        this._hora = _hora;
        this._veterinario = _veterinario;
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

    public Vacuna getVacuna() {
        return _vacuna;
    }

    public void setVacuna(Vacuna _vacuna) {
        this._vacuna = _vacuna;
    }

    public Date getFecha() {
        return _fecha;
    }

    public void setFecha(Date _fecha) {
        this._fecha = _fecha;
    }

    public Time getHora() {
        return _hora;
    }

    public void setHora(Time _hora) {
        this._hora = _hora;
    }

    public User getVeterinario() {
        return _veterinario;
    }

    public void setVeterinario(User _veterinario) {
        this._veterinario = _veterinario;
    }
}
