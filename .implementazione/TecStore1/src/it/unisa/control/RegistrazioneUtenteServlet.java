package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import Bean.UtenteBean;
import it.unisa.model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registrazione")
public class RegistrazioneUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegistrazioneUtenteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getSession().getAttribute("tipologiaUtente").toString().equals("1")
				|| request.getSession().getAttribute("tipologiaUtente").toString().equals("2")
				|| request.getSession().getAttribute("tipologiaUtente").toString().equals("3")
				|| request.getSession().getAttribute("tipologiaUtente").toString().equals("4")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		GestioneAccount model = new GestioneAccount();

		String password = request.getSession().getAttribute("tipologiaUtente").equals("1")
				? request.getAttribute("password").toString()
				: model.generatePassword(15);

		UtenteBean utente = new UtenteBean(request.getAttribute("CF").toString(),
				request.getAttribute("Nome").toString(), request.getAttribute("Cognome").toString(),
				request.getAttribute("Email").toString(), password, request.getAttribute("Via").toString(),
				Integer.parseInt(request.getAttribute("NumeroCivico").toString()),
				request.getAttribute("Citta").toString(), request.getAttribute("Provincia").toString(),
				Integer.parseInt(request.getAttribute("CAP").toString()),
				Integer.parseInt(request.getAttribute("Tipologia").toString()),
				request.getAttribute("CartaDiCredito").toString());

		request.getSession().setAttribute("operazione", "registrazioneUtente");
		try {
			if (model.registrazioneUtente(utente)) {
				request.getSession().setAttribute("email", request.getParameter("email"));
				request.getSession().setAttribute("password", password);
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/errore.jsp");
			}
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
