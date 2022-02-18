package control.carrello;

import java.io.IOException;
import java.sql.SQLException;

import bean.ArticoloBean;
import model.GestioneCarrello;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AggiuntaAlCarrello")

public class AggiuntaAlCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneCarrello model = new GestioneCarrello();
		GestioneVendita model1 = new GestioneVendita();
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

		session.setAttribute("successo", "AggiuntaAlCarrello");

		try {
			ArticoloBean art = model1.dettagliArticolo(request.getParameter("IDArticolo"));
			if (art.getQuantita() >= Integer.parseInt(request.getParameter("quantita").toString())) {
				if (model.aggiuntaArticolo(session.getAttribute("CF").toString(), request.getParameter("IDArticolo"),
						Integer.parseInt(request.getParameter("quantita")))) {
					session.setAttribute("successo", "aggiuntaCarrello");
					redirect = "/successo.jsp";
				} else {
					session.setAttribute("errore", "erroreAggiuntaCarrello");
					redirect = "/errore.jsp";
				}

			} else {
				session.setAttribute("errore", "erroreQuantitaAggiuntaCarrello");
				redirect = "/errore.jsp";
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