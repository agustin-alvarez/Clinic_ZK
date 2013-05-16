/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.especies;

import es.clinica.veterinaria.razas.Raza;
import es.clinica.veterinaria.razas.RazaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class EspecieDAO {
    	
	private final DataSourceEspecie ds = DataSourceEspecie.INSTANCE;
	
	public List<Especie> findAll() {
		List<Especie> allEvents = new ArrayList<Especie>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_especie");

			// fetch all events from database
			Especie prod;
			
			while (rs.next()) {
				prod = new Especie();
				prod.setId (rs.getInt(1));
                                prod.setEspecie (rs.getString(2));
                                
                                RazaDAO raza = new RazaDAO();
                                List<Raza> razalist = raza.findAll("select * from zk_raza where especie= " + prod.getId());
                                for(int i=0; i<razalist.size(); i++) {
                                    prod.asignarRaza(razalist.get(i));
                                }
				
                                allEvents.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Especie> findAll(String consulta) {
		List<Especie> allEvents = new ArrayList<Especie>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Especie prod;
			
			while (rs.next()) {
				prod = new Especie();
				prod.setId (rs.getInt(1));
                                prod.setEspecie (rs.getString(2));
				allEvents.add(prod);
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
                        ResultSet rs = stmt.executeQuery("select * from zk_especie");

                        // fetch all events from database
                        Especie prod;

                        while (rs.next()) {
                                prod = new Especie();
                                prod.setId (rs.getInt(1));
                                prod.setEspecie (rs.getString(2));
                                allEvents.add(prod.getEspecie());
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                    ds.close();
                }

            return allEvents;
	}
                
	public boolean delete(Especie prod) {
		return execute("delete from zk_especie where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(Especie prod) {
		return execute("insert into zk_especie(especie)" +
                                "values ('" + prod.getEspecie()    +   "')");
	}
	
	public boolean update(Especie prod) {
            return execute("UPDATE zk_especie SET especie = '" + prod.getEspecie() + "' WHERE id = '"  + prod.getId() + "'");
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