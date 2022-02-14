package test.testDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.UtenteBean;
import model.GestioneAccount;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runners.MethodSorters;

@TestInstance(Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestUtenteDAO {
	GestioneAccount model = new GestioneAccount();
	String passwordUtente = "password123";
	UtenteBean utente = new UtenteBean("RSSMRA84B02H501C", "Mario", "Rossi", "mariorossi@email.tld", passwordUtente,
			"Roma", 1, "Roma", "RM", 100, 1, "");
	UtenteBean dipendente = new UtenteBean("VRDLGU80A01F205T", "Luigi", "Verdi", "luigiverdi@email.tld",
			model.generatePassword(15), "Milano", 1, "Milano", "MI", 200, 2, "");

	public TestUtenteDAO() {
	}

	@BeforeAll
	public void setUp() {
		try {
			model.registrazioneUtente(utente);
			model.registrazioneUtente(dipendente);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void exists() {
		try {
			assertEquals(true, model.exists(utente.getCF()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void autenticazione() {
		try {
			assertEquals(true, model.autenticazione(utente.getEmail(), passwordUtente));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void dettagliUtente() {
		try {
			UtenteBean u = model.dettagliUtente(utente.getCF());
			boolean status = (u.getCAP() == utente.getCAP() && u.getCognome().equals((utente.getCognome()))
					&& u.getNome().equals(utente.getNome()) && u.getEmail().equals(utente.getEmail())
					&& u.getProvincia().equals(utente.getProvincia()) && u.getTipologia() == utente.getTipologia()
					&& u.getVia().equals(utente.getVia()) && u.getNumeroCivico() == utente.getNumeroCivico()
					&& u.getTipologia() == utente.getTipologia());
			assertEquals(true, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getTipologia() {
		try {
			UtenteBean u = model.dettagliUtente(utente.getCF());

			assertEquals(u.getTipologia(), utente.getTipologia());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modificaUtente() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			utente.setCognome("Verdi");
			model.modificaUtente(utente.getCF(), utente);
			UtenteBean u = model.dettagliUtente(utente.getCF());
			assertEquals("Verdi", u.getCognome());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modificaPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			passwordUtente = "password12345";
			utente.setPassword(passwordUtente);
			assertEquals(true, model.modificaUtente(utente.getCF(), utente)
					&& model.autenticazione(utente.getEmail(), passwordUtente));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void ricercaDipendenti() {
		try {
			ArrayList<UtenteBean> risultati = model.ricercaDipendenti("erdi");
			boolean status = false;
			for (UtenteBean u : risultati) {
				status = (u.getCAP() == dipendente.getCAP() && u.getCognome().equals((dipendente.getCognome()))
						&& u.getNome().equals(dipendente.getNome()) && u.getEmail().equals(dipendente.getEmail())
						&& u.getProvincia().equals(dipendente.getProvincia())
						&& u.getTipologia() == dipendente.getTipologia() && u.getVia().equals(dipendente.getVia())
						&& u.getNumeroCivico() == dipendente.getNumeroCivico()
						&& u.getTipologia() == dipendente.getTipologia());

				if (status == true)
					break;
			}
			assertEquals(true, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void zEliminaUtente() {
		try {
			model.eliminaUtente(utente.getCF(), utente.getCF());
			assertEquals(false, model.exists(utente.getCF()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void zEliminaDipendente() {
		try {
			model.eliminaUtente(utente.getCF(), dipendente.getCF());
			assertEquals(false, model.exists(dipendente.getCF()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
