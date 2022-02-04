package control;

import java.io.IOException;
import java.sql.SQLException;

import Bean.UtenteBean;
import model.GestioneAccount;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/autenticazione")
public class AutenticazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AutenticazioneServlet() {
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

		session.setAttribute("operazione", "Autenticazione");
		try {
			if (model.autenticazione(request.getParameter("email"), request.getParameter("password"))) {
				UtenteBean utente = model.dettagliUtenteByEmail(request.getParameter("email"));
				session.setAttribute("CF", utente.getCF());
				session.setAttribute("Nome", utente.getNome());
				session.setAttribute("Cognome", utente.getCognome());
				session.setAttribute("tipologia", utente.getTipologia());
				redirect = "/index.jsp";
			} else {
				redirect = "/errore.jsp";
			}
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
	}
}
