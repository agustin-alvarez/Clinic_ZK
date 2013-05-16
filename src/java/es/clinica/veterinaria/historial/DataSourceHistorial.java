package es.clinica.veterinaria.historial;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceHistorial {
	
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

    private DataSourceHistorial() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
                    //stmt.executeUpdate("drop table zk_cliente if exists");
                    //stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_historial` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `id_mascota` int(11) NOT NULL, " +
                                       " `id_veterinario` int(11) NOT NULL, " +
                                       " `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                                       " `tipo_visita` int(1) NOT NULL, " +
                                       " `id_peso` int(11) NOT NULL, " +
                                       " `anamnesis` varchar(500) DEFAULT NULL, " +
                                       " `diagnostico` varchar(500) DEFAULT NULL, " +
                                       " `tratamiento` varchar(500) DEFAULT NULL, " +
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