package es.clinica.veterinaria.productos;

/**
 * Event DAO.
 * 
 * @author Agustin Alvarez
 */
import es.clinica.veterinaria.iva.Iva;
import es.clinica.veterinaria.iva.IvaDAO;
import es.clinica.veterinaria.producto_familia.ProductoFamilia;
import es.clinica.veterinaria.producto_familia.ProductoFamiliaDAO;
import es.clinica.veterinaria.proveedores.Proveedor;
import es.clinica.veterinaria.proveedores.ProveedorDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {	
	
	private final DataSourceProducto ds = DataSourceProducto.INSTANCE;
	
	public List<Producto> findAll() {
		List<Producto> allEvents = new ArrayList<Producto>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery("select * from zk_producto");

			// fetch all events from database
			Producto prod;
			
			while (rs.next()) {
				prod = new Producto();
				prod.setId (rs.getInt(1));
//                                prod.setFamilia (rs.getInt(2));
                                ProductoFamiliaDAO familia = new ProductoFamiliaDAO();
                                List<ProductoFamilia> famlist = familia.findAll("select * from zk_producto_familia where id= " + rs.getInt(2));
                                if(famlist.size() == 1)
                                    prod.setFamilia(famlist.get(0));
//                                prod.setId_proveedor (rs.getInt(3));
                                
                                ProveedorDAO prov = new ProveedorDAO();
                                List<Proveedor> provlist = prov.findAll("select * from zk_proveedor where id= " + rs.getInt(3));
                                if(provlist.size() == 1)
                                    prod.setProveedor(provlist.get(0));
                                
                                prod.setCodigo (rs.getString(4));
                                prod.setNombre (rs.getString(5));
				prod.setPvp (rs.getFloat(6)); 
                                
                                //prod.setIva (rs.getInt(7));
                                IvaDAO iva = new IvaDAO();
                                List<Iva> ivalist = iva.findAll("select * from zk_iva where id= " + rs.getInt(7));
                                if(ivalist.size() == 1)
                                    prod.setIva(ivalist.get(0));
                                
                                prod.setPrecio (rs.getFloat(8));
                                prod.setStock (rs.getInt(9));
                                prod.setDescripcion (rs.getString(10));
                                prod.setFotografia (rs.getString(11));
                                prod.setObservaciones (rs.getString(12));
                                prod.setFecha_alta (rs.getDate(14));
				allEvents.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<Producto> findAll(String consulta) {
		List<Producto> allEvents = new ArrayList<Producto>();
		try {
			// get connection
		    Statement stmt = ds.getStatement();
			ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Producto prod;
			
			while (rs.next()) {
				prod = new Producto();
				prod.setId (rs.getInt(1));
//                                prod.setFamilia (rs.getInt(2));
                                ProductoFamiliaDAO familia = new ProductoFamiliaDAO();
                                List<ProductoFamilia> famlist = familia.findAll("select * from zk_producto_familia where id= " + rs.getInt(2));
                                if(famlist.size() == 1) {
                                    prod.setFamilia(famlist.get(0));
                                }
//                                prod.setId_proveedor (rs.getInt(3));
                                
                                ProveedorDAO prov = new ProveedorDAO();
                                List<Proveedor> provlist = prov.findAll("select * from zk_proveedor where id= " + rs.getInt(3));
                                if(provlist.size() == 1) {
                                    prod.setProveedor(provlist.get(0));
                                }
                                
                                prod.setCodigo (rs.getString(4));
                                prod.setNombre (rs.getString(5));
				prod.setPvp (rs.getFloat(6));
                                
                                //prod.setIva (rs.getInt(7));
                                IvaDAO iva = new IvaDAO();
                                List<Iva> ivalist = iva.findAll("select * from zk_iva where id= " + rs.getInt(7));
                                if(ivalist.size() == 1) {
                                    prod.setIva(ivalist.get(0));
                                }
                                
                                prod.setPrecio (rs.getFloat(8));
                                prod.setStock (rs.getInt(9));
                                prod.setDescripcion (rs.getString(10));
                                prod.setFotografia (rs.getString(11));
                                prod.setObservaciones (rs.getString(12));
                                prod.setFecha_alta (rs.getDate(14));
				allEvents.add(prod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
	
	public boolean delete(Producto prod) {
		return execute("delete from zk_producto where id = '" + prod.getId() + "'");
	}
	
	public boolean insert(Producto prod) {
            String descripcion, foto, observacion;        
            
            if(prod.getDescripcion() == null || "".equals(prod.getDescripcion())) {
                descripcion = "NULL";
            }
            else {
                descripcion = "'" + prod.getDescripcion() + "'";
            }
            
            if(prod.getFotografia() == null || "".equals(prod.getFotografia())) {
                foto = "NULL";
            }
            else {
                foto = "'" + prod.getFotografia() + "'";
            }
            
            if(prod.getObservaciones() == null || "".equals(prod.getObservaciones())) {
                observacion = "NULL";
            }
            else {
                observacion = "'" + prod.getObservaciones() + "'";
            }
            
		return execute("insert into zk_producto("
                                    + "id_familia, "
                                    + "id_proveedor, "
                                    + "codigo, "
                                    + "nombre, "
                                    + "pvp, "
                                    + "iva, "
                                    + "precio,"
                                    + "stock, "
                                    + "descripcion, "
                                    + "fotografia, "
                                    + "observaciones)" +
                                "values ('" 
                                        + prod.getFamilia().getId()     +   "','"
                                        + prod.getProveedor().getId()   +   "','"
                                        + prod.getCodigo()              +   "','" 
                                        + prod.getNombre()              +   "','" 
                                        + prod.getPvp()                 +   "','"
                                        + prod.getIva().getId()         +   "','"
                                        + prod.getPrecio()              +   "','"
                                        + prod.getStock()               +   "',"
                                        + descripcion                   +   ","
                                        + foto                          +   ","
                                        + observacion                   +   
                                    ")");
	}
	
	public boolean update(Producto prod) {
            return execute("UPDATE zk_producto SET "
                            +   "id_familia = '"    + prod.getFamilia().getId()     + "', "
                            +   "id_proveedor = '"  + prod.getProveedor().getId()   + "',"
                            +   "codigo = '"        + prod.getCodigo()              + "', "
                            +   "nombre = '"        + prod.getNombre()              + "', " 
                            +   "pvp    = '"        + prod.getPvp()                 + "', "
                            +   "iva    = '"        + prod.getIva().getId()         + "', "
                            +   "precio = '"        + prod.getPrecio()              + "', "
                            +   "stock = '"         + prod.getStock()               + "', "
                            +   "descripcion = '"   + prod.getDescripcion()         + "', "
                            +   "fotografia = '"    + prod.getFotografia()          + "', "
                            +   "observaciones = '" + prod.getObservaciones()       + "' " +
//                            +   "fecha_alta = '"    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(prod.getFecha_alta()) +  
                            " WHERE id = '"         + prod.getId()              + "'"
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