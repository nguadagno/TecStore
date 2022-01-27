package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Bean.UtenteBean;

public class GestioneAccount {	
	public boolean registrazioneUtente(UtenteBean utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

			String registrazioneUtente = "INSERT INTO Utente (`CF`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `VIA`, `NUMEROCIVICO`, `CITTA`, `CAP`, `TIPOLOGIA`)" +
											" VALUES (`?`,`?`,`?`,`?`,`?`,`?`,`?`,`?`,`?`,`?`);";
			try {
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(registrazioneUtente);

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

	public boolean eliminaUtente(String CF) throws SQLException{
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

	public synchronized boolean eliminaUtente(UtenteBean utente) throws SQLException{
		return eliminaUtente(utente.getCF());
	}


	public boolean aggiornamentoUtente (UtenteBean utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		UtenteBean u = new UtenteBean();

		String searchUtenteQuery="SELECT * FROM UTENTE WHERE CF='`?`';";
		try{
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(searchUtenteQuery);
            preparedStatement.setString(1, utente.getCF());
            ResultSet rs = preparedStatement.executeQuery();
            preparedStatement.close();

            if(rs.next()){
               u = new UtenteBean(
                        rs.getString("CF"),
                        rs.getString("Nome"),
                        rs.getString("Cognome"),
                        rs.getString("Email"),
                        rs.getString("password"),
                        rs.getString("Via"),
                        rs.getInt("NumeroCivico"),
                        rs.getString("Citta"),
                        rs.getString("Provincia"),
                        rs.getInt("CAP"),
                        rs.getInt("Tipologia"),
                        rs.getString("CartaDiCredito")
                        );
            }
            else return false;

		
            String updateSQL = "UPDATE Utente SET NOME = `?`, SET COGNOME = `?`, SET EMAIL = `?`, PASSWORD = `?`,"+
							" VIA = `?`, CAP = `?`, NUMEROCIVICO = `?`, ,  CITTA = `?`, PROVINCIA=`?` WHERE CF_CLIENTE = `?`;";

			connection = DriverManagerConnectionPool.getConnection();
			
			if (!utente.getEmail().equals(u.getEmail()) && !utente.checkEmail(utente.getEmail()))
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
			
			preparedStatement = connection.prepareStatement(updateSQL);
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
		String searchTicketQuery = "SELECT * FROM utente WHERE CF='" + CF+ "';";

		try{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				result = new UtenteBean(
						 rs.getString("CF"),
                        rs.getString("Nome"),
                        rs.getString("Cognome"),
                        rs.getString("Email"),
                        rs.getString("password"),
                        rs.getString("Via"),
                        rs.getInt("NumeroCivico"),
                        rs.getString("Citta"),
                        rs.getString("Provincia"),
                        rs.getInt("CAP"),
                        rs.getInt("Tipologia"),
                        rs.getString("CartaDiCredito")
						);
				return result;
			}
			return null;
		} finally {
			try{
				if(connection!=null){
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

}
