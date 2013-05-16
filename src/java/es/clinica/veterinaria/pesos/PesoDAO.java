/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.pesos;

import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
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
public class PesoDAO {
    
	private final DataSourcePeso ds = DataSourcePeso.INSTANCE;
	
	public List<Peso> findAll() {
		List<Peso> allEvents = new ArrayList<Peso>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_peso ORDER BY fecha DESC");

			// fetch all events from database
			Peso peso;
			
			while (rs.next()) {
				peso = new Peso();
				peso.setId (rs.getInt(1));
//                                peso.setEspecie (rs.getInt(2));
                                peso.setValor(rs.getFloat(3));
                                
                                MascotaDAO esp = new MascotaDAO();
                                List<Mascota> esplist = esp.findAll("select * from zk_mascota where id= " + rs.getInt(2));
                                if(esplist.size() == 1) {
                                    peso.setMascota(esplist.get(0));
                                }
                                
                                peso.setFecha(rs.getDate(4));
				allEvents.add(peso);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Peso> findAll(String consulta) {
		List<Peso> allEvents = new ArrayList<Peso>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Peso peso;
			
			while (rs.next()) {
				peso = new Peso();
				peso.setId (rs.getInt(1));
//                                peso.setEspecie (rs.getInt(2));
                                peso.setValor(rs.getFloat(3));
                                
                                MascotaDAO esp = new MascotaDAO();
                                List<Mascota> esplist = esp.findAll("select * from zk_mascota where id= " + rs.getInt(2));
                                if(esplist.size() == 1) {
                                    peso.setMascota(esplist.get(0));
                                }
                                
                                peso.setFecha(rs.getDate(4));
                                
				allEvents.add(peso);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        
	public boolean delete(Peso prod) {
		return execute("delete from zk_peso where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(Peso prod) {
            String fecha_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha());
		return execute("insert into zk_peso(mascota, valor, fecha)" +
                                "values ('" 
                                + prod.getMascota().getId() + "', '" 
                                + prod.getValor()           + "', '"
                                + fecha_hora           + "')");
	}
	
	public boolean update(Peso prod) {
            return execute("UPDATE zk_peso SET valor = '" + prod.getValor() + "' WHERE id = '"  + prod.getId() + "'");
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