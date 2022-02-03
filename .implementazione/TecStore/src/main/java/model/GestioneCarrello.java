package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.ArticoloBean;
import Bean.UtenteBean;

public class GestioneCarrello {

	public ArrayList<ArticoloBean> getCarrello(UtenteBean u) throws SQLException {
		return getCarrello(u.getCF());
	}

	public static ArrayList<ArticoloBean> getCarrello(String CF) throws SQLException {
		ArrayList<ArticoloBean> carrello = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM carrello, articolo WHERE IDArticolo = ID AND IDCliente =?;";

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
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDArticolo);
			preparedStatement.setInt(3, quantita);

			preparedStatement.executeQuery();

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
		String rimozioneCarrelloQuery = "DELETE FROM carrello WHERE IDCliente = ? AND IDArticolo = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(rimozioneCarrelloQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDArticolo);

			preparedStatement.executeQuery();

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
		String aggiuntaCarrelloQuery = "UPDATE carrello SET quantita = ? WHERE IDCliente = ? AND IDArticolo = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(aggiuntaCarrelloQuery);
			preparedStatement.setInt(1, quantita);
			preparedStatement.setString(2, CF);
			preparedStatement.setString(3, IDArticolo);

			preparedStatement.executeQuery();

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
