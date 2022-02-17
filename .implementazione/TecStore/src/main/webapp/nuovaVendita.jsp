<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 1 && tipologia != 4) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Nuova Vendita</title>
</head>
<body>
	<div align="center">
		<div>
			<h3>Nuovo Articolo</h3>
			<br> <br>
		</div>
		<div>
			<form action="InserimentoArticolo" method="post">
				<div>
					<label><b>Nome</b></label> <br> <input type="text" name="nome"
						id="nuovaVendita-Nome" required> <br> <br> <label><b>Descrizione</b></label>
					<br> <input type="text" name="descrizione"
						id="nuovaVendita-descrizione" style="width: 300px; height: 200px;">
					<br> <br> <label><b>Quantità</b></label> <br> <input
						type="number" id="nuovaVendita-quantita" name="quantita" required>
					<br> <br> <label><b>Prezzo</b></label> <br> <input
						type="number" name="prezzo" id="nuovaVendita-prezzo" required>
					<br> <br> <br> <label><b>Rimborsabile</b></label> <br>
					<label><b>Si</b></label> <input type="radio" name="rimborsabile"
						id="nuovaVendita-rimborsabileSI" value="1"> <br> <label><b>No</b></label>
					<input type="radio" name="rimborsabile"
						id="nuovaVendita-rimborsabileNO" value="0">
				</div>
				<div align="right">
					<a href="areaVenditori.jsp" id="nuovaVendita-annulla"><button>Annulla</button></a>
					<input type="submit" value="Continua" id="nuovaVendita-conferma">
				</div>
			</form>
		</div>
	</div>

</body>
</html>