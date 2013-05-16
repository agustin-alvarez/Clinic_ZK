package es.clinica.veterinaria.mascota_vacuna;

import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserDAO;
import es.clinica.veterinaria.vacunas.Vacuna;
import es.clinica.veterinaria.vacunas.VacunaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SaRCo
 */
public class MascotaVacunaDAO {
        
	private final DataSourceMascotaVacuna ds = DataSourceMascotaVacuna.INSTANCE;
	
	public List<MascotaVacuna> findAll() {
		List<MascotaVacuna> allEvents = new ArrayList<MascotaVacuna>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_mascota_vacuna");

			// fetch all events from database
			MascotaVacuna mascvac;
			
			while (rs.next()) {
				mascvac = new MascotaVacuna();
				mascvac.setId (rs.getInt(1));
                                
                                MascotaDAO esp = new MascotaDAO();
                                List<Mascota> esplist = esp.findAll("select * from zk_mascota where id= " + rs.getInt(2));
                                if(esplist.size() == 1) {
                                    mascvac.setMascota(esplist.get(0));
                                }
                                
                                VacunaDAO vac = new VacunaDAO();
                                List<Vacuna> vaclist = vac.findAll("select * from zk_vacuna where id= " + rs.getInt(3));
                                if(vaclist.size() == 1) {
                                    mascvac.setVacuna(vaclist.get(0));
                                }
                                mascvac.setFecha(rs.getDate(4));
                                mascvac.setHora(rs.getTime(4));
                                
                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id= " + rs.getInt(5));
                                if(esplist.size() == 1) {
                                    mascvac.setVeterinario(userlist.get(0));
                                }
                                
				allEvents.add(mascvac);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
        public List<MascotaVacuna> findAll(String consulta) {
		List<MascotaVacuna> allEvents = new ArrayList<MascotaVacuna>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			MascotaVacuna mascvac;
			
			while (rs.next()) {
				mascvac = new MascotaVacuna();
				mascvac.setId (rs.getInt(1));
                                
                                MascotaDAO esp = new MascotaDAO();
                                List<Mascota> esplist = esp.findAll("select * from zk_mascota where id= " + rs.getInt(2));
                                if(esplist.size() == 1) {
                                    mascvac.setMascota(esplist.get(0));
                                }
                                
                                VacunaDAO vac = new VacunaDAO();
                                List<Vacuna> vaclist = vac.findAll("select * from zk_vacuna where id= " + rs.getInt(3));
                                if(vaclist.size() == 1) {
                                    mascvac.setVacuna(vaclist.get(0));
                                }
                                mascvac.setFecha(rs.getDate(4));
                                mascvac.setHora(rs.getTime(4));
                                
                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id= " + rs.getInt(5));
                                if(esplist.size() == 1) {
                                    mascvac.setVeterinario(userlist.get(0));
                                }
                                
				allEvents.add(mascvac);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
                
	public boolean delete(MascotaVacuna prod) {
		return execute("delete from zk_mascota_vacuna where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(MascotaVacuna prod) {
                return execute("insert into zk_mascota_vacuna(mascota, vacuna, veterinario)" +
                               "values (" + prod.getMascota().getId() + ", " 
                                          + prod.getVacuna().getId()  +  ", "
                                          + prod.getVeterinario().getId() + ")");
            
	}
	
	public boolean update(MascotaVacuna prod) {
                return execute("UPDATE zk_mascota_vacuna SET  "
                                + "mascota = " + prod.getMascota().getId()     + ", " 
                                + "vacuna =  " + prod.getVacuna().getId()   + ", "
                                + "WHERE id = "  + prod.getId() );

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