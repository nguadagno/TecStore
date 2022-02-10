<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null
		|| session.getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='index.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia < 2 || tipologia > 5) {
%>
<meta http-equiv="refresh" content="0; URL='index.jsp'" />
<%
return;
}
if (tipologia == 1) { // cliente
%>
<meta http-equiv="refresh" content="0; URL='index.jsp'" />
<%
return;
}
%>
<title>Pagina Iniziale</title>
</head>
<body>
	<div align="center">
		<%
		if (tipologia == 2) { // centralinista
		%>
		<form action="ElencoTicketCentralinista" method="post">
			<input type="submit" value="Gestione Ticket">
		</form>
		<form action="ElencoVenditeCentralinista" method="post">
			<input type="submit" value="Gestione Vendite">
		</form>

		<%
		return;
		} else if (tipologia == 3) { // magazziniere
		%>

		<form action="ElencoRimborsiMagazziniere" method="post">
			<input type="submit" value="Gestione Rimborsi">
		</form>

		<form action="ElencoOrdiniMagazziniere" method="post">
			<input type="submit" value="Gestione Ordini">
		</form>
		<%
		return;
		} else if (tipologia == 4) { // amministratore catalogo
		%>
		<form action="RicercaVendita" method="post">
			<input type="text" name="nome" placeholder="Ricerca vendite...">
			<input type="submit">
		</form>

		<form action="nuovaVendita.jsp" method="post">
			<input type="submit" value="Nuova Vendita">
		</form>

		<%
		} else if (tipologia == 5) { // amministratore personale
		%>
		<form action="RicercaPersonale" method="post">
			<input type="text" name="testo" placeholder="Ricerca dipendenti...">
			<input type="submit">
		</form>

		<form action="registrazione.jsp" method="post">
			<input type="submit" value="Nuovo Dipendente">
		</form>
		<%
		return;
		}
		%>
	</div>
</body>
</html>