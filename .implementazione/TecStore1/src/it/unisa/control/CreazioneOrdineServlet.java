package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import it.unisa.model.GestioneOrdine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CreazioneOrdine")

public class CreazioneOrdineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneOrdine model = new GestioneOrdine();

	public CreazioneOrdineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")) {
			request.getSession().setAttribute("errore", "eccessononautorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "crezioneOrdine");

		try {
			if (model.creazioneOrdine(request.getSession().getAttribute("CF").toString()))
				response.sendRedirect(request.getContextPath() + "/Successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/Errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/Errore.jsp");
		}
	}
}
