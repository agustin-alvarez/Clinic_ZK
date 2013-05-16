/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.pedidos;

import es.clinica.veterinaria.pedido_linea.PedidoLinea;
import es.clinica.veterinaria.pedido_linea.PedidoLineaDAO;
import es.clinica.veterinaria.proveedores.Proveedor;
import es.clinica.veterinaria.proveedores.ProveedorDAO;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class PedidoDAO {

    private final DataSourcePedido ds = DataSourcePedido.INSTANCE;

    public List<Pedido> findAll() {
            List<Pedido> allEvents = new ArrayList<Pedido>();
            try {
                    // get connection
                Statement stmt = ds.getStatement();
                    ResultSet rs = stmt.executeQuery("select * from zk_pedido ORDER BY id DESC");

                    // fetch all events from database
                    Pedido pedido;

                    while (rs.next()) {
                            pedido = new Pedido();
                            pedido.setId (rs.getInt(1));
                            pedido.setFecha (rs.getTimestamp(2));
                            pedido.setFecha_entrega(rs.getDate(3));
                            pedido.setFecha_pago(rs.getDate(4));
                            pedido.setPagado(rs.getBoolean(5));

                            ProveedorDAO prov = new ProveedorDAO();
                            List<Proveedor> provlist = prov.findAll("select * from zk_proveedor where id= " + rs.getInt(6));
                            if(provlist.size() == 1) {
                                pedido.setProveedor(provlist.get(0));
                            }

                            PedidoLineaDAO lnped = new PedidoLineaDAO();
                            List<PedidoLinea> lnlist = lnped.findAll("select * from zk_pedido_linea where id_pedido = " + pedido.getId());
                            for(int i=0; i<lnlist.size(); i++) {
                                pedido.asignarPedidoLinea(lnlist.get(i));
                            }
                            
                            UserDAO user = new UserDAO();
                            List<User> userlist = user.findAll("select * from zk_usuario where id= " + rs.getInt(7));
                            if(userlist.size() == 1) {
                                pedido.setEmpleado(userlist.get(0));
                            }

                            allEvents.add(pedido);
                    }
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                ds.close();
            }

            return allEvents;
    }

    public List<Pedido> findAll(String consulta) {
            List<Pedido> allEvents = new ArrayList<Pedido>();
            try {
                    // get connection
                Statement stmt = ds.getStatement();
                    ResultSet rs = stmt.executeQuery(consulta);

                    // fetch all events from database
                    Pedido pedido;

                    while (rs.next()) {
                            pedido = new Pedido();
                            pedido.setId (rs.getInt(1));
                            pedido.setFecha (rs.getTimestamp(2));
                            pedido.setFecha_entrega(rs.getDate(3));
                            pedido.setFecha_pago(rs.getDate(4));
                            pedido.setPagado(rs.getBoolean(5));

                            ProveedorDAO prov = new ProveedorDAO();
                            List<Proveedor> provlist = prov.findAll("select * from zk_proveedor where id= " + rs.getInt(6));
                            if(provlist.size() == 1) {
                                pedido.setProveedor(provlist.get(0));
                            }

                            allEvents.add(pedido);
                    }
            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                ds.close();
            }

            return allEvents;
    }

    public boolean delete(Pedido prod) {
            return execute("delete from zk_pedido where id = '" + prod.getId() + "'");
    }

    public int insert(Pedido prod) {
        String fecha_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha());
        int pagado;
        int last_id = 0;
        boolean insertado = false;
        if(prod.isPagado()) {
            pagado = 1;
        }
        else {
            pagado = 0;
        }

        if(prod.getFecha_entrega() == null && prod.getFecha_pago() == null) {
            if(execute("insert into zk_pedido(fecha, pagado, id_proveedor, id_empleado)" +
                           "values ('" + fecha_hora + "','"
                                       + pagado + "','"
                                       + prod.getProveedor().getId() +  "','"
                                       + prod.getEmpleado().getId()  + "')")){
                insertado = true;
            }
        }

        else if (prod.getFecha_entrega() != null && prod.getFecha_pago() != null) {
            if( execute("insert into zk_pedido(fecha, fecha_entrega, fecha_pago, pagado, id_proveedor, id_empleado)" +
                           "values ('"
                            + fecha_hora + "','"
                            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_entrega())   + "','"
                            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_pago())      + "','"
                            + pagado + "','"
                            + prod.getProveedor().getId() +  "','"
                            + prod.getEmpleado().getId()  +  "')")){
                insertado = true;
            }
        }

        else if(prod.getFecha_pago() == null && prod.getFecha_entrega() != null) {
            if(execute("insert into zk_pedido(fecha, fecha_entrega, pagado, id_proveedor, id_empleado)" +
                       "values ('" /*+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha()) + "', '" */
                           + fecha_hora + "','"
                           + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_pago()) + "','"
                           + pagado + "','"
                           + prod.getProveedor().getId() +  "','"
                           + prod.getEmpleado().getId()  +  "')")){
                insertado =  true;
            }
        }

        else {
            if(execute("insert into zk_pedido(fecha, fecha_pago, pagado, id_proveedor, id_empleado)" +
                       "values ('" /*+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha()) + "', '" */
                           + fecha_hora + "','"
                           + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_pago()) + "','"
                           + pagado + "','"
                           + prod.getProveedor().getId() +  "','"
                           + prod.getEmpleado().getId()  +  "')")){
                insertado = true;
            }
        }
        
        try{
                Statement stmt = ds.getStatement();
                ResultSet rs = stmt.executeQuery("select id from zk_pedido WHERE fecha='" + fecha_hora +"'");
                rs.next();
                last_id = rs.getInt(1);
//                System.out.println("Id: "+  last_id + ", Fecha: " + fecha_hora);
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            finally {
                ds.close();
            }
        return last_id;
    }

    public boolean update(Pedido prod) {
        int pagado;
        if(prod.isPagado()) {
            pagado = 1;
        }
        else {
            pagado = 0;
        }

        if(prod.getFecha_entrega() == null && prod.getFecha_pago() == null) {
            return execute("UPDATE zk_pedido SET "  +
                        "   fecha_entrega = NULL "  +
                        " , fecha_pago = NULL "     +
                        " , pagado = '"         + pagado  +
                        "', id_proveedor = '"   + prod.getProveedor().getId() +
                        "' WHERE id = '"        + prod.getId() + "'");
        }

        else if(prod.getFecha_pago() == null && prod.getFecha_entrega() != null) {
            return execute("UPDATE zk_pedido SET "  +
                        "   fecha_entrega = '"  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_entrega()) +
                        "', fecha_pago = NULL " +
                        " , pagado = '"         + pagado  +
                        "', id_proveedor = '"   + prod.getProveedor().getId() +
                        "' WHERE id = '"        + prod.getId() + "'");
        }

        else if(prod.getFecha_pago() != null && prod.getFecha_entrega() == null) {
            return execute("UPDATE zk_pedido SET "  +
                        " fecha_entrega = NULL "  + 
                        ", fecha_pago= '"      + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_pago()) +
                        "', pagado = '"       + pagado  +
                        "', id_proveedor = '" + prod.getProveedor().getId() +
                        "' WHERE id = '"      + prod.getId() + "'");
        }

        else {
            return execute("UPDATE zk_pedido SET "  +
                        "   fecha_entrega = '"  + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_entrega()) +
                        "', fecha_pago= '"      + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_pago()) +
                        "', pagado = '"         + pagado  +
                        "', id_proveedor = '"   + prod.getProveedor().getId() +
                        "' WHERE id = '"        + prod.getId() + "'");
        }

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