package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.FotoBean;
import Bean.OrdineBean;
import it.unisa.model.GestioneOrdine;
import it.unisa.model.GestioneVendita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VisualizzaElencoOrdiniCliente")

public class RicercaOrdiniClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneOrdine model = new GestioneOrdine();
	GestioneVendita model1 = new GestioneVendita();

	public RicercaOrdiniClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "ElencoOrdiniCliente");

		try {
			ArrayList<OrdineBean> ordini = model.ricercaOrdiniCliente(
					request.getSession().getAttribute("CF").toString(), request.getAttribute("nome").toString(),
					Integer.parseInt(request.getAttribute("limit").toString()));
			
			ArrayList<FotoBean> foto = model1.getFotoOrdini(ordini);
			
			request.setAttribute("ordini", ordini);
			request.setAttribute("foto", foto);
			
			response.sendRedirect(request.getContextPath() + "/storicoOrdini.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
