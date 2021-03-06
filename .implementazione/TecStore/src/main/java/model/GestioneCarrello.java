package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.ArticoloBean;
import bean.UtenteBean;
import util.DriverManagerConnectionPool;

public class GestioneCarrello {

	public ArrayList<ArticoloBean> GetCarrello(UtenteBean u) throws SQLException {
		return GetCarrello(u.getCF());
	}

	public ArrayList<ArticoloBean> GetCarrello(String CF) throws SQLException {
		ArrayList<ArticoloBean> carrello = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT articolo.ID,articolo.nome,articolo.descrizione,articolo.IDVenditore,carrello.quantita,articolo.prezzo,articolo.stato, articolo.idcentralinista,articolo.data, articolo.rimborsabile FROM carrello, articolo WHERE IDArticolo = ID AND IDCliente =?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, CF);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticoloBean articolo = new ArticoloBean(rs.getString("ID"), rs.getString("Nome"),
						rs.getString("Descrizione"), rs.getString("IDVenditore"), rs.getInt("Quantita"),
						rs.getFloat("Prezzo"), rs.getString("Stato"), rs.getString("IDCentralinista"),
						rs.getDate("Data"), rs.getBoolean("Rimborsabile"));
				carrello.add(articolo);
			}

			return carrello;
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

	public boolean aggiornamentoQuantitaCarrello(String IDArticolo, int quantita) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String aggiuntaCarrelloQuery = "UPDATE articolo set Quantita = quantita + ? where id = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(aggiuntaCarrelloQuery);
			preparedStatement.setInt(1, quantita);
			preparedStatement.setString(2, IDArticolo);
			preparedStatement.execute();

			connection.commit();
			return true;
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

	public boolean aggiuntaArticolo(String CF, ArticoloBean articolo, int quantita) throws SQLException {
		return aggiuntaArticolo(CF, articolo.getID(), quantita);
	}

	public boolean aggiuntaArticolo(UtenteBean utente, String IDArticolo, int quantita) throws SQLException {
		return aggiuntaArticolo(utente.getCF(), IDArticolo, quantita);
	}

	public boolean aggiuntaArticolo(UtenteBean utente, ArticoloBean articolo, int quantita) throws SQLException {
		return aggiuntaArticolo(utente.getCF(), articolo.getID(), quantita);
	}

	public boolean aggiuntaArticolo(String CF, String IDArticolo, int quantita) throws SQLException {
		if (quantita < 1)
			return false;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String aggiuntaCarrelloQuery = "INSERT INTO carrello (IDCliente, IDArticolo, Quantita) VALUES (?,?,?);";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(aggiuntaCarrelloQuery);
			aggiornamentoQuantitaCarrello(IDArticolo, -quantita);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDArticolo);
			preparedStatement.setInt(3, quantita);
			preparedStatement.execute();

			connection.commit();
			return true;
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

	public boolean rimozioneArticolo(String CF, ArticoloBean articolo) throws SQLException {
		return rimozioneArticolo(CF, articolo.getID());
	}

	public boolean rimozioneArticolo(UtenteBean utente, String IDArticolo) throws SQLException {
		return rimozioneArticolo(utente.getCF(), IDArticolo);
	}

	public boolean rimozioneArticolo(UtenteBean utente, ArticoloBean articolo) throws SQLException {
		return rimozioneArticolo(utente.getCF(), articolo.getID());
	}

	public boolean rimozioneArticolo(String CF, String IDArticolo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String quantita = "SELECT quantita FROM carrello WHERE IDCliente = ? AND IDArticolo = ?;";

		String rimozioneCarrelloQuery = "DELETE FROM carrello WHERE IDCliente = ? AND IDArticolo = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(quantita);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDArticolo);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			aggiornamentoQuantitaCarrello(IDArticolo, rs.getInt("quantita"));
			preparedStatement = connection.prepareStatement(rimozioneCarrelloQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDArticolo);

			preparedStatement.execute();

			connection.commit();
			return true;
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

	public boolean aggiornamentoQuantita(String CF, ArticoloBean articolo, int quantita) throws SQLException {
		return aggiornamentoQuantita(CF, articolo.getID(), quantita);
	}

	public boolean aggiornamentoQuantita(UtenteBean utente, ArticoloBean articolo, int quantita) throws SQLException {
		return aggiornamentoQuantita(utente.getCF(), articolo.getID(), quantita);
	}

	public boolean aggiornamentoQuantita(UtenteBean utente, String IDArticolo, int quantita) throws SQLException {
		return aggiornamentoQuantita(utente.getCF(), IDArticolo, quantita);
	}

	public boolean aggiornamentoQuantita(String CF, String IDArticolo, int quantita) throws SQLException {
		if (quantita < 1)
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs;
		String aggiornamentoCarrelloQuery = "UPDATE carrello SET quantita = ? WHERE IDCliente = ? AND IDArticolo = ?";
		String getQuantitaCarrelloQuery = "SELECT quantita FROM carrello WHERE IDCliente = ? AND IDArticolo = ?;";

		int quantitaCarrelloOld = 0;

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(getQuantitaCarrelloQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDArticolo);
			rs = preparedStatement.executeQuery();
			if (rs.next()) {
				quantitaCarrelloOld = rs.getInt("quantita");
				aggiornamentoQuantitaCarrello(IDArticolo, -(quantita - quantitaCarrelloOld));
			} else
				return false;

			preparedStatement = connection.prepareStatement(aggiornamentoCarrelloQuery);
			preparedStatement.setInt(1, quantita);
			preparedStatement.setString(2, CF);
			preparedStatement.setString(3, IDArticolo);

			preparedStatement.execute();

			connection.commit();
			return true;
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

}
