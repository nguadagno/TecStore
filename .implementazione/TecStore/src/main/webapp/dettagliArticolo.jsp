<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,bean.*, control.*"
	session="true"%>
<!DOCTYPE html>
<html>
<head>
<style>
.photo-gallery {
	color: #313437;
	background-color: #fff;
}

.photo-gallery p {
	color: #7d8285;
}

.photo-gallery h2 {
	font-weight: bold;
	margin-bottom: 40px;
	padding-top: 40px;
	color: inherit;
}

@media ( max-width :767px) {
	.photo-gallery h2 {
		margin-bottom: 25px;
		padding-top: 25px;
		font-size: 24px;
	}
}

.photo-gallery .intro {
	font-size: 16px;
	max-width: 500px;
	margin: 0 auto 40px;
}

.photo-gallery .intro p {
	margin-bottom: 0;
}

.photo-gallery .photos {
	padding-bottom: 20px;
}

.photo-gallery .item {
	padding-bottom: 30px;
}
</style>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/style.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/css/lightbox.min.css">

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
		<form action="ricercaArticolo" method="post">
			<input type="text" name="testo" id="dettagliArticolo-ricercaArticolo"
				maxlength="35" id="testo" placeholder="Titolo..." required>
			<input type="submit" id="dettagliArticolo-cerca" value="Cerca">
		</form>
	</div>
	<%
	}

	ArticoloBean risultato = (ArticoloBean) session.getAttribute("dettagliArticolo");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("fotoArticolo");

	if (risultato == null) {
	%>
	<h3>Articolo non Trovato!</h3>
	<a href=paginainiziale.jsp id="dettagliArticolo-tornaIndietro">Torna
		indietro</a>
	<%
	return;
	}
	%>
	<div class="photo-gallery">
		<div class="container">
			<div class="row photos">
				<%
				for (FotoBean f : foto) {
				%>
				<div class="col-sm-6 col-md-4 col-lg-3 item">
					<a href="img?id=<%=f.getID()%>" data-lightbox="photos"><img
						style="max-width: 50px" class="img-fluid"
						src="img?id=<%=f.getID()%>"></a>

					<form action="img?del=<%=f.getID()%>" method=get>
						<input type=submit value="Rimuovi immagine">
					</form>
				</div>
				<%
				}
				%>
			</div>
		</div>
	</div>

	<form action="img?del=all" method=get>
		<input type=submit value="Rimuovi tutte le immagini">
	</form>

	<form action="inserimentoImmagini.jsp" method=get>
		<input type=submit value="Inserisci nuove immagini">
	</form>

	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/js/lightbox.min.js"></script>

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
		if ((session.getAttribute("CF").toString()
		.equals(((ArticoloBean) session.getAttribute("dettagliArticolo")).getIDVenditore())) || tipologia == 4) {
	%>
	<div align="right">
		<form action="ModificaArticolo" method="post">
			<input type="hidden" name="IDArticolo" value="<%=risultato.getID()%>">
			<input type="submit" value="Modifica" id="dettagliArticolo-modifica">
		</form>
	</div>
	<%
	} else {
	%>
	<div align="right">
		<form action="AggiuntaAlCarrello" method="post">
			<input type="submit" value="Aggiungi al Carrello"
				id="dettagliArticolo-aggiungiAlCarrello"> <input
				type="hidden" name="IDArticolo" value="<%=risultato.getID()%>">
			<input type="number" name="quantita"
				id="dettagliArticolo-aggiungiAlCarrelloQuantita"
				style="width: 50px;" value="1" step="1" min="1"
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
				name="stato" value="InVendita"
				id="dettagliArticolo-autorizzaVenditaConferma"><br> <label><b>Rifiuta
					Vendita</b></label> <input type="radio" name="stato"
				id="dettagliArticolo-autorizzaVenditaRifiuta" value="Rifiutato">
			<br> <br> <input type="submit" value="Conferma">
		</form>
	</div>
	<%
	}

	if (tipologia == -1) {
	%>
	<div align="right">
		<form action="autenticazione.jsp" method="post">
			<h4>Per aggiungere articoli al carrello è necessario essere
				autenticati.</h4>
			<input type="submit" value="Autenticazione"
				id="dettagliArticolo-autenticazione">
		</form>
	</div>
	<%
	}
	%>
</body>
</html>