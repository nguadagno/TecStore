package test.testDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runners.MethodSorters;

import Bean.ArticoloBean;
import Bean.MessaggioBean;
import Bean.OrdineBean;
import Bean.TicketBean;
import Bean.UtenteBean;
import model.DriverManagerConnectionPool;
import model.GestioneAccount;
import model.GestioneAssistenza;
import model.GestioneVendita;

@TestInstance(Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestTicketDAO {
	GestioneAssistenza gestioneAssistenza = new GestioneAssistenza();
	GestioneAccount gestioneAccount = new GestioneAccount();
	ArrayList<TicketBean> elenco = new ArrayList<TicketBean>();
	TicketBean ticket = new TicketBean();
	UtenteBean utente = new UtenteBean("RSSMRA84B02H501C", "Mario", "Rossi", "mariorossi@email.tld", "password123",
			"Roma", 1, "Roma", "RM", 100, 1, "");
	String IDTicket = "";
	String CF = "RSSMRA84B02H501C";

	public TestTicketDAO() {
	}

	@BeforeAll
	public void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			gestioneAccount.registrazioneUtente(utente);
			gestioneAssistenza.creazioneTicket("RSSMRA84B02H501C", "Pagamento", "Vorrei info per 123");
			IDTicket = gestioneAssistenza.elencoTicketCliente("RSSMRA84B02H501C", 1).get(0).getIDTicket();
			ticket = gestioneAssistenza.dettagliTicket(IDTicket);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	public void cleanUp() throws SQLException, FileNotFoundException {
		DriverManagerConnectionPool.resetDB(DriverManagerConnectionPool.getConnection("root", "root"));
	}

	@Test
	public void cambiaStato() throws SQLException {
		boolean status = false;
		String stato = "InAttesaCliente";
		gestioneAssistenza.cambiaStato(ticket.getIDTicket(), stato);
		ticket = gestioneAssistenza.dettagliTicket(IDTicket);
		if (ticket.getStato().equals(stato))
			status = true;
		assertEquals(true, status);
	}

	@Test
	public void cambiaStatoNot() throws SQLException {
		boolean status = false;
		gestioneAssistenza.cambiaStato(ticket.getIDTicket(), "StatoSbagliato");
		ticket = gestioneAssistenza.dettagliTicket(IDTicket);
		if (ticket.getStato().equals("StatoSbagliato"))
			status = true;
		assertEquals(false, status);
	}

	@Test
	public void dettagliTicket() throws SQLException {
		boolean status = false;

		TicketBean ticket1 = gestioneAssistenza.dettagliTicket(IDTicket);

		if (ticket1 != null && ticket1.getIDCliente().equals(ticket.getIDCliente())
				&& ticket1.getIDTicket().equals(ticket.getIDTicket()) && ticket1.getStato().equals(ticket.getStato())
				&& ticket1.getTipologia().equals(ticket.getTipologia()))
			status = true;
		assertEquals(true, status);
	}

	@Test
	public void dettagliTicketNot() throws SQLException {
		boolean status = false;

		TicketBean ticket1 = gestioneAssistenza.dettagliTicket("IDFinto");

		if (ticket1 != null && ticket1.getIDCliente().equals(ticket.getIDCliente())
				&& ticket1.getIDTicket().equals(ticket.getIDTicket()) && ticket1.getStato().equals(ticket.getStato())
				&& ticket1.getTipologia().equals(ticket.getTipologia()))
			status = true;
		assertEquals(false, status);
	}

	@Test
	public void elencoTicketCentralinista() throws SQLException {
		int status = 0;
		gestioneAssistenza.creazioneTicket("RSSMRA84B02H501C", "Pagamento", "Vorrei info per 123");
		gestioneAssistenza.creazioneTicket("RSSMRA84B02H501C", "Pagamento", "Vorrei info per 123");
		gestioneAssistenza.creazioneTicket("RSSMRA84B02H501C", "Pagamento", "Vorrei info per 123");
		ArrayList<TicketBean> elenco = gestioneAssistenza.elencoTicketCentralinista(3);
		int finalStatus = elenco.size();
		for (TicketBean a : elenco) {
			if (a.getStato().equals("InAttesa"))
				status++;
		}
		assertEquals(finalStatus, status);

	}

	@Test
	public void elencoTicketCentralinistaNot() throws SQLException {

		gestioneAssistenza.creazioneTicket("RSSMRA84B02H501C", "Pagamento", "Vorrei info per 123");
		gestioneAssistenza.creazioneTicket("RSSMRA84B02H501C", "Pagamento", "Vorrei info per 123");
		gestioneAssistenza.creazioneTicket("RSSMRA84B02H501C", "Pagamento", "Vorrei info per 123");
		ArrayList<TicketBean> elenco = gestioneAssistenza.elencoTicketCentralinista(3);
		int finalStatus = elenco.size();
		int status = elenco.size();
		for (TicketBean a : elenco) {
			if (!a.getStato().equals("InAttesa"))
				status--;
		}
		assertEquals(finalStatus, status);

	}

	@Test
	public void elencoTicketCliente() throws SQLException {
		int status = 0;
		ArrayList<TicketBean> elenco = gestioneAssistenza.elencoTicketCliente(CF, 3);
		int finalStatus = elenco.size();
		for (TicketBean a : elenco) {
			if (a.getIDCliente().equals(CF))
				status++;
		}
		assertEquals(finalStatus, status);

	}

	@Test
	public void elencoTicketClienteNot() throws SQLException {

		ArrayList<TicketBean> elenco = gestioneAssistenza.elencoTicketCliente(CF, 3);
		int finalStatus = elenco.size();
		int status = elenco.size();
		for (TicketBean a : elenco) {
			if (!a.getIDCliente().equals(CF))
				status--;
		}
		assertEquals(status, finalStatus);
	}

	@Test
	public void rispostaTicket() throws SQLException {
		boolean status = false;

		ArrayList<MessaggioBean> elenco1 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		gestioneAssistenza.rispostaTicket(IDTicket, CF, "Siamo Occupati");

		ArrayList<MessaggioBean> elenco2 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		elenco2.removeAll(elenco1);

		if (elenco2.size() == 1 && elenco2.get(0).getIDTicket().equals(IDTicket) && elenco2.get(0).getCF().equals(CF)
				&& elenco2.get(0).getContenuto().equals("Siamo Occupati"))
			status = true;

		assertEquals(true, status);
	}

	@Test
	public void rispostaTicketNot() throws SQLException {
		boolean status = false;

		ArrayList<MessaggioBean> elenco1 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		gestioneAssistenza.rispostaTicket("IDTicket", CF, "Siamo Occupati");

		ArrayList<MessaggioBean> elenco2 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		elenco2.removeAll(elenco1);
		if (elenco2.size() == 1 && elenco2.get(0).getIDTicket().equals(IDTicket) && elenco2.get(0).getCF().equals(CF)
				&& elenco2.get(0).getContenuto().equals("Siamo Occupati"))
			status = true;

		assertEquals(status, false);
	}

	@Test
	public void elencoMessaggiTicket() throws SQLException {

		ArrayList<MessaggioBean> elenco1 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		gestioneAssistenza.rispostaTicket(IDTicket, CF, "1");
		gestioneAssistenza.rispostaTicket(IDTicket, CF, "2");
		gestioneAssistenza.rispostaTicket(IDTicket, CF, "3");
		gestioneAssistenza.rispostaTicket(IDTicket, CF, "4");

		ArrayList<MessaggioBean> elenco3 = new ArrayList<MessaggioBean>();

		elenco3.add(new MessaggioBean(IDTicket, CF, "1", null));
		elenco3.add(new MessaggioBean(IDTicket, CF, "2", null));
		elenco3.add(new MessaggioBean(IDTicket, CF, "3", null));
		elenco3.add(new MessaggioBean(IDTicket, CF, "4", null));

		ArrayList<MessaggioBean> elenco2 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		elenco2.removeAll(elenco1);

		int status = elenco2.size();
		for (MessaggioBean r : elenco2) {
			for (MessaggioBean a : elenco3) {
				if (r.getIDTicket().equals(a.getIDTicket()) && r.getCF().equals(utente.getCF())
						&& r.getContenuto().equals(a.getContenuto())) {
					status--;
					a.setIDTicket(null);
				}
			}
		}

		assertEquals(0, status);
	}

	@Test
	public void elencoMessaggiTicketNot() throws SQLException {

		ArrayList<MessaggioBean> elenco1 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		gestioneAssistenza.rispostaTicket("IDTicket", CF, "1");
		gestioneAssistenza.rispostaTicket("IDTicket", CF, "2");
		gestioneAssistenza.rispostaTicket("IDTicket", CF, "3");
		gestioneAssistenza.rispostaTicket("IDTicket", CF, "4");

		ArrayList<MessaggioBean> elenco3 = new ArrayList<MessaggioBean>();

		elenco3.add(new MessaggioBean("IDTicket", CF, "1", null));
		elenco3.add(new MessaggioBean("IDTicket", CF, "2", null));
		elenco3.add(new MessaggioBean("IDTicket", CF, "3", null));
		elenco3.add(new MessaggioBean("IDTicket", CF, "4", null));

		ArrayList<MessaggioBean> elenco2 = gestioneAssistenza.elencoMessaggiTicket(IDTicket);

		elenco2.removeAll(elenco1);

		int status = elenco2.size();

		for (MessaggioBean r : elenco2) {
			for (MessaggioBean a : elenco3) {
				if (r.getIDTicket().equals(a.getIDTicket()) && r.getCF().equals(utente.getCF())
						&& r.getContenuto().equals(a.getContenuto())) {
					status--;
					a.setIDTicket(null);
				}
			}
		}

		assertEquals(0, status);
	}

}
