<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,bean.*" session="true"%>
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
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 3 && tipologia != 1) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Dettagli Ordine</title>
</head>
<body>
	<%
	OrdineBean ordine = (OrdineBean) session.getAttribute("ordine");
	ArticoloBean articolo = (ArticoloBean) session.getAttribute("articolo");
	UtenteBean cliente = (UtenteBean) session.getAttribute("cliente");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");

	if (ordine == null || cliente == null) {
	%>
	<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
	<%
	return;
	}

	if (tipologia == 3) {
	if (session.getAttribute("operazione").toString() != null) {

		if (session.getAttribute("operazione").toString().equals("spedizione")) {
	%>
	<div align=center>
		<h2>
			<b>Dettagli Ordine</b>
		</h2>
		<br>
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
					</div>
					<%
					}
					%>
				</div>
			</div>
		</div>

		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.8.2/js/lightbox.min.js"></script>

		<div>
			<h4>
				<b>Nome articolo:</b>
			</h4>
			<label><%=articolo.getNome()%></label>
		</div>
		<div>
			<h4>
				<b>Dati cliente</b>
			</h4>
			<label><b>Cognome:</b><%=cliente.getCognome()%>, <b>Nome:</b>
				<%=cliente.getNome()%></label>
		</div>
		<div>
			<h4>
				<b>Indirizzo</b>
			</h4>
			<label><b>Via:</b><%=cliente.getVia()%>, <b>N:</b> <%=cliente.getNumeroCivico()%>.</label><br>
			<label><b>Citta:</b> <%=cliente.getCitta()%>, <%=cliente.getProvincia()%>,<b>
					CAP:</b> <%=cliente.getCAP()%> </label><br> <br> <br>
			<hr>
			<br>
			<div>
				<form action="ConfermaOrdine" method="post">
					<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
					<input type=hidden name=stato value="Spedito"> <input
						type=text max=15 name=Tracking
						id="dettagliOrdine-confermaSpedizioneTracking"> <BR>
					<input type="submit" value="Conferma spedizione"
						id="dettagliOrdine-confermaSpedizione">
				</form>
				<form action="ConfermaOrdine" method="post">
					<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
					<input type=hidden name=stato value="Annullato"><input
						type="submit" value="Annulla ordine"
						id="dettagliOrdine-annullaOrdine">
				</form>
			</div>
		</div>
	</div>
	<%
	} else if (request.getParameter("operazione").equals("rimborso")) {
	%>
	<div align=center>
		<h2>
			<b>Dettagli Rimborso</b>
		</h2>
		<br>
		<div>
			<h4>
				<b>Nome articolo:</b>
			</h4>
			<label><%=articolo.getNome()%></label>
		</div>
		<div>
			<h4>
				<b>Destinatario</b>
			</h4>
			<label><b>Cognome:</b><%=cliente.getCognome()%>, <b>Nome:</b>
				<%=cliente.getNome()%></label>
		</div>
		<div>
			<h4>
				<b>Indirizzo</b>
			</h4>
			<label><b>Via:</b><%=cliente.getVia()%>, <b>N:</b> <%=cliente.getNumeroCivico()%>.</label><br>
			<label><b>Citta:</b> <%=cliente.getCitta()%>, <%=cliente.getProvincia()%>,<b>
					CAP:</b> <%=cliente.getCAP()%> </label><br> <br> <br>
			<hr>
			<br>
		</div>
		<div>
			<form action="RimborsoMagazziniere" method="post">
				<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
				<input type=hidden name=stato value="Rimborsato"><input
					type="submit" value="Conferma rimborso"
					id="dettagliOrdine-confermaRimborso">
			</form>
			<form action="RimborsoMagazziniere" method="post">
				<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
				<input type=hidden name=stato value="RimborsoRifiutato"> <input
					type="submit" value="Rifiuta rimborso"
					id="dettagliOrdine-rifiutaRimborso">
			</form>
		</div>
	</div>

	<%
	}
	}
	} else {
	%>
	<div>
		<div>
			<h4>
				<b>Nome articolo:</b>
			</h4>
			<label><%=articolo.getNome()%></label>
		</div>
		<div>
			<h4>
				<b> Prezzo: </b>
				<%=articolo.getPrezzo()%>&euro;
			</h4>
		</div>
	</div>
	<div>
		<h4>
			<b>Indirizzo di spedizione:</b>
		</h4>
		<label><b>Via:</b><%=cliente.getVia()%>, <b>N:</b> <%=cliente.getNumeroCivico()%>.</label><br>
		<label><b>Citta:</b> <%=cliente.getCitta()%>, <%=cliente.getProvincia()%>,<b>
				CAP:</b> <%=cliente.getCAP()%> </label><br> <br> <br>
		<hr>
		<br>
	</div>
	<div>
		<%
		if (ordine.getStato().equals("Spedito")) {
		%>
		<form action="RimborsoCliente" method="post">
			<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
			<input type="submit" value="Richiedi rimborso"
				id="dettagliOrdine-richiediRimborso">
		</form>
		<%
		}
		if (!ordine.getStato().equals("Spedito") && !ordine.getStato().equals("Annullato")
				&& !ordine.getStato().equals("Rimborsato") && !ordine.getStato().equals("RimborsoRifiutato")) {
		%>
		<form action="AnnullamentoOrdine" method="post">
			<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
			<input type="submit" value="Richiedi annullamento ordine"
				id="dettagliOrdine-annullamentoOrdine">
		</form>
		<%
		}
		%>
	</div>
	<%
	}
	%>
</body>
</html>
