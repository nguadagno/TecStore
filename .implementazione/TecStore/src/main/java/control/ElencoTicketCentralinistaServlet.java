package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.TicketBean;
import model.GestioneAssistenza;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/elencoTicketCentralinista")
public class ElencoTicketCentralinistaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ElencoTicketCentralinistaServlet() {
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

		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().toString().equals("2")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "elencoTicket");

		try {
			int limit = 10;
			if (request.getParameter("limit") != null && !request.getParameter("limit").isEmpty())
				limit = Integer.parseInt(request.getParameter("limit"));
			ArrayList<TicketBean> elencoTicket = model.elencoTicketCentralinista(limit);
			session.setAttribute("elencoTicket", elencoTicket);
			redirect = "/gestioneAssistenza.jsp";
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
