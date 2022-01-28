package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import it.unisa.model.GestioneOrdine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RimborsoMagazziniere")

public class RimborsoMagazziniereServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneOrdine model = new GestioneOrdine();

	public RimborsoMagazziniereServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("3")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "confermaRimborso");

		try {

			if (model.cambiaStato(request.getSession().getAttribute("IDOrdine").toString(),
					request.getSession().getAttribute("Stato").toString()))
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
