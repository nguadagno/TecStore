package control.utente;

import java.io.IOException;
import java.sql.SQLException;

import model.GestioneAccount;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DettagliUtente")
public class DettagliUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DettagliUtenteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAccount model = new GestioneAccount();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || (!session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("5"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "dettagli");
		try {
			if (session.getAttribute("tipologia").toString().equals("1")) {
				session.setAttribute("dettagliutente", model.dettagliUtente(session.getAttribute("CF").toString()));
				redirect = "/dettagliutente.jsp";
			} else if (session.getAttribute("tipologia").toString().equals("5")) {
				session.setAttribute("dettagliutente", model.dettagliUtente(request.getParameter("CF")));
				redirect = "/dettagliutente.jsp";
			} else {
				session.setAttribute("errore", "AccessoNonAutorizzato");
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
