/**
 * Cita DAO.
 * 
 * @author Agustin Alvarez
 */

package es.clinica.veterinaria.citas;
import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.clientes.ClienteDAO;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
import es.clinica.veterinaria.servicio_familia.ServicioFamilia;
import es.clinica.veterinaria.servicio_familia.ServicioFamiliaDAO;
import es.clinica.veterinaria.user.User;
import es.clinica.veterinaria.user.UserDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
//import org.zkoss.zk.ui.Executions;
//import java.util.UUID;

public class CitaDAO {	
	
	private final DataSourceCita ds = DataSourceCita.INSTANCE;
	
	public List<Cita> findAll() {
		List<Cita> allEvents = new ArrayList<Cita>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select * from zk_cita order by id DESC");

			// fetch all events from database
			Cita it;
			
			while (rs.next()) {
                                boolean erroneo = false;
				it = new Cita();
				it.setId(rs.getInt(1));
                                it.setFecha(rs.getDate(2));
                                it.setHora(rs.getString(3));
                                it.setInforme(rs.getString(4));
//                                it.setServicio(rs.getInt(4));
                                
                                ServicioFamiliaDAO serv = new ServicioFamiliaDAO();
                                List<ServicioFamilia> servlist = serv.findAll("select * from zk_servicio_familia where id="+rs.getInt(5));
                                if(servlist.size() == 1) {
                                    it.setServicio(servlist.get(0));
                                }
                                
                                MascotaDAO mas = new MascotaDAO();
                                List<Mascota> maslist = mas.findAll("select * from zk_mascota where id="+rs.getInt(6));
                                if(maslist.size() == 1) {
                                    it.setMascota(maslist.get(0));
                                }else{
                                    erroneo = true;
                                }
                                
                                ClienteDAO cli = new ClienteDAO();
                                List<Cliente> clilist = cli.findAll("select * from zk_cliente where id="+rs.getInt(7));
                                if(clilist.size() == 1) {
                                    it.setCliente(clilist.get(0));
                                }else{
                                    erroneo = true;
                                }
                                
                                it.setEstado(rs.getInt(8));
                                
                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id=" + rs.getInt(9));
                                if(userlist.size() == 1) {
                                    it.setEmpleado(userlist.get(0));
                                }else{
                                    erroneo = true;
                                }
                                
                                //it.setId_cliente(rs.getInt(5));
				if(!erroneo){
                                    allEvents.add(it);
                                }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<Cita> findAll(String consulta) {
		List<Cita> allEvents = new ArrayList<Cita>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Cita it;
			
			while (rs.next()) {
                                boolean erroneo = false;
				it = new Cita();
				it.setId(rs.getInt(1));
                                it.setFecha(rs.getDate(2));
                                it.setHora(rs.getString(3));
                                it.setInforme(rs.getString(4));
                               //   it.setServicio(rs.getInt(4));
                                
                                ServicioFamiliaDAO serv = new ServicioFamiliaDAO();
                                List<ServicioFamilia> servlist = serv.findAll("select * from zk_servicio_familia where id="+rs.getInt(5));
                                if(servlist.size() == 1) {
                                    it.setServicio(servlist.get(0));
                                }
                                                                
                                MascotaDAO mas = new MascotaDAO();
                                List<Mascota> maslist = mas.findAll("select * from zk_mascota where id="+rs.getInt(6));
                                if(maslist.size() == 1) {
                                    it.setMascota(maslist.get(0));
                                }else{
                                    erroneo = true;
                                }
                                
                                //it.setId_cliente(rs.getInt(5));
                                ClienteDAO cli = new ClienteDAO();
                                List<Cliente> clilist = cli.findAll("select * from zk_cliente where id="+rs.getInt(7));
                                if(clilist.size() == 1) {
                                    it.setCliente(clilist.get(0));
                                }else{
                                    erroneo = true;
                                }
				
                                it.setEstado(rs.getInt(8));
                                
                                UserDAO user = new UserDAO();
                                List<User> userlist = user.findAll("select * from zk_usuario where id=" + rs.getInt(9));
                                if(userlist.size() == 1) {
                                    it.setEmpleado(userlist.get(0));
                                }else{
                                    erroneo = true;
                                }
                                if(!erroneo){
                                    allEvents.add(it);
                                }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
	public boolean delete(Cita it) {
		return execute("delete from zk_cita where id = '" + it.getId() + "'");
	}
	
	public boolean insert(Cita it) {
            String cliente, fecha, hora, informe, servicio, mascota, empleado;
            
            if(it.getCliente() != null){
                cliente = "'" + it.getCliente().getId() + "'";
            }
            else{
                cliente = "NULL";
            }
            if(it.getFecha() != null){
                fecha = "'" + new SimpleDateFormat("yyyy-MM-dd").format(it.getFecha())  + "'";
            }
            else{
                fecha = "NULL";
            }
            if(it.getHora() != null){
                //hora = "'" + it.getHora() + ":00" + "'";
                hora = "'" + it.getHora() +  "'";
            }
            else{
                hora = "NULL";
            }
            if(it.getInforme() != null){
                informe = "'" + it.getInforme() + "'";
            }
            else{
                informe = "NULL";
            }
            if(it.getServicio() != null){
                servicio = "'" + it.getServicio().getId() + "'";
            }
            else{
                servicio = "NULL";
            }
            if(it.getMascota() != null){
                mascota = "'" + it.getMascota().getId() + "'";
            }
            else{
                mascota = "NULL";
            }
            if(it.getEmpleado() != null){
                empleado = "'" + it.getEmpleado().getId() + "'";
            }
            else{
                empleado = "NULL";
            }
//            System.out.println("Fecha: " + fecha);
//            System.out.println("Hora: " + hora);
//            System.out.println("Informe: " + informe);
//            System.out.println("Servicio: " + servicio);
//            System.out.println("mascota: " + mascota);
//            System.out.println("cliente: " + cliente);
//            System.out.println("Empleado: " + empleado);
            
                return execute("insert into zk_cita(fecha, hora, informe, tipo, id_mascota, id_cliente, id_empleado) " +
                                "values ("
                                + fecha     + ", "
                                + hora      + ", " 
                                + informe   + ", " 
                                + servicio  + ", " 
                                + mascota   + ", "
                                + cliente   + ", "
                                + empleado  + ")");
	}
	
	public boolean update(Cita it) {
            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(it.getFecha()); 
            String hora = it.getHora();
            String servicio, informe;
            
            if(it.getServicio() == null){
                servicio  = "NULL";
            }
            else{
                servicio = "'" + it.getServicio().getId() + "'";
            }
            if(it.getInforme() == null){
                informe  = "NULL";
            }
            else{
                informe = "'" + it.getInforme() + "'";
            }
            
//            System.out.println("UPDATE zk_cita SET " );
//            System.out.println("fecha =        '" + fecha + "', " );
//            System.out.println("hora =         '" + hora + "', " );
//            System.out.println("informe =      " + informe    + " , " );
//            System.out.println("tipo =         "  + servicio            + " , " );
//            System.out.println("id_mascota =   '" + it.getMascota().getId()   + "' , " );
//            System.out.println("id_cliente =   '" + it.getCliente().getId()   + "' , " );
//            System.out.println("estado =       '" + it.getEstado() + "' " );
//            System.out.println(" WHERE id =    '" + it.getId() + "'");
                    
            return execute("UPDATE zk_cita SET " + 
                    "fecha =        '" + fecha + "', " +
                    "hora =         '" + hora + "', " +
                    "informe =      " + informe    + " , " +
                    "tipo =         "  + servicio            + " , " +
                    "id_mascota =   '" + it.getMascota().getId()   + "' , " +
                    "id_cliente =   '" + it.getCliente().getId()   + "' , " +
                    "estado =       '" + it.getEstado() + "' " +
                    " WHERE id =    '" + it.getId() + "'"
                    );
        }
    /*
        public void consultaClienteMascota(Cliente it){
                final HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("value1", it.getNif() );
                Executions.createComponents("cliente-mascota.zul", null, map);
        }
	*/
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
