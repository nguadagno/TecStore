package control;

import java.io.IOException;
import java.util.ArrayList;

import Bean.ArticoloBean;
import Bean.OrdineBean;
import model.GestioneAccount;
import model.GestioneAssistenza;
import model.GestioneCarrello;
import model.GestioneOrdine;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DatiProva")

public class datiprovaservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAccount g = new GestioneAccount();
		GestioneAssistenza g1 = new GestioneAssistenza();
		GestioneVendita g2 = new GestioneVendita();
		GestioneOrdine g3 = new GestioneOrdine();
		GestioneCarrello g4 = new GestioneCarrello();
		String redirect = "index.jsp";
		RequestDispatcher dd;

		try {
			g.registrazioneUtente("KXBCFY80S51D296C", "nome1", "cognome1", "email1@email.tld", "01234567890", "v1", 1, "v1", "p1", 1, 1);
			g.registrazioneUtente("MJNVJN44D52G247I", "nome2", "cognome2", "email2@email.tld", "01234567890", "v2", 2, "v2", "p2", 2, 2);
			g.registrazioneUtente("VNGKGS70L68L826S", "nome3", "cognome3", "email3@email.tld", "01234567890", "v3", 3, "v3", "p3", 3, 3);
			g.registrazioneUtente("RTDCXV27L46E405N", "nome4", "cognome4", "email4@email.tld", "01234567890", "v4", 4, "v4", "p4", 4, 4);
			g.registrazioneUtente("PKCSZL43R26C890I", "nome5", "cognome5", "email5@email.tld", "01234567890", "v5", 5, "v5", "p5", 5, 5);

//			g1.creazioneTicket("CF1", "asd", "R0");

//			String IDTicket = g1.elencoTicketCentralinista(1).get(0).getIDTicket();

//			g1.rispostaTicket(IDTicket, "CF1", "r1");
//			g1.rispostaTicket(IDTicket, "CF2", "r2");
//			g1.rispostaTicket(IDTicket, "CF1", "r3");

			g2.inserimentoNuovoArticolo("art1", "descr1", "KXBCFY80S51D296C", 10, (float) 15.50, true);
			g2.inserimentoNuovoArticolo("art2", "descr2", "KXBCFY80S51D296C", 15, (float) 30.50, true);
			g2.inserimentoNuovoArticolo("art3", "descr3", "KXBCFY80S51D296C", 20, (float) 99.50, false);
			g2.inserimentoNuovoArticolo("art4", "descr4", "KXBCFY80S51D296C", 40, (float) 99.50, false);

			g2.inserimentoNuovoArticolo("art10", "descr1", "RTDCXV27L46E405N", 10, (float) 15.50, true);
			g2.inserimentoNuovoArticolo("art20", "descr2", "RTDCXV27L46E405N", 15, (float) 30.50, true);
			g2.inserimentoNuovoArticolo("art30", "descr3", "RTDCXV27L46E405N", 20, (float) 99.50, false);
			g2.inserimentoNuovoArticolo("art40", "descr4", "RTDCXV27L46E405N", 40, (float) 99.50, false);

//			ArticoloBean a1 = g2.ricercaArticolo("art10", 10).get(0);
//			ArticoloBean a2 = g2.ricercaArticolo("art20", 10).get(0);
//			ArticoloBean a3 = g2.ricercaArticolo("art30", 10).get(0);
//			ArticoloBean a4 = g2.ricercaArticolo("art40", 10).get(0);
//
//			g4.aggiuntaArticolo("CF1", a1.getID(), 2);
//			g4.aggiuntaArticolo("CF1", a2.getID(), 4);
//			g4.aggiuntaArticolo("CF1", a3.getID(), 6);
//			g4.aggiuntaArticolo("CF1", a4.getID(), 8);
//
//			g3.creazioneOrdine("CF1");
//
//			OrdineBean o1 = g3.ricercaOrdiniCliente("CF1", "art1", 1).get(0);
//			OrdineBean o2 = g3.ricercaOrdiniCliente("CF1", "art2", 1).get(0);
//
//			if (g3.cambiaStato(o1.getID(), "InAttesaRimborso"))
//				System.out.println("o1 rimborso");
//
//			if (g3.cambiaStato(o2.getID(), "InAttesaRimborso"))
//				System.out.println("o2 rimborso");

			redirect = "index.jsp";
		} catch (Exception e) {
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
