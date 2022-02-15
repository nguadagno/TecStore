package control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import Bean.FotoBean;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.GestioneVendita;

@WebServlet("/img")
@MultipartConfig(maxFileSize = 16177215)
public class ImmaginiServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ImmaginiServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Reference:
			// https://codebun.com/how-to-upload-and-retrieve-image-from-mysql-in-jsp-and-servlet/
			GestioneVendita gestioneVendita = new GestioneVendita();

			if (request.getParameter("id") != null) {
				OutputStream os = response.getOutputStream();
				os.write(gestioneVendita.getFoto(request.getParameter("id")));
				os.flush();
				os.close();
			} else {
				Part part = request.getPart("photo");
				if (part != null) {
					System.out.println(part.getName());
					System.out.println(part.getSize());
					System.out.println(part.getContentType());
					InputStream inputStream = part.getInputStream();
					gestioneVendita.inserimentoFoto(request.getParameter("IDarticolo"), (Blob) inputStream);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
	}
}
