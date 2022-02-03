package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.OrdineBean;
import model.GestioneOrdine;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VisualizzaElencoOrdiniMagazziniere")

public class ElencoOrdiniMagazziniereServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneOrdine model = new GestioneOrdine();

	public ElencoOrdiniMagazziniereServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").equals("3")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "ElencoOrdiniMagazziniere");

		try {
			ArrayList<OrdineBean> ordini = model.elencoOrdiniMagazziniere();
			request.setAttribute("ordini", ordini);
			response.sendRedirect(request.getContextPath() + "/elencoOrdini.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
