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
				|| !request.getSession().getAttribute("tipologiaUtente").toString().equals("3")) {
			request.getSession().setAttribute("errore", "eccessononautorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "elaborazioneordine");
		OrdineBean order = new OrdineBean();

		try {

			if (model.cambiaStato(request.getSession().getAttribute("IDOrdine").toString(),
					request.getSession().getAttribute("Stato").toString())) {
				order = model.dettagliOrdineCliente(request.getSession().getAttribute("CF").toString(),
						request.getSession().getAttribute("IDOrdine").toString());

				request.setAttribute("allOrdersByUser", order);
				if (request.getSession().getAttribute("tipologiaUtente").toString().equals("3"))
					response.sendRedirect(request.getContextPath() + "/DettagliOrdineMagazziniere.jsp");
				else
					response.sendRedirect(request.getContextPath() + "/DettagliOrdineCliente.jsp");
			} else
				response.sendRedirect(request.getContextPath() + "/Errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/Errore.jsp");
		}
	}
}
