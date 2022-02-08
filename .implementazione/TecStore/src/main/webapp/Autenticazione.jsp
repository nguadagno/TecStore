<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Autenticazione</title>
</head>
<nav
	class="navbar navbar-expand-lg bg-secondary fixed-top text-uppercase"
	id="mainNav">
	<div class="container">
		<a class="navbar-brand js-scroll-trigger" href="index.jsp">TecStore</a>
	</div>
</nav>

<body>
	<h1>Autenticazione</h1>
	<br>

	<div align="center">
		<h3>Inserisci i tuoi dati di accesso</h3>
		<form action="autenticazione" method="post">

			<p>
				<input type="text" name="email">
			</p>
			<p>
				<input type="password" name="password">
			</p>
			<p>
				<input type="submit" value="Accedi">
			</p>
		</form>
		<a href="/passwordDimenticata.jsp">Password dimenticata?</a> <br>
		<br> <a href="/registrazione.jsp">Non hai un account?
			Registrati!</a> <br>
	</div>
</body>
<br>
</html>