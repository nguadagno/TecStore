package it.unisa.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.ArticoloBean;
import Bean.UtenteBean;
import it.unisa.model.GestioneCarrello;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/GetCarrelloServlet")

public class GetCarrelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneCarrello model = new GestioneCarrello();

	public GetCarrelloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")) {
			request.getSession().setAttribute("errore", "erroreaggiornamentoarticolo");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		ArrayList<ArticoloBean> allOrdersByUser = new ArrayList<ArticoloBean>();
		UtenteBean user = (UtenteBean) request.getSession().getAttribute("user");

		request.getSession().setAttribute("operazione", "getCarrello");

		try {
			allOrdersByUser = model.getCarrello(user);
			request.setAttribute("allOrdersByUser", allOrdersByUser);
			response.sendRedirect(request.getContextPath() + "/Carrello.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/Errore.jsp");
		}
	}
}
