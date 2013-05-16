/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.ventas_linea;

import es.clinica.veterinaria.productos.Producto;
import es.clinica.veterinaria.productos.ProductoDAO;
import es.clinica.veterinaria.servicios.Servicio;
import es.clinica.veterinaria.servicios.ServicioDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class VentaLineaDAO {
    
    private final DataSourceVentaLinea ds = DataSourceVentaLinea.INSTANCE;
	
    public List<VentaLinea> findAll() {
            List<VentaLinea> allEvents = new ArrayList<VentaLinea>();
            try {
                    // get connection
                Statement stmt = ds.getStatement();
                    ResultSet rs = stmt.executeQuery("select * from zk_venta_linea");

                    // fetch all events from database
                    VentaLinea venta;

                    while (rs.next()) {
                            venta = new VentaLinea();
                            venta.setId (rs.getInt(1));

                            //venta.setId_venta(rs.getDate(4));
//                                VentaDAO ped = new VentaDAO();
//                                List<Venta> pedlist = ped.findAll("select * from zk_venta where id= " + rs.getInt(2));
//                                if(pedlist.size() == 1)
//                                    venta.setVenta(pedlist.get(0));

                            //venta.setId_producto(rs.getBoolean(5));
                            venta.setTipo (rs.getInt(6));

                            if(venta.getTipo() == 1){
                                ProductoDAO prod = new ProductoDAO();
                                List<Producto> prodlist = prod.findAll("select * from zk_producto where id= " + rs.getInt(3));
                                if(prodlist.size() == 1) {
                                    venta.setProducto(prodlist.get(0));
                                }
                            }
                            else if(venta.getTipo() == 2){
                                ServicioDAO serv = new ServicioDAO();
                                List<Servicio> servlist = serv.findAll("select * from zk_servicio where id= " + rs.getInt(3));
                                if(servlist.size() == 1) {
                                    venta.setServicio(servlist.get(0));
                                }
                            }
                            venta.setCantidad (rs.getInt(4));
                            venta.setFecha(rs.getDate(5));
                            venta.setHora(rs.getTime(5));
                            venta.setPvp(rs.getFloat(7));
                            venta.setIva(rs.getInt(8));
                            
                            allEvents.add(venta);
                    }
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                ds.close();
            }

            return allEvents;
    }
	
    public List<VentaLinea> findAll(String consulta) {
        List<VentaLinea> allEvents = new ArrayList<VentaLinea>();
        try {
                    // get connection
            Statement stmt = ds.getStatement();
                ResultSet rs = stmt.executeQuery(consulta);

                // fetch all events from database
                VentaLinea venta;

                while (rs.next()) {
                        venta = new VentaLinea();
                        venta.setId (rs.getInt(1));

                        //venta.setId_venta(rs.getDate(4));
//                                VentaDAO ped = new VentaDAO();
//                                List<Venta> pedlist = ped.findAll("select * from zk_venta where id= " + rs.getInt(2));
//                                if(pedlist.size() == 1)
//                                    venta.setVenta(pedlist.get(0));

                        //venta.setId_producto(rs.getBoolean(5));
                        venta.setTipo (rs.getInt(6));

                        if(venta.getTipo() == 1){
                            ProductoDAO prod = new ProductoDAO();
                            List<Producto> prodlist = prod.findAll("select * from zk_producto where id= " + rs.getInt(3));
                            if(prodlist.size() == 1) {
                                venta.setProducto(prodlist.get(0));
                            }
                        }
                        else if(venta.getTipo() == 2){
                            ServicioDAO serv = new ServicioDAO();
                            List<Servicio> servlist = serv.findAll("select * from zk_servicio where id= " + rs.getInt(3));
                            if(servlist.size() == 1) {
                                venta.setServicio(servlist.get(0));
                            }
                        }
                        venta.setCantidad (rs.getInt(4));
                        venta.setFecha(rs.getDate(5));
                        venta.setHora(rs.getTime(5));
                        venta.setPvp(rs.getFloat(7));
                        venta.setIva(rs.getInt(8));

                        allEvents.add(venta);
                }
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                ds.close();
            }

            return allEvents;
    }
    
    public int find_idVenta(VentaLinea ventalinea) {
        int id = 0;
        try {
                Statement stmt = ds.getStatement();
                ResultSet rs = stmt.executeQuery("select id_venta from zk_venta_linea WHERE id=" + ventalinea.getId());
                rs.next();
                id = rs.getInt(1);
                
                    
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                ds.close();
            }

            return id;
    }
    
    public boolean delete(VentaLinea prod) {
            
            boolean insertado = false;
            
            if(prod.getTipo() == 1) //Producto
            {
                int stock = prod.getProducto().getStock() + prod.getCantidad();
                if(execute("UPDATE zk_producto SET " +
                    " stock = "             + stock + 
                    " WHERE id = '"         + prod.getProducto().getId() + "'")) 
                {
                    insertado = execute("delete from zk_venta_linea where id = '" + prod.getId() + "'");
                }
            }
            else if(prod.getTipo() == 2)//Servicio
            {
		insertado = execute("delete from zk_venta_linea where id = '" + prod.getId() + "'");
            }
            return insertado;
	}
	
	public boolean insert(VentaLinea prod) {
            boolean insertado = false;
            
            
//            System.out.println("insert into zk_venta_linea(cantidad, precio, id_venta, id_producto)" +
//                                "values ('" + prod.getCantidad() + "', '" 
//                                            + prod.getProducto().getPrecio() + "', '" 
//                                            + prod.getVenta().getId() + "','" 
//                                            + prod.getProducto().getId() +  "')");
            
            //Producto
            if(prod.getTipo() == 1 && (prod.getProducto().getStock() >= prod.getCantidad()) )
            {
                int stock = prod.getProducto().getStock() - prod.getCantidad();
                if(execute("UPDATE zk_producto SET " +
                        " stock = "             + stock + 
                        " WHERE id = '"         + prod.getProducto().getId() + "'")) 
                {
                    insertado = execute("insert into zk_venta_linea(cantidad, id_venta, id_producto, tipo, pvp, iva)" +
                                "values ('" + prod.getCantidad()                + "', '" 
                                            + prod.getVenta().getId()           + "','" 
                                            + prod.getProducto().getId()        + "',"
                                            + prod.getTipo()                     + ", "
                                            + prod.getPvp()                     + ", "
                                            + prod.getIva() + ")");
                }
            }
            else if(prod.getTipo() == 2) //Servicio
            {
                insertado = execute("insert into zk_venta_linea(cantidad, id_venta, id_producto, tipo, pvp, iva)" +
                                "values ('" + prod.getCantidad()                + "', '" 
                                            + prod.getVenta().getId()           + "', '" 
                                            + prod.getServicio().getId()        + "', "
                                            + prod.getTipo()                     + ",  "
                                            + prod.getPvp()                     + ",  "
                                            + prod.getIva() + ")");
            }
            return insertado;
	}
	
    public boolean update(VentaLinea vl) {
        boolean insertado = false;

        if(vl.getTipo() == 1) //PRODUCTO
        {
            VentaLineaDAO vldao = new VentaLineaDAO();
            System.out.println("Producto: " + vl.getNombre());
            
            List<VentaLinea> vlist = vldao.findAll("select * from zk_venta_linea where id= " + vl.getId());
            if(vlist.size() == 1) 
            {
                System.out.println("Encontrado: " + vl.getNombre());
                VentaLinea aux;
                aux = vlist.get(0);
                
                int cant = aux.getCantidad() - vl.getCantidad();
//                if(cant != 0)
//                {
                    System.out.println("CANT: " + cant);
                    int stock = vl.getProducto().getStock() + cant;
                    if(stock >= 0)
                    {
                        System.out.println("STOCK: " + stock);
                        if(execute("UPDATE zk_producto SET " + " stock = " + stock + " WHERE id = '" + vl.getProducto().getId() + "'")) 
                        {
                            insertado = execute("UPDATE zk_venta_linea SET " +
                                                " cantidad = "         + vl.getCantidad() + 
                                                ", id_producto = "     + vl.getProducto().getId() +
                                                ", pvp = "             + vl.getPvp() +
                                                ", iva = "             + vl.getIva() +
                                                " WHERE id = "         + vl.getId() + " AND tipo=1");
                            System.out.println("INSERTADO: "+ insertado);
                        }
                    }
                    else{
                        vl.setCantidad(aux.getCantidad());
                    }
//                }
                System.out.println("CANT != 0: " + cant);
            }
        }
        else if(vl.getTipo() == 2) //SERVICIO
        {
            insertado = execute("UPDATE zk_venta_linea SET " +
                                " cantidad = "         + vl.getCantidad() + 
                                ", id_producto = "     + vl.getServicio().getId() +
                                ", pvp = "             + vl.getPvp() +
                                ", iva = "             + vl.getIva() +
                                " WHERE id = "         + vl.getId() + " AND tipo=2");
        }
        return insertado;
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