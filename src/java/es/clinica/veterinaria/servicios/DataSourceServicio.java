
package es.clinica.veterinaria.servicios;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SaRCo
 */
public enum DataSourceServicio {
    	
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

    private DataSourceServicio() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_servicio` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `codigo` varchar(9) NOT NULL, " +
                                       " `servicio` varchar(200) NOT NULL, " +
                                       " `descripcion` varchar(200) DEFAULT NULL, " +
                                       " `precio` float NOT NULL, " +
                                       " `id_iva` int(11) NOT NULL, " +
                                       " `id_familia` int(11) DEFAULT NULL, " +
                                       " PRIMARY KEY (`id`) " +
                                       ") ENGINE=InnoDB  DEFAULT CHARSET=utf8 ;");
                    
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