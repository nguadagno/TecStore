package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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

@WebServlet("/VisualizzaElencoOrdiniCliente")

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

		if (!session.getAttribute("tipologia").toString().equals("1")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "ElencoOrdiniCliente");

		try {
			ArrayList<OrdineBean> ordini = model.ricercaOrdiniCliente(session.getAttribute("CF").toString(),
					request.getParameter("nome"), Integer.parseInt(request.getParameter("limit")));

			ArrayList<FotoBean> foto = model1.getFotoOrdini(ordini);

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
