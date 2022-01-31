package it.unisa.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import Bean.ArticoloBean;
import Bean.FotoBean;

public class GestioneVendita {

	public ArrayList<FotoBean> getFoto(String IDArticolo) throws SQLException {

		ArrayList<FotoBean> foto = new ArrayList<FotoBean>();
		Connection connection = null;

		String getFotoQuery = "SELECT * FROM foto WHERE IDArticolo = `" + IDArticolo + "`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(getFotoQuery);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				FotoBean f = new FotoBean(rs.getString("ID"), rs.getString("IDArticolo"), rs.getString("Path"));
				foto.add(f);
			}
			return foto;
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

	public boolean inserimentoNuovoArticolo(String nome, String descrizione, String IDVenditore, int quantita,
			float prezzo, boolean rimborsabile) throws SQLException {
		if (quantita < 1 || prezzo < 0.01 || nome.isEmpty() || descrizione.isEmpty() || IDVenditore.isEmpty())
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String aggiuntaArticoloQuery = "INSERT INTO articolo (Nome, Descrizione, IDVenditore, Quantita, Prezzo, Stato, Data, Rimborsabile) VALUES (`?`,`?`,`?`,`?`,`?`,`?`,`?`,`?`);";
		String getTipologiaVenditore = "SELECT Tipologia FROM utente WHERE ID = `" + IDVenditore + "`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(getTipologiaVenditore);
			ResultSet rs = preparedStatement.executeQuery();
			int tipologia = -1;
			if (rs.next()) {
				tipologia = rs.getInt("Tipologia");
			}
			if (tipologia != 1 && tipologia != 4)
				return false;

			preparedStatement = connection.prepareStatement(aggiuntaArticoloQuery);
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, descrizione);
			preparedStatement.setString(3, IDVenditore);
			preparedStatement.setInt(4, quantita);
			preparedStatement.setFloat(5, prezzo);
			preparedStatement.setString(6, tipologia == 1 ? "InAttesa" : "InVendita");
			preparedStatement.setDate(7, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			preparedStatement.setBoolean(8, rimborsabile);
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

	public boolean modificaArticolo(String nome, String descrizione, String IDVenditore, int quantita, float prezzo,
			boolean rimborsabile) throws SQLException {
		if (quantita < 1 || prezzo < 0.01 || nome.isEmpty() || descrizione.isEmpty() || IDVenditore.isEmpty())
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String aggiuntaArticoloQuery = "UPDATE articolo SET Nome = `?`, Descrizione = `?`, IDVenditore = `?`, Quantita = `?`, Prezzo = `?`, Stato = `?`, Data = `?`, Rimborsabile = `?`);";
		String getTipologiaVenditore = "SELECT Tipologia FROM utente WHERE ID = `" + IDVenditore + "`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(getTipologiaVenditore);
			ResultSet rs = preparedStatement.executeQuery();
			int tipologia = -1;
			if (rs.next()) {
				tipologia = rs.getInt("Tipologia");
			}
			if (tipologia != 1 && tipologia != 4)
				return false;

			preparedStatement = connection.prepareStatement(aggiuntaArticoloQuery);
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, descrizione);
			preparedStatement.setString(3, IDVenditore);
			preparedStatement.setInt(4, quantita);
			preparedStatement.setFloat(5, prezzo);
			preparedStatement.setString(6, tipologia == 1 ? "InAttesa" : "InVendita");
			preparedStatement.setDate(7, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			preparedStatement.setBoolean(8, rimborsabile);
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

	public ArrayList<ArticoloBean> ricercaArticolo(String nome) throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT * FROM articolo WHERE Nome LIKE `%?%`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setString(1, searchArticoloQuery);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticoloBean articolo = new ArticoloBean(rs.getString("ID"), rs.getString("Nome"),
						rs.getString("Descrizione"), rs.getString("IDVenditore"), rs.getInt("Quantita"),
						rs.getFloat("Prezzo"), rs.getString("Stato"), rs.getString("IDCentralinista"),
						rs.getDate("Data"), rs.getBoolean("Rimborsabile"));
				result.add(articolo);
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

	public ArrayList<ArticoloBean> elencoVenditeCentralinista() throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT * FROM articolo WHERE Stato = `InAttesa`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setString(1, searchArticoloQuery);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticoloBean articolo = new ArticoloBean(rs.getString("ID"), rs.getString("Nome"),
						rs.getString("Descrizione"), rs.getString("IDVenditore"), rs.getInt("Quantita"),
						rs.getFloat("Prezzo"), rs.getString("Stato"), rs.getString("IDCentralinista"),
						rs.getDate("Data"), rs.getBoolean("Rimborsabile"));
				result.add(articolo);
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

	public ArrayList<ArticoloBean> elencoVenditeCliente(String CF) throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT * FROM articolo WHERE IDVenditore=`" + CF + "`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setString(1, searchArticoloQuery);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticoloBean articolo = new ArticoloBean(rs.getString("ID"), rs.getString("Nome"),
						rs.getString("Descrizione"), rs.getString("IDVenditore"), rs.getInt("Quantita"),
						rs.getFloat("Prezzo"), rs.getString("Stato"), rs.getString("IDCentralinista"),
						rs.getDate("Data"), rs.getBoolean("Rimborsabile"));
				result.add(articolo);
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

	public ArticoloBean dettagliArticolo(String IDArticolo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String dettagliArticoloQuery = "SELECT * FROM articolo WHERE IDArticolo = `" + IDArticolo + "`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(dettagliArticoloQuery);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				ArticoloBean articolo = new ArticoloBean(rs.getString("ID"), rs.getString("Nome"),
						rs.getString("Descrizione"), rs.getString("IDVenditore"), rs.getInt("Quantita"),
						rs.getFloat("Prezzo"), rs.getString("Stato"), rs.getString("IDCentralinista"),
						rs.getDate("Data"), rs.getBoolean("Rimborsabile"));
				return articolo;
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

	public boolean cambiaStato(String IDArticolo, String stato) throws SQLException {
		if (stato != "InVendita" && stato != "Rifiutato")
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String cambiaStatoQuery = "UPDATE articolo SET stato = `?` WHERE IDArticolo = `?`;";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(cambiaStatoQuery);
			preparedStatement.setString(1, stato);
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

	public boolean rimozioneArticolo(String IDArticolo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String rimozioneArticoloQuery = "UPDATE articolo SET stato=`annullata` WHERE IDArticolo = '?';";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(rimozioneArticoloQuery);
			preparedStatement.setString(1, IDArticolo);
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
