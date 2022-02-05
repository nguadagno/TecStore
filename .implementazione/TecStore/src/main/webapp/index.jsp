<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="Bean.UtenteBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Pagina Iniziale</title>

</head>

<body>
	<div align="center">

		<form action="ricercaArticolo" method="post">
			<p>
				<br> <input type="text" name="testo" maxlength="45"
					placeholder="Cerca..." required> <select name="limit">
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> <input type="submit" value="Conferma">
			</p>
		</form>
		<h5>
			Benvenuto nella nostra pagina shopping! Qui potrai visualizzare tutti
			i prodotti disponibili.<br>Che aspetti? Inizia subito la tua
			elettrificante spesa!
		</h5>
	</div>
	<br>
	<br>
	<hr>
	<br>
	<a href="datiprova?q=1"><button>Inserimento dati di prova</button></a>

	<%
	if (request.getSession().getAttribute("CF") == null) {
	%>
	<div align="right">
		<p>
			<a href="Registrazione.jsp"><button>Registrati</button></a> <a
				href="Autenticazione.jsp"><button>Autenticati</button></a>
		</p>
	</div>
	<%
	} else {
	%>
	<div align="right">
		<p>
			Benvenuto, <i> <%=((UtenteBean) request.getSession().getAttribute("utente")).getNome()%>
				<%=((UtenteBean) request.getSession().getAttribute("utente")).getCognome()%>
				<BR>
			</i> <a href="areavenditori.jsp"><button>Area Venditori</button></a> <a
				href="dettagliprofilo.jsp"><button>Dettagli profilo</button></a>
		</p>
	</div>
	<%
	}
	%>
</body>
</html>