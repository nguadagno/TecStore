package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.FotoBean;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ModificaArticoloServlet")

public class ModificaArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ModificaArticoloServlet() {
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

		if (!session.getAttribute("tipologia").equals("1") || !session.getAttribute("tipologia").equals("4")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
		}

		session.setAttribute("operazione", "modificaArticolo");

		String IDArticolo = request.getParameter("IDArticolo");
		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String IDVenditore = session.getAttribute("IDVenditore") == null ? null
				: session.getAttribute("IDVenditore").toString();
		int quantita = request.getParameter("quantita") == null ? -1
				: Integer.parseInt(request.getParameter("quantita"));
		float prezzo = request.getParameter("prezzo") == null ? -1 : Float.parseFloat(request.getParameter("prezzo"));
		Boolean rimborsabile = request.getParameter("rimborsabile") == null ? null
				: Boolean.parseBoolean(request.getParameter("rimborsabile"));
		@SuppressWarnings("unchecked")
		ArrayList<FotoBean> foto = (ArrayList<FotoBean>) request.getAttribute("foto");

		if (IDArticolo == null || nome == null || descrizione == null || IDVenditore == null || rimborsabile == null
				|| IDArticolo.isEmpty() || nome.isEmpty() || descrizione.isEmpty() || IDVenditore.isEmpty()
				|| quantita < 1 || prezzo < 0.01) {
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
		}

		try {
			if (model.modificaArticolo(IDArticolo, nome, descrizione, IDVenditore, quantita, prezzo, rimborsabile)
					&& model.sovrascritturaFoto(IDArticolo, foto))
				redirect = "/successo.jsp";
			else
				redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);

		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreSQL");
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
		}

		dd.forward(request, response);
		return;
	}
}
