package es.clinica.veterinaria.user;

/**
 * Event DAO.
 * 
 * @author Agustin Alvarez
 */
import es.clinica.veterinaria.poblaciones.Poblacion;
import es.clinica.veterinaria.poblaciones.PoblacionDAO;
import es.clinica.veterinaria.provincias.Provincia;
import es.clinica.veterinaria.provincias.ProvinciaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
        private final DataSourceUser ds = DataSourceUser.INSTANCE;
	StringMD stringMD;
	
	
	public List<User> findAll() {
		List<User> allEvents = new ArrayList<User>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_usuario");

			// fetch all events from database
			User user;
			
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
                                user.setUser(rs.getString(2));
                                user.setPassword(rs.getString(3));
                                user.setTipo(rs.getInt(4));
                                user.setFecha_alta(rs.getDate(5));
                                user.setNombre(rs.getString(6));
                                user.setApellidos(rs.getString(7));
                                user.setNif(rs.getString(8));
                                user.setDireccion(rs.getString(9));
                                
                                PoblacionDAO pob = new PoblacionDAO();
                                List<Poblacion> poblist = pob.findAll("select * from zk_poblacion where id= " + rs.getInt(10));
                                if(poblist.size() == 1) {
                                    user.setPoblacion(poblist.get(0));
                                }
                                
                                //cli.setProvincia(rs.getString(7));
                                ProvinciaDAO pro = new ProvinciaDAO();
                                List<Provincia> prolist = pro.findAll("select * from zk_provincia where id= " + rs.getInt(11));
                                if(prolist.size() == 1) {
                                    user.setProvincia(prolist.get(0));
                                }
                                
                                user.setTelefono(rs.getInt(12));
                                user.setMovil(rs.getInt(13));
                                user.setEmail(rs.getString(14));
                                user.setNss(rs.getString(15));
                                
                                
				allEvents.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<User> findAll(String consulta) {
		List<User> allEvents = new ArrayList<User>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			User user;
			
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(1));
                                user.setUser(rs.getString(2));
                                user.setPassword(rs.getString(3));
                                user.setTipo(rs.getInt(4));
                                user.setFecha_alta(rs.getDate(5));
                                user.setNombre(rs.getString(6));
                                user.setApellidos(rs.getString(7));
                                user.setNif(rs.getString(8));
                                user.setDireccion(rs.getString(9));
                                
                                PoblacionDAO pob = new PoblacionDAO();
                                List<Poblacion> poblist = pob.findAll("select * from zk_poblacion where id= " + rs.getInt(10));
                                if(poblist.size() == 1) {
                                    user.setPoblacion(poblist.get(0));
                                }
                                
                                //cli.setProvincia(rs.getString(7));
                                ProvinciaDAO pro = new ProvinciaDAO();
                                List<Provincia> prolist = pro.findAll("select * from zk_provincia where id= " + rs.getInt(11));
                                if(prolist.size() == 1) {
                                    user.setProvincia(prolist.get(0));
                                }
                                
                                user.setTelefono(rs.getInt(12));
                                user.setMovil(rs.getInt(13));
                                user.setEmail(rs.getString(14));
                                user.setNss(rs.getString(15));
                                
				allEvents.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public User findById(int id) {
            List<User> allUser = findAll("select * from zk_usuario where id='" + id + "'");
            
            int i=0;
            while(i<allUser.size() && allUser.get(i).getId() != id){
                ++i;
            }
            return allUser.get(i);
	}

	public User findUserByName(String name) {
            List<User> allUser = findAll("select * from zk_usuario where user='" + name + "'");
            if(!allUser.isEmpty()){
                int i=0;
                while(i<allUser.size() && !allUser.get(i).getUser().equals(name)){
                    ++i;
                }
                return allUser.get(i);
            }
            else{
                return new User();
            }
	}
        
	
	public boolean delete(User user) {
		return execute("delete from zk_usuario where id = '" + user.getId() + "'");
	}
        
	public boolean insert(User user) {
                String telefono, movil, email, nss;
                String pass_encript = StringMD.getStringMessageDigest(user.getPassword(),StringMD.MD5);
                
                if(user.getTelefono() == 0 ) {
                    telefono = "NULL";
                }
                else {
                    telefono = "'" + user.getTelefono() + "'";
                }
                if(user.getMovil() == 0 ) {
                    movil = "NULL";
                }
                else {
                    movil = "'" + user.getMovil() + "'";
                }
                if(user.getEmail() == null || "".equals(user.getEmail())) {
                    email = "NULL";
                }
                else {
                    email = "'" + user.getEmail() + "'";
                }
                if(user.getNss() == null || "".equals(user.getNss())) {
                    nss = "NULL";
                }
                else {
                    nss = "'" + user.getNss() + "'";
                }
                
		return execute("insert into zk_usuario(user, password, tipo, nombre, apellidos, nif, direccion,"
                        + "ciudad, provincia, telefono, movil, email, nss) " +
                               "values ('" 
                                            + user.getUser()  + "', '" 
                                            + pass_encript      + "', '" 
                                            + user.getTipo()    + "', '" 
                                            + user.getNombre()  + "', '" 
                                            + user.getApellidos()+ "', '" 
                                            + user.getNif()     + "', '" 
                                            + user.getDireccion()+ "', '" 
                                            + user.getPoblacion().getId() + "', '" 
                                            + user.getProvincia().getId()+ "', " 
                                            + telefono    + ", " 
                                            + movil   + ", " 
                                            + email   + ", " 
                                            + nss     + ")"
                               );
	}
	
	public boolean update(User user) {
            int rol;
            String movil, email, nss;
            String pass_encript = StringMD.getStringMessageDigest(user.getPassword(),StringMD.MD5);
            List<User> list = new UserDAO().findAll("SELECT * FROM zk_usuario WHERE id=" + user.getId());
                
            if(user.getMovil() == 0 ) {
                movil = "NULL";
            }
            else {
                movil = "'" + user.getMovil() + "'";
            }
            if(user.getEmail() == null || "".equals(user.getEmail())) {
                email = "NULL";
            }
            else {
                email = "'" + user.getEmail() + "'";
            }
            if(user.getNss() == null || "".equals(user.getNss())) {
                nss = "NULL";
            }
            else {
                nss = "'" + user.getNss() + "'";
            }
            if(user.getTipo() == 0) {
                rol = list.get(0).getTipo();
            }
            else{
                rol = user.getTipo();
            }
            
            //Si el password es igual al anterior no se modifica
            if(list.get(0).getPassword().equals(user.getPassword())){ 
                return execute("UPDATE zk_usuario SET "                 + 
                            "user = '"      + user.getUser()            + "', " + 
                            "tipo = '"      + rol                       + "', " +
                            "nombre = '"    + user.getNombre()          + "', " + 
                            "apellidos = '" + user.getApellidos()       + "', " +
                            "nif = '"       + user.getNif()             + "', " +
                            "direccion = '" + user.getDireccion()       + "', " + 
                            "ciudad = '" + user.getPoblacion().getId()  + "', " +
                            "provincia = '" + user.getProvincia().getId()    + "', " +
                            "telefono = '"  + user.getTelefono()        + "', " + 
                            "movil = "      + movil                     + ", " +
                            "email = "      + email                     + ", " +
                            "nss = "        + nss                       + 
                            " WHERE id = '" + user.getId() + "'"
                    );
            }
            else{
                return execute("UPDATE zk_usuario SET "                 + 
                            "user = '"      + user.getUser()            + "', " + 
                            "password = '"  + pass_encript              + "', " +
                            "tipo = '"      + rol                       + "', " +
                            "nombre = '"    + user.getNombre()          + "', " + 
                            "apellidos = '" + user.getApellidos()       + "', " +
                            "nif = '"       + user.getNif()             + "', " +
                            "direccion = '" + user.getDireccion()       + "', " + 
                            "ciudad = '" + user.getPoblacion().getId()  + "', " +
                            "provincia = '" + user.getProvincia().getId()    + "', " +
                            "telefono = '"  + user.getTelefono()        + "', " + 
                            "movil = "      + movil                     + ", " +
                            "email = "      + email                     + ", " +
                            "nss = "        + nss                       + 
                            " WHERE id = '" + user.getId() + "'"
                    );
            }
        }
        
        public boolean change(User user){
            String pass_encript = StringMD.getStringMessageDigest(user.getPassword(),StringMD.MD5);
            return execute("UPDATE zk_usuario SET "  + 
                    "password = '"  + pass_encript   + "'" +
                    " WHERE id = '" + user.getId() + "'");
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