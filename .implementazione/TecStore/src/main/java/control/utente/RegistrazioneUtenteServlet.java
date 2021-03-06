package control.utente;

import java.io.IOException;

import bean.UtenteBean;
import model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Registrazione")
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
		GestioneAccount model = new GestioneAccount();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") != null && !session.getAttribute("tipologia").toString().equals("5")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		String password = session.getAttribute("tipologia") == null ? request.getParameter("password")
				: model.generatePassword(15);

		int tipologiaUtente;
		if (request.getParameter("tipologiaUtente") != null && session.getAttribute("tipologia").toString().equals("5"))
			tipologiaUtente = Integer.parseInt(request.getParameter("tipologiaUtente"));
		else if (request.getParameter("tipologiaUtente") == null && session.getAttribute("tipologia") == null) {
			tipologiaUtente = 1;
		} else
			tipologiaUtente = -1;
		UtenteBean utente = new UtenteBean(request.getParameter("CF"), request.getParameter("nome"),
				request.getParameter("cognome"), request.getParameter("email"), password, request.getParameter("via"),
				request.getParameter("numerocivico") != null ? Integer.parseInt(request.getParameter("numerocivico"))
						: null,
				request.getParameter("citta"), request.getParameter("provincia"),
				request.getParameter("CAP") != null ? Integer.parseInt(request.getParameter("CAP")) : null,
				tipologiaUtente, "");

		session.setAttribute("operazione", "registrazioneUtente");
		try {
			if (model.registrazioneUtente(utente)) {
				session.setAttribute("emailUtente", request.getParameter("email"));
				session.setAttribute("passwordUtente", password);
				session.setAttribute("successo", "registrazione");
				redirect = "/successo.jsp";
			} else {
				session.setAttribute("errore", "registrazione");
				redirect = "/errore.jsp";
			}
		} catch (Exception e) {
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
