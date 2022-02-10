<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script type="application/javascript">
	
	
var password = document.getElementById("password"), confermapassword = document.getElementById("confermapassword");

function validatePassword(){
  if(password.value != confirm_password.value) {
	  confermapassword.setCustomValidity("Le password non coincidono!");
  } else {
	  confermapassword.setCustomValidity('');
  }
}

password.onchange = validatePassword;
confermapassword.onkeyup = validatePassword;




</script>
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()
		|| session.getAttribute("dettagliutente") == null) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 1 && tipologia != 5) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Dettagli Utente</title>
</head>
<body>
	<%
	UtenteBean utente = null;
	if (session.getAttribute("dettagliutente") != null)
		utente = (UtenteBean) session.getAttribute("dettagliutente");
	else {
	%>
	<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
	<%
	return;
	}
	%>
	<div align="center">
		<form action="ModificaUtente" method=post>
			<%
			if (tipologia == 5) {
			%>
			<h2>Per modificare la password di un dipendente, fare click su
				"Conferma modifica" in fondo alla pagina.</h2>
			<%
			}
			%>

			<h1>Dati utente</h1>

			<h3>Compila i campi per modificare i dati</h3>
			<form action="registrazioneUtente" method="post">
				<h3>
					<i>Dati Autenticazione</i>
				</h3>
				<label for="email">E-mail</label><br> <input type="email"
					name="email" required value=<%=utente.getEmail()%>> <br>
				<br>
				<%
				if (tipologia == 1) {
				%>
				<label for="password">Password</label><br> <input
					type="password" name="password" required value=""> <br>

				<label for="confermapassword">Conferma password</label><br> <input
					type="password" name="confermapassword" required value="">
				<br>
				<%
				}
				%>

				<h3>
					<i>Dati Anagrafici</i>
				</h3>
				<label for="CF">Codice Fiscale</label><br> <input
					type="text" name="CF" id="CF" maxlength="16" required
					value=<%=utente.getCF()%>> <br> <br> <label
					for="nome">Nome</label><br> <input type="text" name="nome"
					id="nome" required value=<%=utente.getNome()%>> <br> <br>
				<label for="cognome">Cognome</label><br> <input type="text"
					name="cognome" id="cognome" required value=<%=utente.getCognome()%>>
				<br> <br> <br>

				<h3>
					<i>Indirizzo</i>
				</h3>
				<label for="citta">Città</label><br> <input type="text"
					name="citta" maxlength="35" id="citta" required
					value=<%=utente.getCitta()%>> <br> <br> <label
					for="via">Via</label><br> <input type="text" name="via"
					id="via" required value=<%=utente.getVia()%>> <br> <br>
				<label for="provincia">Provincia</label><br> <input type="text"
					name="provincia" maxlength="2" id="provincia" required
					value=<%=utente.getProvincia()%>> <br> <br> <label
					for="numeroCivico">Numero civico</label><br> <input type="number"
					name="numeroCivico" id="numeroCivico" required
					value=<%=utente.getNumeroCivico()%>> <br> <br> <label
					for="CAP">CAP</label><br> <input type="number" name="CAP"
					maxlength="5" id="CAP" required value=<%=utente.getCAP()%>>
				<p>
					<%
					if (tipologia == 5) {
					%>
				
				<h3>
					<i>Tipologia dipendente</i>
				</h3>

				<BR> <select id="tipologiaUtente" name="tipologiaUtente">
					<option value="2" <%=utente.getTipologia() == 2 ? "selected" : ""%>>Centralinista</option>
					<option value="3" <%=utente.getTipologia() == 3 ? "selected" : ""%>>Magazziniere</option>
					<option value="4" <%=utente.getTipologia() == 4 ? "selected" : ""%>>Amministratore
						Catalogo</option>
					<option value="5" <%=utente.getTipologia() == 5 ? "selected" : ""%>>Amministratore
						Personale</option>
				</select>
				<%
				}
				%>
				<BR> <BR> <input type=submit value="Conferma modifica">
			</form>
	</div>

</body>
</html>