package control;

import java.io.IOException;
import java.sql.SQLException;
import model.GestioneOrdine;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ConfermaOrdine")

public class ConfermaOrdineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ConfermaOrdineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneOrdine model = new GestioneOrdine();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().equals("3")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "ConfermaOrdine");

		try {
			if (model.cambiaStato(request.getParameter("IDOrdine"), request.getParameter("stato"))) {
				session.setAttribute("successo", "confermaOrdine");
				redirect = "/successo.jsp";
				if (request.getParameter("stato").equals("Spedito")) {
					if (!model.setCodiceTracciamento(request.getParameter("IDOrdine"),
							request.getParameter("Tracking"))) {
						session.setAttribute("errore", "cambiastato_tracking");
						redirect = "/errore.jsp";
					}
				}
			} else {
				session.setAttribute("errore", "cambiastato_tracking");
				redirect = "/errore.jsp";
			}
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
