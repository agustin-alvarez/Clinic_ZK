/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.ficheros;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class FicheroDAO {
    	
	private final DataSourceFichero ds = DataSourceFichero.INSTANCE;
	
	public List<Fichero> findAll() {
		List<Fichero> allEvents = new ArrayList<Fichero>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_ficheros");

			// fetch all events from database
			Fichero prod;
			
			while (rs.next()) {
				prod = new Fichero();
				prod.setId (rs.getInt(1));
                                prod.setId_externo (rs.getInt(2));
                                prod.setTipo(rs.getInt(3));
                                prod.setRuta(rs.getString(4));
                                prod.setFecha(rs.getDate(5));
                                
                                allEvents.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Fichero> findAll(String consulta) {
		List<Fichero> allEvents = new ArrayList<Fichero>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Fichero prod;
			
			while (rs.next()) {
				prod = new Fichero();
				prod.setId (rs.getInt(1));
                                prod.setId_externo (rs.getInt(2));
                                prod.setTipo(rs.getInt(3));
                                prod.setRuta(rs.getString(4));
                                prod.setFecha(rs.getDate(5));
                                
				allEvents.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
	public boolean delete(Fichero prod) {
//            System.out.println("delete from zk_ficheros where id = '" + prod.getId() + "'");
		return execute("delete from zk_ficheros where id = '" + prod.getId() + "'");
	}
	
	public int insert(Fichero prod) {
		if(execute("insert into zk_ficheros(id_externo, tipo, ruta)" +
                                "values (" 
                                            + prod.getId_externo()  + ", " 
                                            + prod.getTipo()        + ",'"
                                            + prod.getRuta()        + "')"))
                {
//                    System.out.println("SELECT * FROM zk_ficheros WHERE id_externo='"+ prod.getId_externo() + "' AND "
//                        + " tipo='"+ prod.getTipo() +"' AND ruta='"+prod.getRuta()+"'");
                    List<Fichero> fichero = findAll("SELECT * FROM zk_ficheros WHERE id_externo='"+ prod.getId_externo() + "' AND "
                        + " tipo='"+ prod.getTipo() +"' AND ruta='"+prod.getRuta()+"'");
                    return fichero.get(0).getId();
                }
                else{
                    System.out.println("Fichero NO insertado en la BD");
                    return 0;
                }
	}
	
//	public boolean update(Fichero prod) {
//            return execute("UPDATE zk_ficheros SET especie = '" + prod.getFichero() + "' WHERE id = '"  + prod.getId() + "'");
//    }
	
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