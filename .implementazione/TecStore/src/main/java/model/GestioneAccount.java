package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

import Bean.UtenteBean;

public class GestioneAccount {
	public boolean exists(UtenteBean utente) throws SQLException {
		return exists(utente.getCF());
	}

	public static boolean exists(String CF) throws SQLException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			String selectClienteSQL = "SELECT * FROM cliente WHERE CF = ?;";
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
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

	public boolean registrazioneUtente(String CF, String nome, String cognome, String email, String password,
			String via, int numeroCivico, String citta, String provincia, int CAP, int tipologia)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		return registrazioneUtente(new UtenteBean(CF, nome, cognome, email, password, via, numeroCivico, citta,
				provincia, CAP, tipologia, ""));
	}

	public boolean registrazioneUtente(UtenteBean utente)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		if (utente == null || utente.getCF() == null || utente.getNome() == null || utente.getCognome() == null
				|| utente.getEmail() == null || utente.getPassword() == null || utente.getVia() == null
				|| utente.getProvincia() == null || utente.getCitta() == null || utente.getCF().isEmpty()
				|| utente.getNome().isEmpty() || utente.getCognome().isEmpty() || utente.getEmail().isEmpty()
				|| utente.getPassword().isEmpty() || utente.getVia().isEmpty() || utente.getNumeroCivico() < 0
				|| utente.getProvincia().isEmpty() || utente.getCitta().isEmpty() || utente.getCAP() < 0
				|| utente.getTipologia() < 0)
			return false;

		String registrazioneUtente = "INSERT INTO utente (`CF`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `VIA`, `NUMEROCIVICO`, `PROVINCIA`, `CITTA`, `CAP`, `TIPOLOGIA`) VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(registrazioneUtente);

			preparedStatement.setString(1, utente.getCF());
			preparedStatement.setString(2, utente.getNome());
			preparedStatement.setString(3, utente.getCognome());
			preparedStatement.setString(4, utente.getEmail());
			preparedStatement.setString(5, encryptPassword(utente.getPassword()));
			preparedStatement.setString(6, utente.getVia());
			preparedStatement.setInt(7, utente.getNumeroCivico());
			preparedStatement.setString(8, utente.getProvincia());
			preparedStatement.setString(9, utente.getCitta());
			preparedStatement.setInt(10, utente.getCAP());
			preparedStatement.setInt(11, utente.getTipologia());

			preparedStatement.executeUpdate();

			connection.commit();
			return true;
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return false;
	}

	public boolean eliminaUtente(String CF, String CFtarget) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String deleteSQL = "DELETE FROM WHERE CF = ?;";

		try {
			if (getTipologia(CF) == 1)
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			else if (getTipologia(CF) == 5)
				connection = DriverManagerConnectionPool.getConnection("ammpersonale", "ammpersonale");
			else
				return false;

			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, CFtarget);
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

	public boolean eliminaUtente(String CF, UtenteBean utente) throws SQLException {
		return eliminaUtente(CF, utente.getCF());
	}

	public boolean modificaUtente(String CF, String CFtarget, String nome, String cognome, String email,
			String password, String via, int numeroCivico, String citta, String provincia, int CAP, int tipologia,
			String cartaDiCredito) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		return modificaUtente(CF, new UtenteBean(CFtarget, nome, cognome, email, password, via, numeroCivico, citta,
				provincia, CAP, tipologia, cartaDiCredito));
	}

	public boolean modificaUtente(String CF, UtenteBean utente)
			throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		UtenteBean u = dettagliUtente(CF, utente.getCF());

		String updateUtenteQuery = "UPDATE Utente SET NOME = ?, SET COGNOME = ?, SET EMAIL = ?, PASSWORD = ?,"
				+ " VIA = ?, CAP = ?, NUMEROCIVICO = ?, ,  CITTA = ?, PROVINCIA=? WHERE CF = ?;";
		try {
			if (getTipologia(CF) == 1)
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			else if (getTipologia(CF) == 5)
				connection = DriverManagerConnectionPool.getConnection("ammpersonale", "ammpersonale");
			else
				return false;

			if (utente == null || (!utente.getEmail().equals(u.getEmail()) && !utente.checkEmail(utente.getEmail())))
				return false;

			if (!utente.getPassword().equals(u.getPassword())) {
				if (utente.checkPassword(utente.getPassword())) {
					utente.setPassword(encryptPassword(utente.getPassword()));
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

	public UtenteBean dettagliUtente(String CF, String CFtarget) throws SQLException {
		UtenteBean result = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchUtenteQuery = "SELECT * FROM utente WHERE CF='" + CF + "';";

		try {
			if (getTipologia(CF) == 1)
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			else if (getTipologia(CF) == 5)
				connection = DriverManagerConnectionPool.getConnection("ammpersonale", "ammpersonale");
			else
				return new UtenteBean();

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
		String searchUtenteQuery = "SELECT * FROM utente WHERE email = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");

			preparedStatement = connection.prepareStatement(searchUtenteQuery);
			preparedStatement.setString(1, email);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				result = new UtenteBean(rs.getString("CF"), rs.getString("Nome"), rs.getString("Cognome"),
						rs.getString("Email"), rs.getString("password"), rs.getString("Via"), rs.getInt("NumeroCivico"),
						rs.getString("Citta"), rs.getString("Provincia"), rs.getInt("CAP"), rs.getInt("Tipologia"),
						rs.getString("CartaDiCredito"));
				return result;
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return null;
	}

	public ArrayList<UtenteBean> ricercaDipendenti(String CF, String testo) throws SQLException {
		ArrayList<UtenteBean> result = new ArrayList<UtenteBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM utente WHERE Tipologia != ? AND (nome LIKE `%?%` OR cognome LIKE `%?$` OR CF LIKE `%?$`);";

		try {
			if (getTipologia(CF) == 5)
				connection = DriverManagerConnectionPool.getConnection("ammpersonale", "ammpersonale");
			else
				return result;
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
		try {
			if (email == null || password == null || email.isEmpty() || password.isEmpty())
				return false;
			return verifyPassword(email, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public int getTipologia(String CF) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT tipologia FROM utente CF= ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, CF);
			rs = preparedStatement.executeQuery();

			return rs.next() ? rs.getInt("tipologia") : -1;
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

	public int getTipologiaByEmail(String CF) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT tipologia FROM utente email = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, CF);
			rs = preparedStatement.executeQuery();

			return rs.next() ? rs.getInt("tipologia") : -1;
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

	public String encryptPassword(String password) {
		if (password == null || password.isEmpty())
			return "";
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean verifyPassword(String email, String password) throws SQLException {
		Connection connection = null;
		try {
			if (email == null || password == null || email.isEmpty() || password.isEmpty())
				return false;

			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			String getPasswordQuery = "SELECT Password FROM utente WHERE email = ?;";
			String hash;

			PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
			preparedStatement.setString(1, email);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next())
				hash = rs.getString("Password");
			else
				return false;

			return (BCrypt.checkpw(password, hash));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}

		return false;
	}
}
