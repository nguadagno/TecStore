package control.vendita;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet("/ricercaArticolo")
public class RicercaArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RicercaArticoloServlet() {
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
				&& !session.getAttribute("tipologia").toString().equals("5"))) {
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
			if (request.getParameter("limit") != null)
				limit = Integer.parseInt(request.getParameter("limit").toString());
			ArrayList<ArticoloBean> risultati = model.ricercaArticolo(request.getParameter("testo"), limit);
			ArrayList<FotoBean> foto = model.getFoto(risultati);

			session.setAttribute("risultati", risultati);
			session.setAttribute("foto", foto);

			redirect = "/risultatiRicerca.jsp";
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
