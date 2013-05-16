package es.clinica.veterinaria.mascotas;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceMascota {
	
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

    private DataSourceMascota() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
                    //stmt.executeUpdate("drop table zk_mascota if exists");
                    //stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_mascota` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `chip` varchar(20) DEFAULT NULL, " +
                                       " `nombre` varchar(50) NOT NULL, " +
                                       " `sexo` varchar(6) DEFAULT NULL COMMENT '0)Macho 1)Hembra', " +
                                       " `fecha_nac` date NOT NULL, " +
                                       " `fecha_def` date DEFAULT NULL, " +
                                       " `peso` float DEFAULT NULL, " +
                                       " `altura` float DEFAULT NULL, " +
                                       " `observaciones` varchar(250) DEFAULT NULL, " +
                                       " `especie` varchar(100) DEFAULT NULL, " +
                                       " `pelo` varchar(100) DEFAULT NULL COMMENT '1)Corto 2)Duro 3)Largo 4)Medio 5)Medio-Largo 6)Propio 7)Rizado', " +
                                       " `raza` varchar(100) DEFAULT NULL, " +
                                       " `fecha_alta` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                                       " `fecha_baja` datetime DEFAULT NULL, " +
                                       " `id_cliente` int(11) NOT NULL, " +
                                       " PRIMARY KEY (`id`), " +
                                       " UNIQUE KEY `chip` (`chip`) " +
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