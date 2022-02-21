 package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import bean.ArticoloBean;
import bean.OrdineBean;
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
		GestioneAccount gestioneAccount = new GestioneAccount();
		GestioneAssistenza gestioneAssistenza = new GestioneAssistenza();
		GestioneVendita gestioneVendita = new GestioneVendita();
		GestioneOrdine gestioneOrdine = new GestioneOrdine();
		GestioneCarrello gestioneCarrello = new GestioneCarrello();
		String redirect = "index.jsp";
		RequestDispatcher dd;

		try {
			gestioneAccount.registrazioneUtente("KXBCFY80S51D296C", "nome1", "cognome1", "email1@email.tld",
					"01234567890", "v1", 1, "v1", "p1", 1, 1);
			gestioneAccount.registrazioneUtente("MJNVJN44D52G247I", "nome2", "cognome2", "email2@email.tld",
					"01234567890", "v2", 2, "v2", "p2", 2, 2);
			gestioneAccount.registrazioneUtente("VNGKGS70L68L826S", "nome3", "cognome3", "email3@email.tld",
					"01234567890", "v3", 3, "v3", "p3", 3, 3);
			gestioneAccount.registrazioneUtente("RTDCXV27L46E405N", "nome4", "cognome4", "email4@email.tld",
					"01234567890", "v4", 4, "v4", "p4", 4, 4);
			gestioneAccount.registrazioneUtente("PKCSZL43R26C890I", "nome5", "cognome5", "email5@email.tld",
					"01234567890", "v5", 5, "v5", "p5", 5, 5);

			gestioneAssistenza.creazioneTicket("KXBCFY80S51D296C", "Spedizione", "messaggio 1 ticket 1 prova");

			String IDTicket = gestioneAssistenza.elencoTicketCentralinista(1).get(0).getIDTicket();

			gestioneAssistenza.rispostaTicket(IDTicket, "KXBCFY80S51D296C", "messaggio 2 ticket 1 prova");
			gestioneAssistenza.rispostaTicket(IDTicket, "MJNVJN44D52G247I", "messaggio 3 ticket 1 prova");
			gestioneAssistenza.rispostaTicket(IDTicket, "KXBCFY80S51D296C", "messaggio 4 ticket 1 prova");

			gestioneVendita.inserimentoNuovoArticolo("articolo1", "descrizione articolo 1 ++++", "KXBCFY80S51D296C", 10, (float) 15.50, true);
			gestioneVendita.inserimentoNuovoArticolo("articolo2", "descrizione articolo 2 ++++", "KXBCFY80S51D296C", 15, (float) 30.50, true);
			gestioneVendita.inserimentoNuovoArticolo("articolo3", "descrizione articolo 3 ++++", "KXBCFY80S51D296C", 20, (float) 99.50, false);
			gestioneVendita.inserimentoNuovoArticolo("articolo4", "descrizione articolo 4 ++++", "KXBCFY80S51D296C", 40, (float) 99.50, false);

			gestioneVendita.inserimentoNuovoArticolo("articolo10", "descrizione articolo 10 ++++", "RTDCXV27L46E405N", 10, (float) 15.50, true);
			gestioneVendita.inserimentoNuovoArticolo("articolo20", "descrizione articolo 20 ++++", "RTDCXV27L46E405N", 15, (float) 30.50, true);
			gestioneVendita.inserimentoNuovoArticolo("articolo30", "descrizione articolo 30 ++++", "RTDCXV27L46E405N", 20, (float) 99.50, false);
			gestioneVendita.inserimentoNuovoArticolo("articolo40", "descrizione articolo 40 ++++", "RTDCXV27L46E405N", 40, (float) 99.50, false);

			ArticoloBean a1 = gestioneVendita.ricercaArticolo("art10", 10).get(0);
			ArticoloBean a2 = gestioneVendita.ricercaArticolo("art20", 10).get(0);
			ArticoloBean a3 = gestioneVendita.ricercaArticolo("art30", 10).get(0);
			ArticoloBean a4 = gestioneVendita.ricercaArticolo("art40", 10).get(0);

//			gestioneVendita.inserimentoFoto(a1.getID(),
//					new FileInputStream(new File("D:/TecStore/.implementazione/TecStore/test/img/F1.png")));
//			gestioneVendita.inserimentoFoto(a1.getID(),
//					new FileInputStream(new File("D:/TecStore/.implementazione/TecStore/test/img/F2.png")));
//			gestioneVendita.inserimentoFoto(a1.getID(),
//					new FileInputStream(new File("D:/TecStore/.implementazione/TecStore/test/img/F3.png")));
//			gestioneVendita.inserimentoFoto(a2.getID(),
//					new FileInputStream(new File("D:/TecStore/.implementazione/TecStore/test/img/F2.png")));
//			gestioneVendita.inserimentoFoto(a3.getID(),
//					new FileInputStream(new File("D:/TecStore/.implementazione/TecStore/test/img/F3.png")));

			gestioneCarrello.aggiuntaArticolo("KXBCFY80S51D296C", a1.getID(), 2);
			gestioneCarrello.aggiuntaArticolo("KXBCFY80S51D296C", a2.getID(), 4);
			gestioneCarrello.aggiuntaArticolo("KXBCFY80S51D296C", a3.getID(), 6);
			gestioneCarrello.aggiuntaArticolo("KXBCFY80S51D296C", a4.getID(), 8);

			gestioneOrdine.creazioneOrdine("KXBCFY80S51D296C");

			OrdineBean o1 = gestioneOrdine.ricercaOrdiniCliente("KXBCFY80S51D296C", "art1", 1).get(0);
			OrdineBean o2 = gestioneOrdine.ricercaOrdiniCliente("KXBCFY80S51D296C", "art2", 1).get(0);

			if (gestioneOrdine.cambiaStato(o1.getID(), "InAttesaRimborso"))
				System.out.println("o1 rimborso");

			if (gestioneOrdine.cambiaStato(o2.getID(), "InAttesaRimborso"))
				System.out.println("o2 rimborso");

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
