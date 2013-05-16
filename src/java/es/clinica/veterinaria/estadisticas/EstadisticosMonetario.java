
package es.clinica.veterinaria.estadisticas;

/**
 * @author SaRCo
 */

public class EstadisticosMonetario {
    private String _tipo;
    private float _enero;
    private float _febrero;
    private float _marzo;
    private float _abril;
    private float _mayo; 
    private float _junio;
    private float _julio;
    private float _agosto;
    private float _septiembre;
    private float _octubre;
    private float _noviembre;
    private float _diciembre;

    
    public EstadisticosMonetario() {}
    
    public EstadisticosMonetario(String _tipo, float _enero, float _febrero, float _marzo, float _abril, float _mayo, float _junio, float _julio, float _agosto, float _septiembre, float _octubre, float _noviembre, float _diciembre) {
        this._tipo = _tipo;
        this._enero = _enero;
        this._febrero = _febrero;
        this._marzo = _marzo;
        this._abril = _abril;
        this._mayo = _mayo;
        this._junio = _junio;
        this._julio = _julio;
        this._agosto = _agosto;
        this._septiembre = _septiembre;
        this._octubre = _octubre;
        this._noviembre = _noviembre;
        this._diciembre = _diciembre;
    }

    public String getTipo() {
        return _tipo;
    }

    public void setTipo(String _tipo) {
        this._tipo = _tipo;
    }

    public float getEnero() {
        return _enero;
    }

    public void setEnero(float _enero) {
        this._enero = _enero;
    }

    public float getFebrero() {
        return _febrero;
    }

    public void setFebrero(float _febrero) {
        this._febrero = _febrero;
    }

    public float getMarzo() {
        return _marzo;
    }

    public void setMarzo(float _marzo) {
        this._marzo = _marzo;
    }

    public float getAbril() {
        return _abril;
    }

    public void setAbril(float _abril) {
        this._abril = _abril;
    }

    public float getMayo() {
        return _mayo;
    }

    public void setMayo(float _mayo) {
        this._mayo = _mayo;
    }

    public float getJunio() {
        return _junio;
    }

    public void setJunio(float _junio) {
        this._junio = _junio;
    }

    public float getJulio() {
        return _julio;
    }

    public void setJulio(float _julio) {
        this._julio = _julio;
    }

    public float getAgosto() {
        return _agosto;
    }

    public void setAgosto(float _agosto) {
        this._agosto = _agosto;
    }

    public float getSeptiembre() {
        return _septiembre;
    }

    public void setSeptiembre(float _septiembre) {
        this._septiembre = _septiembre;
    }

    public float getOctubre() {
        return _octubre;
    }

    public void setOctubre(float _octubre) {
        this._octubre = _octubre;
    }

    public float getNoviembre() {
        return _noviembre;
    }

    public void setNoviembre(float _noviembre) {
        this._noviembre = _noviembre;
    }

    public float getDiciembre() {
        return _diciembre;
    }

    public void setDiciembre(float _diciembre) {
        this._diciembre = _diciembre;
    }
    
}
