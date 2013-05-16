
package es.clinica.veterinaria.estadisticas;

/**
 * @author SaRCo
 */

public class EstadisticosMasc {
    private String _tipo;
    private int _enero;
    private int _febrero;
    private int _marzo;
    private int _abril;
    private int _mayo; 
    private int _junio;
    private int _julio;
    private int _agosto;
    private int _septiembre;
    private int _octubre;
    private int _noviembre;
    private int _diciembre;

    
    public EstadisticosMasc() {}
    
    public EstadisticosMasc(String _tipo, int _enero, int _febrero, int _marzo, int _abril, int _mayo, int _junio, int _julio, int _agosto, int _septiembre, int _octubre, int _noviembre, int _diciembre) {
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

    public int getEnero() {
        return _enero;
    }

    public void setEnero(int _enero) {
        this._enero = _enero;
    }

    public int getFebrero() {
        return _febrero;
    }

    public void setFebrero(int _febrero) {
        this._febrero = _febrero;
    }

    public int getMarzo() {
        return _marzo;
    }

    public void setMarzo(int _marzo) {
        this._marzo = _marzo;
    }

    public int getAbril() {
        return _abril;
    }

    public void setAbril(int _abril) {
        this._abril = _abril;
    }

    public int getMayo() {
        return _mayo;
    }

    public void setMayo(int _mayo) {
        this._mayo = _mayo;
    }

    public int getJunio() {
        return _junio;
    }

    public void setJunio(int _junio) {
        this._junio = _junio;
    }

    public int getJulio() {
        return _julio;
    }

    public void setJulio(int _julio) {
        this._julio = _julio;
    }

    public int getAgosto() {
        return _agosto;
    }

    public void setAgosto(int _agosto) {
        this._agosto = _agosto;
    }

    public int getSeptiembre() {
        return _septiembre;
    }

    public void setSeptiembre(int _septiembre) {
        this._septiembre = _septiembre;
    }

    public int getOctubre() {
        return _octubre;
    }

    public void setOctubre(int _octubre) {
        this._octubre = _octubre;
    }

    public int getNoviembre() {
        return _noviembre;
    }

    public void setNoviembre(int _noviembre) {
        this._noviembre = _noviembre;
    }

    public int getDiciembre() {
        return _diciembre;
    }

    public void setDiciembre(int _diciembre) {
        this._diciembre = _diciembre;
    }
    
    
    
}
