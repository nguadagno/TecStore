package control.vendita;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.ArticoloBean;
import bean.FotoBean;
import model.GestioneVendita;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ModificaArticolo")

public class ModificaArticoloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ModificaArticoloServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GestioneVendita model = new GestioneVendita();
		HttpSession session = request.getSession(true);
		String redirect = "";
		RequestDispatcher dd;

		if (session.getAttribute("tipologia") == null || (!session.getAttribute("tipologia").toString().equals("1")
				&& !session.getAttribute("tipologia").toString().equals("4"))) {
			session.setAttribute("errore", "AccessoNonAutorizzato");
			response.setStatus(403);
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
		}

		session.setAttribute("operazione", "modificaArticolo");

		String IDArticolo = request.getParameter("IDArticolo");

		String nome = request.getParameter("nome") != null ? request.getParameter("nome")
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getNome();

		String descrizione = request.getParameter("descrizione") != null ? request.getParameter("descrizione")
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getDescrizione();

		String IDVenditore = session.getAttribute("CF").toString();

		int quantita = request.getParameter("quantita") != null
				? request.getParameter("quantita") == null ? -1 : Integer.parseInt(request.getParameter("quantita"))
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getQuantita();

		float prezzo = request.getParameter("prezzo") != null
				? request.getParameter("prezzo") == null ? -1 : Float.parseFloat(request.getParameter("prezzo"))
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getPrezzo();

		Boolean rimborsabile = request.getParameter("rimborsabile") != null
				? request.getParameter("rimborsabile") == null ? null
						: Boolean.parseBoolean(request.getParameter("rimborsabile"))
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).isRimborsabile();

		@SuppressWarnings({ "unchecked" })
		ArrayList<FotoBean> foto = (ArrayList<FotoBean>) request.getAttribute("foto");

		if (nome == null || descrizione == null || IDVenditore == null || rimborsabile == null || nome.isEmpty()
				|| descrizione.isEmpty() || IDVenditore.isEmpty() || quantita < 1 || prezzo < 0.01) {
			session.setAttribute("errore", "erroreParametriNull");
			redirect = "/errore.jsp";
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}

		if (IDArticolo != null) {
			try {
				session.setAttribute("dettagliArticolo", model.dettagliArticolo(IDArticolo));
				session.setAttribute("fotoArticolo", model.getAllFoto(IDArticolo));
				redirect = "/modificaArticolo.jsp";
				dd = request.getRequestDispatcher(redirect);
				dd.forward(request, response);
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				redirect = "/dettagliArticolo.jsp";
				if (model.modificaArticolo(((ArticoloBean) session.getAttribute("dettagliArticolo")).getID(), nome,
						descrizione, IDVenditore, quantita, prezzo, rimborsabile)) {
					session.setAttribute("dettagliArticolo",
							model.dettagliArticolo(((ArticoloBean) session.getAttribute("dettagliArticolo")).getID()));
					session.setAttribute("fotoArticolo",
							model.getAllFoto(((ArticoloBean) session.getAttribute("dettagliArticolo")).getID()));
					session.setAttribute("successo", "modificaArticolo");
				} else {
					session.setAttribute("errore", "modificaArticolo");
				}

			} catch (SQLException e) {
				response.setStatus(500);
				session.setAttribute("errore", "erroreSQL");
				redirect = "/errore.jsp";
				dd = request.getRequestDispatcher(redirect);
				dd.forward(request, response);
				e.printStackTrace();
				return;
			}
			dd = request.getRequestDispatcher(redirect);
			dd.forward(request, response);
			return;
		}
	}
}
