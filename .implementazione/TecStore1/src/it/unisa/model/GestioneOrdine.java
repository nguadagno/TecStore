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
	public class OrdineCliente {

		private final static String TABLE_NAME="Ordine";

		public synchronized boolean inserimentoNuovoOrdine(OrdineBean ordine, UtenteBean utente) throws SQLException{
			
				Connection connection = null;
				PreparedStatement preparedStatement = null;
				String selectClienteSQL = "SELECT * FROM CLIENTE WHERE CF_CLIENTE = `?`;";
				
				try {
					connection = DriverManagerConnectionPool.getConnection();
					preparedStatement = connection.prepareStatement(selectClienteSQL);
					preparedStatement.setString(1, utente.getCF());
					ResultSet rs = preparedStatement.executeQuery();
					
					rs.last();
					if(rs.getRow()!= 1) 
						return false;
		
			String insertSql = "INSERT INTO " + TABLE_NAME + " (ID, IDCLIENTE, IDARTICOLO, QUANTITA, DATA, STATO, CODICETRACCIAMENTO) VALUES (`?`,`?`,`?`,`?`,`?`,`?`,`?`);";

				connection = DriverManagerConnectionPool.getConnection();
				preparedStatement = connection.prepareStatement(insertSql);
				preparedStatement.setString(1, ordine.getID());
				preparedStatement.setString(2, ordine.getIDCliente());
				preparedStatement.setString(3, ordine.getIDArticolo());
				preparedStatement.setInt(4, ordine.getQuantita());
				preparedStatement.setDate(5, (Date) ordine.getData());
				preparedStatement.setString(6, "InAttesa");
				preparedStatement.setString(7, null);
				
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
		
		public synchronized ArrayList <OrdineBean> elencoOrdini()throws SQLException{
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			
			ArrayList <OrdineBean> ordersList = new ArrayList<OrdineBean>();
			
			String searchSqlCodeOrder="SELECT * FROM " + TABLE_NAME +" WHERE STATO = 'InAttesa';";
			
			try{
				connection=DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(searchSqlCodeOrder);
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

		public synchronized ArrayList <OrdineBean> elencoOrdiniCliente(String CF)throws SQLException{
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			
			ArrayList <OrdineBean> ordersList = new ArrayList<OrdineBean>();
			
			String searchSqlCodeOrder="SELECT * FROM " + TABLE_NAME +" WHERE IDCLIENTE = '?';";
			
			try{
				connection=DriverManagerConnectionPool.getConnection();
				preparedStatement=connection.prepareStatement(searchSqlCodeOrder);
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
		
		public synchronized ArrayList <OrdineBean> elencoOrdiniCliente(UtenteBean utente)throws SQLException{
			return elencoOrdiniCliente(utente.getCF());
		}

		public boolean cambiaStatoOrdine(String ID, String stato) throws SQLException {
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
}
