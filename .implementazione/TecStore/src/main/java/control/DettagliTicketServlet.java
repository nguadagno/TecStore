package control;

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

@WebServlet("/dettagliTicket")
public class DettagliTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DettagliTicketServlet() {
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
			System.out.println("qui");
			request.getSession(true).setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "creazioneTicket");

		try {
			if (session.getAttribute("IDTicket") == null || session.getAttribute("IDTicket").toString().isEmpty()) {
				response.setStatus(500);
				request.getSession(true).setAttribute("errore", "IDTicket mancante");
				redirect = "/errore.jsp";
				return;
			}

			if (session.getAttribute("tipologia").equals("2"))
				model.cambiaStato(request.getParameter("IDTicket"), "InElaborazione");
			request.getSession(true).setAttribute("messaggi",
					model.elencoMessaggiTicket(request.getParameter("IDTicket")));
			System.out.println(model.elencoMessaggiTicket(request.getParameter("IDTicket").toString()));
			redirect = "/dettagliTicket.jsp";
		} catch (SQLException e) {
			response.setStatus(500);
			request.getSession(true).setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
