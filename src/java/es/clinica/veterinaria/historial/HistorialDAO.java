/**
 * Historial DAO.
 * 
 * @author Agustin Alvarez
 */
package es.clinica.veterinaria.historial;
import es.clinica.veterinaria.ficheros.Fichero;
import es.clinica.veterinaria.ficheros.FicheroDAO;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
import es.clinica.veterinaria.pesos.DataSourcePeso;
import es.clinica.veterinaria.pesos.Peso;
import es.clinica.veterinaria.pesos.PesoDAO;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HistorialDAO {	
	
	private final DataSourceHistorial ds = DataSourceHistorial.INSTANCE;
        private final DataSourcePeso ps = DataSourcePeso.INSTANCE;
	
	public List<Historial> findAll() {
		List<Historial> allEvents = new ArrayList<Historial>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select * from zk_historial");

			// fetch all events from database
			Historial obj;
			
			while (rs.next()) {
				obj = new Historial();
				obj.setId(rs.getInt(1));
                                //obj.setId_mascota(rs.getInt(2));
                                MascotaDAO masc = new MascotaDAO();
                                List<Mascota> masclist = masc.findAll("select * from zk_mascota where id= " + rs.getInt(2));
                                if(masclist.size() == 1) {
                                    obj.setMascota(masclist.get(0));
                                }
                                                                
                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id= " + rs.getInt(4));
                                if(userlist.size() == 1) {
                                    obj.setId_veterinario(userlist.get(0));
                                }
                                
                                obj.setFecha(rs.getDate(4));
                                obj.setHora(rs.getTime(4));
                                obj.setTipo(rs.getInt(5));
                                
                                PesoDAO peso = new PesoDAO();
                                List<Peso> pesolist = peso.findAll("select * from zk_peso where id= " + rs.getInt(6));
                                if(pesolist.size() == 1) {
                                    obj.setPeso(pesolist.get(0));
                                }
                                
                                obj.setAnamnesis(rs.getString(7));
                                obj.setDiagnostico(rs.getString(8));
                                obj.setTratamiento(rs.getString(9));
                                
                                
				allEvents.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        	public List<Historial> findAll(String consulta) {
		List<Historial> allEvents = new ArrayList<Historial>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Historial obj;
			
			while (rs.next()) {
				obj = new Historial();
				obj.setId(rs.getInt(1));
                                //obj.setId_mascota(rs.getInt(2));
                                MascotaDAO masc = new MascotaDAO();
                                List<Mascota> masclist = masc.findAll("select * from zk_mascota where id= " + rs.getInt(2));
                                if(masclist.size() == 1) {
                                    obj.setMascota(masclist.get(0));
                                }
                                
                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id= " + rs.getInt(4));
                                if(userlist.size() == 1) {
                                    obj.setId_veterinario(userlist.get(0));
                                }
                                obj.setFecha(rs.getDate(4));
                                obj.setHora(rs.getTime(4));
                                obj.setTipo(rs.getInt(5));
                                
                                PesoDAO peso = new PesoDAO();
                                List<Peso> pesolist = peso.findAll("select * from zk_peso where id= " + rs.getInt(6));
                                if(pesolist.size() == 1) {
                                    obj.setPeso(pesolist.get(0));
                                }
                                
                                obj.setAnamnesis(rs.getString(7));
                                obj.setDiagnostico(rs.getString(8));
                                obj.setTratamiento(rs.getString(9));

                                List<Fichero> filelist = 
                                        new FicheroDAO().findAll("select * from zk_ficheros where "
                                                                 + "id_externo = " + obj.getId() 
                                                                 + " AND tipo = 1");
                                for(int i=0; i<filelist.size(); i++) {
                                    obj.addFichero(filelist.get(i));
                                }
				allEvents.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
	public boolean delete(Historial masc) {
		return execute("delete from zk_historial where id = '" + masc.getId() + "'");
	}
	
	public int insert(Historial cli) {
            int last_id = -1;
            String anamnesis, diagnostico, tratamiento;
            String fecha_hora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cli.getFecha());
            
            if(cli.getAnamnesis() == null || "".equals(cli.getAnamnesis())){
                anamnesis = "NULL";
            }
            else{
                anamnesis = "'" + cli.getAnamnesis() + "'";
            }
            
            if(cli.getDiagnostico() == null || "".equals(cli.getDiagnostico())){
                diagnostico = "NULL";
            }
            else{
                diagnostico = "'" + cli.getDiagnostico() + "'";
            }
            
            if(cli.getTratamiento() == null || "".equals(cli.getTratamiento())){
                tratamiento = "NULL";
            }
            else{
                tratamiento = "'" + cli.getTratamiento() + "'";
            }
            
            cli.getPeso().setFecha(cli.getFecha());
            
            if(new PesoDAO().insert(cli.getPeso()))
            {
                List<Peso> pesolist = new PesoDAO().findAll("select * from zk_peso where fecha='" + fecha_hora + "' AND mascota="+cli.getMascota().getId());
                if(pesolist.size() == 1) {
                    cli.setPeso(pesolist.get(0));
                }
            }
            if( execute("insert into zk_historial(id_mascota, id_veterinario, fecha, tipo_visita, id_peso, " +
                           "anamnesis, diagnostico, tratamiento) " +
                           "values (" 
                            + cli.getMascota().getId() + ", " 
                            + cli.getId_veterinario().getId() + ", '"
                            + fecha_hora    + "', "
                            + cli.getTipo() + ", " 
                            + cli.getPeso().getId() + ", "
                            + anamnesis + ", " 
                            + diagnostico +  ", " 
                            + tratamiento + ")"))
            {
                try{
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select id from zk_historial where "
                                + "id_mascota="+cli.getMascota().getId() 
                                + " AND "
                                + "fecha='" + fecha_hora + "'");
                        rs.next();
                        last_id = rs.getInt(1);
        //                System.out.println("Id: "+  last_id + ", Fecha: " + fecha_hora);
                    }
                    catch (SQLException e){
                        e.printStackTrace();
                    }
                    finally {
                        ds.close();
                    }
            }
                
            return last_id;
	}
	
	public boolean update(Historial cli) {
            int tipo_visita = 1;
            
            if(cli.getTipo() == 0)
            {
                tipo_visita = 1;
            }
            else{
                tipo_visita = cli.getTipo();
            }
            
            return execute("UPDATE zk_historial SET " +
                    "tipo_visita = '"   + tipo_visita         + "', " +
                    "anamnesis = '"     + cli.getAnamnesis()    + "', " +
                    "diagnostico = '"   + cli.getDiagnostico()  + "', " +
                    "tratamiento = '"   + cli.getTratamiento()  + "' " +
                    //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cli.getFechaAlta()) + 
                    " WHERE id = '" + cli.getId() + "'"
                    );
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
