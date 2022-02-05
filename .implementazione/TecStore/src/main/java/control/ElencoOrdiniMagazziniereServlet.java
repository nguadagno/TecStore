package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.OrdineBean;
import model.GestioneOrdine;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VisualizzaElencoOrdiniMagazziniere")

public class ElencoOrdiniMagazziniereServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ElencoOrdiniMagazziniereServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneOrdine model = new GestioneOrdine();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (!session.getAttribute("tipologia").equals("3")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "ElencoOrdiniMagazziniere");

		try {
			ArrayList<OrdineBean> ordini = model.elencoOrdiniMagazziniere();
			session.setAttribute("ordini", ordini);
			redirect = "/elencoordini.jsp";
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
