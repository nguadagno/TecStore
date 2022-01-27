package it.unisa.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import Bean.ClienteBean;
import Bean.MessaggioBean;
import Bean.TicketBean;

public class GestioneAssistenza {	
	public ArrayList<TicketBean> elencoTicketCliente(ClienteBean c) throws SQLException {
		return elencoTicketCliente(c.getCF());
	}
	
	public ArrayList<TicketBean> elencoTicketCliente(String CF) throws SQLException {
		ArrayList<TicketBean> ticketList = new ArrayList<TicketBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM ticket WHERE IDCliente = " + CF + ";";

		try{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				TicketBean ticket = new TicketBean(
						rs.getString("IDTicket"),
						rs.getString("IDCliente"),
						rs.getString("Tipologia"),
						rs.getString("Stato")
						);
				ticketList.add(ticket);
			}
			
			return ticketList;
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
	
	public ArrayList<TicketBean> elencoTicketCentralinista(int limit) throws SQLException {
		ArrayList<TicketBean> ticketList = new ArrayList<TicketBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM ticket WHERE stato = 'InAttesa' LIMIT = " + limit + ";";

		try{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				TicketBean ticket = new TicketBean(
						rs.getString("IDTicket"),
						rs.getString("IDCliente"),
						rs.getString("Tipologia"),
						rs.getString("Stato")
						);
				ticketList.add(ticket);
			}
			
			return ticketList;
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
	
	public boolean creazioneTicket (String CF, String tipologia) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		//create table test(id int primary key, data uuid default random_uuid());
		String insertTicketQuery = "INSERT INTO ticket (IDCliente, Tipologia, Stato) VALUES (`?`,`?`,`?`);";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(insertTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, tipologia);
			preparedStatement.setString(3, "InAttesa");
		
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
	
	public boolean cambiaStato(String IDTicket, String stato) throws SQLException {
		if (stato != "InElaborazione" && stato != "InAttesa")
			return false;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String cambiaStatoQuery = "UPDATE ticket SET stato = `?` WHERE IDTicket = `?`;";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(cambiaStatoQuery);
			preparedStatement.setString(1, stato);
			preparedStatement.setString(2, IDTicket);
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
	
	public TicketBean selezionaTicket(String IDTicket) throws SQLException {
		TicketBean result = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM ticket WHERE IDTicket = '" + IDTicket + "';";

		try {
			this.cambiaStato(IDTicket, "InElaborazione");
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				result = new TicketBean(
						rs.getString("IDTicket"),
						rs.getString("IDCliente"),
						rs.getString("Tipologia"),
						rs.getString("Stato")
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
	
	public ArrayList<MessaggioBean> elencoMessaggiTicket (String IDTicket) throws SQLException {
		ArrayList<MessaggioBean> result = new ArrayList<MessaggioBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM messaggio WHERE IDTicket = '" + IDTicket + "';";

		try{
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			rs = preparedStatement.executeQuery();
			
			while(rs.next()){
				MessaggioBean m = new MessaggioBean(
						rs.getString("IDTicket"),
						rs.getString("CF"),
						rs.getString("Contenuto"),
						rs.getDate("Date")
						);
				result.add(m);
			}
			return result;
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
	
	public boolean rispostaTicket(String CF, String Contenuto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		//create table test(id int primary key, data uuid default random_uuid());
		String insertTicketQuery = "INSERT INTO messaggio (CF, Contenuto, Data) VALUES (`?`,`?`,`?`);";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(insertTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, Contenuto);
			preparedStatement.setString(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString());
		
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
	
	public boolean chiusuraTicket(String IDTicket) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String closeTicketQuery = "UPDATE ticket SET stato='Chiuso' WHERE IDTicket='" + IDTicket + "';";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement=connection.prepareStatement(closeTicketQuery);
			preparedStatement.executeUpdate();
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
