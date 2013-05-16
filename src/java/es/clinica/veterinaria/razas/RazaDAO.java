/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.razas;

import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class RazaDAO {
    
	private final DataSourceRaza ds = DataSourceRaza.INSTANCE;
	
	public List<Raza> findAll() {
		List<Raza> allEvents = new ArrayList<Raza>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_raza");

			// fetch all events from database
			Raza raza;
			
			while (rs.next()) {
				raza = new Raza();
				raza.setId (rs.getInt(1));
//                                raza.setEspecie (rs.getInt(2));
                                raza.setRaza(rs.getString(3));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(2));
                                if(esplist.size() == 1)
                                    raza.setEspecie(esplist.get(0));
                                
				allEvents.add(raza);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Raza> findAll(String consulta) {
		List<Raza> allEvents = new ArrayList<Raza>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Raza raza;
			
			while (rs.next()) {
				raza = new Raza();
				raza.setId (rs.getInt(1));
//                                raza.setEspecie (rs.getInt(2));
                                raza.setRaza(rs.getString(3));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(2));
                                if(esplist.size() == 1)
                                    raza.setEspecie(esplist.get(0));
                                
				allEvents.add(raza);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        
        public List<String> findAllString() {
                List<String> allEvents = new ArrayList<String>();
                try {
                        // get connection
                    Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select * from zk_raza");

                        // fetch all events from database
                        Raza prod;

                        while (rs.next()) {
                                prod = new Raza();
                                prod.setId (rs.getInt(1));
//                                prod.setEspecie (rs.getInt(2));
                                prod.setRaza(rs.getString(3));
                                allEvents.add(prod.getRaza());
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                    ds.close();
                }

            return allEvents;
	}
                
	public boolean delete(Raza prod) {
		return execute("delete from zk_raza where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(Raza prod) {
		return execute("insert into zk_raza(especie, raza)" +
                                "values ('" + prod.getEspecie().getId() + "', '" + prod.getRaza()    +   "')");
	}
	
	public boolean update(Raza prod) {
            return execute("UPDATE zk_raza SET raza = '" + prod.getRaza() + "', especie = '" + prod.getEspecie().getId() + "' WHERE id = '"  + prod.getId() + "'");
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