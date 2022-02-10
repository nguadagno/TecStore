<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* , control.*"
	session="true"%>
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
} else
tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if ((tipologia != 1 && tipologia != 2 && tipologia != 4) || session.getAttribute("dettagliArticolo") == null
//|| session.getAttribute("fotoArticolo") == null
) {
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

	if (risultato == null) {
	%>
	<h3>Articolo non Trovato!</h3>
	<%
	// TODO pulsante per tornare indietro
	return;
	}
	%>

	<div align="center">
		<h2>Nome:</h2>
		<h4><%=risultato.getNome()%></h4>
	</div>
	<div align="center">
		<h2>Prezzo:</h2>
		<h4><%=risultato.getPrezzo()%></h4>
	</div>
	<div align="center">
		<h2>Quantita disponibile:</h2>
		<h4><%=risultato.getQuantita()%></h4>
	</div>
	<div align="center">
		<h2>Descrizione:</h2>
		<h4><%=risultato.getDescrizione()%></h4>
	</div>

	<%
	if (tipologia == 1 || tipologia == 4) {
		if ((session.getAttribute("CF").toString().equals(request.getParameter("IDVenditore"))) || tipologia == 4) {
	%>
	<div align="right">
		<form action="modificaArticolo.jsp" method="post">
			<input type="hidden" name="IDArticolo" value="<%=risultato.getID()%>">
			<input type="hidden" name="nome" value="<%=risultato.getNome()%>">
			<input type="hidden" name="descrizione"
				value="<%=risultato.getDescrizione()%>"> <input
				type="hidden" name="IDVenditore"
				value="<%=risultato.getIDVenditore()%>"> <input
				type="hidden" name="quantita" value="<%=risultato.getQuantita()%>">
			<input type="hidden" name="prezzo" value="<%=risultato.getPrezzo()%>">
			<input type="hidden" name="rimborsabile"
				value="<%=risultato.isRimborsabile()%>"> <input
				type="submit" value="Modifica">
		</form>
	</div>


	<%
	} else {
	%>
	<div align="right">
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
	}
	} else if (tipologia == 2) {
	%>
	<div align="right">
		<form action="AutorizzazioneVendita" method="post">
			<label><b>Conferma Vendita</b></label> <input type="radio"
				name="stato" value="InVendita"><br> <label><b>Rifiuta
					Vendita</b></label> <input type="radio" name="stato" value="Rifiutato">
			<br> <br> <input type="submit" value="Conferma">
		</form>
	</div>
	<%
	}
	%>
</body>
</html>
