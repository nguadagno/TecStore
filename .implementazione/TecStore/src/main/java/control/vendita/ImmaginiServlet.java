package control.vendita;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

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
		try {
			// Reference:
			// https://codebun.com/how-to-upload-and-retrieve-image-from-mysql-in-jsp-and-servlet/
			if (request.getParameter("id") != null) {
				OutputStream os = response.getOutputStream();
				os.write(gestioneVendita.getFoto(request.getParameter("id")));
				os.flush();
				os.close();
			}
		} catch (SQLException e) {
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
