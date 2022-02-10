<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Errore</title>
</head>
<body>
	<div align="center">
		<!-- TODO redirect a pagina corretta e messaggio corretto in base all'operazione -->
		<p>
		<h3>Qualcosa è andato storto durante l'operazione scelta.</h3>
		<%=session.getAttribute("operazione").toString()%>
		<br>
		<%=session.getAttribute("errore").toString()%>
		<p>
			<a href="index.jsp"><button>Continua</button></a>
		</p>
	</div>
</body>
</html>