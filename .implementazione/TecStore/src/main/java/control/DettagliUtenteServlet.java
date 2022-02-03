package control;

import java.io.IOException;
import java.sql.SQLException;

import model.GestioneAccount;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dettagliUtente")
public class DettagliUtenteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DettagliUtenteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneAccount model = new GestioneAccount();

		request.getSession().setAttribute("operazione", "dettagli");
		try {
			request.getSession().setAttribute("utente", model.dettagliUtente(request.getParameter("CF")));
			response.sendRedirect(request.getContextPath() + "/dettagliUtente.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
