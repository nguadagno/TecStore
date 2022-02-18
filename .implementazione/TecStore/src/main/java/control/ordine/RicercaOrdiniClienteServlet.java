package control.ordine;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.ArticoloBean;
import bean.FotoBean;
import bean.OrdineBean;
import model.GestioneOrdine;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ElencoOrdiniCliente")

public class RicercaOrdiniClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public RicercaOrdiniClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneOrdine model = new GestioneOrdine();
		GestioneVendita model1 = new GestioneVendita();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().equals("1")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "ElencoOrdiniCliente");

		try {
			int limit = 10;
			if (request.getParameter("limit") != null)
				limit = Integer.parseInt(request.getParameter("limit"));
			ArrayList<OrdineBean> ordini = model.ricercaOrdiniCliente(session.getAttribute("CF").toString(),
					request.getParameter("nome"), limit);
			ArrayList<FotoBean> foto = model1.getFotoOrdini(ordini);

			ArrayList<ArticoloBean> articoli = new ArrayList<ArticoloBean>();

			for (OrdineBean o : ordini) {
				articoli.add(model1.dettagliArticolo(o.getIDArticolo()));
			}
			
			session.setAttribute("articoli", articoli);
			session.setAttribute("ordini", ordini);
			session.setAttribute("foto", foto);

			redirect = "/storicoOrdini.jsp";
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
