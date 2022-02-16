<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 1) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Creazione Ticket</title>
</head>
<body>

	<h1>Creazione ticket</h1>

	<br>
	<br>
	<br>

	<form action="CreazioneTicket" method="post">
		<div align="center">
			<label for="tipologia"> <b>Seleziona il tipo di ticket</b>
			</label> <select name="tipologia" id="tipologia"
				id="creazioneTicket-tipologia" required>
				<option value="Amministrativo">Amministrativo</option>
				<option value="Ordini">Ordini</option>
				<option value="Spedizione">Spedizione</option>
				<option value="Rimborso">Rimborso</option>
				<option value="Profilo">Profilo</option>
			</select> <BR> <BR> <BR> <input type="text"
				id="creazioneTicket-messaggio" name="messaggio"
				style="width: 300px; height: 300px;" required>
		</div>
		<br> <br> <br>
		<hr>
		<br>
		<div>
			<input type="submit" value="Conferma" id="creazioneTicket-conferma">
			<a href="dettagliArticolo.jsp" id="creazioneTicket-Annulla">
				<button>Annulla</button>
			</a>
		</div>
	</form>
</body>
</html>