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

		String IDArticolo = session.getAttribute("dettagliArticolo") == null ? request.getParameter("IDArticolo")
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getID();

		String nome = session.getAttribute("dettagliArticolo") == null ? request.getParameter("nome")
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getNome();

		String descrizione = session.getAttribute("dettagliArticolo") == null ? request.getParameter("descrizione")
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getDescrizione();

		String IDVenditore = session.getAttribute("dettagliArticolo") == null ? request.getParameter("IDVenditore")
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getIDVenditore();

		int quantita = session.getAttribute("dettagliArticolo") == null
				? request.getParameter("quantita") == null ? -1 : Integer.parseInt(request.getParameter("quantita"))
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getQuantita();

		float prezzo = session.getAttribute("dettagliArticolo") == null
				? request.getParameter("quantitprezzoa") == null ? -1 : Integer.parseInt(request.getParameter("prezzo"))
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).getPrezzo();

		Boolean rimborsabile = session.getAttribute("dettagliArticolo") == null
				? request.getParameter("rimborsabile") == null ? null
						: Boolean.parseBoolean(request.getParameter("rimborsabile"))
				: ((ArticoloBean) session.getAttribute("dettagliArticolo")).isRimborsabile();

		@SuppressWarnings({ "unchecked" })
		ArrayList<FotoBean> foto = (ArrayList<FotoBean>) request.getAttribute("foto");

		if (IDArticolo == null || nome == null || descrizione == null || IDVenditore == null || rimborsabile == null
				|| IDArticolo.isEmpty() || nome.isEmpty() || descrizione.isEmpty() || IDVenditore.isEmpty()
				|| quantita < 1 || prezzo < 0.01) {
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
				if (model.modificaArticolo(IDArticolo, nome, descrizione, IDVenditore, quantita, prezzo,
						rimborsabile)) {
					session.setAttribute("successo", "modificaArticolo");
					redirect = "/successo.jsp";
				} else {
					session.setAttribute("errore", "modificaArticolo");
					redirect = "/errore.jsp";
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
