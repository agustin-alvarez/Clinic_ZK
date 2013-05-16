/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.clinica.veterinaria.pedidos;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SaRCo
 */
public enum DataSourcePedido {
        	
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

    private DataSourcePedido() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
//                    stmt.executeUpdate("drop table zk_raza if exists");
                    //stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_pedido` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " +
                                       " `fecha_entrega` timestamp NULL DEFAULT NULL, " +
                                       " `fecha_pago` timestamp NULL DEFAULT NULL, " +
                                       " `pagado` tinyint(1) NOT NULL, " +
                                       " `id_proveedor` int(11) NOT NULL, " +
                                       " `id_empleado` int(11) NOT NULL, " +
                                       " PRIMARY KEY (`id`) " +
                                       ") ENGINE=InnoDB  DEFAULT CHARSET=utf8;"
                                       );
                    
                    
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