<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Registrazione</title>
</head>
<nav
	class="navbar navbar-expand-lg bg-secondary fixed-top text-uppercase"
	id="mainNav">
	<div class="container">
		<a class="navbar-brand js-scroll-trigger" href="index.jsp">TecStore</a>
	</div>
</nav>

<body>
	<%
	int tipologia = -1;
	if (session.getAttribute("tipologia") != null)
		tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());
	%>
	<div align="center">
		<h1>Registrazione</h1>

		<h3>Compila i campi per continuare</h3>
		<form action="Registrazione" method="post">
			<h3>
				<i>Dati Autenticazione</i>
			</h3>
			<label for="email">E-mail</label><br> <input type="email"
				name="email" required> <br> <br>
			<%
			if (tipologia != 5) {
			%>
			<label for="password">Password</label><br> <input
				type="password" name="password" required> <br> <br>
			<br>
			<%
			} else {
			%>
			<h5>La password per l'utente verrà generata automaticamente al
				momento della registrazione.</h5>
			<%
			}
			%>
			<h3>
				<i>Dati Anagrafici</i>
			</h3>
			<label for="codicefiscale">Codice Fiscale</label><br> <input
				type="text" name="CF" id="CF" maxlength="16" required> <br>
			<br> <label for="nome">Nome</label><br> <input type="text"
				name="nome" id="nome" required> <br> <br> <label
				for="cognome">Cognome</label><br> <input type="text"
				name="cognome" id="cognome" required> <br> <br> <br>

			<h3>
				<i>Indirizzo</i>
			</h3>

			<label for="citta">Città</label><br> <input type="text"
				name="citta" maxlength="35" id="citta" required> <br> <br>

			<label for="via">Via</label><br> <input type="text" name="via"
				id="via" required> <br> <br> <label
				for="provincia">Provincia</label><br> <input type="text"
				name="provincia" maxlength="2" id="provincia" required> <br>
			<br> <label for="numerocivico">Numero civico</label><br> <input
				type="number" name="numerocivico" id="numerocivico" required>
			<br> <br> <label for="CAP">CAP</label><br> <input
				type="number" name="CAP" maxlength="5" id="CAP" required>
			<p>
				<%
				if (tipologia == 5) {
				%>
			
			<h3>
				<i>Tipologia dipendente</i>
			</h3>
			<BR> <label for="tipologiaUtente">Scegli una tipologia</label> <BR>
			<select id="tipologiaUtente" name="tipologiaUtente">
				<option value="2">Centralinista</option>
				<option value="3">Magazziniere</option>
				<option value="4">Amministratore Catalogo</option>
				<option value="5">Amministratore Personale</option>
			</select>
			<%
			}
			%>
			<div align="center">
				<br> <input class="final" id="conferma" type="submit"
					name="submit" value="Conferma"> <input id="resetta"
					class="final" type="reset" name="reset" value="Reset">
			</div>
		</form>
	</div>
</body>
<br>
</html>
