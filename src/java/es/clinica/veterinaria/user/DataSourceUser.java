package es.clinica.veterinaria.user;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceUser {
	
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

	private DataSourceUser() {
		// drop the table if it exists
		try {
			Statement stmt = this.getStatement();
                        System.out.println("Entra en DataSourceUser()");
			//stmt.executeUpdate("DROP TABLE zk_usuario IF EXISTS");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_usuario` ( " +
                                           " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                           " `user` varchar(20) NOT NULL, " +
                                           " `password` varchar(50) NOT NULL, " +
                                           " `tipo` int(2) NOT NULL DEFAULT '3' COMMENT '1)Admin 2)Veterinario 3)Empleado', " +
                                           " `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                                           " `nombre` varchar(100) NOT NULL, " +
                                           " `apellidos` varchar(100) NOT NULL, " +
                                           " `nif` varchar(10) NOT NULL, " +
                                           " `direccion` varchar(100) NOT NULL, " +
                                           " `ciudad` int(9) NOT NULL, " +
                                           " `provincia` int(9) NOT NULL, " +
                                           " `telefono` int(9) DEFAULT NULL, " +
                                           " `movil` int(9) DEFAULT NULL, " +
                                           " `email` varchar(100) DEFAULT NULL, " +
                                           " `nss` varchar(100) DEFAULT NULL, " +
                                           " PRIMARY KEY (`id`), " +
                                           " UNIQUE KEY `user` (`user`) " +
                                           ") ENGINE=InnoDB  DEFAULT CHARSET=utf8;");
                        
                        ResultSet rs = stmt.executeQuery("SELECT * FROM zk_usuario");
                        rs.last();
                        
                        if(rs.getRow() == 0){
                            
                            stmt.executeUpdate("INSERT INTO `zk_usuario` ("
                                                    + "`id`, "
                                                    + "`user`, "
                                                    + "`password`, "
                                                    + "`tipo`, "
                                                    + "`fecha_alta`, "
                                                    + "`nombre`, "
                                                    + "`apellidos`, "
                                                    + "`nif`, "
                                                    + "`direccion`, "
                                                    + "`ciudad`, "
                                                    + "`provincia`, "
                                                    + "`telefono`, "
                                                    + "`movil`, "
                                                    + "`email`, "
                                                    + "`nss`) "
                                            + "VALUES"
                                                    + "(1, "
                                                    + "'admin', "
                                                    + "'21232f297a57a5a743894a0e4a801fc3', " //La contraseña es 'admin'
                                                    + "1, "
                                                    + "'2012-08-24 00:00:00', "
                                                    + "'Administrador', "
                                                    + "'Administrador', "
                                                    + "'00000000A', "
                                                    + "'', "
                                                    + "0, "
                                                    + "0, "
                                                    + "NULL, "
                                                    + "NULL, "
                                                    + "NULL, "
                                                    + "NULL);");
                        }
                
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