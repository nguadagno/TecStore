package control.vendita;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import bean.ArticoloBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.GestioneVendita;

@WebServlet("/img")
@MultipartConfig(maxFileSize = 16177215)
public class ImmaginiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	GestioneVendita gestioneVendita = new GestioneVendita();

	public ImmaginiServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		try {
			// Reference:
			// https://codebun.com/how-to-upload-and-retrieve-image-from-mysql-in-jsp-and-servlet/
			if (request.getParameter("id") != null) {
				OutputStream os = response.getOutputStream();
				os.write(gestioneVendita.getFoto(request.getParameter("id")));
				os.flush();
				os.close();
			}
			return;
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreRicercaImmagine");
			RequestDispatcher dd = request.getRequestDispatcher("/errore.jsp");
			dd.forward(request, response);
			e.printStackTrace();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}

		try {
			if (request.getParameter("del") != null) {
				if (request.getParameter("del").equals("all") && gestioneVendita
						.rimozioneFoto(((ArticoloBean) session.getAttribute("dettagliArticolo")).getID())) {
					RequestDispatcher dd = request.getRequestDispatcher("/dettagliArticolo.jsp");
					dd.forward(request, response);
					return;
				} else if (gestioneVendita.rimozioneFoto(
						((ArticoloBean) session.getAttribute("dettagliArticolo")).getID(),
						request.getParameter("del"))) {

					RequestDispatcher dd = request.getRequestDispatcher("/inserimentoImmagini.jsp");
					dd.forward(request, response);
					return;
				} else {
					response.setStatus(500);
					session.setAttribute("errore", "erroreEliminazioneImmagini");
					RequestDispatcher dd = request.getRequestDispatcher("/errore.jsp");
					dd.forward(request, response);
				}
			}
			return;
		} catch (SQLException e) {
			response.setStatus(500);
			session.setAttribute("errore", "erroreRimozioneImmagine");
			RequestDispatcher dd = request.getRequestDispatcher("/errore.jsp");
			dd.forward(request, response);
			e.printStackTrace();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String redirect = "paginainiziale.jsp";
		RequestDispatcher dd;
		HttpSession session = request.getSession(true);
		Part part = request.getPart("foto");
		if (part != null) {
			InputStream inputStream = part.getInputStream();
			try {
				if (gestioneVendita.inserimentoFoto(session.getAttribute("IDArticolo").toString(), inputStream))
					redirect = "inserimentoImmagini.jsp";
				else {
					response.setStatus(500);
					session.setAttribute("errore", "erroreInserimentoImmagine");
					redirect = "/errore.jsp";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		dd = request.getRequestDispatcher(redirect);
		dd.forward(request, response);
		return;
	}
}
