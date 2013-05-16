package es.clinica.veterinaria.mascotas;

/**
 * Event DAO.
 * 
 * @author Agustin Alvarez
 */

import es.clinica.veterinaria.clientes.Cliente;
import es.clinica.veterinaria.clientes.ClienteDAO;
import es.clinica.veterinaria.especies.Especie;
import es.clinica.veterinaria.especies.EspecieDAO;
import es.clinica.veterinaria.razas.Raza;
import es.clinica.veterinaria.razas.RazaDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
//import java.util.UUID;

public class MascotaDAO {	
    
	private final DataSourceMascota ds = DataSourceMascota.INSTANCE;
	
        public List<Mascota> findAll() {
		
            List<Mascota> allEvents = new ArrayList<Mascota>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery("select * from zk_mascota order by fecha_baja asc");

			// fetch all events from database
			Mascota masc;
			
			while (rs.next()) {
				masc = new Mascota();
				masc.setId(rs.getInt(1));
                                masc.setChip(rs.getString(2));
                                masc.setNombre(rs.getString(3));
                                masc.setSexo(rs.getString(4));
				masc.setNacimiento(rs.getDate(5));
                                masc.setDefuncion(rs.getDate(6));
                                masc.setPeso(rs.getFloat(7));
                                masc.setAltura(rs.getFloat(8));
                                masc.setObserv(rs.getString(9));
//                                masc.setEspecie(rs.getString(10));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(10));
                                if(esplist.size() == 1) {
                                    masc.setEspecie(esplist.get(0));
                                }
                                
                                masc.setPelo(rs.getString(11));
//                                masc.setRaza(rs.getString(12));
                                RazaDAO raza = new RazaDAO();
                                List<Raza> razalist = raza.findAll("select * from zk_raza where id= " + rs.getInt(12));
                                if(razalist.size() == 1) {
                                    masc.setRaza(razalist.get(0));
                                }
                                
				masc.setFechaalta(rs.getDate(13));
                                masc.setFechabaja(rs.getDate(14));
                                
                                ClienteDAO cli = new ClienteDAO();
                                List<Cliente> clilist = cli.findAll("select * from zk_cliente where id="+rs.getInt(15));
                                if(clilist.size() == 1) {
                                    masc.setCliente(clilist.get(0));
                                }
                                
				allEvents.add(masc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        public List<Mascota> findAll(String consulta) {
		
            List<Mascota> allEvents = new ArrayList<Mascota>();
		try {
			// get connection
                        Statement stmt = ds.getStatement();
                        ResultSet rs = stmt.executeQuery(consulta);

			// fetch all events from database
			Mascota masc;
			
			while (rs.next()) {
				masc = new Mascota();
				masc.setId(rs.getInt(1));
                                masc.setChip(rs.getString(2));
                                masc.setNombre(rs.getString(3));
                                masc.setSexo(rs.getString(4));
				masc.setNacimiento(rs.getDate(5));
                                masc.setDefuncion(rs.getDate(6));
                                masc.setPeso(rs.getFloat(7));
                                masc.setAltura(rs.getFloat(8));
                                masc.setObserv(rs.getString(9));
//                                masc.setEspecie(rs.getString(10));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(10));
                                if(esplist.size() == 1) {
                                    masc.setEspecie(esplist.get(0));
                                }
                                
                                masc.setPelo(rs.getString(11));
                                
//                                masc.setRaza(rs.getString(12));
                                RazaDAO raza = new RazaDAO();
                                List<Raza> razalist = raza.findAll("select * from zk_raza where id= " + rs.getInt(12));
                                if(razalist.size() == 1) {
                                    masc.setRaza(razalist.get(0));
                                }
                                
				masc.setFechaalta(rs.getDate(13));
                                masc.setFechabaja(rs.getDate(14));
                                
                                ClienteDAO cli = new ClienteDAO();
                                List<Cliente> clilist = cli.findAll("select * from zk_cliente where id="+rs.getInt(15));
                                if(clilist.size() == 1) {
                                    masc.setCliente(clilist.get(0));
                                }
                                
				allEvents.add(masc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allEvents;
	}
        
        
	
	public boolean delete(Mascota masc) {
		return execute("delete from zk_mascota where id = '" + masc.getId() + "'");
	}
	
	public boolean insert(Mascota masc) {
            return execute("INSERT INTO  zk_mascota (chip, nombre, sexo, fecha_nac, peso, altura, observaciones, especie, pelo, raza, id_cliente )" +
                           " VALUES ('" + masc.getChip()        + "','" 
                                        + masc.getNombre()      + "','"
                                        + masc.getSexo()        + "','"
                                        + new SimpleDateFormat("yyyy-MM-dd").format(masc.getNacimiento())  + "','"
                                        + masc.getPeso()        + "','"
                                        + masc.getAltura()      + "','"
                                        + masc.getObserv()      + "','"
                                        + masc.getEspecie().getId()     + "','"
                                        + masc.getPelo()        + "','"
                                        + masc.getRaza().getId()       + "','"
                                        + masc.getCliente().getId() + "')");

	}
	
	public boolean update(Mascota masc) {
            String defuncion, fbaja, observ;
            if(masc.getDefuncion()== null) {
                defuncion = "NULL";
            }
            else {
                defuncion = "'"     + new SimpleDateFormat("yyyy-MM-dd").format(masc.getDefuncion())   + "'";
            }
            
            if(masc.getFechabaja()== null) {
                fbaja = "NULL";
            }
            else {
                fbaja = "'"     + new SimpleDateFormat("yyyy-MM-dd").format(masc.getFechabaja())   + "'";
            }
            
            if(masc.getObserv()== null) {
                observ = "NULL";
            }
            else {
                observ = "'"     + masc.getObserv()   + "'";
            }
            
            if(!"NULL".equals(defuncion) && "NULL".equals(fbaja)){
                fbaja = defuncion;
            }
            
            if(!"NULL".equals(fbaja)){
                execute("DELETE FROM zk_cita WHERE id_mascota=" + masc.getId() + " AND CURDATE() < fecha");
            }
                
            return execute("UPDATE zk_mascota SET " + 
                            "chip = '"          + masc.getChip()        + "', " +
                            "nombre = '"        + masc.getNombre()      + "', " +
                            "sexo = '"          + masc.getSexo()        + "', " +
                            "fecha_nac = '"     + new SimpleDateFormat("yyyy-MM-dd").format(masc.getNacimiento())             + "', "  +
                            "fecha_def = "      + defuncion             + ", " +   
                            "peso = '"          + masc.getPeso()        + "', " +
                            "altura = '"        + masc.getAltura()      + "', " +
                            "observaciones = "  + observ                + ", " +
                            "especie = '"       + masc.getEspecie().getId()     + "', " +
                            "pelo = '"          + masc.getPelo()        + "', " +
                            "raza = '"          + masc.getRaza().getId()        + "', " +
                            "id_cliente= '"      + masc.getCliente().getId()     + "', " +
                            "fecha_baja = "     +  fbaja  + 
                            //new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(masc.getFechaAlta()) + 
                            " WHERE id = '" + masc.getId() + "'"
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

    public List<Mascota> getClienteMascotas(Cliente cli) {
		List<Mascota> allMasc = new ArrayList<Mascota>();
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
                                masc.setSexo(rs.getString(4));
				masc.setNacimiento(rs.getDate(5));
                                masc.setDefuncion(rs.getDate(6));
                                masc.setPeso(rs.getFloat(7));
                                masc.setAltura(rs.getFloat(8));
                                masc.setObserv(rs.getString(9));
//                                masc.setEspecie(rs.getString(10));
                                
                                EspecieDAO esp = new EspecieDAO();
                                List<Especie> esplist = esp.findAll("select * from zk_especie where id= " + rs.getInt(10));
                                if(esplist.size() == 1)
                                    masc.setEspecie(esplist.get(0));
                                
                                masc.setPelo(rs.getString(11));
//                                masc.setRaza(rs.getString(12));
                                RazaDAO raza = new RazaDAO();
                                List<Raza> razalist = raza.findAll("select * from zk_raza where id= " + rs.getInt(12));
                                if(razalist.size() == 1)
                                    masc.setRaza(razalist.get(0));
                                
				masc.setFechaalta(rs.getDate(13));
                                masc.setFechabaja(rs.getDate(14));
				allMasc.add(masc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    ds.close();
		}
		
		return allMasc;
	}
    /*
    public List<Mascota> getFilterMascota(MascotaFilter mascotaFilter) {
		List<Mascota> masc = new ArrayList<Mascota>();
                //List<Mascota> allEventos = findAll();
		for (Iterator<Mascota> i = allEvents.iterator(); i.hasNext();) 
                {
			Mascota tmp = i.next();
			if (tmp.getChip().toLowerCase().startsWith(mascotaFilter.getChip())  >= 0 &&
                            tmp.getNombre().toLowerCase().startsWith(mascotaFilter.getNombre()) >= 0 ) 
                        {
				masc.add(tmp);
			}
		}
		return masc;
	}*/
}
