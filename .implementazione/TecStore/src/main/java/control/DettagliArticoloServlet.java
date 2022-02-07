package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.ArticoloBean;
import Bean.FotoBean;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DettagliArticolo")

public class DettagliArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DettagliArticoloServlet() {
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

		if (!session.getAttribute("tipologia").toString().equals("1") && !session.getAttribute("tipologia").toString().equals("2")
				&& !session.getAttribute("tipologia").toString().equals("4")) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "dettagliArticolo");

		try {
			ArticoloBean articolo = model.dettagliArticolo(request.getParameter("IDArticolo"));
			ArrayList<FotoBean> foto = model.getFoto(request.getParameter("IDArticolo"));

			session.setAttribute("dettagliArticolo", articolo);
			session.setAttribute("fotoArticolo", foto);

			if (session.getAttribute("tipologia").toString().equals("2"))
				redirect = "/autorizzazioneVendita.jsp";
			else
				redirect = "/dettagliArticolo.jsp";

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
