package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;

import Bean.OrdineBean;
import it.unisa.model.GestioneOrdine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ElaborazioneOrdine")

public class DettagliOrdineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneOrdine model = new GestioneOrdine();

	public DettagliOrdineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")
				&& !request.getSession().getAttribute("tipologiaUtente").toString().equals("3")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "dettagliOrdine");

		try {
			OrdineBean ordine = model.dettagliOrdineByID(request.getAttribute("IDOrdine").toString());
			request.getSession().setAttribute("ordine", ordine);
			request.setAttribute("ordine",
					model.dettagliOrdineCliente(request.getSession().getAttribute("CF").toString(),
							request.getSession().getAttribute("IDOrdine").toString()));
			if (request.getSession().getAttribute("tipologiaUtente").toString().equals("3")) {
				model.cambiaStato(request.getSession().getAttribute("IDOrdine").toString(), "InElaborazione");
				response.sendRedirect(request.getContextPath() + "/dettagliOrdineMagazziniere.jsp");
			} else if (request.getSession().getAttribute("tipologiaUtente").toString().equals("1"))
				response.sendRedirect(request.getContextPath() + "/dettagliOrdine.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
