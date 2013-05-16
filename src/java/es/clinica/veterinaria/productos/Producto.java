package es.clinica.veterinaria.productos;

import es.clinica.veterinaria.items.Item;
import es.clinica.veterinaria.iva.Iva;
import es.clinica.veterinaria.producto_familia.ProductoFamilia;
import es.clinica.veterinaria.proveedores.Proveedor;
import java.util.Date;

/**
 * Producto.
 * 
 * @author Agustin Alvarez
 */

//import java.util.Date;

public class Producto extends Item{
    private float _pvp;
    private int _stock;
    private ProductoFamilia _familia;
    private Proveedor _proveedor;
    private String _fotografia;

    public Producto () {}
    
    public Producto(int _id, String _codigo, String _nombre, String _descripcion, 
                    float _precio, Iva _iva, String _observaciones, Date _fecha_alta,
                    float _pvp, int _stock, ProductoFamilia _familia, 
                    Proveedor _proveedor, String _fotografia ) {
        
        super(_id, _codigo, _nombre, _descripcion,  _precio, _iva, _observaciones, _fecha_alta);
        this._pvp = _pvp;
        this._stock = _stock;
        this._familia = _familia;
        this._proveedor = _proveedor;
        this._fotografia = _fotografia;
    }

    public void setPvp(float spvp){
        this._pvp = spvp;
    }
    public float getPvp() {
        return _pvp;
    }
    public void setStock(int stock){
        this._stock = stock;
    }
    public int getStock(){
        return _stock;
    }

    public ProductoFamilia getFamilia() {
        return _familia;
    }

    public void setFamilia(ProductoFamilia _familia) {
        this._familia = _familia;
    }


    public String getFotografia() {
        return _fotografia;
    }

    public void setFotografia(String _fotografia) {
        this._fotografia = _fotografia;
    }

    public Proveedor getProveedor() {
        return _proveedor;
    }

    public void setProveedor(Proveedor _proveedor) {
        this._proveedor = _proveedor;
    }

}
