package control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import Bean.ArticoloBean;
import Bean.FotoBean;
import model.GestioneVendita;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/DettagliArticolo")

public class DettagliArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneVendita model = new GestioneVendita();

	public DettagliArticoloServlet() {
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

		request.getSession().setAttribute("operazione", "dettagliArticolo");

		try {
			ArticoloBean articolo = model.dettagliArticolo(request.getSession().getAttribute("IDArticolo").toString());
			ArrayList<FotoBean> foto = model.getFoto(request.getSession().getAttribute("IDArticolo").toString());

			request.setAttribute("dettagliArticolo", articolo);
			request.setAttribute("fotoArticolo", foto);

			if (request.getSession().getAttribute("tipologiaUtente").toString().equals("2"))
				response.sendRedirect(request.getContextPath() + "/autorizzazioneVendita.jsp");
			else
				response.sendRedirect(request.getContextPath() + "/dettagliArticolo.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
