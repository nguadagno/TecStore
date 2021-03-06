package control.carrello;

import java.io.IOException;
import java.sql.SQLException;
import model.GestioneCarrello;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/aggiornamentoQuantitaCarrello")

public class AggiornamentoQuantitaCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@SuppressWarnings("static-access")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneCarrello model = new GestioneCarrello();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;
		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().equals("1")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "AggiornamentoQuantitaCarrello");

		try {
			if (model.aggiornamentoQuantita(session.getAttribute("CF").toString(), request.getParameter("IDArticolo"),
					Integer.parseInt(request.getParameter("quantita")))) {
				session.setAttribute("carrello", model.GetCarrello(session.getAttribute("CF").toString()));
				redirect = "/carrello.jsp";
			} else {
				session.setAttribute("errore", "erroreAggiornamentoQuantitaCarrello");
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
