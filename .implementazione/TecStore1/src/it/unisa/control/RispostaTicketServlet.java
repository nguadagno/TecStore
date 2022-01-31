package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import it.unisa.model.GestioneAssistenza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/rispostaTicket")
public class RispostaTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RispostaTicketServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAssistenza model = new GestioneAssistenza();

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")
				&& !request.getSession().getAttribute("tipologiaUtente").toString().equals("2")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "rispostaTicket");

		try {
			if (model.rispostaTicket(request.getSession().getAttribute("IDTicket").toString(),
					request.getSession().getAttribute("CF").toString(),
					request.getSession().getAttribute("messaggio").toString()))
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/errore.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
