package test.testDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runners.MethodSorters;

import bean.ArticoloBean;
import bean.UtenteBean;
import model.GestioneAccount;
import model.GestioneCarrello;
import model.GestioneVendita;
import util.DriverManagerConnectionPool;

@TestInstance(Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestCarrelloDAO {
	GestioneAccount gestioneAccount = new GestioneAccount();
	GestioneVendita gestioneVendita = new GestioneVendita();
	GestioneCarrello gestioneCarrello = new GestioneCarrello();
	ArrayList<ArticoloBean> articoliCheck = new ArrayList<ArticoloBean>();
	UtenteBean utente = new UtenteBean("RSSMRA84B02H501C", "Mario", "Rossi", "mariorossi@email.tld", "password123",
			"Roma", 1, "Roma", "RM", 100, 1, "");
	ArticoloBean articolo = new ArticoloBean(null, "articolo", "descr", "RSSMRA84B02H501C", 10, (float) 40.1,
			"InVendita", null, null, true);
	ArticoloBean articolo1 = new ArticoloBean(null, "articolo1", "descr1", "RSSMRA84B02H501C", 15, (float) 45.1, null,
			null, null, true);
	ArticoloBean articolo2 = new ArticoloBean(null, "articolo2", "descr2", "RSSMRA84B02H501C", 20, (float) 50, null,
			null, null, true);
	ArticoloBean articolo3 = new ArticoloBean(null, "articolo3", "descr3", "RSSMRA84B02H501C", 25, (float) 73.5, null,
			null, null, true);

	public TestCarrelloDAO() {
	}

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		try {
			gestioneAccount.registrazioneUtente(utente);

			articolo.setID(gestioneVendita.inserimentoNuovoArticolo(articolo));
			articolo1.setID(gestioneVendita.inserimentoNuovoArticolo(articolo1));
			articolo2.setID(gestioneVendita.inserimentoNuovoArticolo(articolo2));
			articolo3.setID(gestioneVendita.inserimentoNuovoArticolo(articolo3));

			gestioneVendita.cambiaStato(articolo.getID(), "InVendita");
			gestioneVendita.cambiaStato(articolo1.getID(), "InVendita");
			gestioneVendita.cambiaStato(articolo2.getID(), "InVendita");
			gestioneVendita.cambiaStato(articolo3.getID(), "InVendita");

			gestioneCarrello.aggiuntaArticolo(utente.getCF(), articolo.getID(), 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	void tearDownAfterClass() throws Exception {
		DriverManagerConnectionPool.resetDB(DriverManagerConnectionPool.getConnection("root", "root"));
	}

	@Test
	void aggiornamentoQuantitaCarrello() throws SQLException {
		boolean status = false;
		int oldQuantita = -1, newQuantita = -1;
		if (gestioneVendita.dettagliArticolo(articolo.getID()) != null) {
			oldQuantita = gestioneVendita.dettagliArticolo(articolo.getID()).getQuantita();
			status = gestioneCarrello.aggiornamentoQuantita(utente.getCF(), articolo.getID(), oldQuantita + 2);
			newQuantita = gestioneVendita.dettagliArticolo(articolo.getID()).getQuantita();
		}
		if (status)
			assertEquals(newQuantita - oldQuantita, 2);
		else
			assertEquals(false, true);
	}

	@Test
	void aggiornamentoQuantitaCarrelloNot() throws SQLException {
		boolean status = false;
		int oldQuantita = -1, newQuantita = -1;
		if (gestioneVendita.dettagliArticolo("articolo inesistente") != null) {
			oldQuantita = gestioneVendita.dettagliArticolo("articolo inesistente").getQuantita();
			status = gestioneCarrello.aggiornamentoQuantita(utente.getCF(), articolo.getID(), oldQuantita + 2);
			newQuantita = gestioneVendita.dettagliArticolo("articolo inesistente").getQuantita();
		}
		if (status)
			assertEquals(newQuantita - oldQuantita, 2);
		else
			assertEquals(true, true);
	}

	@Test
	void aggiuntaArticolo() throws SQLException {
		boolean status = false;
		if (gestioneVendita.dettagliArticolo(articolo2.getID()) != null) {
			status = gestioneCarrello.aggiuntaArticolo(utente.getCF(), articolo2.getID(), 5);
		}

		assertEquals(true, status);
	}

	@Test
	void aggiuntaArticoloNot() throws SQLException {
		boolean status = false;
		if (gestioneVendita.dettagliArticolo("articolo inesistente") != null) {
			status = gestioneCarrello.aggiuntaArticolo(utente.getCF(), "articolo inesistente", 5);
		}

		assertEquals(false, status);
	}

	@Test
	void getCarrello() throws SQLException {
		gestioneCarrello.aggiuntaArticolo(utente.getCF(), articolo1, 10);
		gestioneCarrello.aggiuntaArticolo(utente.getCF(), articolo3, 10);

		ArrayList<ArticoloBean> carrello = gestioneCarrello.GetCarrello(utente);

		articoliCheck.add(articolo1);
		articoliCheck.add(articolo3);

		int status = articoliCheck.size();

		for (ArticoloBean c : carrello) {
			for (ArticoloBean cCheck : articoliCheck) {
				if (c.getID().equals(cCheck.getID()) && c.getIDVenditore().equals(utente.getCF())
						&& c.getNome().equals(cCheck.getNome())) {
					status--;
					c.setID(null);
					cCheck.setID(null);
				}

			}
		}
		articoliCheck.removeAll(articoliCheck);
		assertEquals(0, status);
	}

	@Test
	void rimozioneArticolo() throws SQLException {
		ArrayList<ArticoloBean> carrello = gestioneCarrello.GetCarrello(utente);
		for (ArticoloBean c : carrello) {
			gestioneCarrello.rimozioneArticolo(utente.getCF(), c);
		}

		assertEquals(0, gestioneCarrello.GetCarrello(utente).size());
	}

}
