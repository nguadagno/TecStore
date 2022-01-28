package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import it.unisa.model.GestioneOrdine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RimborsoCliente")

public class RimborsoClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneOrdine model = new GestioneOrdine();

	public RimborsoClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("3")) {
			request.getSession().setAttribute("errore", "eccessononautorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "confermaordine");

		try {

			if (model.cambiaStato(request.getSession().getAttribute("IDOrdine").toString(),
					request.getSession().getAttribute("Stato").toString()))
				response.sendRedirect(request.getContextPath() + "/Successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/Errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/Errore.jsp");
		}
	}
}
