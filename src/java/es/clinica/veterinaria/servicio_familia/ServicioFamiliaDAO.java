/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.servicio_familia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class ServicioFamiliaDAO {
        
	private final DataSourceServicioFamilia ds = DataSourceServicioFamilia.INSTANCE;
	
	public List<ServicioFamilia> findAll() {
		List<ServicioFamilia> allEvents = new ArrayList<ServicioFamilia>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_servicio_familia");

			// fetch all events from database
			ServicioFamilia familia;
			
			while (rs.next()) {
				familia = new ServicioFamilia();
				familia.setId (rs.getInt(1));
                                familia.setNombre (rs.getString(2));
                                familia.setDescripcion(rs.getString(3));
                                
				allEvents.add(familia);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<ServicioFamilia> findAll(String consulta) {
		List<ServicioFamilia> allEvents = new ArrayList<ServicioFamilia>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			ServicioFamilia familia;
			
			while (rs.next()) {
				familia = new ServicioFamilia();
				familia.setId (rs.getInt(1));
                                familia.setNombre (rs.getString(2));
                                familia.setDescripcion(rs.getString(3));
                                
				allEvents.add(familia);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
                
	public boolean delete(ServicioFamilia prod) {
		return execute("delete from zk_servicio_familia where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(ServicioFamilia prod) {
            if (prod.getDescripcion() == null)
                return execute("insert into zk_servicio_familia(nombre, descripcion)" +
                               "values ('" + prod.getNombre()    +   "', NULL)");
            else
                return execute("insert into zk_servicio_familia(nombre, descripcion)" +
                               "values ('" + prod.getNombre()    +   "', '" + prod.getDescripcion() + "')");
	}
	
	public boolean update(ServicioFamilia prod) {
            if (prod.getDescripcion() == null)
                return execute("UPDATE zk_servicio_familia SET nombre = '" + prod.getNombre() + "', descripcion = NULL WHERE id = '"  + prod.getId() + "'");
            else
                return execute("UPDATE zk_servicio_familia SET nombre = '" + prod.getNombre() + "', descripcion = '" + prod.getDescripcion() + "' WHERE id = '"  + prod.getId() + "'");
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