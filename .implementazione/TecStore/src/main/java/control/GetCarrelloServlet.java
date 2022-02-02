package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.ArticoloBean;
import Bean.FotoBean;
import Bean.UtenteBean;
import model.GestioneCarrello;
import model.GestioneVendita;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/GetCarrelloServlet")

public class GetCarrelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneCarrello model = new GestioneCarrello();
	GestioneVendita model1 = new GestioneVendita();

	public GetCarrelloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")) {
			request.getSession().setAttribute("errore", "ErroreRichiestaCarrello");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		UtenteBean user = (UtenteBean) request.getSession().getAttribute("user");

		request.getSession().setAttribute("operazione", "getCarrello");

		try {
			ArrayList<ArticoloBean> carrello = model.getCarrello(user);
			ArrayList<FotoBean> foto = model1.getFoto(carrello);

			request.setAttribute("carrello", carrello);
			request.setAttribute("foto", foto);
			response.sendRedirect(request.getContextPath() + "/carrello.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
