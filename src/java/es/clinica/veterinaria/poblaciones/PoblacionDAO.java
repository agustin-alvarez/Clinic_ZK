package es.clinica.veterinaria.poblaciones;

/**
 * Event DAO.
 * 
 * @author Agustin Alvarez
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PoblacionDAO {	
	
	private final DataSourcePoblacion ds = DataSourcePoblacion.INSTANCE;
	
	public List<Poblacion> findAll() {
		List<Poblacion> allEvents = new ArrayList<Poblacion>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_poblacion");

			// fetch all events from database
			Poblacion prod;
			
			while (rs.next()) {
				prod = new Poblacion();
				prod.setId (rs.getInt(1));
                                prod.setProvincia (rs.getInt(2));
                                prod.setPoblacion (rs.getString(3));
				allEvents.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Poblacion> findAll(String consulta) {
		List<Poblacion> allEvents = new ArrayList<Poblacion>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Poblacion prod;
			
			while (rs.next()) {
				prod = new Poblacion();
				prod.setId (rs.getInt(1));
                                prod.setProvincia (rs.getInt(2));
                                prod.setPoblacion (rs.getString(3));
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
                        ResultSet rs = stmt.executeQuery("select * from zk_poblacion");

                        // fetch all events from database
                        Poblacion prod;

                        while (rs.next()) {
                                prod = new Poblacion();
                                prod.setId (rs.getInt(1));
                                prod.setProvincia (rs.getInt(2));
                                prod.setPoblacion (rs.getString(3));
                                allEvents.add(prod.getPoblacion());
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                    ds.close();
                }

            return allEvents;
	}
                
	public boolean delete(Poblacion prod) {
		return execute("delete from zk_poblacion where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(Poblacion prod) {
		return execute("insert into zk_poblacion(provincia, poblacion)" +
                                "values ('" + prod.getProvincia() + "','" + prod.getPoblacion() + "')");
	}
	
	public boolean update(Poblacion prod) {
            return execute("UPDATE zk_provincia SET provincia = '" + prod.getProvincia() + " WHERE id = '"  + prod.getId() + "'");
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