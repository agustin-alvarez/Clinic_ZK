package es.clinica.veterinaria.provincias;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import org.hsqldb.jdbcDriver;

public enum DataSourceProvincia {
	
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

    private DataSourceProvincia() {
            // drop the table if it exists
            try {
                    Statement stmt = this.getStatement();
                    //stmt.executeUpdate("drop table zk_provincia if exists");
                    //stmt.executeUpdate("create table event (evtid VARCHAR(37) NOT NULL PRIMARY KEY, name VARCHAR(100), priority INTEGER, evtdate TIMESTAMP)");
                    stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `zk_provincia` ( " +
                                       " `id` int(11) NOT NULL AUTO_INCREMENT, " +
                                       " `provincia` varchar(200) NOT NULL, " +
                                       " PRIMARY KEY (`id`) " +
                                       ") ENGINE=InnoDB  DEFAULT CHARSET=utf8;");
                    
                    ResultSet rs = stmt.executeQuery("SELECT * FROM zk_provincia");
                    
                    rs.last();
                    
                    if(rs.getRow() == 0){
                        
                        stmt.executeUpdate("INSERT INTO `zk_provincia` (`id`, `provincia`) VALUES" +
                                       "  (1, 'Álava')," +
                                       "  (2, 'Albacete')," +
                                       "  (3, 'Alicante')," +
                                       "  (4, 'Almería')," +
                                       "  (5, 'Ávila')," +
                                       "  (6, 'Badajoz')," +
                                       "  (7, 'Baleares (Illes)')," +
                                       "  (8, 'Barcelona')," +
                                       "  (9, 'Burgos')," +
                                       "  (10, 'Cáceres')," +
                                       "  (11, 'Cádiz')," +
                                       "  (12, 'Castellón')," +
                                       "  (13, 'Ciudad Real')," +
                                       "  (14, 'Córdoba')," +
                                       "  (15, 'A Coruña')," +
                                       "  (16, 'Cuenca')," +
                                       "  (17, 'Girona')," +
                                       "  (18, 'Granada')," +
                                       "  (19, 'Guadalajara')," +
                                       "  (20, 'Guipúzcoa')," +
                                       "  (21, 'Huelva')," +
                                       "  (22, 'Huesca')," +
                                       "  (23, 'Jaén')," +
                                       "  (24, 'León')," +
                                       "  (25, 'Lleida')," +
                                       "  (26, 'La Rioja')," +
                                       "  (27, 'Lugo')," +
                                       "  (28, 'Madrid')," +
                                       "  (29, 'Málaga')," +
                                       "  (30, 'Murcia')," +
                                       "  (31, 'Navarra')," +
                                       "  (32, 'Ourense')," +
                                       "  (33, 'Asturias')," +
                                       "  (34, 'Palencia')," +
                                       "  (35, 'Las Palmas')," +
                                       "  (36, 'Pontevedra')," +
                                       "  (37, 'Salamanca')," +
                                       "  (38, 'Santa Cruz de Tenerife')," +
                                       "  (39, 'Cantabria')," +
                                       "  (40, 'Segovia')," +
                                       "  (41, 'Sevilla')," +
                                       "  (42, 'Soria')," +
                                       "  (43, 'Tarragona')," +
                                       "  (44, 'Teruel')," +
                                       "  (45, 'Toledo')," +
                                       "  (46, 'Valencia')," +
                                       "  (47, 'Valladolid')," +
                                       "  (48, 'Vizcaya')," +
                                       "  (49, 'Zamora')," +
                                       "  (50, 'Zaragoza')," +
                                       "  (51, 'Ceuta')," +
                                       "  (52, 'Melilla');");
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