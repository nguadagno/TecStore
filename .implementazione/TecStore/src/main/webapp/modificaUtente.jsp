<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica Utente</title>
</head>
<body>

	<%
	UtenteBean utente = (UtenteBean) session.getAttribute("utente");

	if (utente == null) {
		return;
	}
	%>
	<div align="center">
		<div>
			<h3>Modifica Utente</h3>
			<br> <br>
		</div>
		<div>
			<form action="modificautente" method="post">
	<div>
		<input type="hidden" name="CF" value="<%=utente.getCF()%>">
		<label><b>Nome:</b></label> <br> <input type="text"
			name="nome" value="<%=utente.getNome()%>"> <br> <br>
		<label><b>Cognome:</b></label> <br> <input type="text"
			name="cognome" value="<%=utente.getCognome()%>" required>
		<br> <br> <label><b>Email:</b></label> <br> <input
			type="text" name="email" value="<%=utente.getEmail()%>" required>
		<br> <br> <label><b>Password:</b></label> <br> <input
			type="password" name="password" value="<%=utente.getPassword()%>"
			required> <br> <br> <label><b>Via:</b></label> <br>
		<input type="text" name="via" value="<%=utente.getVia()%>">
		<br> <br> <label><b>Numero:</b></label> <br> <input
			type="number" name="numeroCivico"
			value="<%=utente.getNumeroCivico()%>"><br> <br>
		<label><b>Citta:</b></label> <br> <input type="text"
			name="citta" value="<%=utente.getCitta()%>"> <br> <br>
		<label><b>Provincia:</b></label> <br> <input type="text"
			name="provincia" value="<%=utente.getProvincia()%>"> <br>
		<br> <label><b>CAP:</b></label> <br> <input type="number"
			name="CAP" value="<%=utente.getCAP()%>"> <input
			type="hidden" name="tipologia" value="<%=utente.getTipologia()%>">
		<input type="hidden" name="cartaDiCredito"
			value="<%=utente.getCartaDiCredito()%>">
	</div>
	<div align="right">
		<input type="submit" value="Modifica">
	</div>
</form>
		</div>
	</div>
</body>
</html>