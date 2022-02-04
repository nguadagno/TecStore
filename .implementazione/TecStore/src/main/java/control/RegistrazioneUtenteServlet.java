package control;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import Bean.UtenteBean;
import model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		GestioneAccount model = new GestioneAccount();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologiaUtente").equals("1") && session.getAttribute("tipologiaUtente").equals("2")
				&& session.getAttribute("tipologiaUtente").equals("3")
				&& session.getAttribute("tipologiaUtente").equals("4")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
		}

		String password = session.getAttribute("tipologiaUtente").equals("1") ? request.getParameter("password")
				: model.generatePassword(15);

		UtenteBean utente = new UtenteBean(request.getParameter("CF"), request.getParameter("Nome"),
				request.getParameter("Cognome"), request.getParameter("Email"), password, request.getParameter("Via"),
				Integer.parseInt(request.getParameter("NumeroCivico")), request.getParameter("Citta"),
				request.getParameter("Provincia"), Integer.parseInt(request.getParameter("CAP")),
				Integer.parseInt(request.getParameter("Tipologia")), request.getParameter("CartaDiCredito"));

		session.setAttribute("operazione", "registrazioneUtente");
		try {
			if (model.registrazioneUtente(utente)) {
				session.setAttribute("email", request.getParameter("email"));
				session.setAttribute("password", password);
				redirect = "/successo.jsp";
			} else {
				redirect = "/errore.jsp";
			}
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
	}
}
