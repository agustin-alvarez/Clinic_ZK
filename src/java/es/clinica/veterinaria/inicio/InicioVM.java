/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.inicio;

import es.clinica.veterinaria.productos.Producto;
import es.clinica.veterinaria.productos.ProductoDAO;
import java.util.List;
import org.zkoss.bind.annotation.Init;

/**
 *
 * @author SaRCo
 */
public class InicioVM {
    
    private ProductoDAO prodDao = new ProductoDAO();
    private List<Producto> prodList;
    
    @Init
    public void init () {
        prodList = new ProductoDAO().findAll("SELECT * FROM zk_producto WHERE stock < 3");
    }
    public List<Producto> getAvisoProducto(){
        return prodDao.findAll("SELECT * FROM zk_producto WHERE stock < 3");
    }
}
