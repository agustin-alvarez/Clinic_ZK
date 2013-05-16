/**
 * Event DAO.
 * 
 * @author Agustin Alvarez
 */
package es.clinica.veterinaria.clientes;
import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import es.clinica.veterinaria.mascotas.Mascota;
import es.clinica.veterinaria.mascotas.MascotaDAO;
import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.poblaciones.PoblacionDAO;
import es.clinica.veterinaria.provincias.Provincia;
import es.clinica.veterinaria.provincias.ProvinciaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
//import org.zkoss.zk.ui.Executions;
//import java.util.UUID;

public class ClienteDAO {	
	
	private final DataSourceCliente ds = DataSourceCliente.INSTANCE;
	
	public List<Cliente> findAll() {
		List<Cliente> allEvents = new ArrayList<Cliente>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select * from zk_cliente");

			// fetch all events from database
			Cliente cli;
			
			while (rs.next()) {
				cli = new Cliente();
				cli.setId(rs.getInt(1));
                                cli.setNombre(rs.getString(2));
				cli.setApellidos(rs.getString(3));
                                cli.setNif(rs.getString(4));
                                cli.setDireccion(rs.getString(5));
                                //cli.setCiudad(rs.getString(6));
                                PoblacionDAO pob = new PoblacionDAO();
                                List<Poblacion> poblist = pob.findAll("select * from zk_poblacion where id= " + rs.getInt(6));
                                if(poblist.size() == 1) {
                                    cli.setCiudad(poblist.get(0));
                                }
                                
                                //cli.setProvincia(rs.getString(7));
                                ProvinciaDAO pro = new ProvinciaDAO();
                                List<Provincia> prolist = pro.findAll("select * from zk_provincia where id= " + rs.getInt(7));
                                if(prolist.size() == 1) {
                                    cli.setProvincia(prolist.get(0));
                                }
                                
                                cli.setTelefono(rs.getInt(8));
                                cli.setTelefono2(rs.getInt(9));
                                cli.setEmail(rs.getString(10));
				cli.setFechaalta(rs.getDate(11));
                                cli.setCodigopostal(rs.getInt(12));		
				allEvents.add(cli);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<Cliente> findAll(String consulta) {
		List<Cliente> allEvents = new ArrayList<Cliente>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Cliente cli;
			
			while (rs.next()) {
				cli = new Cliente();
				cli.setId(rs.getInt(1));
                                cli.setNombre(rs.getString(2));
				cli.setApellidos(rs.getString(3));
                                cli.setNif(rs.getString(4));
                                cli.setDireccion(rs.getString(5));
                                //cli.setCiudad(rs.getString(6));
                                PoblacionDAO pob = new PoblacionDAO();
                                List<Poblacion> poblist = pob.findAll("select * from zk_poblacion where id= " + rs.getInt(6));
                                if(poblist.size() == 1) {
                                    cli.setCiudad(poblist.get(0));
                                }
                                
                                //cli.setProvincia(rs.getString(7));
                                ProvinciaDAO pro = new ProvinciaDAO();
                                List<Provincia> prolist = pro.findAll("select * from zk_provincia where id= " + rs.getInt(7));
                                if(prolist.size() == 1) {
                                    cli.setProvincia(prolist.get(0));
                                }
                                
                                cli.setTelefono(rs.getInt(8));
                                cli.setTelefono2(rs.getInt(9));
                                cli.setEmail(rs.getString(10));
				cli.setFechaalta(rs.getDate(11));
                                cli.setCodigopostal(rs.getInt(12));
				allEvents.add(cli);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        //Dado un cliente, se saca sus mascotas
        public List<Mascota> getClienteMascotas(Cliente cli) {
		List<Mascota> allEvents = new ArrayList<Mascota>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select * from zk_mascota where id_cliente=" + cli.getId());

			// fetch all events from database
			Mascota masc;
			
			while (rs.next()) {
				masc = new Mascota();
				masc.setId(rs.getInt(1));
                                masc.setChip(rs.getString(2));
                                masc.setNombre(rs.getString(3));
				masc.setNacimiento(rs.getDate(4));
                                masc.setDefuncion(rs.getDate(5));
                                masc.setPeso(rs.getFloat(6));
                                masc.setAltura(rs.getFloat(7));
                                masc.setObserv(rs.getString(8));
//                              masc.setEspecie(rs.getString(9));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(9));
                                if(esplist.size() == 1) {
                                    masc.setEspecie(esplist.get(0));
                                }
                                
                                masc.setPelo(rs.getString(10));
//                              masc.setRaza(rs.getString(11));
                                MascotaDAO masco = new MascotaDAO();
                                List<Mascota> masclist = masco.findAll("select * from zk_mascota where id= " + rs.getInt(11));
                                if(masclist.size() == 1) {
                                    masc.setEspecie(esplist.get(0));
                                }
                                
				masc.setFechaalta(rs.getDate(12));
                                masc.setFechabaja(rs.getDate(13));
				allEvents.add(masc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
	public boolean delete(Cliente cli) {
		return execute("delete from zk_cliente where id = '" + cli.getId() + "'");
	}
	
	public boolean insert(Cliente cli) {
            String email;
            String telefono2;
            String codigopostal;
            
            if(cli.getEmail() == null || "".equals(cli.getEmail()))
            {
                email = "NULL";
            }
            else{
                email = "'" + cli.getEmail() + "'";
            }
            
            if(cli.getTelefono2()  == 0)
            {
                telefono2 = "NULL";
            }
            else{
                telefono2 = "'" + cli.getTelefono2() + "'";
            }
            
            if(cli.getCodigopostal()  == 0)
            {
                codigopostal = "NULL";
            }
            else{
                codigopostal = "'" + cli.getCodigopostal() + "'";
            }
            
                     System.out.println("Nombre: "+ cli.getNombre() );
                     System.out.println("Apellidos: " + cli.getApellidos());
                     System.out.println("NIF: " + cli.getNif());
                      System.out.println("Direccion: "+ cli.getDireccion());
                      System.out.println("Ciudad: " + cli.getCiudad().getId() );
                      System.out.println("Provincia: " + cli.getProvincia().getId());
                      System.out.println("Telefono: " +cli.getTelefono());
                      System.out.println("Telefono2: " + telefono2 );
                      System.out.println("Email: " + email );
            
            return execute("insert into zk_cliente(nombre, apellidos, nif, direccion, ciudad, provincia, telefono, telefono2, email, codigopostal) " +
                "values ('" 
                    + cli.getNombre() + "','" 
                    + cli.getApellidos() + "','" 
                    + cli.getNif() + "','" 
                    + cli.getDireccion() + "','" 
                    + cli.getCiudad().getId() + "','" 
                    + cli.getProvincia().getId() +  "','" 
                    + cli.getTelefono() + "'," 
                    + telefono2 +  "," 
                    + email + ","
                    + codigopostal + ")");
	}
	
	public boolean update(Cliente cli) {
            String email;
            String telefono2;
            String codigopostal;
            
            if(cli.getEmail() == null || "".equals(cli.getEmail()))
            {
                email = "NULL";
            }
            else{
                email = "'" + cli.getEmail() + "'";
            }
            
            if(cli.getTelefono2()  == 0)
            {
                telefono2 = "NULL";
            }
            else{
                telefono2 = "'" + cli.getTelefono2() + "'";
            }
            
            if(cli.getCodigopostal()  == 0)
            {
                codigopostal = "NULL";
            }
            else{
                codigopostal = "'" + cli.getCodigopostal() + "'";
            }
            
            System.out.println("UPDATE zk_cliente SET " +
                    "nombre = '"    + cli.getNombre() + "', " +
                    "apellidos = '" + cli.getApellidos() + "', " + 
                    "nif = '"       + cli.getNif() +  "'," +
                    "direccion = '" + cli.getDireccion()    + "' , " +
                    "ciudad = '"    + cli.getCiudad().getId()       + "' , " +
                    "provincia = '" + cli.getProvincia().getId()    + "' , " +
                    "telefono = '"  + cli.getTelefono()     + "' , " +
                    "telefono2 = " + telefono2    + " , " +
                    "email = "     + email        + " ,  " +
                    "codigopostal = "     + codigopostal        + 
                    " WHERE id = '" + cli.getId() + "'");
            
            return execute("UPDATE zk_cliente SET " +
                    "nombre = '"    + cli.getNombre() + "', " +
                    "apellidos = '" + cli.getApellidos() + "', " + 
                    "nif = '"       + cli.getNif() +  "'," +
                    "direccion = '" + cli.getDireccion()    + "' , " +
                    "ciudad = '"    + cli.getCiudad().getId()       + "' , " +
                    "provincia = '" + cli.getProvincia().getId()    + "' , " +
                    "telefono = '"  + cli.getTelefono()     + "' , " +
                    "telefono2 = " + telefono2    + " , " +
                    "email = "     + email        + " , " +
                    "codigopostal = "     + codigopostal        + 
                    " WHERE id = '" + cli.getId() + "'"
                    );
        }
    /*
        public void consultaClienteMascota(Cliente cli){
                final HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("value1", cli.getNif() );
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
