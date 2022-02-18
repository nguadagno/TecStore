package control.vendita;

import java.io.IOException;
import java.util.ArrayList;

import bean.ArticoloBean;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ElencoVenditeCentralinista")

public class ElencoVenditeCentralinistaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ElencoVenditeCentralinistaServlet() {
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

		if (session.getAttribute("tipologia") == null
				|| !session.getAttribute("tipologia").toString().toString().equals("2")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "elencoVenditeCentralinista");

		try {
			int limit = 10;
			if (request.getParameter("limit") != null)
				limit = Integer.parseInt(request.getParameter("limit"));
			ArrayList<ArticoloBean> elenco = model.elencoVenditeCentralinista(limit);
			session.setAttribute("elenco", elenco);
			redirect = "/elencoVenditeCentralinista.jsp";

		} catch (Exception e) {
			redirect = "/errore.jsp";
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
