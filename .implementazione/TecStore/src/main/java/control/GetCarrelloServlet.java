package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.ArticoloBean;
import Bean.FotoBean;
import Bean.UtenteBean;
import model.GestioneCarrello;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/GetCarrelloServlet")

public class GetCarrelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public GetCarrelloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneCarrello model = new GestioneCarrello();
		GestioneVendita model1 = new GestioneVendita();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || !session.getAttribute("tipologia").toString().equals("1")) {
			session.setAttribute("errore", "ErroreRichiestaCarrello");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		UtenteBean user = (UtenteBean) session.getAttribute("user");

		session.setAttribute("operazione", "GetCarrello");

		try {
			ArrayList<ArticoloBean> carrello = model.GetCarrello(user);
			ArrayList<FotoBean> foto = model1.getFoto(carrello);

			session.setAttribute("carrello", carrello);
			session.setAttribute("foto", foto);
			redirect = "carrello.jsp";
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
