package es.clinica.veterinaria.proveedores;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProveedorDAO {	
	
	private final DataSourceProveedor ds = DataSourceProveedor.INSTANCE;
	
	public List<Proveedor> findAll() {
		List<Proveedor> allEvents = new ArrayList<Proveedor>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_proveedor");

			// fetch all events from database
			Proveedor prov;
			
			while (rs.next()) {
				prov = new Proveedor();
				prov.setId(rs.getInt(1));
                                prov.setNif(rs.getString(2));
                                prov.setNombre(rs.getString(3));
                                prov.setDireccion(rs.getString(4));
                                //prov.setPoblacion(rs.getInt(5));
                                PoblacionDAO pob = new PoblacionDAO();
                                List<Poblacion> poblist = pob.findAll("select * from zk_poblacion where id= " + rs.getInt(5));
                                if(poblist.size() == 1) {
                                    prov.setPoblacion(poblist.get(0));
                                }
                                
                                //cli.setProvincia(rs.getString(6));
                                ProvinciaDAO pro = new ProvinciaDAO();
                                List<Provincia> prolist = pro.findAll("select * from zk_provincia where id= " + rs.getInt(6));
                                if(prolist.size() == 1) {
                                    prov.setProvincia(prolist.get(0));
                                }
                                
                                prov.setTelefono(rs.getString(7));
                                prov.setTelefono2(rs.getString(8));
                                prov.setMovil(rs.getString(9));
                                prov.setFax(rs.getString(10));
                                prov.setEmail(rs.getString(11));
				prov.setFecha_alta(rs.getDate(12));
                                prov.setFecha_baja(rs.getDate(13));
                                prov.setObservaciones(rs.getString(14));
                                prov.setActivo(rs.getBoolean(15));
                                prov.setContacto(rs.getString(16));
				allEvents.add(prov);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<Proveedor> findAll(String consulta) {
		List<Proveedor> allEvents = new ArrayList<Proveedor>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Proveedor prov;
			
			while (rs.next()) {
				prov = new Proveedor();
				prov.setId(rs.getInt(1));
                                prov.setNif(rs.getString(2));
                                prov.setNombre(rs.getString(3));
                                prov.setDireccion(rs.getString(4));
                                //prov.setPoblacion(rs.getInt(5));
                                PoblacionDAO pob = new PoblacionDAO();
                                List<Poblacion> poblist = pob.findAll("select * from zk_poblacion where id= " + rs.getInt(5));
                                if(poblist.size() == 1) {
                                    prov.setPoblacion(poblist.get(0));
                                }
                                
                                //cli.setProvincia(rs.getString(6));
                                ProvinciaDAO pro = new ProvinciaDAO();
                                List<Provincia> prolist = pro.findAll("select * from zk_provincia where id= " + rs.getInt(6));
                                if(prolist.size() == 1) {
                                    prov.setProvincia(prolist.get(0));
                                }
                                
                                prov.setTelefono(rs.getString(7));
                                prov.setTelefono2(rs.getString(8));
                                prov.setMovil(rs.getString(9));
                                prov.setFax(rs.getString(10));
                                prov.setEmail(rs.getString(11));
				prov.setFecha_alta(rs.getDate(12));
                                prov.setFecha_baja(rs.getDate(13));
                                prov.setObservaciones(rs.getString(14));
                                prov.setActivo(rs.getBoolean(15));
                                prov.setContacto(rs.getString(16));
				allEvents.add(prov);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
	public boolean delete(Proveedor cli) {
		return execute("delete from zk_proveedor where id = '" + cli.getId() + "'");
	}
	
	public boolean insert(Proveedor prov) {
            String telefono2, movil, fax,
                   email, fecha_baja, observaciones, contacto;
            
            if(prov.getTelefono2() == null || "".equals(prov.getTelefono2())){
                telefono2 = "NULL";
            }
            else{
                telefono2 = "'" + prov.getTelefono2() + "'";
            }
            
            if(prov.getMovil() == null || "".equals(prov.getTelefono2())){
                movil = "NULL";
            }
            else{
                movil = prov.getMovil() + "";
            }
            
            if(prov.getFax() == null || "".equals(prov.getTelefono2())){
                fax = "NULL";
            }
            else{
                fax = prov.getFax() + "";
            }
            
            if(prov.getEmail() == null || "".equals(prov.getEmail())){
                email = "NULL";
            }
            else{
                email = "'" + prov.getEmail() + "'";
            }
            
            if(prov.getObservaciones() == null || "".equals(prov.getObservaciones())){
                observaciones = "NULL";
            }
            else{
                observaciones = "'" + prov.getObservaciones() + "'";
            }
            
            if(prov.getContacto() == null || "".equals(prov.getContacto())){
                contacto = "NULL";
            }
            else{
                contacto = "'" + prov.getContacto() + "'";
            }
            
            if(prov.getFecha_baja() == null ){
                fecha_baja = "NULL";
            }
            else{
                fecha_baja = "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prov.getFecha_baja()) + "'";
            }
            
            
		return execute("insert into zk_proveedor(nif, nombre, direccion, poblacion, provincia, telefono, telefono2, " +
                               "movil, fax, email, fecha_baja, observaciones, activo, contacto) " +
                               "values ('" + 
                                prov.getNif()       + "','" + 
                                prov.getNombre()    + "','" + 
                                prov.getDireccion() + "','" + 
                                prov.getPoblacion().getId() + "','" + 
                                prov.getProvincia().getId() + "', " +
                                prov.getTelefono()  + ", " + 
                                telefono2           + ", " + 
                                movil               + ", " + 
                                fax                 + ", " + 
                                email               + ", " +
                                fecha_baja          + ", " +
                                observaciones       + ", " +
                                " 1, " + 
                                contacto + ")" );
	}
	
	public boolean update(Proveedor prov) {
            String telefono2, movil, fax,
                   email, fecha_baja, observaciones, contacto;
            
            if(prov.getTelefono2() == null || "".equals(prov.getTelefono2())){
                telefono2 = "NULL";
            }
            else{
                telefono2 = "'" + prov.getTelefono2() + "'";
            }
            
            if(prov.getMovil() == null || "".equals(prov.getTelefono2())){
                movil = "NULL";
            }
            else{
                movil = "'" + prov.getMovil() + "'";
            }
            
            if(prov.getFax() == null || "".equals(prov.getTelefono2())){
                fax = "NULL";
            }
            else{
                fax = "'" + prov.getFax() + "'";
            }
            
            if(prov.getEmail() == null || "".equals(prov.getEmail())){
                email = "NULL";
            }
            else{
                email = "'" + prov.getEmail() + "'";
            }
            
            if(prov.getObservaciones() == null || "".equals(prov.getObservaciones())){
                observaciones = "NULL";
            }
            else{
                observaciones = "'" + prov.getObservaciones() + "'";
            }
            
            if(prov.getContacto() == null || "".equals(prov.getContacto())){
                contacto = "NULL";
            }
            else{
                contacto = "'" + prov.getContacto() + "'";
            }
            
            if(prov.getFecha_baja() == null ){
                fecha_baja = "NULL";
            }
            else{
                fecha_baja = "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prov.getFecha_baja()) + "'";
            }
            
            return execute("UPDATE zk_proveedor SET "        + 
                    "nif = '"       + prov.getNif()          + "', " +
                    "nombre = '"    + prov.getNombre()       + "', " + 
                    "direccion = '" + prov.getDireccion()    + "', " +
                    "poblacion = "  + prov.getPoblacion().getId()    + ", " +
                    "provincia = "  + prov.getProvincia().getId()    + ", " +
                    "telefono = "   + prov.getTelefono()     + ", " +
                    "telefono2 = "  + telefono2              + ", " +
                    "movil = "      + movil                  + ", " +
                    "fax = "        + fax                    + ", " +
                    "email = "      + email                  + ", " +
                    "fecha_baja ="  + fecha_baja             + ", " +
                    //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prov.getFechaAlta()) + 
                    "observaciones = "     + observaciones   + ",  " +
                    "activo = "     + prov.isActivo()        + 
                    " WHERE id = '" + prov.getId() + "'"
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