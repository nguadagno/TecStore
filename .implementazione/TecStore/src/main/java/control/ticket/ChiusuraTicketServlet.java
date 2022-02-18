package control.ticket;

import java.io.IOException;
import java.sql.SQLException;

import model.GestioneAssistenza;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ChiusuraTicket")
public class ChiusuraTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChiusuraTicketServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAssistenza model = new GestioneAssistenza();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || (!session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("2"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}
		session.setAttribute("operazione", "chiusuraTicket");

		try {
			if (model.cambiaStato(request.getParameter("IDTicket"), "Chiuso")) {
				session.setAttribute("successo", "chiusuraTicket");
				redirect = "/successo.jsp";
			} else {
				session.setAttribute("errore", "chiusuraTicket");
				redirect = "/errore.jsp";
			}
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
