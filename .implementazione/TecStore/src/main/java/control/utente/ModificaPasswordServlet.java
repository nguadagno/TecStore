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

@WebServlet("/ModificaPassword")
public class ModificaPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModificaPasswordServlet() {
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

		session.setAttribute("operazione", "modificaPassword");

		try {
			UtenteBean u = model.dettagliUtente(session.getAttribute("CF").toString());
			u.setPassword(
					u.getTipologia() == 1
							? request.getParameter("password").toString().length() > 10
									&& request.getParameter("password").toString().length() < 64
											? request.getParameter("password").toString()
											: ""
							: model.generatePassword(15));

			if (u.getPassword() != null && ! u.getPassword().isEmpty() && model.modificaUtente(u.getCF(), u)) {
				if (session.getAttribute("tipologia").toString().equals("5")) {
					session.setAttribute("emailUtente", u.getEmail());
					session.setAttribute("passwordUtente", u.getPassword());
				}
				session.setAttribute("successo", "modificaPassword");
				redirect = "/successo.jsp";
			} else {
				session.setAttribute("errore", "modificaPassword");
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
