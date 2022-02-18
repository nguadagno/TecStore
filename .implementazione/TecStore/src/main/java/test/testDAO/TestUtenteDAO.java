package test.testDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.ArrayList;

import model.GestioneAccount;
import util.DriverManagerConnectionPool;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runners.MethodSorters;

import bean.UtenteBean;

@TestInstance(Lifecycle.PER_CLASS)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class TestUtenteDAO {
	GestioneAccount gestioneAccount = new GestioneAccount();
	String passwordUtente = "password123";
	UtenteBean utente = new UtenteBean("RSSMRA84B02H501C", "Mario", "Rossi", "mariorossi@email.tld", passwordUtente,
			"Roma", 1, "Roma", "RM", 100, 1, "");
	UtenteBean dipendente = new UtenteBean("VRDLGU80A01F205T", "Luigi", "Verdi", "luigiverdi@email.tld",
			gestioneAccount.generatePassword(15), "Milano", 1, "Milano", "MI", 200, 2, "");

	public TestUtenteDAO() {
	}

	@BeforeAll
	public void setUp() {
		try {
			gestioneAccount.registrazioneUtente(utente);
			gestioneAccount.registrazioneUtente(dipendente);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@AfterAll
	public void cleanUp() throws SQLException, FileNotFoundException {
		DriverManagerConnectionPool.resetDB(DriverManagerConnectionPool.getConnection("root", "root"));
	}

	@SuppressWarnings("static-access")
	@Test
	public void exists() {
		try {
			assertEquals(true, gestioneAccount.exists(utente.getCF()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void existsNot() {
		try {
			assertEquals(false, gestioneAccount.exists(""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void autenticazione() {
		try {
			assertEquals(true, gestioneAccount.autenticazione(utente.getEmail(), passwordUtente));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void autenticazioneNoPassword() {
		try {
			assertEquals(false, gestioneAccount.autenticazione(utente.getEmail(), ""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void autenticazioneNoEmail() {
		try {
			assertEquals(false, gestioneAccount.autenticazione("", passwordUtente));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void dettagliUtente() {
		try {
			UtenteBean u = gestioneAccount.dettagliUtente(utente.getCF());
			boolean status = false;
			if (u != null)
				status = (u.getCAP() == utente.getCAP() && u.getCognome().equals((utente.getCognome()))
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
	public void dettagliUtenteNot() {
		try {
			UtenteBean u = gestioneAccount.dettagliUtente("");
			boolean status = false;
			if (u != null)
				status = (u.getCAP() == utente.getCAP() && u.getCognome().equals((utente.getCognome()))
						&& u.getNome().equals(utente.getNome()) && u.getEmail().equals(utente.getEmail())
						&& u.getProvincia().equals(utente.getProvincia()) && u.getTipologia() == utente.getTipologia()
						&& u.getVia().equals(utente.getVia()) && u.getNumeroCivico() == utente.getNumeroCivico()
						&& u.getTipologia() == utente.getTipologia());
			assertEquals(false, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getTipologia() {
		try {
			UtenteBean u = gestioneAccount.dettagliUtente(utente.getCF());
			assertEquals(u.getTipologia(), utente.getTipologia());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getTipologiaNot() {
		try {
			assertEquals(null, gestioneAccount.dettagliUtente(""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modificaUtente() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			utente.setCognome("Verdi");
			gestioneAccount.modificaUtente(utente.getCF(), utente);
			assertEquals("Verdi", gestioneAccount.dettagliUtente(utente.getCF()).getCognome());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modificaUtenteNot() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			utente.setCognome("");
			gestioneAccount.modificaUtente(utente.getCF(), utente);
			assertNotEquals("", gestioneAccount.dettagliUtente(utente.getCF()).getCognome());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modificaPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			passwordUtente = "password12345";
			utente.setPassword(passwordUtente);
			assertEquals(true, gestioneAccount.modificaUtente(utente.getCF(), utente)
					&& gestioneAccount.autenticazione(utente.getEmail(), passwordUtente));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void modificaPasswordNot() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			passwordUtente = "";
			utente.setPassword(passwordUtente);
			assertEquals(false, gestioneAccount.modificaUtente(utente.getCF(), utente)
					&& gestioneAccount.autenticazione(utente.getEmail(), passwordUtente));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void ricercaDipendenti() {
		try {
			ArrayList<UtenteBean> risultati = gestioneAccount.ricercaDipendenti("erdi");
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

	@Test
	public void ricercaDipendentiNot() {
		try {
			ArrayList<UtenteBean> risultati = gestioneAccount.ricercaDipendenti("");
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
			assertEquals(false, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void zEliminaUtente() {
		try {
			gestioneAccount.eliminaUtente(utente.getCF(), utente.getCF());
			assertEquals(false, gestioneAccount.exists(utente.getCF()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	@Test
	public void zEliminaDipendente() {
		try {
			gestioneAccount.eliminaUtente(utente.getCF(), dipendente.getCF());
			assertEquals(false, gestioneAccount.exists(dipendente.getCF()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
