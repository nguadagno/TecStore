<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,bean.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica Articolo</title>
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

if (tipologia != 1 && tipologia != 4) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
</head>
<body>
	<%
	ArticoloBean risultato = (ArticoloBean) session.getAttribute("dettagliArticolo");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("fotoArticolo");
	%>
	<div align="center">
		<div>
			<h3>Modifica Articolo</h3>
			<br> <br>
		</div>
		<div>
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
								<input type=submit value="Rimuovi foto" id="rimozione-foto">
							</form>
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

			<form action="ModificaArticolo" method="post">
				<div>
					<label><b>Nome</b></label> <br> <input type="text" name="nome"
						value="<%=risultato.getNome()%>" id="modificaArticolo-Nome">
					<br> <br> <label><b>Descrizione</b></label> <br> <input
						type="text" id="modificaArticolo-descrizione" name="descrizione"
						value="<%=risultato.getDescrizione()%>"
						style="width: 300px; height: 200px;" required> <br> <br>
					<label><b>Quantità</b></label> <br> <input type="number"
						id="modificaArticolo-quantita" name="quantita"
						value="<%=risultato.getQuantita()%>" required> <br> <br>
					<label><b>Prezzo</b></label> <br> <input type="number"
						name="prezzo" id="modificaArticolo-prezzo"
						value="<%=risultato.getPrezzo()%>" required> <br> <br>
					<label><b>Foto</b></label> <br> <input type="file"
						id="modificaArticolo-foto" name="foto"
						accept="image/png, image/jpeg"> <br> <br> <label><b>Rimborsabile</b></label>
					<br> <label><b>Si</b></label> <input type="radio"
						name="rimborsabile" value="1" id="modificaArticolo-rimborsabileSi"
						<%=risultato.isRimborsabile() ? "selected" : ""%>> <br>
					<label><b>No</b></label> <input type="radio" name="rimborsabile"
						value="0" id="modificaArticolo-rimborsabileNo"
						<%=!risultato.isRimborsabile() ? "selected" : ""%>>
				</div>
				<div align="right">
					<input type="submit" value="Continua"
						id="modificaArticolo-Conferma">
				</div>
			</form>
			<a href="paginainiziale.jsp"
				id="modificaArticolo-tornaPaginaIniziale"><button>Annulla</button></a>
		</div>
	</div>

</body>
</html>
