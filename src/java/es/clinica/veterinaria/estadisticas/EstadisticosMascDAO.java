
package es.clinica.veterinaria.estadisticas;

import es.clinica.veterinaria.citas.DataSourceCita;
import es.clinica.veterinaria.mascota_vacuna.DataSourceMascotaVacuna;
import es.clinica.veterinaria.mascotas.DataSourceMascota;
import es.clinica.veterinaria.pedido_linea.DataSourcePedidoLinea;
import es.clinica.veterinaria.ventas_linea.DataSourceVentaLinea;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author SaRCo
 */
public class EstadisticosMascDAO {
            
	private final DataSourceMascota ds = DataSourceMascota.INSTANCE;
        private final DataSourceMascotaVacuna dsmv = DataSourceMascotaVacuna.INSTANCE;
        private final DataSourceCita dsc = DataSourceCita.INSTANCE;
        private final DataSourceVentaLinea dsvl = DataSourceVentaLinea.INSTANCE;
        private final DataSourcePedidoLinea dspl = DataSourcePedidoLinea.INSTANCE;
	
	public List<EstadisticosMasc> findAll() {
		List<EstadisticosMasc> allEvents = new ArrayList<EstadisticosMasc>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_mascota");

			// fetch all events from database
			EstadisticosMasc estad;
			
			while (rs.next()) {
				estad = new EstadisticosMasc();
                              
				allEvents.add(estad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
	public List<EstadisticosMasc> findAll(String consulta) {
		List<EstadisticosMasc> allEvents = new ArrayList<EstadisticosMasc>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			EstadisticosMasc estad;
			
			while (rs.next()) {
				estad = new EstadisticosMasc();
                                
                                estad.setTipo(rs.getString(1));
				estad.setEnero (rs.getInt(2));
                                estad.setFebrero (rs.getInt(3));
                                estad.setMarzo (rs.getInt(4));
                                estad.setAbril (rs.getInt(5));
                                estad.setMayo (rs.getInt(6));
                                estad.setJunio(rs.getInt(7));
                                estad.setJulio (rs.getInt(8));
                                estad.setAgosto (rs.getInt(9));
                                estad.setSeptiembre (rs.getInt(10));
                                estad.setOctubre (rs.getInt(11));
                                estad.setNoviembre (rs.getInt(12));
                                estad.setDiciembre (rs.getInt(13));
                                
				allEvents.add(estad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<EstadisticosMonetario> findAllMonetario(String consulta) {
		List<EstadisticosMonetario> allEvents = new ArrayList<EstadisticosMonetario>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			EstadisticosMonetario estad;
			
			while (rs.next()) {
				estad = new EstadisticosMonetario();
                                
                                estad.setTipo(rs.getString(1));
				estad.setEnero (rs.getFloat(2));
                                estad.setFebrero (rs.getFloat(3));
                                estad.setMarzo (rs.getFloat(4));
                                estad.setAbril (rs.getFloat(5));
                                estad.setMayo (rs.getFloat(6));
                                estad.setJunio(rs.getFloat(7));
                                estad.setJulio (rs.getFloat(8));
                                estad.setAgosto (rs.getFloat(9));
                                estad.setSeptiembre (rs.getFloat(10));
                                estad.setOctubre (rs.getFloat(11));
                                estad.setNoviembre (rs.getFloat(12));
                                estad.setDiciembre (rs.getFloat(13));
                                
				allEvents.add(estad);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
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