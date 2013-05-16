
package es.clinica.veterinaria.mascotas;
import es.clinica.veterinaria.citas.Cita;
import es.clinica.veterinaria.citas.CitaDAO;
import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.razas.Raza;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class Mascota{

//    public enum    Especie { AVE, BOVINA, CANINA, CONEJO, FELINA };
//    public enum    Raza    { };
    public enum    Pelo    { CORTO, DURO, LARGO, MEDIO, MEDIO_LARGO, PROPIO, RIZADO };
    
    private int _id;
    private String _chip;
    private String _nombre;
    private String _sexo;
    private Especie _especie;
    private Raza _raza;
    private String _pelo;
    private Date _nacimiento;
    private Date _defuncion;
    private float _peso;
    private float _altura;
    private String _observ;
    private Date _fecha_alta;
    private Date _fecha_baja;
    private Cliente _cliente = new Cliente(); 
//    Especie _e_especie;
    Pelo    _e_pelo;
    
    public Mascota() {}
    
    public Mascota(int _id, String _chip, String _nombre, String _sexo, Date _nacimiento, 
                    Date _defuncion, float _peso, float _altura, String _observ,
                    Especie _especie, String _pelo, Raza _raza, Date _fecha_alta, Date _fecha_baja, Cliente _cliente) 
    {
        this._id = _id;
        this._chip = _chip;
        this._nombre = _nombre;
        this._sexo = _sexo;
        this._especie = _especie;
        this._raza = _raza;
        this._pelo = _pelo;
        this._nacimiento = _nacimiento;
        this._defuncion = _defuncion;
        this._peso = _peso;
        this._altura = _altura;
        this._observ = _observ;
        this._fecha_alta = _fecha_alta;
        this._fecha_baja = _fecha_baja;
        this._cliente  = _cliente;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public String getSexo() {
        return _sexo;
    }

    public void setSexo(String _sexo) {
        this._sexo = _sexo;
    }

    public float getAltura() {
        return _altura;
    }

    public void setAltura(float _altura) {
        this._altura = _altura;
    }

    public String getChip() {
        return _chip;
    }

    public void setChip(String _chip) {
        this._chip = _chip;
    }

    public Date getDefuncion() {
        return _defuncion;
    }

    public void setDefuncion(Date _defuncion) {
        this._defuncion = _defuncion;
    }

    public Especie getEspecie() {
        return _especie;
    }

    public void setEspecie(Especie _especie) {
        this._especie = _especie;
    }

    public Raza getRaza() {
        return _raza;
    }

    public void setRaza(Raza _raza) {
        this._raza = _raza;
    }
    
    public Date getNacimiento() {
        return _nacimiento;
    }

    public void setNacimiento(Date _nacimiento) {
        this._nacimiento = _nacimiento;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String getObserv() {
        return _observ;
    }

    public void setObserv(String _observ) {
        this._observ = _observ;
    }

    public String getPelo() {
        return _pelo;
    }

    public void setPelo(String _pelo) {
        this._pelo = _pelo;
    }

    public float getPeso() {
        return _peso;
    }

    public void setPeso(float _peso) {
        this._peso = _peso;
    }

    public void setFechaalta(Date _fecha_alta){
        this._fecha_alta = _fecha_alta;
    }
    
    public Date getFechaalta(){
        return _fecha_alta;
    }
    
    public void setFechabaja(Date _fecha_baja){
        this._fecha_baja = _fecha_baja;
    }
    
    public Date getFechabaja(){
        return _fecha_baja;
    }
    
    public Cliente getCliente() {
        return _cliente;
    }

    public void setCliente(Cliente _cliente) {
        this._cliente = _cliente;
    }
    
    public Cita ultimaCita(){
        CitaDAO cita = new CitaDAO();
        List<Cita> citalist = cita.findAll("select * from zk_cita where id_mascota= " + _id + " AND fecha < NOW()");
            return citalist.get(citalist.size()-1);
        
    }
    
    public Cita proximaCita(){
        CitaDAO cita = new CitaDAO();
        List<Cita> citalist = cita.findAll("select * from zk_cita where id_mascota= " + _id + " AND fecha > NOW()");
        if(citalist.size() == 1) {
            return citalist.get(0);
        }else {
            return null;
        }
    }
    
}
