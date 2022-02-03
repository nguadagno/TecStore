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

@WebServlet("/modificaPassword")
public class ModificaPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ModificaPasswordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!request.getSession().getAttribute("tipologiaUtente").equals("5")
				&& !request.getSession().getAttribute("tipologiaUtente").equals("1")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		GestioneAccount model = new GestioneAccount();

		request.getSession().setAttribute("operazione", "modificaPassword");

		try {
			UtenteBean u = model.dettagliUtente(request.getSession().getAttribute("CF").toString());
			u.setPassword(u.getTipologia() == 1 ? request.getSession().getAttribute("CF").toString()
					: model.generatePassword(15));
			if (model.modificaUtente(u.getCF(), u)) {
				request.getSession().setAttribute("email", u.getEmail());
				request.getSession().setAttribute("password", u.getPassword());
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + "/errore.jsp");
			}
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
