package test.testDAO;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import Bean.UtenteBean;
import model.GestioneAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestUtenteDAO {
	GestioneAccount model = new GestioneAccount();
	UtenteBean utente = new UtenteBean("RSSMRA84B02H501C", "Mario", "Rossi", "mariorossi@email.tld", "password123",
			"Roma", 1, "Roma", "RM", 100, 1, "");

	public TestUtenteDAO() {
	}

	@BeforeEach
	public void setUp() {
		try {
			model.registrazioneUtente(utente);
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

}
