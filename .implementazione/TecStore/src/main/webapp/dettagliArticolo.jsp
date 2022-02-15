<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* , control.*"
	session="true"%>
<!DOCTYPE html>
<html>
<head>
<style>
/* Reference: https://www.w3schools.com/howto/howto_js_slideshow_gallery.asp */
* {
	box-sizing: border-box;
}

.container {
	position: relative;
}

.cursor {
	cursor: pointer;
}

.prev, .next {
	cursor: pointer;
	position: absolute;
	top: 40%;
	width: auto;
	padding: 16px;
	margin-top: -50px;
	color: white;
	font-weight: bold;
	font-size: 20px;
	border-radius: 0 3px 3px 0;
	user-select: none;
	-webkit-user-select: none;
}

.next {
	right: 0;
	border-radius: 3px 0 0 3px;
}

.prev:hover, .next:hover {
	background-color: rgba(0, 0, 0, 0.8);
}

.caption-container {
	text-align: center;
	background-color: #222;
	padding: 2px 16px;
	color: white;
}

.row:after {
	content: "";
	display: table;
	clear: both;
}

.column {
	float: left;
	width: 16.66%;
}

.demo {
	opacity: 0.6;
}

.active, .demo:hover {
	opacity: 1;
}
</style>
<meta charset="ISO-8859-1">

<%
int tipologia = -1;
if (session.getAttribute("tipologia") != null)
	tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if ((tipologia != 1 && tipologia != 2 && tipologia != 4 && tipologia != -1)
		|| session.getAttribute("dettagliArticolo") == null || session.getAttribute("fotoArticolo") == null) {
%>
<meta http-equiv="refresh" content="0;URL ='paginainiziale.jsp'" />
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
	<a href=paginainiziale.jsp>Torna indietro</a>
	<%
	return;

	}

	for (FotoBean f : foto) {
	%>
	<div class="container">
		<div class="mySlides">
			<img src="img?id=<%=f.getID()%>" style="width: 100%">
		</div>
		<a class="prev" onclick="plusSlides(-1)">&#10094; </a> <a class="next"
			onclick="plusSlides(1)">&#10095; </a>
	</div>
	<%
	}
	%>
	<div align="center">
		<h2>Nome:</h2>
		<h4>
			<%=risultato.getNome()%>
		</h4>
	</div>
	<div align="center">
		<h2>Prezzo:</h2>
		<h4>
			<%=risultato.getPrezzo()%>
		</h4>
	</div>
	<div align="center">
		<h2>Quantita disponibile:</h2>
		<h4>
			<%=risultato.getQuantita()%>
		</h4>
	</div>
	<div align="center">
		<h2>Descrizione:</h2>
		<h4>
			<%=risultato.getDescrizione()%>
		</h4>
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
	}

	else {
	%>
	<div align="right">
		<form action="AggiuntaAlCarrello" method="post">
			<input type="submit" value="Aggiungi al Carrello"> <input
				type="hidden" name="IDArticolo" value="<%=risultato.getID()%>">
			<input type="number" name="quantita" style="width: 50px;" value="1"
				step="1" min="1"
				max="<%=10 > risultato.getQuantita() ? risultato.getQuantita() : 10%>"
				required>
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

	if (tipologia == -1) {
	%>
	<div align="right">
		<form action="autenticazione.jsp" method="post">
			<h4>Per aggiungere articoli al carrello � necessario essere
				autenticati.</h4>
			<input type="submit" value="Autenticazione">
		</form>
	</div>
	<%
	}
	%>
</body>
</html>