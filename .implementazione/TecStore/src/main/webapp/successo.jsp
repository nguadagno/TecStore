<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Successo</title>
</head>
<body>
	<div align="center">
		<!-- TODO redirect a pagina corretta e messaggio corretto in base all'operazione -->
		<p>
		<h3>Operazione avvenuta con successo!</h3>
		<p>
			<a href="index.jsp"><button>Continua</button></a>

			<%
			if (session.getAttribute("tipologia") != null && session.getAttribute("tipologia").toString().equals("5")
					&& session.getAttribute("passwordUtente") != null
					&& !session.getAttribute("passwordUtente").toString().isEmpty() && session.getAttribute("emailUtente") != null
					&& !session.getAttribute("emailUtente").toString().isEmpty()) {
			%>
		
		<div align=center>
			<h3>Dati dipendente modificati!</h3>
			<h4>Le nuove credenziali di accesso sono:</h4>
			<h5>
				<b>Email:</b> <BR>
				<%=session.getAttribute("emailUtente").toString()%></h5>
			<h5>
				<b>Password:</b><BR><%=session.getAttribute("passwordUtente").toString()%></h5>
		</div>

		<%
		}
		%>
		</p>
	</div>
</body>
</html>