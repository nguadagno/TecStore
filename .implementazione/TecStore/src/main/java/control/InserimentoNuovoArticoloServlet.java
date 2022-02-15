package control;

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

@WebServlet("/InserimentoArticolo")
public class InserimentoNuovoArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public InserimentoNuovoArticoloServlet() {
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

		if (session.getAttribute("tipologia") == null || (!session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("4"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "InserimentoArticolo");

		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String IDVenditore = session.getAttribute("CF") == null ? null : session.getAttribute("CF").toString();
		int quantita = request.getParameter("quantita") == null ? -1
				: Integer.parseInt(request.getParameter("quantita"));
		float prezzo = request.getParameter("prezzo") == null ? -1 : Float.parseFloat(request.getParameter("prezzo"));
		Boolean rimborsabile = request.getParameter("rimborsabile") == null ? null
				: Boolean.parseBoolean(request.getParameter("rimborsabile"));

		if (nome == null || descrizione == null || IDVenditore == null || rimborsabile == null || nome.isEmpty()
				|| descrizione.isEmpty() || IDVenditore.isEmpty() || quantita < 1 || prezzo < 0.01) {
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		try {
			String IDArticolo = model.inserimentoNuovoArticolo(nome, descrizione, IDVenditore, quantita, prezzo,
					rimborsabile);
			if (!IDArticolo.isEmpty()) {
				session.setAttribute("IDArticolo", IDArticolo);
				redirect = "/inserimentoImmagini.jsp";
			} else
				redirect = "/errore.jsp";
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
