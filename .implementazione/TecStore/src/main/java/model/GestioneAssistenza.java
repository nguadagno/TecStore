package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Set;

import bean.MessaggioBean;
import bean.TicketBean;
import bean.UtenteBean;
import util.DriverManagerConnectionPool;

public class GestioneAssistenza {
	public ArrayList<TicketBean> elencoTicketCliente(UtenteBean c, int limit) throws SQLException {
		return elencoTicketCliente(c.getCF(), limit);
	}

	public ArrayList<TicketBean> elencoTicketCliente(String CF, int limit) throws SQLException {
		ArrayList<TicketBean> ticketList = new ArrayList<TicketBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT IDTicket, IDCliente, Tipologia, Stato, MAX(Data) AS Data FROM ticket NATURAL JOIN messaggio WHERE ticket.IDCliente = ? GROUP BY IDTicket LIMIT ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setInt(2, limit);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				TicketBean ticket = new TicketBean(rs.getString("IDTicket"), rs.getString("IDCliente"),
						rs.getString("Tipologia"), rs.getString("Stato"), rs.getDate("data"));
				ticketList.add(ticket);
			}

			return ticketList;
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

	public ArrayList<TicketBean> elencoTicketCentralinista(int limit) throws SQLException {
		ArrayList<TicketBean> ticketList = new ArrayList<TicketBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT IDTicket, CF, Tipologia, Stato, MAX(Data) AS Data FROM ticket NATURAL JOIN messaggio WHERE stato = 'InAttesa' GROUP BY IDTicket LIMIT ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("centralinista", "centralinista");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setInt(1, limit);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				TicketBean ticket = new TicketBean(rs.getString("IDTicket"), rs.getString("CF"),
						rs.getString("Tipologia"), rs.getString("Stato"), rs.getDate("Data"));
				ticketList.add(ticket);
			}

			return ticketList;
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

	public boolean creazioneTicket(String CF, String tipologia, String messaggio) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		final Set<String> tipologieTicket = Set.of("Amministrativo", "Ordini", "Spedizione", "Rimborso", "Profilo");

		if (messaggio == null || messaggio.length() < 15 || messaggio.length() > 1024
				|| !tipologieTicket.contains(tipologia)) {
			return false;
		}

		String insertTicketQuery = "INSERT INTO ticket (IDCliente, Tipologia) VALUES (?,?);";
		String getIDTicketQuery = "SELECT IDTicket FROM ticket WHERE IDCliente = ? AND Tipologia = ? ORDER BY DataCreazione DESC LIMIT 1;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(insertTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, tipologia);

			preparedStatement.execute();

			preparedStatement = connection.prepareStatement(getIDTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, tipologia);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				connection.commit();
				return rispostaTicket(rs.getString("IDTicket"), CF, messaggio);
			} else {
				connection.rollback();
				return false;
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
		return false;
	}

	public boolean cambiaStato(TicketBean ticket, String stato) throws SQLException {
		return cambiaStato(ticket.getIDTicket(), stato);
	}

	public boolean cambiaStato(String IDTicket, String stato) throws SQLException {
		final Set<String> states = Set.of("InElaborazione", "InAttesa", "Chiuso", "InAttesaCliente");
		if (!states.contains(stato))
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String cambiaStatoQuery = "UPDATE ticket SET stato = ? WHERE IDTicket = ?;";
		String getStatoQuery = "SELECT stato FROM ticket where IDTicket = ?;";

		try {
			connection = DriverManagerConnectionPool.getConnection("centralinista", "centralinista");
			preparedStatement = connection.prepareStatement(getStatoQuery);
			preparedStatement.setString(1, IDTicket);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				String statoOld = rs.getString("stato");
				if (statoOld == "InElaborazione")
					return false;
			}

			preparedStatement = connection.prepareStatement(cambiaStatoQuery);
			preparedStatement.setString(1, stato);
			preparedStatement.setString(2, IDTicket);
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

	public TicketBean dettagliTicket(TicketBean ticket) throws SQLException {
		return dettagliTicket(ticket.getIDTicket());
	}

	public TicketBean dettagliTicket(String IDTicket) throws SQLException {
		TicketBean result = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT IDTicket, CF, Tipologia, Stato, MAX(Data) AS Data FROM ticket NATURAL JOIN messaggio WHERE IDTicket = ? GROUP BY IDTicket;";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, IDTicket);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				result = new TicketBean(rs.getString("IDTicket"), rs.getString("CF"), rs.getString("Tipologia"),
						rs.getString("Stato"), rs.getDate("data"));
				return result;
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

	public ArrayList<MessaggioBean> elencoMessaggiTicket(TicketBean ticket) throws SQLException {
		return elencoMessaggiTicket(ticket.getIDTicket());
	}

	public ArrayList<MessaggioBean> elencoMessaggiTicket(String IDTicket) throws SQLException {
		ArrayList<MessaggioBean> result = new ArrayList<MessaggioBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT IDTicket, CF, Tipologia, Data, Contenuto FROM ticket NATURAL JOIN messaggio WHERE IDTicket = ? ORDER BY DATA ASC;";

		try {
			GestioneAccount gestioneaccount = new GestioneAccount();
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			preparedStatement.setString(1, IDTicket);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				MessaggioBean m = new MessaggioBean(rs.getString("IDTicket"), rs.getString("CF"),
						rs.getString("Contenuto"), rs.getDate("Data"),
						gestioneaccount.dettagliUtente(rs.getString("CF")));
				result.add(m);
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

	public boolean rispostaTicket(TicketBean ticket, UtenteBean utente, String contenuto) throws SQLException {
		return rispostaTicket(ticket.getIDTicket(), utente.getCF(), contenuto);
	}

	public boolean rispostaTicket(TicketBean ticket, String CF, String contenuto) throws SQLException {
		return rispostaTicket(ticket.getIDTicket(), CF, contenuto);
	}

	public boolean rispostaTicket(String IDTicket, UtenteBean utente, String contenuto) throws SQLException {
		return rispostaTicket(IDTicket, utente.getCF(), contenuto);
	}

	public boolean rispostaTicket(String IDTicket, String CF, String contenuto) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		if (contenuto == null || contenuto.length() < 15 || contenuto.length() > 1024) {
			return false;
		}

		String insertTicketQuery = "INSERT INTO messaggio (CF, IDTicket, Contenuto) VALUES (?, ?, ?);";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(insertTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDTicket);
			preparedStatement.setString(3, contenuto);

			preparedStatement.executeUpdate();

			connection.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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
