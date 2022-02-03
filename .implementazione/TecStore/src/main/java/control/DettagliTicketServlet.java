package control;

import java.io.IOException;
import java.sql.SQLException;

import model.GestioneAssistenza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

		if (!request.getSession().getAttribute("tipologiaUtente").equals("1")
				&& !request.getSession().getAttribute("tipologiaUtente").equals("2")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "creazioneTicket");

		try {
			if (request.getSession().getAttribute("tipologiaUtente").equals("2"))
				model.cambiaStato(request.getParameter("IDTicket"), "InElaborazione");
			request.getSession().setAttribute("messaggi", model.elencoMessaggiTicket(request.getParameter("IDTicket")));
			response.sendRedirect(request.getContextPath() + "/dettagliTicket.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
