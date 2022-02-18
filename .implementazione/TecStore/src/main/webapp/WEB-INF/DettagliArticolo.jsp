<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,bean.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<div align="center">
		<h3>Ricerca Articolo</h3>
		<form action="RicercaArticolo" method="post">
			<input id="markBar" type="text" name="testo" maxlength="35"
				id="testo" placeholder="Titolo..." required> <input
				type="submit" value="Cerca">
		</form>
	</div>
	<br>
	<br>
	<hr>

</body>
</html>