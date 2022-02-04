package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.FotoBean;
import Bean.OrdineBean;
import model.GestioneOrdine;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ElaborazioneOrdine")

public class DettagliOrdineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DettagliOrdineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneOrdine model = new GestioneOrdine();
		GestioneVendita model1 = new GestioneVendita();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (!session.getAttribute("tipologiaUtente").equals("1")
				&& !session.getAttribute("tipologiaUtente").equals("3")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
		}

		session.setAttribute("operazione", "dettagliOrdine");

		try {
			OrdineBean ordine = model.dettagliOrdineByID(request.getParameter("IDOrdine"));
			session.setAttribute("ordine", ordine);
			session.setAttribute("ordine", model.dettagliOrdineCliente(session.getAttribute("CF").toString(),
					request.getParameter("IDOrdine")));

			ArrayList<FotoBean> foto = model1.getFoto(request.getParameter("IDArticolo"));

			session.setAttribute("foto", foto);

			if (session.getAttribute("tipologiaUtente").equals("3")) {
				model.cambiaStato(request.getParameter("IDOrdine"), "InElaborazione");
				redirect = "/dettagliOrdineMagazziniere.jsp";
			} else if (session.getAttribute("tipologiaUtente").equals("1"))
				redirect = "/dettagliOrdine.jsp";
			else
				redirect = "/errore.jsp";

		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
	}
}
