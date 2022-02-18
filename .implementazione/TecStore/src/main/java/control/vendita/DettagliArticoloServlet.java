package control.vendita;

import java.io.IOException;
import java.util.ArrayList;

import bean.ArticoloBean;
import bean.FotoBean;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DettagliArticolo")

public class DettagliArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DettagliArticoloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneVendita model = new GestioneVendita();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") != null && (!session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("5")
				&& !session.getAttribute("tipologia").toString().equals("2"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "dettagliArticolo");

		try {
			ArticoloBean articolo;
			if (request.getParameter("IDArticolo") != null)
				articolo = model.dettagliArticolo(request.getParameter("IDArticolo"));
			else
				articolo = (ArticoloBean) session.getAttribute("dettagliArticolo");

			session.setAttribute("dettagliArticolo", articolo);
			session.setAttribute("fotoArticolo", model.getAllFoto(request.getParameter("IDArticolo")));

			if (session.getAttribute("tipologia") != null && session.getAttribute("tipologia").toString().equals("2"))
				model.cambiaStato(articolo.getID(), "InElaborazione");

			redirect = "/dettagliArticolo.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}