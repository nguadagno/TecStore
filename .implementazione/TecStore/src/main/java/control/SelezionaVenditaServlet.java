package control;

import java.io.IOException;
import java.sql.SQLException;

import Bean.ArticoloBean;
import model.GestioneVendita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SelezionaVendita")

public class SelezionaVenditaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneVendita model = new GestioneVendita();

	public SelezionaVenditaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").toString().equals("1")
				&& !request.getSession().getAttribute("tipologiaUtente").toString().equals("2")
				&& !request.getSession().getAttribute("tipologiaUtente").toString().equals("4")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "selezionaVendita");
		ArticoloBean vendita = new ArticoloBean();
		try {
			vendita = model.dettagliArticolo(request.getSession().getAttribute("CF").toString());

			request.setAttribute("dettagliArticolo", vendita);
			if (request.getSession().getAttribute("tipologiaUtente").toString().equals("1"))
				response.sendRedirect(request.getContextPath() + "/dettagliVenditaCliente.jsp");

			else if (request.getSession().getAttribute("tipologiaUtente").toString().equals("3"))
				response.sendRedirect(request.getContextPath() + "/dettagliVenditaMagazziniere.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/dettagliVenditaCentralinista.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
