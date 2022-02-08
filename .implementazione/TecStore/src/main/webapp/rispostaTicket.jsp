<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (request.getSession().getAttribute("tipologia") == null
		|| request.getSession().getAttribute("tipologia").toString().isEmpty()
		|| request.getSession().getAttribute("IDTicket") == null) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(request.getSession().getAttribute("tipologia").toString());

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
				type="text" name="messaggio" style="width: 300px; height: 300px;"
				required>
		</div>
		<br> <br> <br>
		<hr>
		<br>
		<div>
			<input type="submit" value="Conferma"> <a href="index.jsp">
				<button>Annulla</button>
			</a>
		</div>
	</form>
</body>
</html>