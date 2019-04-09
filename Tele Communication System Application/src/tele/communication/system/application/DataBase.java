package tele.communication.system.application;

/**
 *
 * @author HAROLD FINCH
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private String username;
    private String password;
    private final String CONN_STRING = "jdbc:oracle:thin:@localhost:1521:orcl";
    public Connection connection = null;
    //private static OracleDBMS instance = null;

    public DataBase(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                connection = DriverManager.getConnection(CONN_STRING, username, password);
            } catch (SQLException e) {
                System.out.println("Connection Failed! Check it from console");
            }
        }

        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
