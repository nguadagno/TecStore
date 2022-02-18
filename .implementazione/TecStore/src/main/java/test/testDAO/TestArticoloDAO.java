package test.testDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

import model.GestioneAccount;
import model.GestioneVendita;
import util.DriverManagerConnectionPool;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runners.MethodSorters;

import bean.ArticoloBean;
import bean.UtenteBean;;

@TestInstance(Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestArticoloDAO {
	GestioneVendita gestioneVendita = new GestioneVendita();
	GestioneAccount gestioneAccount = new GestioneAccount();
	UtenteBean utente = new UtenteBean("RSSMRA84B02H501C", "Mario", "Rossi", "mariorossi@email.tld", "password123",
			"Roma", 1, "Roma", "RM", 100, 1, "");
	ArticoloBean articolo = new ArticoloBean(null, "articolo", "descr", "RSSMRA84B02H501C", 3, (float) 40.1,
			"InVendita", null, null, true);
	ArticoloBean articolo1 = new ArticoloBean(null, "articolo1", "descr1", "RSSMRA84B02H501C", 3, (float) 40, null,
			null, null, true);
	ArticoloBean articolo2 = new ArticoloBean(null, "articolo2", "descr2", "RSSMRA84B02H501C", 3, (float) 40, null,
			null, null, true);
	ArticoloBean articolo3 = new ArticoloBean(null, "articolo3", "descr3", "RSSMRA84B02H501C", 3, (float) 40, null,
			null, null, true);

	ArrayList<ArticoloBean> articoliCheck = new ArrayList<ArticoloBean>();
	String IDArticolo;

	public TestArticoloDAO() {
	}

	@BeforeAll
	public void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			gestioneAccount.registrazioneUtente(utente);

			articolo.setID(gestioneVendita.inserimentoNuovoArticolo(articolo));
			articolo1.setID(gestioneVendita.inserimentoNuovoArticolo(articolo1));
			articolo2.setID(gestioneVendita.inserimentoNuovoArticolo(articolo2));

			articoliCheck.add(articolo1);
			articoliCheck.add(articolo2);

			gestioneVendita.cambiaStato(articolo.getID(), "InVendita");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	public void cleanUp() throws SQLException, FileNotFoundException {
		DriverManagerConnectionPool.resetDB(DriverManagerConnectionPool.getConnection("root", "root"));
	}

	@Test
	public void ricercaArticolo() throws SQLException {
		ArrayList<ArticoloBean> articoli = gestioneVendita.ricercaArticolo(articolo.getNome());
		boolean status = false;
		for (ArticoloBean a : articoli) {
			status = (a.getDescrizione().equals(articolo.getDescrizione()) && a.getID().equals(articolo.getID())
					&& a.getNome().equals(articolo.getNome()) && a.getPrezzo() == articolo.getPrezzo()
					&& a.getQuantita() == articolo.getQuantita());

			if (status == true)
				break;

		}
		assertEquals(true, status);
	}

	@Test
	public void ricercaArticoloNot() throws SQLException {
		ArrayList<ArticoloBean> articoli = gestioneVendita.ricercaArticolo("articolo non esistente");
		boolean status = false;
		for (ArticoloBean a : articoli) {
			status = (a.getDescrizione().equals(articolo.getDescrizione()) && a.getID().equals(articolo.getID())
					&& a.getNome().equals(articolo.getNome()) && a.getPrezzo() == articolo.getPrezzo()
					&& a.getQuantita() == articolo.getQuantita());

			if (status == true)
				break;

		}
		assertEquals(false, status);
	}

	@Test
	public void modificaArticolo() throws SQLException {
		articolo.setPrezzo(2);
		gestioneVendita.modificaArticolo(articolo);
		gestioneVendita.cambiaStato(articolo.getID(), "InVendita");

		ArticoloBean a = gestioneVendita.dettagliArticolo(articolo.getID());

		boolean status = (a.getDescrizione().equals(articolo.getDescrizione()) && a.getID().equals(articolo.getID())
				&& a.getNome().equals(articolo.getNome()) && a.getPrezzo() == articolo.getPrezzo()
				&& a.getQuantita() == articolo.getQuantita());

		assertEquals(true, status);
	}

	@Test
	public void modificaArticoloNot() throws SQLException {
		articolo.setNome(null);
		gestioneVendita.modificaArticolo(articolo);
		ArticoloBean a = gestioneVendita.dettagliArticolo(articolo.getID());

		boolean status = (a.getDescrizione().equals(articolo.getDescrizione()) && a.getID().equals(articolo.getID())
				&& a.getNome().equals(articolo.getNome()) && a.getPrezzo() == articolo.getPrezzo()
				&& a.getQuantita() == articolo.getQuantita());

		assertEquals(true, status);
	}

	@Test
	public void elencoVenditeCentralinista() throws SQLException {
		ArrayList<ArticoloBean> articoli = gestioneVendita.elencoVenditeCentralinista(3);

		int status = articoliCheck.size();

		for (ArticoloBean a : articoli) {
			for (ArticoloBean aCheck : articoliCheck) {
				if (a.getDescrizione().equals(aCheck.getDescrizione()) && a.getID().equals(aCheck.getID())
						&& a.getNome().equals(aCheck.getNome()) && a.getPrezzo() == aCheck.getPrezzo()
						&& a.getQuantita() == aCheck.getQuantita() && a.getStato().equals("InAttesa")) {
					status--;
					aCheck.setID(null);
					a.setID(null);
				}
			}
		}
		assertEquals(0, status);
	}

	@Test
	public void elencoVenditeCF() throws SQLException {
		ArrayList<ArticoloBean> articoli = gestioneVendita.elencoVenditeCF(utente.getCF(), "art", 3);

		int status = articoliCheck.size();

		for (ArticoloBean a : articoli) {
			for (ArticoloBean aCheck : articoliCheck) {
				if (a.getDescrizione().equals(aCheck.getDescrizione()) && a.getID().equals(aCheck.getID())
						&& a.getNome().equals(aCheck.getNome()) && a.getPrezzo() == aCheck.getPrezzo()
						&& a.getQuantita() == aCheck.getQuantita() && a.getIDVenditore().equals(utente.getCF())) {
					status--;
					aCheck.setID(null);
					a.setID(null);
				}
			}
		}
		assertEquals(0, status);
	}

	@Test
	public void elencoVenditeCFNot() throws SQLException {
		ArrayList<ArticoloBean> articoli = gestioneVendita.elencoVenditeCF("", "art", 3);

		int status = articoliCheck.size();

		for (ArticoloBean a : articoli) {
			for (ArticoloBean aCheck : articoliCheck) {
				if (a.getDescrizione().equals(aCheck.getDescrizione()) && a.getID().equals(aCheck.getID())
						&& a.getNome().equals(aCheck.getNome()) && a.getPrezzo() == aCheck.getPrezzo()
						&& a.getQuantita() == aCheck.getQuantita() && a.getIDVenditore().equals(utente.getCF())) {
					status--;
					aCheck.setID(null);
					a.setID(null);
				}
			}
		}
		assertEquals(2, status);
	}

	@Test
	public void elencoVenditeTipologia() throws SQLException {
		ArrayList<ArticoloBean> articoli = gestioneVendita.elencoVenditeTipologia("1", "art", 10);

		int status = articoliCheck.size();

		for (ArticoloBean a : articoli) {
			for (ArticoloBean aCheck : articoliCheck) {
				if (a.getDescrizione().equals(aCheck.getDescrizione()) && a.getID().equals(aCheck.getID())
						&& a.getNome().equals(aCheck.getNome()) && a.getPrezzo() == aCheck.getPrezzo()
						&& a.getQuantita() == aCheck.getQuantita()
						&& gestioneAccount.getTipologia(a.getIDVenditore()) == 1) {
					status--;
					aCheck.setID(null);
					a.setID(null);
				}
			}
		}
		assertEquals(0, status);
	}

	@Test
	public void elencoVenditeTipologiaNot() throws SQLException {
		ArrayList<ArticoloBean> articoli = gestioneVendita.elencoVenditeTipologia("-1", "art", 10);

		int status = articoliCheck.size();

		for (ArticoloBean a : articoli) {
			for (ArticoloBean aCheck : articoliCheck) {
				if (a.getDescrizione().equals(aCheck.getDescrizione()) && a.getID().equals(aCheck.getID())
						&& a.getNome().equals(aCheck.getNome()) && a.getPrezzo() == aCheck.getPrezzo()
						&& a.getQuantita() == aCheck.getQuantita()
						&& gestioneAccount.getTipologia(a.getIDVenditore()) == -1) {
					status--;
					aCheck.setID(null);
					a.setID(null);
				}
			}
		}
		assertEquals(2, status);
	}

	@Test
	public void inserimentoNuovoArticolo() throws SQLException {
		articolo3.setID(gestioneVendita.inserimentoNuovoArticolo(articolo3));
		assertNotEquals(null, articolo3.getID());
	}

	@Test
	public void inserimentoNuovoArticoloNot() throws SQLException {
		articolo3 = new ArticoloBean(null, "", "", "RSSMRA84B02H501C", 3, (float) -1, null, null, null, true);
		articolo3.setID(gestioneVendita.inserimentoNuovoArticolo(articolo3));
		assertEquals(null, articolo3.getID());
	}

	@Test
	public void zzzRimozioneArticolo() throws SQLException {
		gestioneVendita.rimozioneArticolo(utente.getCF(), articolo.getID());
		gestioneVendita.rimozioneArticolo(utente.getCF(), articolo1.getID());
		gestioneVendita.rimozioneArticolo(utente.getCF(), articolo2.getID());
		gestioneVendita.rimozioneArticolo(utente.getCF(), articolo3.getID());

		assertEquals(false, gestioneVendita.ricercaArticolo("art").size() > 0);
	}

}
