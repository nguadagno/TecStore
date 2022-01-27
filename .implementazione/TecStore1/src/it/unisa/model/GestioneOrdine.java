package it.unisa.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.OrdineBean;
import Bean.UtenteBean;

public class GestioneOrdine {
	public boolean creazioneOrdine(OrdineBean ordine, UtenteBean utente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String selectClienteSQL = "SELECT * FROM cliente WHERE CF = `?`;";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectClienteSQL);
			preparedStatement.setString(1, utente.getCF());
			ResultSet rs = preparedStatement.executeQuery();
			
			rs.last();
			if(rs.getRow()!= 1) 
				return false;
			String insertSql = "INSERT INTO ordine (ID, IDCLIENTE, IDARTICOLO, QUANTITA, DATA, STATO) VALUES (`?`,`?`,`?`,`?`,`?`,`?`);";

			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSql);
			preparedStatement.setString(1, ordine.getID());
			preparedStatement.setString(2, ordine.getIDCliente());
			preparedStatement.setString(3, ordine.getIDArticolo());
			preparedStatement.setInt(4, ordine.getQuantita());
			preparedStatement.setDate(5, (Date) ordine.getData());
			preparedStatement.setString(6, "InAttesa");
			
			preparedStatement.executeUpdate();

			connection.commit();
			return true;
		} finally {
			try {
				if (connection!=null) {
					connection.close();
				}
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}
	
	public ArrayList <OrdineBean> elencoOrdiniMagazziniere() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		ArrayList <OrdineBean> ordersList = new ArrayList<OrdineBean>();
		
		String elencoOrdiniMagazziniereQuery = "SELECT * FROM ordine WHERE stato = 'InAttesa';";
		
		try{
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(elencoOrdiniMagazziniereQuery);
			rs=preparedStatement.executeQuery();
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				OrdineBean order = new OrdineBean(
						rs.getString("ID"),
						rs.getString("IDCliente"),
						rs.getString("IDArticolo"),
						rs.getInt("quantita"),
						rs.getDate("data"),
						rs.getString("stato"),
						rs.getString("codiceTracciamento")
						);
				ordersList.add(order);
			}
			
			return ordersList;
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

	public ArrayList <OrdineBean> elencoOrdiniCliente(String CF) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		ArrayList <OrdineBean> ordersList = new ArrayList<OrdineBean>();
		
		String elencoOrdiniClienteQuery = "SELECT * FROM ordine WHERE IDCliente = '?';";
		
		try{
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(elencoOrdiniClienteQuery);
			preparedStatement.setString(1,CF);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				OrdineBean order = new OrdineBean(
						rs.getString("ID"),
						rs.getString("IDCliente"),
						rs.getString("IDArticolo"),
						rs.getInt("quantita"),
						rs.getDate("data"),
						rs.getString("stato"),
						rs.getString("codiceTracciamento")
						);
				ordersList.add(order);
			}
			
			return ordersList;
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
	
	public ArrayList <OrdineBean> elencoOrdiniCliente(UtenteBean utente) throws SQLException{
		return elencoOrdiniCliente(utente.getCF());
	}
	
	public OrdineBean dettagliOrdineCliente(String CF, String IDOrdine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
			
		String dettagliOrdineQuery = "SELECT * FROM ordine WHERE IDCliente = `?` AND ID = `?`;";
		
		try{
			connection=DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(dettagliOrdineQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDOrdine);
			rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				return new OrdineBean(
						rs.getString("ID"),
						rs.getString("IDCliente"),
						rs.getString("IDArticolo"),
						rs.getInt("quantita"),
						rs.getDate("data"),
						rs.getString("stato"),
						rs.getString("codiceTracciamento")
						);

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
	
	public OrdineBean dettagliOrdineCliente(UtenteBean utente, String IDOrdine) throws SQLException {
		return dettagliOrdineCliente(utente.getCF(), IDOrdine);
	}

	public boolean cambiaStato(String ID, String stato) throws SQLException {
		if (stato != "Spedito" && stato != "Rimborsato" && stato != "InAttesaDiRimborso" && stato != "Annullato")
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String cambiaStatoQuery = "UPDATE ordine SET stato = `?` WHERE ID = `?`;";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(cambiaStatoQuery);
			preparedStatement.setString(1, stato);
			preparedStatement.setString(2, ID);
			preparedStatement.executeQuery();
			connection.commit();
			return true;
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