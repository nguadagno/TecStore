package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.ArticoloBean;
import Bean.FotoBean;
import Bean.OrdineBean;
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
			ArrayList<ArticoloBean> articoli = model1.ricercaArticolo(request.getParameter("nome"), limit);
			ArrayList<OrdineBean> ordini = model.ricercaOrdiniCliente(session.getAttribute("CF").toString(),
					request.getParameter("nome"), limit);

			ArrayList<FotoBean> foto = model1.getFotoOrdini(ordini);
			for (int i = 0; i < articoli.size(); i++) {
				int j;
				for (j = 0; j < ordini.size(); j++) {
					if (articoli.get(i).getID().equals(ordini.get(j).getID()))
						break;
				}
				if (j == ordini.size())
					articoli.remove(i);
			}
			articoli.sort(null);
			ordini.sort(null);
			System.out.println(articoli);
			System.out.println(ordini);
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
