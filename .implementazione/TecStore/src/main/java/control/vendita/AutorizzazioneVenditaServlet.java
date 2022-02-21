package control.vendita;

import java.io.IOException;
import java.sql.SQLException;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AutorizzazioneVendita")

public class AutorizzazioneVenditaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public AutorizzazioneVenditaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneVendita model = new GestioneVendita();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().equals("2")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "autorizzazioneVendita");

		try {
			if (model.cambiaStato(request.getParameter("IDArticolo"), request.getParameter("stato"))) {
				session.setAttribute("successo", "cambiaStatoVendita");
				redirect = "/successo.jsp";
			} else {
				session.setAttribute("errore", "cambiaStatoVendita");
				redirect = "/errore.jsp";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
