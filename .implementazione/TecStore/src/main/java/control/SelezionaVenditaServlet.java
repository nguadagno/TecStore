package control;

import java.io.IOException;
import java.sql.SQLException;

import Bean.ArticoloBean;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SelezionaVendita")

public class SelezionaVenditaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public SelezionaVenditaServlet() {
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

		if (!session.getAttribute("tipologia").equals("1")
				&& !session.getAttribute("tipologia").equals("2")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "selezionaVendita");
		ArticoloBean vendita = new ArticoloBean();
		try {
			vendita = model.dettagliArticolo(session.getAttribute("CF").toString());

			session.setAttribute("dettagliArticolo", vendita);
			if (session.getAttribute("tipologia").equals("1"))
				redirect = "/dettaglivenditacliente.jsp";
			else
				redirect = "/dettagliVenditaCentralinista.jsp";

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
