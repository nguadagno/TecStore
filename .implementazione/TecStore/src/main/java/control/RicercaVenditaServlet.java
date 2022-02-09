package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.ArticoloBean;
import Bean.FotoBean;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/RicercaVendita")
public class RicercaVenditaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RicercaVenditaServlet() {
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

		if (session.getAttribute("tipologia") == null || (!session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("4"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "ricercaArticolo");
		try {

			int limit = 10;
			if (request.getAttribute("limit") != null)
				limit = Integer.parseInt(request.getParameter("limit"));
			ArrayList<ArticoloBean> risultati;
			if (session.getAttribute("tipologia").toString().equals("1"))
				risultati = model.elencoVenditeCF(session.getAttribute("CF").toString(), request.getParameter("nome"),
						limit);
			else
				risultati = model.elencoVenditeTipologia(session.getAttribute("tipologia").toString(),
						request.getParameter("nome"), limit);

			ArrayList<FotoBean> foto = model.getFoto(risultati);

			session.setAttribute("risultati", risultati);
			session.setAttribute("foto", foto);

			redirect = "/risultatiRicerca.jsp";

		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
