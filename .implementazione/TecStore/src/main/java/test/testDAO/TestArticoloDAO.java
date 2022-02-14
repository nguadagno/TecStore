package test.testDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.ArticoloBean;
import Bean.UtenteBean;
import model.GestioneAccount;
import model.GestioneVendita;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;;

@TestInstance(Lifecycle.PER_CLASS)
class TestArticoloDAO {
	GestioneVendita model = new GestioneVendita();
	UtenteBean utente = new UtenteBean("RSSMRA84B02H501C", "Mario", "Rossi", "mariorossi@email.tld", "password123",
			"Roma", 1, "Roma", "RM", 100, 1, "");
	ArticoloBean articolo = new ArticoloBean(null, "Tastiera", "bella", "RSSMRA84B02H501C", 3, (float) 40, null, null,
			null, true);
	String IDArticolo;

	public TestArticoloDAO() {
	}

	@BeforeAll
	public void setUp() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {

			GestioneAccount model1 = new GestioneAccount();
			model1.registrazioneUtente(utente);
			articolo.setID(model.inserimentoNuovoArticolo(articolo));
			model.cambiaStato(articolo.getID(), "InVendita");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void ricercaArticolo() throws SQLException {
		ArrayList<ArticoloBean> articoli = model.ricercaArticolo(articolo.getNome());
		boolean status = false;
		for (ArticoloBean a : articoli) {

			if (a.getDescrizione().equals(articolo.getDescrizione()) && a.getID().equals(articolo.getID())
					&& a.getNome().equals(articolo.getNome()) && a.getPrezzo() == articolo.getPrezzo()
					&& a.getQuantita() == articolo.getQuantita()) {
				status = true;
				break;
			}
		}
		assertEquals(true, status);
	}

	@SuppressWarnings("static-access")
	@Test
	public void modificaArticolo() throws SQLException {
		articolo.setPrezzo(2);

		model.modificaArticolo(articolo);
		model.cambiaStato(articolo.getID(), "InVendita");
		ArticoloBean a = model.dettagliArticolo(articolo.getID());

		boolean status = false;

		if (a.getDescrizione().equals(articolo.getDescrizione()) && a.getID().equals(articolo.getID())
				&& a.getNome().equals(articolo.getNome()) && a.getPrezzo() == articolo.getPrezzo()
				&& a.getQuantita() == articolo.getQuantita())
			status = true;

		assertEquals(true, status);
	}

	@SuppressWarnings("static-access")
	@Test
	public void elencoVenditeCentralinista() throws SQLException {
		ArticoloBean articolo1 = new ArticoloBean(null, "articolo1", "bella", "RSSMRA84B02H501C", 3, (float) 40, null,
				null, null, true);
		ArticoloBean articolo2 = new ArticoloBean(null, "articolo2", "bella", "RSSMRA84B02H501C", 3, (float) 40, null,
				null, null, true);
		ArrayList<ArticoloBean> articoli2 = new ArrayList<ArticoloBean>();
		articoli2.add(articolo2);
		articoli2.add(articolo1);
		articolo1.setID(model.inserimentoNuovoArticolo(articolo1));
		articolo2.setID(model.inserimentoNuovoArticolo(articolo2));
		ArrayList<ArticoloBean> articoli = model.elencoVenditeCentralinista(3);
		int status = 0;
		for (ArticoloBean a : articoli) {
			for (ArticoloBean a1 : articoli2) {
				if (a.getDescrizione().equals(a1.getDescrizione()) && a.getID().equals(a1.getID())
						&& a.getNome().equals(a1.getNome()) && a.getPrezzo() == a1.getPrezzo()
						&& a.getQuantita() == a1.getQuantita()) {
					status++;
					a1.setID(null);
					a.setID(null);
				}
			}
		}
		assertEquals(2, status);
	}

}
