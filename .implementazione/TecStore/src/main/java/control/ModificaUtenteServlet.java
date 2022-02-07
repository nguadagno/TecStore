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

@WebServlet("/Modificautente")
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

		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().equals("5")
				&& !session.getAttribute("tipologia").toString().equals("1")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "modificaUtente");

		try {
			if (model.modificaUtente(request.getParameter("CF"), new UtenteBean(request.getParameter("CF"),
					request.getParameter("nome"), request.getParameter("cognome"), request.getParameter("email"),
					request.getParameter("password"), request.getParameter("via"),
					Integer.parseInt(request.getParameter("numeroCivico")), request.getParameter("citta"),
					request.getParameter("provincia"), Integer.parseInt(request.getParameter("CAP")),
					Integer.parseInt(request.getParameter("tipologia")), request.getParameter("cartaDiCredito")))) {
				redirect = "/successo.jsp";
			} else {
				redirect = "/errore.jsp";

			}
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
