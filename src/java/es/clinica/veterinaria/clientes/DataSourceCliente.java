package es.clinica.veterinaria.clientes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceCliente {
	
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

    private DataSourceCliente() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
                    //stmt.executeUpdate("drop table zk_cliente if exists");
                    //stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_cliente` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `nombre` varchar(150) NOT NULL, " +
                                       " `apellidos` varchar(150) NOT NULL, " +
                                       " `nif` varchar(10) NOT NULL, " +
                                       " `direccion` varchar(250) NOT NULL, " +
                                       " `ciudad` INT(11) NOT NULL, " +
                                       " `provincia` INT(11) NOT NULL, " +
                                       " `telefono` int(9) NOT NULL, " +
                                       " `telefono2` int(9) DEFAULT NULL, " +
                                       " `email` varchar(200) DEFAULT NULL, " +
                                       " `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                                       " `codigopostal` int(5) DEFAULT NULL, " +
                                       " PRIMARY KEY (`id`), " +
                                       " UNIQUE KEY `nif` (`nif`) " +
                                     " ) ENGINE=InnoDB  DEFAULT CHARSET=utf8;");
                    
                    ResultSet rs = stmt.executeQuery("SELECT * FROM zk_cliente");
                    rs.last();
                    
                    if(rs.getRow() == 0){
                    
                        stmt.executeUpdate("INSERT INTO `zk_cliente` (`id`, `nombre`, `apellidos`, "
                                                              + "`nif`, `direccion`, `ciudad`, "
                                                              + "`provincia`, `telefono`, `telefono2`, "
                                                              + "`email`) VALUES "
                                            + "(1, 'Venta', 'Rápida', '00000000A', 'Anónimo', '1785', '11', 900000000, "
                                            + "NULL, 'ventarapida@ventarapida.com');");
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