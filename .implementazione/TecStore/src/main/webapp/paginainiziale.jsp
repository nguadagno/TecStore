<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()) {
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
			<input id="gestioneticket" type="submit" value="Gestione Ticket">
		</form>
		<form action="ElencoVenditeCentralinista" method="post">
			<input id="gestionevendite" type="submit" value="Gestione Vendite">
		</form>

		<%
		} else if (tipologia == 3) { // magazziniere
		%>

		<form action="ElencoRimborsiMagazziniere" method="post">
			<input id="gestionerimborsi" type="submit" value="Gestione Rimborsi">
		</form>

		<form action="ElencoOrdiniMagazziniere" method="post">
			<input id="gestioneordini" type="submit" value="Gestione Ordini">
		</form>
		<%
		} else if (tipologia == 4) { // amministratore catalogo
		%>
		<form action="RicercaVendita" method="post">
			<input id="ricercavendita" type="text" name="nome" placeholder="Ricerca vendite...">
			<input type="submit">
		</form>

		<form action="nuovaVendita.jsp" method="post">
			<input type="submit" value="Nuova Vendita">
		</form>

		<%
		} else if (tipologia == 5) { // amministratore personale
		%>
		<form action="RicercaPersonale" method="post">
			<input id="ricercapersonale" type="text" name="testo" placeholder="Ricerca dipendenti...">
			<input type="submit">
		</form>

		<form action="registrazione.jsp" method="post">
			<input id="nuovodipendente" type="submit" value="Nuovo Dipendente">
		</form>
		<%
		}
		%>
	</div>
	<div>
		<form action="Deautenticazione" method="post">
			<input id="logout" type="submit" value="LogOut">
		</form>
	</div>
</body>
</html>