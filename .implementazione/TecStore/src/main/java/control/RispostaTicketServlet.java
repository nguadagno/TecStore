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

@WebServlet("/RispostaTicket")
public class RispostaTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RispostaTicketServlet() {
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

		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("2")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "rispostaTicket");

		try {
			session.setAttribute("errore", "rispostaTicket");
			redirect = "/errore.jsp";
			if (model.rispostaTicket(request.getParameter("IDTicket"), session.getAttribute("CF").toString(),
					request.getParameter("messaggio"))) {
				if (session.getAttribute("tipologia").toString().equals("2")
						&& model.cambiaStato(request.getParameter("IDTicket"), "InAttesaCliente")) {
					session.setAttribute("successo", "rispostaTicketCentralinista");
					redirect = "/successo.jsp";
				}
				if (session.getAttribute("tipologia").toString().equals("1")
						&& model.cambiaStato(request.getParameter("IDTicket"), "InAttesa")) {
					session.setAttribute("successo", "rispostaTicket");
					redirect = "/successo.jsp";
				}
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
