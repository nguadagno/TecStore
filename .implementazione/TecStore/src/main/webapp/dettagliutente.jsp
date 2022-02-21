<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,bean.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
		<form action="ModificaUtente" method="post">
			<h3>
				<i>Dati Autenticazione</i>
			</h3>
			<label for="email">E-mail</label><br> <input type="email"
				name="email" id="dettagliUtente-email" required
				value=<%=utente.getEmail()%>> <br> <br>
			<%
			if (tipologia == 1) {
			%>
			<a href="modificaPassword.jsp" id="dettagliUtente-modificaPassword">Modifica
				Password</a>
			<%
			}
			%>

			<h3>
				<i>Dati Anagrafici</i>
			</h3>
			<br> <input type="hidden" name="CF" id="CF" maxlength="16"
				value=<%=utente.getCF()%>> <br> <br> <label
				for="nome">Nome</label><br> <input type="text" name="nome"
				id="nome" id="dettagliUtente-nome" required
				value=<%=utente.getNome()%>> <br> <br> <label
				for="cognome">Cognome</label><br> <input type="text"
				name="cognome" id="cognome" id="dettagliUtente-cognome" required
				value=<%=utente.getCognome()%>> <br> <br> <br>

			<h3>
				<i>Indirizzo</i>
			</h3>
			<label for="citta">Città</label><br> <input type="text"
				id="dettagliUtente-citta" name="citta" maxlength="35" id="citta"
				required value=<%=utente.getCitta()%>> <br> <br> <label
				for="via">Via</label><br> <input type="text" name="via"
				id="dettagliUtente-via" id="via" required value=<%=utente.getVia()%>>
			<br> <br> <label for="provincia">Provincia</label><br>
			<input type="text" id="dettagliUtente-provincia" name="provincia"
				maxlength="2" id="provincia" required
				value=<%=utente.getProvincia()%>> <br> <br> <label
				for="numeroCivico">Numero civico</label><br> <input
				type="number" name="numeroCivico" id="numeroCivico"
				id="dettagliUtente-numeroCivico" required
				value=<%=utente.getNumeroCivico()%>> <br> <br> <label
				for="CAP">CAP</label><br> <input type="number" name="CAP"
				maxlength="5" id="CAP" id="dettagliUtente-cap" required
				value=<%=utente.getCAP()%>>
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
			<BR> <BR> <input type=submit value="Conferma modifica"
				id="dettagliUtente-confermaModifica">
		</form>
	</div>
	<div align="Left">
		<a href="index.jsp"><button>Torna Alla Home</button></a>
	</div>

</body>
</html>