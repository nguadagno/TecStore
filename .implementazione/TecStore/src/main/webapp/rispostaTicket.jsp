<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Risposta TIcket</title>
</head>
<body>
	<br>
	<br>
	<br>
	<div align="center">
		<h2>Ciao! Inserisci il tuo messaggio, ti rispoderemo il prima
			possibile</h2>

	</div>

	<form action="rispostaTIcket" method="post">
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