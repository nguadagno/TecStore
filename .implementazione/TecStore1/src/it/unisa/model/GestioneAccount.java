package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import Bean.UtenteBean;

public class GestioneAccount {
	public boolean registrazioneUtente(String CF, String nome, String cognome, String email, String password,
			String via, int numeroCivico, String citta, String provincia, int CAP, int tipologia, String cartaDiCredito)
			throws SQLException {
		return registrazioneUtente(new UtenteBean(CF, nome, cognome, email, password, via, numeroCivico, citta,
				provincia, CAP, tipologia, cartaDiCredito));
	}

	public boolean exists(UtenteBean utente) throws SQLException {
		return exists(utente.getCF());
	}

	public static boolean exists(String CF) throws SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			String selectClienteSQL = "SELECT * FROM cliente WHERE CF = `?`;";
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectClienteSQL);
			preparedStatement.setString(1, CF);
			ResultSet rs = preparedStatement.executeQuery();

			rs.last();
			return (rs.getRow() != 1);
		} finally {
			try {
				if (connection != null)
					connection.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public boolean registrazioneUtente(UtenteBean utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String registrazioneUtente = "INSERT INTO Utente (`CF`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `VIA`, `NUMEROCIVICO`, `CITTA`, `CAP`, `TIPOLOGIA`)"
				+ " VALUES (`?`,`?`,`?`,`?`,`?`,`?`,`?`,`?`,`?`,`?`);";
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(registrazioneUtente);

			preparedStatement.setString(1, utente.getCF());
			preparedStatement.setString(2, utente.getNome());
			preparedStatement.setString(3, utente.getCognome());
			preparedStatement.setString(4, utente.getEmail());
			preparedStatement.setString(5, utente.getPassword());
			preparedStatement.setString(6, utente.getVia());
			preparedStatement.setInt(7, utente.getNumeroCivico());
			preparedStatement.setString(8, utente.getCitta());
			preparedStatement.setInt(9, utente.getCAP());
			preparedStatement.setInt(10, utente.getTipologia());

			preparedStatement.executeUpdate();

			connection.commit();
			return true;
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public boolean eliminaUtente(String CF) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM WHERE CF_CLIENTE = `?`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, CF);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public boolean eliminaUtente(UtenteBean utente) throws SQLException {
		return eliminaUtente(utente.getCF());
	}

	public boolean modificaUtente(String CF, String nome, String cognome, String email, String password, String via,
			int numeroCivico, String citta, String provincia, int CAP, int tipologia, String cartaDiCredito)
			throws SQLException {
		return modificaUtente(CF, new UtenteBean(CF, nome, cognome, email, password, via, numeroCivico, citta,
				provincia, CAP, tipologia, cartaDiCredito));
	}

	public boolean modificaUtente(String CF, UtenteBean utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		UtenteBean u = dettagliUtente(CF);
		ResultSet rs = null;

		String updateUtenteQuery = "UPDATE Utente SET NOME = `?`, SET COGNOME = `?`, SET EMAIL = `?`, PASSWORD = `?`,"
				+ " VIA = `?`, CAP = `?`, NUMEROCIVICO = `?`, ,  CITTA = `?`, PROVINCIA=`?` WHERE CF = `?`;";
		try {
			connection = DriverManagerConnectionPool.getConnection();

			if (utente == null || (!utente.getEmail().equals(u.getEmail()) && !utente.checkEmail(utente.getEmail())))
				return false;

			if (!utente.getPassword().equals(u.getPassword())) {
				if (utente.checkPassword(utente.getPassword())) {
					String getPasswordQuery = "SELECT PASSWORD(`?`);";
					preparedStatement = connection.prepareStatement(getPasswordQuery);
					preparedStatement.setString(1, utente.getPassword());
					rs = preparedStatement.executeQuery();
					preparedStatement.close();
					rs.next();
					utente.setPassword(rs.getString(0));
				}
			}

			preparedStatement = connection.prepareStatement(updateUtenteQuery);
			preparedStatement.setString(1, utente.getNome());
			preparedStatement.setString(2, utente.getCognome());
			preparedStatement.setString(3, utente.getEmail());
			preparedStatement.setString(4, utente.getPassword());
			preparedStatement.setString(5, utente.getVia());
			preparedStatement.setInt(6, utente.getCAP());
			preparedStatement.setInt(7, utente.getNumeroCivico());
			preparedStatement.setString(8, utente.getCitta());
			preparedStatement.setString(9, utente.getProvincia());
			preparedStatement.setString(10, utente.getCF());
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public UtenteBean dettagliUtente(String CF) throws SQLException {
		UtenteBean result = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchUtenteQuery = "SELECT * FROM utente WHERE CF='" + CF + "';";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchUtenteQuery);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				result = new UtenteBean(rs.getString("CF"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("Email"), rs.getString("password"), rs.getString("Via"), rs.getInt("NumeroCivico"),
						rs.getString("Citta"), rs.getString("Provincia"), rs.getInt("CAP"), rs.getInt("Tipologia"),
						rs.getString("CartaDiCredito"));
				return result;
			}
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public UtenteBean dettagliUtenteByEmail(String email) throws SQLException {
		UtenteBean result = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchUtenteQuery = "SELECT * FROM utente WHERE email = `?`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchUtenteQuery);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				result = new UtenteBean(rs.getString("CF"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("Email"), rs.getString("password"), rs.getString("Via"), rs.getInt("NumeroCivico"),
						rs.getString("Citta"), rs.getString("Provincia"), rs.getInt("CAP"), rs.getInt("Tipologia"),
						rs.getString("CartaDiCredito"));
				return result;
			}
			return null;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public ArrayList<UtenteBean> ricercaDipendenti(String testo) throws SQLException {
		ArrayList<UtenteBean> result = new ArrayList<UtenteBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM utente WHERE Tipologia != `1` AND (nome LIKE `%?%` OR cognome LIKE `%?$` OR CF LIKE `%?$`);";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, testo);
			preparedStatement.setString(2, testo);
			preparedStatement.setString(3, testo);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				UtenteBean u = new UtenteBean(rs.getString("CF"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("Email"), rs.getString("password"), rs.getString("Via"), rs.getInt("NumeroCivico"),
						rs.getString("Citta"), rs.getString("Provincia"), rs.getInt("CAP"), rs.getInt("Tipologia"),
						rs.getString("CartaDiCredito"));
				result.add(u);
			}
			return result;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public boolean autenticazione(String email, String password) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM utente WHERE email = `?` AND password = PASSWORD(`?`);";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			rs = preparedStatement.executeQuery();

			rs.last();
			return (rs.getRow() != 1);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	public String generatePassword(int length) {
		if (length < 10) {
			length = 10;
		}

		final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
		final char[] numbers = "0123456789".toCharArray();
		final char[] symbols = "^$?!@#%&".toCharArray();
		final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789^?!".toCharArray();

		Random random = new SecureRandom();

		StringBuilder password = new StringBuilder();

		for (int i = 0; i < length - 4; i++) {
			password.append(allAllowed[random.nextInt(allAllowed.length)]);
		}

		password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
		password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
		password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);
		password.insert(random.nextInt(password.length()), symbols[random.nextInt(symbols.length)]);

		return password.toString();
	}
}
