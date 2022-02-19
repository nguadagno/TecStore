<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()
		|| session.getAttribute("IDTicket") == null) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 1 && tipologia != 2) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Risposta TIcket</title>
</head>
<body>
	<br>
	<br>
	<br>
	<%
	if (tipologia == 1) {
	%>
	<div align="center">
		<h2>Inserisci il tuo messaggio, ti risponderemo il prima
			possibile</h2>
	</div>
	<%
	}
	%>

	<form action="RispostaTicket" method="post">
		<div align="center">
			<input type="hidden" name="IDTicket"
				value="<%=session.getAttribute("IDTicket").toString()%>"> <input
				type="text" id="rispostaTicket-messaggio" name="messaggio"
				style="width: 300px; height: 300px;" required>
		</div>
		<br> <br> <br>
		<hr>
		<br>
		<div>
			<input type="submit" value="Conferma" id="rispostaTicket-conferma">
			<a href="index.jsp" id="rispostaTicket-annulla">
				<button>Annulla</button>
			</a>
		</div>
	</form>
</body>
</html>