package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import it.unisa.model.GestioneCarrello;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AggiornamentoCarrelloServlet")

public class AggiornamentoCarrelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	GestioneCarrello model = new GestioneCarrello();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")) {
			request.getSession().setAttribute("errore", "eccessononautorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "aggiornamentocarrello");

		try {
			if (model.aggiornamentoQuantita(request.getSession().getAttribute("CF").toString(),
					request.getParameter("IDArticolo"), (int) request.getAttribute("quantita")))
				response.sendRedirect(request.getContextPath() + "/Successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/Errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/Errore.jsp");
		}
	}
}
