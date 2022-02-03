package control;

import java.io.IOException;
import java.sql.SQLException;

import Bean.UtenteBean;
import model.GestioneAccount;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/modificautente")
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

		if (!request.getSession().getAttribute("tipologiaUtente").equals("5")
				&& !request.getSession().getAttribute("tipologiaUtente").equals("1")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		GestioneAccount model = new GestioneAccount();

		request.getSession().setAttribute("operazione", "modificaUtente");

		try {
			if (model.modificaUtente(request.getParameter("CF"), new UtenteBean(request.getParameter("CF"),
					request.getParameter("nome"), request.getParameter("cognome"), request.getParameter("email"),
					request.getParameter("password"), request.getParameter("via"),
					Integer.parseInt(request.getParameter("numeroCivico")), request.getParameter("citta"),
					request.getParameter("provincia"), Integer.parseInt(request.getParameter("CAP")),
					Integer.parseInt(request.getParameter("tipologia")), request.getParameter("cartaDiCredito")))) {
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/errore.jsp");
			}
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
