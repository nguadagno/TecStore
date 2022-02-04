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

@WebServlet("/elencoTicketCliente")
public class ElencoTicketClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ElencoTicketClienteServlet() {
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

		if (!session.getAttribute("tipologiaUtente").equals("1")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
		}

		session.setAttribute("operazione", "elencoTicket");

		try {
			ArrayList<TicketBean> elenco = model.elencoTicketCliente(request.getParameter("CF"),
					Integer.parseInt(request.getParameter("CF")));
			session.setAttribute("elenco", elenco);
			redirect = "/centroassistenza.jsp";
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";

		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
	}
}
