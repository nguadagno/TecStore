package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DriverManagerConnectionPool {

	private static List<Connection> freeDbConnections;

	static {
		freeDbConnections = new LinkedList<Connection>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
		}
	}

	private static synchronized Connection createDBConnection(String username, String password) throws SQLException {
		Connection newConnection = null;
		String ip = "192.168.1.80";
		String port = "3306";
		String db = "tecstore";

		newConnection = DriverManager.getConnection(
				"jdbc:mysql://" + ip + ":" + port + "/" + db + "?serverTimezone=UTC", username, password);

		newConnection.setAutoCommit(false);
		return newConnection;
	}

	public static synchronized Connection getConnection(String username, String password) throws SQLException {
		Connection connection;

		if (!freeDbConnections.isEmpty()) {
			connection = (Connection) freeDbConnections.get(0);
			freeDbConnections.remove(0);

			try {
				if (connection.isClosed())
					connection = getConnection(username, password);
			} catch (SQLException e) {
				connection.close();
				connection = getConnection(username, password);
			}
		} else {
			connection = createDBConnection(username, password);
		}

		return connection;
	}

	public static synchronized void releaseConnection(Connection connection) throws SQLException {
		if (connection != null)
			freeDbConnections.add(connection);
	}

	public static void resetDB(Connection conn) throws FileNotFoundException, SQLException {
		 importSQL(conn, new FileInputStream(new File("tecstore.sql")));
	}

	// Reference:
	// https://stackoverflow.com/questions/1497569/how-to-execute-sql-script-file-using-jdbc
	public static void importSQL(Connection conn, InputStream in) throws SQLException {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(in);
		s.useDelimiter("(;(\r)?\n)|(--\n)");
		Statement st = null;
		try {
			st = conn.createStatement();
			while (s.hasNext()) {
				String line = s.next();
				if (line.startsWith("/*!") && line.endsWith("*/")) {
					int i = line.indexOf(' ');
					line = line.substring(i + 1, line.length() - " */".length());
				}

				if (line.trim().length() > 0) {
					st.execute(line);
				}
			}
		} finally {
			if (st != null)
				st.close();
		}
	}

}
