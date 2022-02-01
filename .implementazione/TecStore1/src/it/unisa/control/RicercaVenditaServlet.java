package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.ArticoloBean;
import Bean.FotoBean;
import it.unisa.model.GestioneVendita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ricercaarticolo")
public class RicercaVenditaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RicercaVenditaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		GestioneVendita model = new GestioneVendita();

		request.getSession().setAttribute("operazione", "ricercaArticolo");
		try {
			ArrayList<ArticoloBean> risultati = model.elencoVenditeCF(
					request.getSession().getAttribute("CF").toString(), request.getAttribute("nome").toString(),
					Integer.parseInt(request.getAttribute("limit").toString()));

			ArrayList<FotoBean> foto = model.getFoto(risultati);

			request.getSession().setAttribute("risultati", risultati);
			request.getSession().setAttribute("foto", foto);

			response.sendRedirect(request.getContextPath() + "/risultatiRicerca.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
