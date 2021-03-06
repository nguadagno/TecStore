package control.utente;

import java.io.IOException;

import bean.UtenteBean;
import model.GestioneAccount;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ModificaUtente")
public class ModificaUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModificaUtenteServlet() {
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

		if (session.getAttribute("tipologia") == null || (!session.getAttribute("tipologia").toString().equals("5")
				&& !session.getAttribute("tipologia").toString().equals("1"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "modificaUtente");

		try {
			UtenteBean oldUtente = model.dettagliUtente(request.getParameter("CF"));
			String password = "";

			int tipologiaUtente = -1;
			int tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

			if (session.getAttribute("tipologia") != null && session.getAttribute("tipologia").toString().equals("5"))
				password = model.generatePassword(15);
			else if (session.getAttribute("tipologia") != null
					&& session.getAttribute("tipologia").toString().equals("1")
					&& request.getParameter("numeroCarta") == null && request.getParameter("password") != null)
				password = request.getParameter("password");
			else
				password = oldUtente.getPassword();

			if (request.getParameter("tipologiaUtente") != null)
				tipologiaUtente = Integer.parseInt(request.getParameter("tipologiaUtente"));
			else if (session.getAttribute("tipologia").toString().equals("1"))
				tipologiaUtente = 1;
			else {
				session.setAttribute("errore", "modificaUtente");
				redirect = "/errore.jsp";
			}

			if (tipologia == 5 && tipologiaUtente == 1) {
				session.setAttribute("errore", "modificaUtente");
				redirect = "/errore.jsp";
			} else {
				String CF = request.getParameter("CF") == null ? oldUtente.getCF() : request.getParameter("CF");
				String nome = request.getParameter("nome") == null ? oldUtente.getNome() : request.getParameter("nome");
				String cognome = request.getParameter("cognome") == null ? oldUtente.getCognome()
						: request.getParameter("cognome");
				String email = request.getParameter("email") == null ? oldUtente.getEmail()
						: request.getParameter("email");
				String via = request.getParameter("via") == null ? oldUtente.getVia() : request.getParameter("via");
				String citta = request.getParameter("citta") == null ? oldUtente.getCitta()
						: request.getParameter("citta");
				String provincia = request.getParameter("provincia") == null ? oldUtente.getProvincia()
						: request.getParameter("provincia");
				int CAP = request.getParameter("CAP") == null ? oldUtente.getCAP()
						: Integer.parseInt(request.getParameter("CAP"));
				int numeroCivico = request.getParameter("numeroCivico") == null ? oldUtente.getNumeroCivico()
						: Integer.parseInt(request.getParameter("numeroCivico"));
				String cartaDiCredito = request.getParameter("numeroCarta") == null
						|| request.getParameter("CVV") == null || request.getParameter("anno") == null
						|| request.getParameter("mese") == null ? oldUtente.getCartaDiCredito()
								: model.encryptString(request.getParameter("numeroCarta") + request.getParameter("CVV")
										+ request.getParameter("anno") + request.getParameter("mese"));

				if (model.modificaUtente(session.getAttribute("CF").toString(), new UtenteBean(CF, nome, cognome, email,
						password, via, numeroCivico, citta, provincia, CAP, tipologiaUtente, cartaDiCredito))) {
					if (session.getAttribute("tipologia").toString().equals("5")) {
						session.setAttribute("passwordUtente", password);
						session.setAttribute("emailUtente", request.getParameter("email"));
					}
					session.setAttribute("successo", "modificaUtente");
					redirect = "/successo.jsp";
				} else {
					session.setAttribute("errore", "modificaUtente");
					redirect = "/errore.jsp";
				}
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
