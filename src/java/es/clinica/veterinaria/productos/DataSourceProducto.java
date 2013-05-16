package es.clinica.veterinaria.productos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceProducto {
	
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

    private DataSourceProducto() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
                    //stmt.executeUpdate("drop table zk_producto if exists");
                    //stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_producto` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `id_familia` int(11) DEFAULT NULL, " +
                                       " `id_proveedor` int(11) DEFAULT NULL, " +
                                       " `codigo` varchar(50) NOT NULL, " +
                                       " `nombre` varchar(100) NOT NULL, " +
                                       " `pvp` float NOT NULL, " +
                                       " `iva` int(2) NOT NULL, " +
                                       " `precio` float DEFAULT NULL, " +
                                       " `stock` int(11) NOT NULL, " +
                                       " `descripcion` varchar(250) DEFAULT NULL, " +
                                       " `fotografia` varchar(250) DEFAULT NULL, " +
                                       " `observaciones` varchar(250) DEFAULT NULL, " +
                                       " `unidad` varchar(10) DEFAULT NULL, " +
                                       " `fecha_alta` timestamp NULL DEFAULT CURRENT_TIMESTAMP, " +
                                       " PRIMARY KEY (`id`), " +
                                       " UNIQUE KEY `codigo` (`codigo`) " +
                                       ") ENGINE=InnoDB  DEFAULT CHARSET=utf8");
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