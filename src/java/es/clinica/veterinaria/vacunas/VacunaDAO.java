
package es.clinica.veterinaria.vacunas;
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
public class VacunaDAO {
    
	private final DataSourceVacuna ds = DataSourceVacuna.INSTANCE;
	
	public List<Vacuna> findAll() {
		List<Vacuna> allEvents = new ArrayList<Vacuna>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_vacuna");

			// fetch all events from database
			Vacuna vacuna;
			
			while (rs.next()) {
				vacuna = new Vacuna();
				vacuna.setId (rs.getInt(1));
                                vacuna.setNombre(rs.getString(2));
                                vacuna.setDescripcion(rs.getString(3));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(4));
                                if(esplist.size() == 1) {
                                    vacuna.setEspecie(esplist.get(0));
                                }
                                
                                vacuna.setDias(rs.getInt(5));
                                vacuna.setFecha(rs.getDate(6));
                                
				allEvents.add(vacuna);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<Vacuna> findAll(String consulta) {
		List<Vacuna> allEvents = new ArrayList<Vacuna>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Vacuna vacuna;
			
			while (rs.next()) {
				vacuna = new Vacuna();
				vacuna.setId (rs.getInt(1));
                                vacuna.setNombre(rs.getString(2));
                                vacuna.setDescripcion(rs.getString(3));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(4));
                                if(esplist.size() == 1) {
                                    vacuna.setEspecie(esplist.get(0));
                                }
                                
                                vacuna.setDias(rs.getInt(5));
                                vacuna.setFecha(rs.getDate(6));
                                
				allEvents.add(vacuna);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
	public boolean delete(Vacuna prod) {
		return execute("delete from zk_vacuna where id = " + prod.getId() );
	}
	
	public boolean insert(Vacuna prod) {
            String descripcion;
            if(prod.getDescripcion() == null){
                descripcion = "NULL";
            }
            else{
                descripcion = "'" + prod.getDescripcion() + "'";
            }
		return execute("insert into zk_vacuna(nombre, descripcion, especie, dias)" +
                                "values ('" 
                                            + prod.getNombre()          + "', " 
                                            + descripcion               + ", "   
                                            + prod.getEspecie().getId() + ", "
                                            + prod.getDias()    + ")");
	}
	
	public boolean update(Vacuna prod) {
            String descripcion;
            if(prod.getDescripcion() == null){
                descripcion = "NULL";
            }
            else{
                descripcion = "'" + prod.getDescripcion() + "'";
            }
            return execute("UPDATE zk_vacuna SET "
                            + "nombre = '" + prod.getNombre() + "', "
                            + "descripcion = " + descripcion + ", "
                            + "dias = "        + prod.getDias() + " "
                            + " WHERE id = "  + prod.getId() );
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

