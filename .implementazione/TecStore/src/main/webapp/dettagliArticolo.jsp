<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* , control.*"
	session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (request.getSession().getAttribute("tipologia") == null
		|| request.getSession().getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(request.getSession().getAttribute("tipologia").toString());

if ((tipologia != 1 && tipologia != 2 && tipologia != 4) || session.getAttribute("dettagliArticolo") == null
		|| session.getAttribute("fotoArticolo") == null) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Dettagli articolo</title>
</head>
<body>

	<%
	if (tipologia == 1) {
	%>
	<div align="center">
		<form action="RicercaArticolo" method="post">
			<input type="text" name="testo" maxlength="35" id="testo"
				placeholder="Titolo..." required> <input type="submit"
				value="Cerca">
		</form>
	</div>
	<%
	}

	ArticoloBean risultato = (ArticoloBean) session.getAttribute("dettagliArticolo");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("fotoArticolo");

	if (risultato == null || foto == null) {
	%>
	<h3>Articolo non Trovato!</h3>
	Fare click
	<a href="ElencoVenditeCentralinista">qui</a> per tornare all'elenco
	delle vendite.
	<%
	return;
	}
	%>
	<div align="center">
		<!--  TODO: foto -->
		<div>
			<h3>
				Nome:
				<%=risultato.getNome()%>
			</h3>
		</div>
		<div>
			<h3>
				Prezzo:<%=risultato.getPrezzo()%>
				&euro;
			</h3>
		</div>
		<div align="center">
			<h3>
				Quantità disponibile:
				<%=risultato.getQuantita()%>
		</div>
		<div>
			<h3>Descrizione:</h3>
		</div>
		<div>
			<h4>
				<%=risultato.getDescrizione()%>
			</h4>
		</div>

		<%
		if (tipologia == 1) {
		%>
		<div align="Right">
			<form action="AggiuntaAlCarrello" method="post">
				<input type="submit" value="Aggiungi al Carrello"> <input
					type="hidden" name="IDArticolo" value="<%=risultato.getID()%>">
				<select name="quantita">
					<option value="10">1</option>
					<option value="20">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
				</select>
			</form>
		</div>
		<%
		} else {
		%>
		<div align=center>
			<form action="AutorizzazioneVendita" method=post>
				<input type=hidden name="IDArticolo" value="<%=risultato.getID()%>">
				<input type=radio id="InVendita" name="stato" value="InVendita">
				<label for="Autorizzata">Autorizza</label> <BR> <input
					type=radio id="Rifiutata" name="stato" value="Rifiutato" checked>
				<label for="Rifiutato">Rifiuta</label> <BR>

				<button type=submit>Conferma</button>
			</form>
		</div>
		<%
		}
		%>
	</div>

</body>
</html>