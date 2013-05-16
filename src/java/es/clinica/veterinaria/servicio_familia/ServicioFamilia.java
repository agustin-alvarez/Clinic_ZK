package es.clinica.veterinaria.servicio_familia;

/**
 * @author SaRCo
 */
public class ServicioFamilia {
    
    private int _id;
    private String _nombre;
    private String _descripcion;

    public ServicioFamilia() {}
    
    public ServicioFamilia(int _id, String _nombre, String _descripcion) {
        this._id = _id;
        this._nombre = _nombre;
        this._descripcion = _descripcion;
    }

    public String getDescripcion() {
        return _descripcion;
    }

    public void setDescripcion(String _descripcion) {
        this._descripcion = _descripcion;
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
}
