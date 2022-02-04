<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (request.getParameter("tipologia") == null || request.getParameter("tipologia").isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='index.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(request.getParameter("tipologia"));

if (tipologia < 2 || tipologia > 5) {
%>
<meta http-equiv="refresh" content="0; URL='index.jsp'" />
<%
}
%>
<title>Pagina Iniziale</title>
</head>
<body>
	<div align="center">
		<%
		if (tipologia == 1) { // cliente
		%>
		<meta http-equiv="refresh" content="0; URL='index.jsp'" />
		return;
		<%
		} else if (tipologia == 2) { // centralinista
		%>
		<form action="elencoTicketCentralinista" method="post">
			<input type="submit" value="Gestione Ticket">
		</form>
		<form action="elencoVenditeCentralinista" method="post">
			<input type="submit" value="Gestione Vendite">
		</form>

		<%
		return;
		} else if (tipologia == 3)

		{ // magazziniere
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
			<input type="text" name="testo" placeholder="Ricerca vendite...">
			<input type="submit">
		</form>

		<form action="nuovavendita.jsp" method="post">
			<input type="submit" value="Nuova Vendita">
		</form>

		<%
		} else if (tipologia == 5) { // amministratore personale
		%>
		<form action="RicercaPersonale" method="post">
			<input type="text" name="testo" placeholder="Ricerca dipendenti...">
			<input type="submit">
		</form>

		<form action="nuovodipendente.jsp" method="post">
			<input type="submit" value="Nuovo Dipendente">
		</form>
		<%
		return;
		}
		%>
	</div>
</body>
</html>