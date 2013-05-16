package es.clinica.veterinaria.proveedores;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceProveedor {
	
	INSTANCE;
	
    private static final String url = "jdbc:mysql://localhost:3306/clinica";
    private static final String user = "root";
    private static final String pwd = "admin";

	private Connection conn = null;

	static {
		try {
			//new jdbcDriver();
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private DataSourceProveedor() {
		// drop the table if it exists
		try {
			Statement stmt = this.getStatement();
			//stmt.executeUpdate("DROP TABLE zk_proveedor IF EXISTS");
			//stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_proveedor` ( " +
                                           " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                           " `nif` varchar(20) NOT NULL, " +
                                           " `nombre` varchar(200) NOT NULL, " +
                                           " `direccion` varchar(200) NOT NULL, " +
                                           " `poblacion` int(11) NOT NULL, " +
                                           " `provincia` int(11) NOT NULL, " +
                                           " `telefono` int(11) NOT NULL, " +
                                           " `telefono2` int(11) DEFAULT NULL, " +
                                           " `movil` int(11) DEFAULT NULL, " +
                                           " `fax` int(11) DEFAULT NULL, " +
                                           " `email` varchar(100) DEFAULT NULL, " +
                                           " `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                                           " `fecha_baja` timestamp NULL DEFAULT NULL, " +
                                           " `observaciones` varchar(400) DEFAULT NULL, " +
                                           " `activo` tinyint(1) NOT NULL COMMENT '0)Inactivo 1)Activo', " +
                                           " `contacto` varchar(100) DEFAULT NULL, " +
                                           " PRIMARY KEY (`id`) " +
                                           ") ENGINE=InnoDB  DEFAULT CHARSET=utf8;");
                        stmt.close();
		} catch (SQLException e) {
                        e.printStackTrace();
		} finally {
			this.close();
		}
	}

	public Statement getStatement() throws SQLException { //Se produce la conexion con la BD
		Statement stmt;// = null;
		// get connection
		conn = DriverManager.getConnection(url, user, pwd);
		stmt = conn.createStatement();
		return stmt;
	}

	public void close() {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}