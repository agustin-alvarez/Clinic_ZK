/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.iva;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class IvaDAO {
        
	private final DataSourceIva ds = DataSourceIva.INSTANCE;
	
	public List<Iva> findAll() {
		List<Iva> allEvents = new ArrayList<Iva>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_iva");

			// fetch all events from database
			Iva iva;
			
			while (rs.next()) {
				iva = new Iva();
				iva.setId (rs.getInt(1));
                                iva.setValor(rs.getInt(2));
                                iva.setNombre (rs.getString(3));
                                iva.setDescripcion(rs.getString(4));
                                
				allEvents.add(iva);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Iva> findAll(String consulta) {
		List<Iva> allEvents = new ArrayList<Iva>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Iva iva;
			
			while (rs.next()) {
				iva = new Iva();
				iva.setId (rs.getInt(1));
                                iva.setValor(rs.getInt(2));
                                iva.setNombre (rs.getString(3));
                                iva.setDescripcion(rs.getString(4));
                                
				allEvents.add(iva);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
                
	public boolean delete(Iva prod) {
		return execute("delete from zk_iva where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(Iva prod) {
            if (prod.getDescripcion() == null)
                return execute("insert into zk_iva(valor, nombre, descripcion)" +
                               "values ('" + prod.getValor() + "', '" + prod.getNombre()    +   "', NULL)");
            else
                return execute("insert into zk_iva(valor, nombre, descripcion)" +
                               "values ('" + prod.getValor() + "', '" + prod.getNombre()    +   "', '" + prod.getDescripcion() + "')");
	}
	
	public boolean update(Iva prod) {
            if (prod.getDescripcion() == null)
                return execute("UPDATE zk_iva SET  "
                            + "valor = '" + prod.getValor()     + "', " 
                            + "nombre = '" + prod.getNombre()   + "', "
                            + "descripcion = NULL WHERE id = '"  + prod.getId() + "'");
            else
                return execute("UPDATE zk_iva SET  " 
                               + "valor = '" + prod.getValor()  + "', "
                               + "nombre = '" + prod.getNombre() + "', "
                               + "descripcion = '" + prod.getDescripcion() + "' WHERE id = '"  + prod.getId() + "'");
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