package control;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import model.GestioneAccount;
import model.GestioneAssistenza;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/datiprova")

public class datiprovaservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAccount g = new GestioneAccount();
		GestioneAssistenza g1 = new GestioneAssistenza();
		GestioneVendita g2 = new GestioneVendita ();
		String redirect = "index.jsp";
		RequestDispatcher dd;

		try {
			g.registrazioneUtente("CF1", "nome1", "cognome1", "email1", "p1", "v1", 1, "v1", "p1", 1, 1);
			g.registrazioneUtente("CF2", "nome2", "cognome2", "email2", "p2", "v2", 2, "v2", "p2", 2, 2);
			g.registrazioneUtente("CF3", "nome3", "cognome3", "email3", "p3", "v3", 3, "v3", "p3", 3, 3);
			g.registrazioneUtente("CF4", "nome4", "cognome4", "email4", "p4", "v4", 4, "v4", "p4", 4, 4);
			g.registrazioneUtente("CF5", "nome5", "cognome5", "email5", "p5", "v5", 5, "v5", "p5", 5, 5);

			g1.creazioneTicket("CF1", "asd", "R0");

			String IDTicket = g1.elencoTicketCentralinista(1).get(0).getIDTicket();

			g1.rispostaTicket(IDTicket, "CF1", "r1");
			g1.rispostaTicket(IDTicket, "CF2", "r2");
			g1.rispostaTicket(IDTicket, "CF1", "r3");
			
			g2.inserimentoNuovoArticolo("art1", "descr1", "CF1", 10, (float) 15.50, true);
			g2.inserimentoNuovoArticolo("art2", "descr2", "CF1", 15, (float) 30.50, true);
			g2.inserimentoNuovoArticolo("art3", "descr3", "CF1", 20, (float) 99.50, false);

			redirect = "index.jsp";
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
