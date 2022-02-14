<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="Bean.UtenteBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Pagina Iniziale</title>

</head>

<body>
	<div align="center">

		<form action="ricercaArticolo" method="post">
			<p>
				<br> <input type="text" name="testo" maxlength="45"
					placeholder="Cerca..." required> <select name="limit">
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> <input type="submit" value="Conferma">
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
			<a href="registrazione.jsp"><button>Registrati</button></a> <a
				href="autenticazione.jsp"><button>Autenticati</button></a>
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
			<a href="areaVenditori.jsp"><button>Area Venditori</button></a>
			<form action="DettagliUtente" method="post">
				<input type="submit" value="Dettagli Utente">
			</form>
		</div>
		<div>
			<form action="getCarrello" method="post">
				<input type="submit" value="Carrello">
			</form>
		</div>
		<div>
			<form action="ElencoTicketCliente" method="post">
				<input type="submit" value="Servizio Clienti">
			</form>
		</div>
		<div>
			<form action="Deautenticazione" method="post">
				<input type="submit" value="LogOut">
			</form>
		</div>
	</div>
	<%
	}
	%>
</body>
</html>