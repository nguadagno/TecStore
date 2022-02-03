package model;

import java.sql.*;

import java.util.LinkedList;
import java.util.List;

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

}
