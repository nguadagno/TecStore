package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.TicketBean;
import it.unisa.model.GestioneAssistenza;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/elencoTicketCliente")
public class ElencoTicketClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ElencoTicketClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAssistenza model = new GestioneAssistenza();

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "elencoTicket");

		try {
			ArrayList<TicketBean> elenco = model.elencoTicketCliente(request.getAttribute("CF").toString());
			request.getSession().setAttribute("elenco", elenco);
			response.sendRedirect(request.getContextPath() + "/centroassistenza.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
