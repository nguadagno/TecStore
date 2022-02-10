package control;

import java.io.IOException;

import Bean.UtenteBean;
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
			String password = "";
			if (session.getAttribute("tipologia") != null && session.getAttribute("tipologia").toString().equals("5"))
				password = model.generatePassword(15);
			else if (session.getAttribute("tipologia") != null
					&& session.getAttribute("tipologia").toString().equals("1"))
				password = request.getParameter("password");
			else
				throw new Exception();

			int tipologia = 1;

			if (session.getAttribute("tipologiaUtente") != null)
				tipologia = Integer.parseInt(request.getParameter("tipologiaUtente"));

			if (model.modificaUtente(session.getAttribute("CF").toString(),
					new UtenteBean(request.getParameter("CF"), request.getParameter("nome"),
							request.getParameter("cognome"), request.getParameter("email"), password,
							request.getParameter("via"), Integer.parseInt(request.getParameter("numeroCivico")),
							request.getParameter("citta"), request.getParameter("provincia"),
							Integer.parseInt(request.getParameter("CAP")), tipologia,
							request.getParameter("cartaDiCredito")))) {
				if (session.getAttribute("tipologia").toString().equals("5")) {
					session.setAttribute("passwordUtente", password);
					session.setAttribute("emailUtente", request.getParameter("email"));
				}
				redirect = "/successo.jsp";
			} else {
				redirect = "/errore.jsp";
			}
		} catch (Exception e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroremodificautente");
			redirect = "/errore.jsp";
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
