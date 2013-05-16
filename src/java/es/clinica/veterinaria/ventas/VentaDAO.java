/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.ventas;

import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.clientes.ClienteDAO;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserDAO;
import es.clinica.veterinaria.ventas_linea.VentaLinea;
import es.clinica.veterinaria.ventas_linea.VentaLineaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class VentaDAO {

	private final DataSourceVenta ds = DataSourceVenta.INSTANCE;

	public List<Venta> findAll() {
		List<Venta> allEvents = new ArrayList<Venta>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select * from zk_venta order by fecha desc");

			// fetch all events from database
			Venta it;

			while (rs.next()) {
				it = new Venta();
				it.setId(rs.getInt(1));
                                it.setFecha(rs.getDate(2));
                                it.setHora(rs.getTime(2));

                                ClienteDAO cli = new ClienteDAO();
                                List<Cliente> clilist = cli.findAll("select * from zk_cliente where id=" + rs.getInt(3));
                                if(clilist.size() == 1) {
                                    it.setCliente(clilist.get(0));
                                }

                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id=" + rs.getInt(4));
                                if(userlist.size() == 1) {
                                    it.setVendedor(userlist.get(0));
                                }
                                
                                UserDAO vet = new UserDAO();
                                List<User> vetlist = vet.findAll("select * from zk_usuario where id=" + rs.getInt(5));
                                if(vetlist.size() == 1) {
                                    it.setVeterinario(vetlist.get(0));
                                }

                                VentaLineaDAO lnventa = new VentaLineaDAO();
                                List<VentaLinea> lnlist = lnventa.findAll("select * from zk_venta_linea where id_venta = " + it.getId());
                                for(int i=0; i<lnlist.size(); i++) {
                                    it.asignarVentaLinea(lnlist.get(i));
                                }
                                
                                it.setAlbaran(rs.getString(6));
                                if(rs.getInt(8) == 0) {
                                    it.setFacturado(false);
                                }
                                else {
                                    it.setFacturado(true);
                                }

				allEvents.add(it);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}

		return allEvents;
	}

        public List<Venta> findAll(String consulta) {
		List<Venta> allEvents = new ArrayList<Venta>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Venta it;

			while (rs.next()) {
				it = new Venta();
				it.setId(rs.getInt(1));
                                it.setFecha(rs.getDate(2));
                                it.setHora(rs.getTime(2));

                                ClienteDAO cli = new ClienteDAO();
                                List<Cliente> clilist = cli.findAll("select * from zk_cliente where id="+rs.getInt(3));
                                if(clilist.size() == 1) {
                                    it.setCliente(clilist.get(0));
                                }

                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id=" + rs.getInt(4));
                                if(userlist.size() == 1) {
                                    it.setVendedor(userlist.get(0));
                                }

                                VentaLineaDAO lnventa = new VentaLineaDAO();
                                List<VentaLinea> lnlist = lnventa.findAll("select * from zk_venta_linea where id_venta = " + it.getId());
                                for(int i=0; i<lnlist.size(); i++) {
                                    it.asignarVentaLinea(lnlist.get(i));
                                }

				allEvents.add(it);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}

		return allEvents;
	}


	public boolean delete(Venta it) {
		return execute("delete from zk_venta where id = '" + it.getId() + "'");
	}

	public int insert(Venta it) {
            String fecha_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(it.getFecha());
            System.out.println("Fecha-hora: " + fecha_hora);
            int last_id = 0;
            if(execute("insert into zk_venta(fecha , id_cliente, id_vendedor) " +
                               "values ( '" + fecha_hora + "' , '"+ it.getCliente().getId() + "','" + it.getVendedor().getId() + "')"))
            {
                try{
                    Statement stmt = ds.getStatement();
                    ResultSet rs = stmt.executeQuery("select id from zk_venta WHERE fecha='" + fecha_hora +"' AND id_cliente='"+ it.getCliente().getId() +"'");
                    rs.next();
                    last_id = rs.getInt(1);
//                    System.out.println("Id: "+  last_id + ", Fecha: " +fecha_hora);
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
                finally {
                    ds.close();
                }
            }
                return last_id;
	}

        public int last_id(){
            int last_id = 0;
            try{
                Statement stmt = ds.getStatement();
                ResultSet rs = stmt.executeQuery("select * from zk_venta");
                last_id = rs.getInt(1);
            } catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
            return last_id;

        }

	public boolean update(Venta it) {
//            String fecha_hora = new SimpleDateFormat("yyyy-MM-dd").format(it.getFecha()) + " " + new SimpleDateFormat("HH:mm:ss").format(it.getHora());
            return execute("UPDATE zk_venta SET " +
//                    "fecha = '" + fecha_hora + "', " +
                    "id_cliente = '" + it.getCliente().getId()   + "' , " +
                    "id_veterinario = '" + it.getVeterinario().getId()   + "'  " +
                    " WHERE id = '" + it.getId() + "'"
                    );
        }
        
        public boolean updateCliente(Venta it) {
            return execute("UPDATE zk_venta SET " +
//                    "fecha = '" + fecha_hora + "', " +
                    "id_cliente = '" + it.getCliente().getId()   + "' " +
                    " WHERE id = '" + it.getId() + "'"
                    );
        }
        
        public boolean updateVeterinario(Venta it) {
            return execute("UPDATE zk_venta SET " +
                    "id_veterinario = '" + it.getVeterinario().getId()   + "'  " +
                    " WHERE id = '" + it.getId() + "'"
                    );
        }
        
        public boolean updateFactura(Venta it) {
            int facturado;
            String factura;
            
            if(it.getFactura() != null) {
                factura = "'" + it.getFactura().getId() + "'";
            }
            else {
                factura = "0";
            }
            
            if(it.isFacturado()) {
                facturado = 1;
            }
            else {
                facturado = 0;
                factura = "0";
            }
            return execute("UPDATE zk_venta SET " +
                           "factura   = "  + factura    + ",  " +
                           "facturado = '" + facturado  + "' " +
                           " WHERE id = '" + it.getId() + "'"
                          );
        }
        
        public boolean updateAlbaran(Venta it) {
            return execute("UPDATE zk_venta SET " +
                           "albaran = '" + it.getAlbaran()  + "'  " +
                           " WHERE id = '" + it.getId() + "'"
                          );
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
