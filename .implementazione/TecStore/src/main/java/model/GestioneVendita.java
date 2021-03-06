package model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;

import bean.ArticoloBean;
import bean.FotoBean;
import bean.OrdineBean;
import util.DriverManagerConnectionPool;

public class GestioneVendita {

	public byte[] getFoto(String IDFoto) throws SQLException {
		Connection connection = null;

		String getFotoQuery = "SELECT * FROM foto WHERE ID = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			PreparedStatement preparedStatement = connection.prepareStatement(getFotoQuery);
			preparedStatement.setString(1, IDFoto);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return rs.getBytes("foto");
			}
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

	public ArrayList<FotoBean> getAllFoto(String IDArticolo) throws SQLException {
		Connection connection = null;

		String getFotoQuery = "SELECT * FROM foto WHERE IDArticolo = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			PreparedStatement preparedStatement = connection.prepareStatement(getFotoQuery);
			preparedStatement.setString(1, IDArticolo);
			ResultSet rs = preparedStatement.executeQuery();
			ArrayList<FotoBean> foto = new ArrayList<FotoBean>();

			while (rs.next()) {
				FotoBean f = new FotoBean(rs.getString("ID"), rs.getString("IDArticolo"), rs.getBlob("foto"));
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

	public ArrayList<FotoBean> getFoto(ArrayList<ArticoloBean> articoli) throws SQLException {
		ArrayList<FotoBean> foto = new ArrayList<FotoBean>();
		Connection connection = null;

		String getFotoQuery = "SELECT * FROM foto WHERE IDArticolo = ? LIMIT 1;";

		try {
			for (ArticoloBean a : articoli) {
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
				PreparedStatement preparedStatement = connection.prepareStatement(getFotoQuery);
				preparedStatement.setString(1, a.getID());
				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					FotoBean f = new FotoBean(rs.getString("ID"), rs.getString("IDArticolo"), rs.getBlob("foto"));
					foto.add(f);
				}
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

	public ArrayList<FotoBean> getFotoOrdini(ArrayList<OrdineBean> ordini) throws SQLException {
		ArrayList<FotoBean> foto = new ArrayList<FotoBean>();
		Connection connection = null;

		String getFotoQuery = "SELECT * FROM foto WHERE IDArticolo = ? LIMIT 1;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			for (OrdineBean a : ordini) {
				PreparedStatement preparedStatement = connection.prepareStatement(getFotoQuery);
				preparedStatement.setString(1, a.getIDArticolo());
				ResultSet rs = preparedStatement.executeQuery();

				if (rs.next()) {
					FotoBean f = new FotoBean(rs.getString("ID"), rs.getString("IDArticolo"), rs.getBlob("foto"));
					foto.add(f);
				}
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

	public boolean rimozioneFoto(String IDArticolo) throws SQLException {
		String rimozioneFotoQuery = "DELETE FROM foto WHERE IDArticolo = ?;";
		Connection connection = null;

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			PreparedStatement preparedStatement = connection.prepareStatement(rimozioneFotoQuery);
			preparedStatement.setString(1, IDArticolo);
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

	public boolean rimozioneFoto(String IDArticolo, String IDFoto) throws SQLException {
		String rimozioneFotoQuery = "DELETE FROM foto WHERE IDArticolo = ? AND ID = ?;";
		Connection connection = null;

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			PreparedStatement preparedStatement = connection.prepareStatement(rimozioneFotoQuery);
			preparedStatement.setString(1, IDArticolo);
			preparedStatement.setString(2, IDFoto);
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

	public boolean sovrascritturaFoto(String IDArticolo, ArrayList<FotoBean> foto) throws SQLException {
		Connection connection = null;
		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			connection.setAutoCommit(false);
			return rimozioneFoto(IDArticolo) && inserimentoFoto(IDArticolo, foto);
		} catch (SQLException e) {
			connection.rollback();
		} finally {
			connection.commit();
		}
		return false;
	}

	public boolean inserimentoFoto(String IDArticolo, ArrayList<FotoBean> foto) throws SQLException {
		String insertFotoQuery = "INSERT INTO foto (IDArticolo, Foto) VALUES (?, ?);";
		Connection connection = null;

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			for (FotoBean f : foto) {
				PreparedStatement preparedStatement = connection.prepareStatement(insertFotoQuery);
				preparedStatement.setString(1, IDArticolo);
				preparedStatement.setBytes(2, f.getFoto());
				preparedStatement.execute();
				connection.commit();
			}
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

	public boolean inserimentoFoto(String IDArticolo, InputStream foto) throws SQLException {
		String insertFotoQuery = "INSERT INTO foto (IDArticolo, Foto) VALUES (?, ?);";
		Connection connection = null;

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");

			PreparedStatement preparedStatement = connection.prepareStatement(insertFotoQuery);
			preparedStatement.setString(1, IDArticolo);
			preparedStatement.setBlob(2, foto);
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

	public String inserimentoNuovoArticolo(ArticoloBean a) throws SQLException {
		return inserimentoNuovoArticolo(a.getNome(), a.getDescrizione(), a.getIDVenditore(), a.getQuantita(),
				a.getPrezzo(), a.isRimborsabile());
	}

	public String inserimentoNuovoArticolo(String nome, String descrizione, String IDVenditore, int quantita,
			float prezzo, Boolean rimborsabile) throws SQLException {
		if (nome == null || descrizione == null || IDVenditore == null || rimborsabile == null || quantita < 1
				|| prezzo < 0.01 || nome.isEmpty() || descrizione.isEmpty() || IDVenditore.isEmpty())
			return "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String aggiuntaArticoloQuery = "INSERT INTO articolo (Nome, Descrizione, IDVenditore, Quantita, Prezzo, Stato, Rimborsabile) VALUES (?,?,?,?,?,?,?);";
		String getIDArticoloQuery = "SELECT ID FROM articolo WHERE nome = ? AND IDVenditore = ? AND quantita = ? AND prezzo = ?";

		try {
			GestioneAccount gestioneaccount = new GestioneAccount();
			int tipologia = gestioneaccount.getTipologia(IDVenditore);
			if (tipologia == 1)
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			else if (tipologia == 4)
				connection = DriverManagerConnectionPool.getConnection("ammcatalogo", "ammcatalogo");
			else
				return "";

			preparedStatement = connection.prepareStatement(aggiuntaArticoloQuery);
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, descrizione);
			preparedStatement.setString(3, IDVenditore);
			preparedStatement.setInt(4, quantita);
			preparedStatement.setFloat(5, prezzo);
			preparedStatement.setString(6, tipologia == 1 ? "InAttesa" : "InVendita");
			preparedStatement.setBoolean(7, rimborsabile);
			preparedStatement.execute();

			preparedStatement = connection.prepareStatement(getIDArticoloQuery);
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, IDVenditore);
			preparedStatement.setInt(3, quantita);
			preparedStatement.setFloat(4, prezzo);
			ResultSet rs = preparedStatement.executeQuery();

			String IDArticolo = "";
			if (rs.next())
				IDArticolo = rs.getString("ID");

			connection.commit();
			return IDArticolo;

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
		return "";

	}

	public boolean modificaArticolo(ArticoloBean a) throws SQLException {
		return modificaArticolo(a.getID(), a.getNome(), a.getDescrizione(), a.getIDVenditore(), a.getQuantita(),
				a.getPrezzo(), a.isRimborsabile());
	}

	public boolean modificaArticolo(String IDArticolo, String nome, String descrizione, String IDVenditore,
			int quantita, float prezzo, boolean rimborsabile) throws SQLException {
		if (quantita < 1 || prezzo < 0.01 || nome.isEmpty() || descrizione.isEmpty() || IDVenditore.isEmpty())
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String aggiuntaArticoloQuery = "UPDATE articolo SET Nome = ?, Descrizione = ?, IDVenditore = ?, Quantita = ?, Prezzo = ?, Stato = ?, Rimborsabile = ? WHERE ID = ?;";

		try {
			GestioneAccount gestioneaccount = new GestioneAccount();
			int tipologia = gestioneaccount.getTipologia(IDVenditore);

			if (tipologia == 1)
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			else if (tipologia == 4)
				connection = DriverManagerConnectionPool.getConnection("ammcatalogo", "ammcatalogo");
			else
				return false;

			preparedStatement = connection.prepareStatement(aggiuntaArticoloQuery);
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, descrizione);
			preparedStatement.setString(3, IDVenditore);
			preparedStatement.setInt(4, quantita);
			preparedStatement.setFloat(5, prezzo);
			preparedStatement.setString(6, tipologia == 1 ? "InAttesa" : "InVendita");
			preparedStatement.setBoolean(7, rimborsabile);
			preparedStatement.setString(8, IDArticolo);
			preparedStatement.executeUpdate();

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

	public ArrayList<ArticoloBean> ricercaArticolo(String nome, int limit) throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT * FROM articolo WHERE Nome LIKE ? and stato = 'InVendita' LIMIT ? ;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setString(1, "%" + nome + "%");
			preparedStatement.setInt(2, limit);
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

	public ArrayList<ArticoloBean> ricercaArticolo(String nome) throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT * FROM articolo WHERE Nome LIKE ? and stato = 'InVendita';";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setString(1, "%" + nome + "%");
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

	public ArrayList<ArticoloBean> elencoVenditeCentralinista(int limit) throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT * FROM articolo WHERE Stato = 'InAttesa' LIMIT ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("centralinista", "centralinista");
			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setInt(1, limit);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ArticoloBean articolo = new ArticoloBean(rs.getString("ID"), rs.getString("Nome"),
						rs.getString("Descrizione"), rs.getString("IDVenditore"), rs.getInt("Quantita"),
						rs.getFloat("Prezzo"), rs.getString("Stato"), rs.getString("IDCentralinista"),
						rs.getDate("Data"), rs.getBoolean("Rimborsabile"));
				result.add(articolo);
			}

			return result;
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

	public ArrayList<ArticoloBean> elencoVenditeCF(String CF, String nome, int limit) throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT * FROM articolo WHERE IDVenditore=? AND nome LIKE ? LIMIT ?;";

		try {
			GestioneAccount gestioneaccount = new GestioneAccount();
			if (gestioneaccount.getTipologia(CF) == 1)
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			else if (gestioneaccount.getTipologia(CF) == 4)
				connection = DriverManagerConnectionPool.getConnection("ammcatalogo", "ammcatalogo");
			else
				return result;

			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, "%" + nome + "%");
			preparedStatement.setInt(3, limit);
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

	public ArrayList<ArticoloBean> elencoVenditeTipologia(String tipologia, String nome, int limit)
			throws SQLException {
		ArrayList<ArticoloBean> result = new ArrayList<ArticoloBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchArticoloQuery = "SELECT articolo.ID ,articolo.nome, articolo.descrizione, articolo.IDVenditore, articolo.quantita ,articolo.prezzo, articolo.stato ,articolo.IDCentralinista, articolo.data, articolo.rimborsabile FROM articolo, utente WHERE IDVenditore=utente.CF AND tipologia = ? AND articolo.nome LIKE ? LIMIT ?;";
		try {

			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");

			preparedStatement = connection.prepareStatement(searchArticoloQuery);
			preparedStatement.setString(1, tipologia);
			preparedStatement.setString(2, "%" + nome + "%");
			preparedStatement.setInt(3, limit);
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
		String dettagliArticoloQuery = "SELECT * FROM articolo WHERE ID = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(dettagliArticoloQuery);
			preparedStatement.setString(1, IDArticolo);
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
		final Set<String> states = Set.of("InVendita", "InElaborazione", "Rifiutato");
		if (IDArticolo == null || stato == null || !states.contains(stato))
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String cambiaStatoQuery = "UPDATE articolo SET stato = ? WHERE ID = ?;";
		try {
			connection = DriverManagerConnectionPool.getConnection("centralinista", "centralinista");
			preparedStatement = connection.prepareStatement(cambiaStatoQuery);
			preparedStatement.setString(1, stato);
			preparedStatement.setString(2, IDArticolo);
			preparedStatement.execute();
			connection.commit();
			return true;
		} catch (SQLException e) {
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

	public boolean rimozioneArticolo(String CF, String IDArticolo) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String rimozioneArticoloQuery = "UPDATE articolo SET stato='annullata' WHERE ID = ?;";

		try {
			GestioneAccount gestioneaccount = new GestioneAccount();
			if (gestioneaccount.getTipologia(CF) == 1)
				connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			else if (gestioneaccount.getTipologia(CF) == 4)
				connection = DriverManagerConnectionPool.getConnection("ammcatalogo", "ammcatalogo");
			else
				return false;
			preparedStatement = connection.prepareStatement(rimozioneArticoloQuery);
			preparedStatement.setString(1, IDArticolo);
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
