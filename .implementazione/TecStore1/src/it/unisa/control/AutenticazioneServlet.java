package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import Bean.UtenteBean;
import it.unisa.model.GestioneAccount;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/autenticazione")
public class AutenticazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AutenticazioneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAccount model = new GestioneAccount();

		request.getSession().setAttribute("operazione", "Autenticazione");
		try {
			if (model.autenticazione(request.getAttribute("email").toString(),
					request.getAttribute("password").toString())) {
				UtenteBean utente = model.dettagliUtenteByEmail(request.getAttribute("email").toString());
				request.getSession().setAttribute("CF", utente.getCF());
				request.getSession().setAttribute("tipologia", utente.getTipologia());
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/errore.jsp");
			}
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
