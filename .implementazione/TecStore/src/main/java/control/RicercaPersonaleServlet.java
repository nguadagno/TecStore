package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.UtenteBean;
import model.GestioneAccount;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/ricercapersonale")
public class RicercaPersonaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RicercaPersonaleServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("5")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		GestioneAccount model = new GestioneAccount();

		request.getSession().setAttribute("operazione", "ricercaPersonale");
		try {
			ArrayList<UtenteBean> risultati = model.ricercaDipendenti(request.getAttribute("testo").toString());
			request.getSession().setAttribute("risultati", risultati);
			response.sendRedirect(request.getContextPath() + "/GestionePersonale.jsp");
		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
