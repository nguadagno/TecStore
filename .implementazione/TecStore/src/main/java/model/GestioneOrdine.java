package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import Bean.ArticoloBean;
import Bean.OrdineBean;
import Bean.UtenteBean;

public class GestioneOrdine {
	public boolean creazioneOrdine(UtenteBean utente) throws SQLException {
		return creazioneOrdine(utente.getCF());
	}

	public boolean creazioneOrdine(String CF) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String creazioneOrdineQuery = "INSERT INTO ordine (IDCLIENTE, IDARTICOLO, QUANTITA) VALUES (?,?,?);";
		String eliminazioneCarrelloQuery = "DELETE FROM carrello WHERE IDCliente = ?;";

		try {
			if (!GestioneAccount.exists(CF))
				return false;

			ArrayList<ArticoloBean> carrello = GestioneCarrello.GetCarrello(CF);

			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			for (ArticoloBean e : carrello) {
				preparedStatement = connection.prepareStatement(creazioneOrdineQuery);
				preparedStatement.setString(1, CF);
				preparedStatement.setString(2, e.getID());
				preparedStatement.setInt(3, e.getQuantita());

				preparedStatement.executeUpdate();
			}

			preparedStatement = connection.prepareStatement(eliminazioneCarrelloQuery);
			preparedStatement.setString(1, CF);

			preparedStatement.execute();

			connection.commit();
			return true;
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

	public ArrayList<OrdineBean> elencoOrdiniMagazziniere() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<OrdineBean> ordersList = new ArrayList<OrdineBean>();

		String elencoOrdiniMagazziniereQuery = "SELECT * FROM ordine WHERE stato = 'InAttesa' ORDER BY data;";

		try {
			connection = DriverManagerConnectionPool.getConnection("magazziniere", "magazziniere");
			preparedStatement = connection.prepareStatement(elencoOrdiniMagazziniereQuery);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrdineBean order = new OrdineBean(rs.getString("ID"), rs.getString("IDCliente"),
						rs.getString("IDArticolo"), rs.getInt("quantita"), rs.getDate("data"), rs.getString("stato"),
						rs.getString("codiceTracciamento"));
				ordersList.add(order);
			}

			return ordersList;
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

	public ArrayList<OrdineBean> ricercaOrdiniCliente(UtenteBean utente, String nome, int limit) throws SQLException {
		return ricercaOrdiniCliente(utente.getCF(), nome, limit);
	}

	public ArrayList<OrdineBean> ricercaOrdiniCliente(String CF, String nome, int limit) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<OrdineBean> ordersList = new ArrayList<OrdineBean>();

		String elencoOrdiniClienteQuery = "SELECT ordine.id as ID, idcliente, idarticolo, ordine.quantita, ordine.data, ordine.stato, codicetracciamento, nome FROM ordine, articolo WHERE idcliente = ? AND articolo.nome LIKE ? AND articolo.id = ordine.idarticolo LIMIT ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(elencoOrdiniClienteQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, "%" + nome + "%");
			preparedStatement.setInt(3, limit);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrdineBean order = new OrdineBean(rs.getString("ID"), rs.getString("IDCliente"),
						rs.getString("IDArticolo"), rs.getInt("quantita"), rs.getDate("data"), rs.getString("stato"),
						rs.getString("codiceTracciamento"));
				ordersList.add(order);
			}

			return ordersList;
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

	public OrdineBean dettagliOrdineCliente(UtenteBean utente, String IDOrdine) throws SQLException {
		return dettagliOrdineCliente(utente.getCF(), IDOrdine);
	}

	public OrdineBean dettagliOrdineCliente(String CF, String IDOrdine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String dettagliOrdineQuery = "SELECT * FROM ordine WHERE IDCliente = ? AND ID = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(dettagliOrdineQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDOrdine);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return new OrdineBean(rs.getString("ID"), rs.getString("IDCliente"), rs.getString("IDArticolo"),
						rs.getInt("quantita"), rs.getDate("data"), rs.getString("stato"),
						rs.getString("codiceTracciamento"));
			}
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

	public OrdineBean dettagliOrdineByID(String IDOrdine) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String dettagliOrdineQuery = "SELECT * FROM ordine WHERE ID = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(dettagliOrdineQuery);
			preparedStatement.setString(1, IDOrdine);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return new OrdineBean(rs.getString("ID"), rs.getString("IDCliente"), rs.getString("IDArticolo"),
						rs.getInt("quantita"), rs.getDate("data"), rs.getString("stato"),
						rs.getString("codiceTracciamento"));

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

	public boolean cambiaStato(String ID, String stato) throws SQLException {
		final Set<String> states = Set.of("Spedito", "InElaborazione", "Rimborsato", "RimborsoRifiutato", "InAttesaRimborso", "Annullato");
		if (!states.contains(stato))
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String cambiaStatoQuery = "UPDATE ordine SET stato = ? WHERE ID = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("magazziniere", "magazziniere");

			preparedStatement = connection.prepareStatement(cambiaStatoQuery);
			preparedStatement.setString(1, stato);
			preparedStatement.setString(2, ID);
			preparedStatement.executeUpdate();
			connection.commit();
			return true;
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

	public boolean setCodiceTracciamento(String IDOrdine, String codiceTracking) throws SQLException {
		if (codiceTracking == null || codiceTracking.isEmpty())
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String setTrackingQuery = "UPDATE ordine SET CodiceTracciamento = ? WHERE ID = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("magazziniere", "magazziniere");
			preparedStatement = connection.prepareStatement(setTrackingQuery);
			preparedStatement.setString(1, codiceTracking);
			preparedStatement.setString(2, IDOrdine);
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

	public ArrayList<OrdineBean> elencoRimborsi(int limite) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		ArrayList<OrdineBean> ordersList = new ArrayList<OrdineBean>();

		String elencoOrdiniClienteQuery = "SELECT * FROM ordine WHERE stato = 'InAttesaRimborso' LIMIT ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("magazziniere", "magazziniere");
			preparedStatement = connection.prepareStatement(elencoOrdiniClienteQuery);
			preparedStatement.setInt(1, limite);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				OrdineBean order = new OrdineBean(rs.getString("ID"), rs.getString("IDCliente"),
						rs.getString("IDArticolo"), rs.getInt("quantita"), rs.getDate("data"), rs.getString("stato"),
						rs.getString("codiceTracciamento"));
				ordersList.add(order);
			}

			return ordersList;
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
}
