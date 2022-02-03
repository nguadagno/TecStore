package control;

import java.io.IOException;
import java.sql.SQLException;
import model.GestioneCarrello;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AggiuntaAlCarrello")

public class AggiuntaAlCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	GestioneCarrello model = new GestioneCarrello();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").equals("1")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("successo", "AggiuntaAlCarrello");

		try {
			if (model.aggiuntaArticolo(request.getSession().getAttribute("CF").toString(),
					request.getParameter("IDArticolo"), Integer.parseInt(request.getParameter("quantita"))))
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/errore");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}