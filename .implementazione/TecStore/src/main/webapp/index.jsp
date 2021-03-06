<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="bean.UtenteBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Pagina Iniziale</title>

</head>

<body>
	<div align="center">

		<form action="ricercaArticolo" method="post">
			<p>
				<br> <input type="text" name="testo" maxlength="25"
					id="index-testoRicerca" placeholder="Cerca..." required> <select
					name="limit" id="index-testoLimit">
					<option value="10" selected>10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> <input type="submit" value="Conferma" id="index-ricerca">
			</p>
		</form>
		<h5>
			Benvenuto nella nostra pagina shopping! Qui potrai visualizzare tutti
			i prodotti disponibili.<br>Che aspetti? Inizia subito la tua
			elettrificante spesa!
		</h5>
	</div>
	<br>
	<br>
	<hr>
	<br>
	<a href="DatiProva?q=1"><button>Inserimento dati di prova</button></a>

	<%
	if (session.getAttribute("CF") == null) {
	%>
	<div align="right">
		<p>
			<a href="registrazione.jsp" id="index-registrazione"><button>Registrati</button></a>
			<a href="autenticazione.jsp" id="index-autenticazione"><button>Autenticati</button></a>
		</p>
	</div>
	<%
	} else {
	if (Integer.parseInt(session.getAttribute("tipologia").toString()) > 1
			&& Integer.parseInt(session.getAttribute("tipologia").toString()) < 6) {
	%>
	<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
	<%
	return;
	}
	%>
	<div align="right">
		<p>
			Benvenuto, <i> <%=((UtenteBean) session.getAttribute("utente")).getNome()%>
				<%=((UtenteBean) session.getAttribute("utente")).getCognome()%> <BR>
			</i>
		<div>
			<form action="RicercaVendita" id="index-areaVenditori" method="post">
				<button>Area Venditori</button>
			</form>

			<form action="DettagliUtente" method="post">
				<input type="submit" value="Dettagli Utente"
					id="index-dettagliUtente">
			</form>
		</div>
		<div>
			<form action="getCarrello" method="post">
				<input type="submit" value="Carrello" id="index-cerrello">
			</form>
		</div>
		<div>

			<form action="ElencoOrdiniCliente" method="post">
				<input type="submit" value="Storico Ordini" id="index-storicoOrdini">
			</form>

			<form action="ElencoTicketCliente" method="post">
				<input type="submit" value="Servizio Clienti"
					id="index-servizioClienti">
			</form>

		</div>
		<div>
			<form action="Deautenticazione" method="post">
				<input id="logout" type="submit" value="LogOut">
			</form>
		</div>
	</div>
	<%
	}
	%>
</body>
</html>