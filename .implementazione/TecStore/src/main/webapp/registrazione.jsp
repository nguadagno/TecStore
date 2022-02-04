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
	<div align="center">

		<h1>Registrazione</h1>

		<h3>Compila i campi per continuare</h3>
		<form action="registrazioneUtente" method="post">
			<br> <br> <input type="hidden" name="tipologia" value="1">

			<h3>
				<i>Dati Autenticazione</i>
			</h3>
			<label for="email">E-mail</label><br> <input type="email"
				name="Email" required> <br> <br> <label
				for="password">Password</label><br> <input type="password"
				name="password" required> <br> <br> <br>
			<h3>
				<i>Dati Anagrafici</i>
			</h3>

			<label for="codicefiscale">Codice Fiscale</label><br> <input
				type="text" name="CF" id="codicefiscale" maxlength="16" required>
			<br> <br> <label for="nome">Nome</label><br> <input
				type="text" name="Nome" id="nome" required> <br> <br>

			<label for="cognome">Cognome</label><br> <input type="text"
				name="Cognome" id="cognome" required> <br> <br> <br>

			<h3>
				<i>Dati Spedizione</i>
			</h3>
			<label for="citta">Città</label><br> <input type="text"
				name="Citta" maxlength="35" id="citta" required> <br> <br>

			<label for="via">Via</label><br> <input type="text"
				name="Provincia" id="via" required> <br> <br> <label
				for="via">Provincia</label><br> <input type="text" name="Via"
				maxlength="2" id="via" required> <br> <br> <label
				for="ncivico">Numero civico</label><br> <input type="number"
				name="NumeroCivico" id="ncivico" required> <br> <br>

			<label for="cap">CAP</label><br> <input type="number" name="CAP"
				maxlength="5" id="cap" required>
			<p>
			<h3>
				<i>Carta di Credito</i>
			</h3>
			<h6>Avrai la possibilità di inserire la carta di credito anche
				in un secondo momento</h6>

			<label for="email">Numero Carta</label><br> <input type="text"
				name="CartaDiCredito"> <br> <br>
		</form>
	</div>
	<div align="center">
		<br> <input class="final" id="conferma" type="submit"
			name="submit" value="Conferma"> <input id="resetta"
			class="final" type="reset" name="reset" value="Reset">
	</div>
</body>
<br>
</html>