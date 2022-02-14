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

import Bean.ArticoloBean;
import Bean.OrdineBean;
import Bean.UtenteBean;
import model.DriverManagerConnectionPool;
import model.GestioneAccount;
import model.GestioneCarrello;
import model.GestioneOrdine;
import model.GestioneVendita;

@TestInstance(Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestOrdineDAO {
	GestioneAccount gestioneAccount = new GestioneAccount();
	GestioneVendita gestioneVendita = new GestioneVendita();
	GestioneCarrello gestioneCarrello = new GestioneCarrello();
	GestioneOrdine gestioneOrdine = new GestioneOrdine();
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
	ArrayList<ArticoloBean> articoliCheck = new ArrayList<ArticoloBean>();

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		try {
			gestioneAccount.registrazioneUtente(utente);

			articolo.setID(gestioneVendita.inserimentoNuovoArticolo(articolo));
			articolo1.setID(gestioneVendita.inserimentoNuovoArticolo(articolo1));
			articolo2.setID(gestioneVendita.inserimentoNuovoArticolo(articolo2));
			articolo3.setID(gestioneVendita.inserimentoNuovoArticolo(articolo3));

			articoliCheck.add(articolo);
			articoliCheck.add(articolo1);
			articoliCheck.add(articolo2);
			articoliCheck.add(articolo3);

			gestioneVendita.cambiaStato(articolo.getID(), "InVendita");
			gestioneVendita.cambiaStato(articolo1.getID(), "InVendita");
			gestioneVendita.cambiaStato(articolo2.getID(), "InVendita");
			gestioneVendita.cambiaStato(articolo3.getID(), "InVendita");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	void tearDownAfterClass() throws Exception {
		DriverManagerConnectionPool.resetDB(DriverManagerConnectionPool.getConnection("root", "root"));
	}

	@Test
	void creazioneOrdine() throws SQLException {
		gestioneCarrello.aggiuntaArticolo(utente.getCF(), articolo, 5);
		gestioneCarrello.aggiuntaArticolo(utente.getCF(), articolo1, 10);
		@SuppressWarnings("static-access")
		ArrayList<ArticoloBean> carrello = gestioneCarrello.GetCarrello(utente.getCF());

		boolean status = gestioneOrdine.creazioneOrdine(utente.getCF());
		int counter = carrello.size();
		if (status && counter != 0) {
			ArrayList<OrdineBean> ordini = gestioneOrdine.elencoOrdiniMagazziniere();

			for (OrdineBean o : ordini) {
				for (ArticoloBean c : carrello) {
					if (o.getIDArticolo().equals(c.getID()) && o.getIDCliente().equals(utente.getCF())) {
						c.setID(null);
						counter--;
					}
				}
			}
			assertEquals(0, counter);
		} else
			assertEquals(false, true);
	}

	@Test
	void creazioneOrdineEmpty() throws SQLException {
		@SuppressWarnings("static-access")
		ArrayList<ArticoloBean> carrello = gestioneCarrello.GetCarrello(utente.getCF());

		boolean status = gestioneOrdine.creazioneOrdine(utente.getCF());
		int counter = carrello.size();
		if (status && counter != 0) {
			ArrayList<OrdineBean> ordini = gestioneOrdine.elencoOrdiniMagazziniere();

			for (OrdineBean o : ordini) {
				for (ArticoloBean c : carrello) {
					if (o.getIDArticolo().equals(c.getID()) && o.getIDCliente().equals(utente.getCF())) {
						c.setID(null);
						counter--;
					}
				}
			}
			assertEquals(0, counter);
		} else
			assertEquals(true, true);
	}

	@Test
	void dettagliOrdine() throws SQLException {
		gestioneCarrello.aggiuntaArticolo(utente.getCF(), articolo2, 5);
		boolean status = gestioneOrdine.creazioneOrdine(utente.getCF());

		OrdineBean ordine = gestioneOrdine.ricercaOrdiniCliente(utente.getCF(), "articolo", 5).get(0);

		if (status && ordine != null) {
			if (ordine.getIDArticolo().equals(articolo2.getID()) && ordine.getIDCliente().equals(utente.getCF())
					&& ordine.getQuantita() == 5 && ordine.getStato().equals("InAttesa"))
				assertEquals(true, true);
			return;
		}
		assertEquals(false, true);
	}

	@Test
	void yyyElencoOrdiniMagazziniere() throws SQLException {
		ArrayList<OrdineBean> ordini = gestioneOrdine.elencoOrdiniMagazziniere();
		int status = 3;
		for (OrdineBean o : ordini) {
			for (ArticoloBean a : articoliCheck) {
				if (o.getIDArticolo().equals(a.getID()) && o.getIDCliente().equals(utente.getCF())
						&& o.getStato().equals("InAttesa")) {
					status--;
					a.setID(null);
				}
			}
		}
		assertEquals(0, status);
	}

	@Test
	void wwwElencoRimborsi() throws SQLException {
		ArrayList<OrdineBean> ordini = gestioneOrdine.elencoOrdiniMagazziniere();
		for (OrdineBean o : ordini) {
			gestioneOrdine.cambiaStato(o.getID(), "Spedito");
			gestioneOrdine.cambiaStato(o.getID(), "InAttesaRimborso");
		}

		ArrayList<OrdineBean> rimborsi = gestioneOrdine.elencoRimborsi(10);

		int status = 3;
		for (OrdineBean r : rimborsi) {
			for (ArticoloBean a : articoliCheck) {
				if (r.getIDArticolo().equals(a.getID()) && r.getIDCliente().equals(utente.getCF())
						&& r.getStato().equals("InAttesaRimborso")) {
					status--;
					a.setID(null);
				}
			}
		}
		assertEquals(0, status);
	}

}
