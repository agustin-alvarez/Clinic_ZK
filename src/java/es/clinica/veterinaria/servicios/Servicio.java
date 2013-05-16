package es.clinica.veterinaria.servicios;

import es.clinica.veterinaria.items.Item;
import es.clinica.veterinaria.iva.Iva;
import es.clinica.veterinaria.servicio_familia.ServicioFamilia;
import java.util.Date;

/**
 * @author SaRCo
 */

public class Servicio extends Item{
    private ServicioFamilia _familia;
    
    public Servicio() {}
    
    public Servicio(int _id, String _codigo, String _nombre, String _descripcion, float _precio, Iva _iva, String _observaciones , Date _fecha_alta, ServicioFamilia familia) 
    {
        super(_id, _codigo, _nombre, _descripcion,  _precio, _iva, _observaciones, _fecha_alta);
        this._familia = familia;
    }

    public ServicioFamilia getFamilia() {
        return _familia;
    }

    public void setFamilia(ServicioFamilia _familia) {
        this._familia = _familia;
    }
}
