package control.utente;

import java.io.IOException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Deautenticazione")
public class DeautenticazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeautenticazioneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String redirect = "index.jsp";
		RequestDispatcher dd;
		session.invalidate();
		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
