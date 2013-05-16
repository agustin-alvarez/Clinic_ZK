/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.pedido_linea;

import es.clinica.veterinaria.pedidos.DataSourcePedido;
import es.clinica.veterinaria.pedidos.Pedido;
import es.clinica.veterinaria.pedidos.PedidoDAO;
import es.clinica.veterinaria.productos.Producto;
import es.clinica.veterinaria.productos.ProductoDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class PedidoLineaDAO {
    
	private final DataSourcePedidoLinea ds = DataSourcePedidoLinea.INSTANCE;
	
	public List<PedidoLinea> findAll() {
		List<PedidoLinea> allEvents = new ArrayList<PedidoLinea>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_pedido_linea");

			// fetch all events from database
			PedidoLinea pedido;
			
			while (rs.next()) {
				pedido = new PedidoLinea();
				pedido.setId (rs.getInt(1));
                                pedido.setCantidad (rs.getInt(2));
                                pedido.setCoste(rs.getFloat(3));
                                //pedido.setId_pedido(rs.getDate(4));
                                PedidoDAO ped = new PedidoDAO();
                                List<Pedido> pedlist = ped.findAll("select * from zk_pedido where id= " + rs.getInt(4));
                                if(pedlist.size() == 1)
                                    pedido.setPedido(pedlist.get(0));
                                
                                //pedido.setId_producto(rs.getBoolean(5));
                                ProductoDAO prod = new ProductoDAO();
                                List<Producto> prodlist = prod.findAll("select * from zk_producto where id= " + rs.getInt(5));
                                if(prodlist.size() == 1)
                                    pedido.setProducto(prodlist.get(0));
                                
				allEvents.add(pedido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<PedidoLinea> findAll(String consulta) {
		List<PedidoLinea> allEvents = new ArrayList<PedidoLinea>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			PedidoLinea pedido;
			
			while (rs.next()) {
				pedido = new PedidoLinea();
				pedido.setId (rs.getInt(1));
                                pedido.setCantidad (rs.getInt(2));
                                pedido.setCoste(rs.getFloat(3));
                                
                                // Relacion PedidoLinea - Pedido
                                PedidoDAO ped = new PedidoDAO();
                                List<Pedido> pedlist = ped.findAll("select * from zk_pedido where id= " + rs.getInt(4));
                                if(pedlist.size() == 1)
                                    pedido.setPedido(pedlist.get(0));
                                
                                // Relacon PedidoLinea - Producto
                                ProductoDAO prod = new ProductoDAO();
                                List<Producto> prodlist = prod.findAll("select * from zk_producto where id= " + rs.getInt(5));
                                if(prodlist.size() == 1)
                                    pedido.setProducto(prodlist.get(0));
                                
				allEvents.add(pedido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
                        
	public boolean delete(PedidoLinea prod) {
		return execute("delete from zk_pedido_linea where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(PedidoLinea prod) {
		return execute("insert into zk_pedido_linea(cantidad, coste, id_pedido, id_producto)" +
                                "values ('" + prod.getCantidad() + "', '" 
                                            + prod.getProducto().getPrecio() + "', '" 
                                            + prod.getPedido().getId() + "','" 
                                            + prod.getProducto().getId() +  "')");
	}
	
	public boolean update(PedidoLinea prod) {
            return execute("UPDATE zk_pedido_linea SET " +
                            " cantidad = '"         + prod.getCantidad() + 
                            "', coste = '"          + prod.getProducto().getPrecio()  + 
                            "', id_pedido = '"      + prod.getPedido().getId() + 
                            "', id_producto = '"     + prod.getProducto().getId() +
                            "' WHERE id = '"         + prod.getId() + "'");
    }
	
	private boolean execute(String sql) {

		try {
                        Statement stmt = ds.getStatement();
                        stmt.execute(sql);
                        if (stmt != null) {
                            stmt.close();
                        }
            
                        return true;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    } finally {
                        ds.close();
                    }           
	}
}