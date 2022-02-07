package control;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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

@WebServlet("/modificaPassword")
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

		if (!session.getAttribute("tipologia").toString().equals("5")
				&& !session.getAttribute("tipologia").toString().equals("1")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "modificaPassword");

		try {
			UtenteBean u = model.dettagliUtente(session.getAttribute("CF").toString(), request.getParameter("CF"));
			u.setPassword(
					u.getTipologia() == 1 ? request.getParameter("password").toString() : model.generatePassword(15));
			if (model.modificaUtente(u.getCF(), u)) {
				session.setAttribute("email", u.getEmail());
				session.setAttribute("password", u.getPassword());
				redirect = "/successo.jsp";
			} else {
				redirect = "/errore.jsp";
			}
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
