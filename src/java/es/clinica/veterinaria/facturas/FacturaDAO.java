
package es.clinica.veterinaria.facturas;

import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.clientes.ClienteDAO;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserDAO;
import es.clinica.veterinaria.ventas.Venta;
import es.clinica.veterinaria.ventas.VentaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SaRCo
 */
public class FacturaDAO {
        
	private final DataSourceFactura ds = DataSourceFactura.INSTANCE;
	
	public List<Factura> findAll() {
		List<Factura> allEvents = new ArrayList<Factura>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_factura");

			// fetch all events from database
			Factura factura;
			
			while (rs.next()) {
                            boolean erroneo = false;
                            factura = new Factura();
                            factura.setId (rs.getInt(1));
                            factura.setNumero (rs.getInt(2));

                            ClienteDAO cli = new ClienteDAO();
                            List<Cliente> clilist = cli.findAll("select * from zk_cliente where id=" + rs.getInt(3));
                            if(clilist.size() == 1) {
                                factura.setCliente(clilist.get(0));
                            }else{
                                erroneo = true;
                            }

                            UserDAO user = new UserDAO();
                            List<User> userlist = user.findAll("select * from zk_usuario where id=" + rs.getInt(4));
                            if(userlist.size() == 1) {
                                factura.setEmpleado(userlist.get(0));
                            }else{
                                erroneo = true;
                            }

                            factura.setRuta(rs.getString(5));
                            factura.setFecha(rs.getDate(6));
                            
                            VentaDAO lnventa = new VentaDAO();
                            List<Venta> lnlist = lnventa.findAll("select * from zk_venta where factura = " + factura.getId());
                            for(int i=0; i<lnlist.size(); i++) {
                                factura.asignarVenta(lnlist.get(i));
                            }
                                
                            allEvents.add(factura);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Factura> findAll(String consulta) {
		List<Factura> allEvents = new ArrayList<Factura>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Factura factura;
			
			while (rs.next()) {
                            boolean erroneo = false;
                            factura = new Factura();
                            factura.setId (rs.getInt(1));
                            factura.setNumero (rs.getInt(2));

                            ClienteDAO cli = new ClienteDAO();
                            List<Cliente> clilist = cli.findAll("select * from zk_cliente where id=" + rs.getInt(3));
                            if(clilist.size() == 1) {
                                factura.setCliente(clilist.get(0));
                            }else{
                                erroneo = true;
                            }

                            UserDAO user = new UserDAO();
                            List<User> userlist = user.findAll("select * from zk_usuario where id=" + rs.getInt(4));
                            if(userlist.size() == 1) {
                                factura.setEmpleado(userlist.get(0));
                            }else{
                                erroneo = true;
                            }

                            factura.setRuta(rs.getString(5));
                            factura.setFecha(rs.getDate(6));

                            allEvents.add(factura);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
                
	public boolean delete(Factura prod) {
		return execute("delete from zk_factura where id = '" + prod.getId() + "'");
	}
	
	public Map insert(Factura prod) {
            int last_id = 0, last_num=0;
            Map map = new HashMap<String, Integer>();
            String fecha_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha());
            
            try {
                    Statement stmt = ds.getStatement();
                    ResultSet rs = stmt.executeQuery("SELECT numero FROM zk_factura ORDER BY numero DESC LIMIT 1");
                    rs.next();
                    last_num = rs.getInt(1) + 1;
//                System.out.println("Id: "+  last_id + ", Fecha: " + fecha_hora);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    ds.close();
                }
            
            boolean insertado = execute("insert into zk_factura(numero, cliente, empleado, factura, fecha)" +
                           "values ('" 
                            + last_num   + "', '" 
                            + prod.getCliente().getId()  + "', '" 
                            + prod.getEmpleado().getId() + "', '" 
                            + prod.getRuta()     + "', '"
                            + fecha_hora         + "')");
            if(insertado){
                try {
                    Statement stmt = ds.getStatement();
                    ResultSet rs = stmt.executeQuery("SELECT id FROM zk_factura WHERE cliente='"+ prod.getCliente().getId() + "' AND fecha='" + fecha_hora + "'");
                    rs.next();
                    last_id = rs.getInt(1);
//                System.out.println("Id: "+  last_id + ", Fecha: " + fecha_hora);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    ds.close();
                }
                
            }
            map.put("last_id", last_id);
            map.put("last_num", last_num);
            return map;
	}
	
	public boolean update(Factura prod) {
                return execute("UPDATE zk_factura SET  " 
                               + "numero = '"   + prod.getNumero()      + "', "
                               + "cliente = '"  + prod.getCliente().getId()     + "', "
                               + "empleado = '" + prod.getEmpleado().getId()    + "', "
                               + "factura = '"  + prod.getRuta()        + "' "
                               + "WHERE id = '" + prod.getId() + "'");
        }
        
        public boolean updatenumero(Factura fact){
            return execute("UPDATE zk_factura SET  " 
                            + "numero =   '" + fact.getNumero()      + "', "
                            + "empleado = '" + fact.getEmpleado().getId()    + "' "
                            + "WHERE id = '" + fact.getId() + "'");
        }
        
        public boolean updatefecha(Factura fact){
            String fecha_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fact.getFecha());
            return execute("UPDATE zk_factura SET  " 
                            + "fecha =   '" + fecha_hora      + "', "
                            + "empleado = '" + fact.getEmpleado().getId()    + "' "
                            + "WHERE id = '" + fact.getId() + "'");
        }
        
        public boolean updatePDF(Factura fact){
            return execute("UPDATE zk_factura SET  " 
                            + "factura = '" + fact.getRuta()    + "' "
                            + "WHERE id = '" + fact.getId() + "'");
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