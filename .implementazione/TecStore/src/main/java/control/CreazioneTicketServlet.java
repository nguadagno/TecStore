package control;

import java.io.IOException;
import java.sql.SQLException;

import model.GestioneAssistenza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/creazioneTicket")
public class CreazioneTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreazioneTicketServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAssistenza model = new GestioneAssistenza();

		if (!request.getSession().getAttribute("tipologiaUtente").equals("1")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "creazioneTicket");

		try {
			if (model.creazioneTicket(request.getSession().getAttribute("CF").toString(),
					request.getParameter("messaggio")))
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/errore.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
