package es.clinica.veterinaria.iva;

/**
 * @author SaRCo
 */
public class Iva {
    
    private int _id;
    private int _valor;
    private String _nombre;
    private String _descripcion;

    public Iva() {}
    
    public Iva(int _id, int _valor, String _nombre, String _descripcion) {
        this._id = _id;
        this._valor = _valor;
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

    public int getValor() {
        return _valor;
    }

    public void setValor(int _valor) {
        this._valor = _valor;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }
    
    
}
