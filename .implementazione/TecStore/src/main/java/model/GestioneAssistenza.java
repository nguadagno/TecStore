package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import Bean.ClienteBean;
import Bean.MessaggioBean;
import Bean.TicketBean;
import Bean.UtenteBean;

public class GestioneAssistenza {
	public ArrayList<TicketBean> elencoTicketCliente(ClienteBean c, int limit) throws SQLException {
		return elencoTicketCliente(c.getCF(), limit);
	}

	public ArrayList<TicketBean> elencoTicketCliente(String CF, int limit) throws SQLException {
		ArrayList<TicketBean> ticketList = new ArrayList<TicketBean>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM ticket WHERE IDCliente = ? LIMIT = ?;";

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
		String searchTicketQuery = "SELECT DISTINCT IDTicket,messaggio.Data AS Data ,CF,Tipologia,Stato FROM ticket NATURAL JOIN messaggio WHERE stato = 'InAttesa' ORDER BY messaggio.Data LIMIT ?;";

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

	public boolean creazioneTicket(String CF, String tipologia) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertTicketQuery = "INSERT INTO ticket (IDCliente, Tipologia, Stato) VALUES (?,?,?);";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(insertTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, tipologia);
			preparedStatement.setString(3, "InAttesa");

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

	public boolean cambiaStato(TicketBean ticket, String stato) throws SQLException {
		return cambiaStato(ticket.getIDTicket(), stato);
	}

	public boolean cambiaStato(String IDTicket, String stato) throws SQLException {
		if (stato != "InElaborazione" && stato != "InAttesa" && stato != "Chiuso")
			return false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String cambiaStatoQuery = "UPDATE ticket SET stato = ? WHERE IDTicket = ?;";
		String getStatoQuery = "SELECT stato FROM ticket where ID = ?;";

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

	public TicketBean dettagliTicket(TicketBean ticket) throws SQLException {
		return dettagliTicket(ticket.getIDTicket());
	}

	public TicketBean dettagliTicket(String IDTicket) throws SQLException {
		TicketBean result = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String searchTicketQuery = "SELECT * FROM ticket WHERE IDTicket = '" + IDTicket + "';";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				result = new TicketBean(rs.getString("IDTicket"), rs.getString("IDCliente"), rs.getString("Tipologia"),
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
		String searchTicketQuery = "SELECT * FROM messaggio WHERE IDTicket = '" + IDTicket + "';";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(searchTicketQuery);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				MessaggioBean m = new MessaggioBean(rs.getString("IDTicket"), rs.getString("CF"),
						rs.getString("Contenuto"), rs.getDate("Date"));
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

		String insertTicketQuery = "INSERT INTO messaggio (CF, IDTicket, Contenuto, Data) VALUES (?, ?, ?, ?);";

		try {
			connection = DriverManagerConnectionPool.getConnection("cliente", "cliente");
			preparedStatement = connection.prepareStatement(insertTicketQuery);
			preparedStatement.setString(1, CF);
			preparedStatement.setString(2, IDTicket);
			preparedStatement.setString(3, contenuto);
			preparedStatement.setDate(4, new java.sql.Date(Calendar.getInstance().getTime().getTime()));

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
