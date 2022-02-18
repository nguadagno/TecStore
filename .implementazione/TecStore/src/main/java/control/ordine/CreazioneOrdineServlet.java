package control.ordine;

import java.io.IOException;
import java.sql.SQLException;

import bean.UtenteBean;
import model.GestioneAccount;
import model.GestioneOrdine;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CreazioneOrdine")

public class CreazioneOrdineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public CreazioneOrdineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneOrdine model = new GestioneOrdine();
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

		session.setAttribute("operazione", "creazioneOrdine");

		try {
			GestioneAccount a = new GestioneAccount();

			UtenteBean u = a.dettagliUtente(session.getAttribute("CF").toString());
			if (u.getCartaDiCredito() == null) {
				redirect = "/inserimentoCartaDiCredito.jsp";
			} else {
				if (model.creazioneOrdine(session.getAttribute("CF").toString())) {
					session.setAttribute("successo", "creazioneOrdine");
					redirect = "/successo.jsp";
				} else {
					session.setAttribute("errore", "creazioneOrdine");
					redirect = "/errore.jsp";
				}
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
