package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.FotoBean;
import model.GestioneVendita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/InserimentoArticolo")

public class InserimentoNuovoArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneVendita model = new GestioneVendita();

	public InserimentoNuovoArticoloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").equals("1")
				|| !request.getSession().getAttribute("tipologiaUtente").equals("4")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "InserimentoArticolo");

		String nome = request.getParameter("nome");
		String descrizione = request.getParameter("descrizione");
		String IDVenditore = request.getParameter("IDVenditore");
		int quantita = Integer.parseInt(request.getParameter("quantita"));
		float prezzo = Float.parseFloat(request.getParameter("prezzo"));
		boolean rimborsabile = Boolean.parseBoolean(request.getParameter("rimborsabile"));
		@SuppressWarnings("unchecked")
		ArrayList<FotoBean> foto = (ArrayList<FotoBean>) request.getAttribute("foto");

		if (nome.isEmpty() || descrizione.isEmpty() || IDVenditore.isEmpty() || quantita < 1 || prezzo < 0.01
				|| request.getParameter("rimborsabile").isEmpty() || foto.size() < 1)
			response.sendRedirect(request.getContextPath() + "/errore.jsp");

		try {
			if (model.inserimentoNuovoArticolo(nome, descrizione, IDVenditore, quantita, prezzo, rimborsabile))
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
