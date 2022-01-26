package it.unisa.model;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import Bean.UtenteBean;

public class GestioneAccount {

	private final static String TABLE_NAME="Utente";
	
	public synchronized void insertUser(UtenteBean utente) throws SQLException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
			String insertSQL = "INSERT INTO "+TABLE_NAME+"(`CF`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `VIA`, `NUMEROCIVICO`, `CITTA`, `CAP`, `TIPOLOGIA`)"+" VALUES (?,?,?,?,?,?,?,?,?,?);";
			try{
				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(insertSQL);
				
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
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public synchronized boolean eliminaUtente(String CF) throws SQLException{
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int rs=0;
		
		String deleteSQL = "DELETE FROM "+TABLE_NAME+" WHERE CF_CLIENTE = ?;";
	
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, CF);
			rs=preparedStatement.executeUpdate();
			connection.commit();		
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return (rs!=0);
	}
	
	public synchronized boolean eliminaUtente(UtenteBean utente) throws SQLException{
		return eliminaUtente(utente.getCF());
	}
	

	public synchronized void updateUser(UtenteBean utente) throws SQLException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UtenteBean u = new UtenteBean();
		
		String searchUtenteQuery="SELECT * FROM UTENTE WHERE CF="+ utente.getCF()+";";
		try{
            connection = DriverManagerConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(searchUtenteQuery);
            ResultSet rs = preparedStatement.executeQuery();
            
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
                        rs.getInt("Tipologia")
                        );
            }
		}finally {}
		
		String updateSQL = "UPDATE "+TABLE_NAME+" SET NOME = ?, SET COGNOME = ?, SET EMAIL = ?, PASSWORD = ?, VIA = ?, CAP = ?, NUMEROCIVICO = ?, ,  CITTA = ?, PROVINCIA=? WHERE CF_CLIENTE = ?;";
	
		try {
			connection = DriverManagerConnectionPool.getConnection();
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
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
}
}
		