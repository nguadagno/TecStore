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
	UtenteBean a = (UtenteBean) session.getAttribute("utente");

	if (a == null) {
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
					<input type="hidden" name="CF" value="<%=a.getCF()%>"> <label><b>Nome:</b></label>
					<br> <input type="text" name="nome" value="<%=a.getNome()%>">
					<br> <br> <label><b>Cognome:</b></label> <br> <input
						type="text" name="cognome" value="<%=a.getCognome()%>" required>
					<br> <br> <label><b>Email:</b></label> <br> <input
						type="text" name="email" value="<%=a.getEmail()%>" required>
					<br> <br> <label><b>Password:</b></label> <br> <input
						type="password" name="password" value="<%=a.getPassword()%>"
						required> <br> <br> <label><b>Via:</b></label> <br>
					<input type="text" name="via" value="<%=a.getVia()%>"> <br>
					<br> <label><b>Numero:</b></label> <br> <input
						type="number" name="numeroCivico" value="<%=a.getNumeroCivico()%>"><br>
					<br> <label><b>Citta:</b></label> <br> <input type="text"
						name="citta" value="<%=a.getCitta()%>"> <br>
					<br> <label><b>Provincia:</b></label> <br> <input
						type="text" name="provincia" value="<%=a.getProvincia()%>">
					<br>
					<br>
					<label><b>CAP:</b></label> <br> <input type="number"
						name="CAP" value="<%=a.getCAP()%>"> <input type="hidden"
						name="tipologia" value="<%=a.getTipologia()%>"> <input
						type="hidden" name="cartaDiCredito"
						value="<%=a.getCartaDiCredito()%>">
				</div>
				<div align="right">
					<input type="submit" value="Modifica">
				</div>

			</form>
		</div>
	</div>


</body>
</html>