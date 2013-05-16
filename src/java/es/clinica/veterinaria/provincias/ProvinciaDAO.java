package es.clinica.veterinaria.provincias;

/**
 * Event DAO.
 * 
 * @author Agustin Alvarez
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProvinciaDAO {	
	
	private final DataSourceProvincia ds = DataSourceProvincia.INSTANCE;
	
	public List<Provincia> findAll() {
		List<Provincia> allEvents = new ArrayList<Provincia>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_provincia");

			// fetch all events from database
			Provincia prod;
			
			while (rs.next()) {
				prod = new Provincia();
				prod.setId (rs.getInt(1));
                                prod.setProvincia (rs.getString(2));
				allEvents.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<Provincia> findAll(String consulta) {
		List<Provincia> allEvents = new ArrayList<Provincia>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Provincia prod;
			
			while (rs.next()) {
				prod = new Provincia();
				prod.setId (rs.getInt(1));
                                prod.setProvincia (rs.getString(2));
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
                        ResultSet rs = stmt.executeQuery("select * from zk_provincia");

                        // fetch all events from database
                        Provincia prod;

                        while (rs.next()) {
                                prod = new Provincia();
                                prod.setId (rs.getInt(1));
                                prod.setProvincia (rs.getString(2));
                                allEvents.add(prod.getProvincia());
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {
                    ds.close();
                }

            return allEvents;
	}
                
	public boolean delete(Provincia prod) {
		return execute("delete from zk_provincia where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(Provincia prod) {
		return execute("insert into zk_provincia(provincia)" +
                                "values ('" + prod.getProvincia()    +   "')");
	}
	
	public boolean update(Provincia prod) {
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