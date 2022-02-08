package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.FotoBean;
import Bean.OrdineBean;
import model.GestioneAccount;
import model.GestioneOrdine;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/DettagliOrdine")

public class DettagliOrdineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DettagliOrdineServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneOrdine model = new GestioneOrdine();
		GestioneVendita model1 = new GestioneVendita();
		GestioneAccount model2 = new GestioneAccount();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || (!session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("3"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		session.setAttribute("operazione", "dettagliOrdine");

		try {
			OrdineBean ordine = model.dettagliOrdineByID(request.getParameter("IDOrdine"));
			session.setAttribute("ordine", ordine);
			session.setAttribute("cliente", model2.dettagliUtente(ordine.getIDCliente(), ordine.getIDCliente()));

			ArrayList<FotoBean> foto = model1.getFoto(request.getParameter("IDArticolo"));
			session.setAttribute("foto", foto);

			if (session.getAttribute("tipologia").toString().equals("3"))
				model.cambiaStato(request.getParameter("IDOrdine"), "InElaborazione");
			redirect = "/dettagliOrdine.jsp";
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
