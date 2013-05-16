/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.pedido_linea;

import es.clinica.veterinaria.pedidos.Pedido;
import es.clinica.veterinaria.productos.Producto;

/**
 *
 * @author SaRCo
 */
public class PedidoLinea {
    
    private int _id;
    private int _cantidad;
    private float _coste;
    private Pedido _pedido;
    private Producto _producto;

    public PedidoLinea () {}
    
    public PedidoLinea(int _id, int _cantidad, float _coste, Pedido _pedido, Producto _producto) {
        this._id = _id;
        this._cantidad = _cantidad;
        this._coste = _coste;
        this._pedido = _pedido;
        this._producto = _producto;
    }

    public int getCantidad() {
        return _cantidad;
    }

    public void setCantidad(int _cantidad) {
        this._cantidad = _cantidad;
    }

    public float getCoste() {
        return _coste;
    }

    public void setCoste(float _coste) {
        this._coste = _coste;
    }

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public Pedido getPedido() {
        return _pedido;
    }

    public void setPedido(Pedido _pedido) {
        this._pedido = _pedido;
    }

    public Producto getProducto() {
        return _producto;
    }

    public void setProducto(Producto _producto) {
        this._producto = _producto;
    }
    
    public float getCostetotal(){
        return _coste * _cantidad;
    }
    
    
}
