<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Creazione Ticket</title>
</head>
<body>

	<br>
	<br>
	<br>
	
	<form action="creazioneTIcket" method="post">
		<div align="center">
			<input type="text" name="messaggio"
				style="width: 300px; height: 300px;" required>
		</div>
		<br> <br> <br>
		<hr>
		<br>
		<div>
			<input type="submit" value="Conferma"> <a
				href="dettagliArticolo.jsp">
				<button>Annulla</button>
			</a>
		</div>
	</form>
</body>
</html>