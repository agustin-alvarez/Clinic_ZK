package es.clinica.veterinaria.citas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceCita {
	
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

    private DataSourceCita() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
                    //stmt.executeUpdate("drop table zk_cita if exists");
                    stmt.executeUpdate(" CREATE TABLE IF NOT EXISTS `zk_cita` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `fecha` date NOT NULL, " +
                                       " `hora` time DEFAULT NULL, " +
                                       " `informe` varchar(500) DEFAULT NULL, " +
                                       " `tipo` int(1) DEFAULT NULL, " +
                                       " `id_mascota` int(11) NOT NULL, " +
                                       " `id_cliente` int(11) NOT NULL, " +
                                       " `estado` tinyint(4) NOT NULL DEFAULT '2' COMMENT '0)Cancelado 1)Acudido 2)Pendiente 3)Avisado', " +
                                       " `id_empleado` int(11) NOT NULL, " +
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