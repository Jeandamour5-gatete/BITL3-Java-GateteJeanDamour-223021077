//Umuhire Elie  223014167
//Gatete Jean d'Amour   223021077
//Umuhoza Uwase Rosine  222016920

package telecom.system;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DB {
	private static final String URL = "jdbc:mysql://localhost:3306/tel?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
	private static final String USER = "root"; 
	private static final String PASS = "password";
	Connection con = DriverManager.getConnection(URL, USER, PASS);
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}

