package control;

import java.io.IOException;
import java.sql.SQLException;
import model.GestioneVendita;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RimozioneArticolo")

public class RimozioneArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	GestioneVendita model = new GestioneVendita();

	public RimozioneArticoloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getSession().getAttribute("tipologiaUtente").equals("1")
				&& !request.getSession().getAttribute("tipologiaUtente").equals("4")) {
			request.getSession().setAttribute("errore", "AccessoNonAutorizzato");
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}

		request.getSession().setAttribute("operazione", "rimozioneArticolo");

		try {
			if (request.getSession().getAttribute("IDVenditore").equals(request.getSession().getAttribute("CF"))) {
				model.rimozioneArticolo(request.getSession().getAttribute("CF").toString(),
						request.getParameter("IDArticolo"));
				response.sendRedirect(request.getContextPath() + "/successo.jsp");
			} else
				response.sendRedirect(request.getContextPath() + "/errore.jsp");

		} catch (SQLException e) {
			response.sendRedirect(request.getContextPath() + "/errore.jsp");
		}
	}
}
