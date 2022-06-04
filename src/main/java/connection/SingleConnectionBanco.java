package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBanco {
	
	private static String banco = "jdbc:postgresql://ec2-34-198-186-145.compute-1.amazonaws.com:5432/dbuaekhkslc2e8?sslmode=require&autoReconnect=true";
	
	private static String user = "wioftumdikspcg";
	
	private static String senha = "b2188164b7a81ec8dc5a0fd119e1d10428da9f5ddf02ff30a8b94279bdf67d5c";
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		return connection;
	}
	
	static {
		conectar();
	}
	
	public SingleConnectionBanco() {
		conectar();
	}
	
	public static void conectar() {
		
		try {
			if(connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
