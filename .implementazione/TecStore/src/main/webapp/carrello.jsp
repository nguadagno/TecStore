<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,bean.*, control.*, model.*" session="true"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()
		|| session.getAttribute("carrello") == null) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 1) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Carrello</title>
</head>
<body>
	<%
	ArrayList<ArticoloBean> carrello = (ArrayList<ArticoloBean>) session.getAttribute("carrello");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");
	float totale = 0;
	if (carrello == null || carrello.size() == 0) {
	%>
	<h3>Il Carrello &egrave; Vuoto!</h3>
	<%
	} else {
	%>
	<table>
		<tr>
			<th>Nome</th>
			<th>Prezzo</th>
			<th></th>
			<th></th>
		</tr>

		<%
		for (ArticoloBean a : carrello) {
			totale += (a.getPrezzo() * a.getQuantita());
		%>
		<tr>
			<td><%=a.getNome()%></td>
			<td><%=a.getPrezzo()%>&euro;</td>
			<td>
				<form action="rimozioneDalCarrello" method="post">
					<input type="submit" id="carrello-rimuovi" value="Rimuovi">
					<input value="<%=a.getID()%>" type="hidden" name="IDArticolo">

				</form>
			</td>
			<td>
				<form action="aggiornamentoQuantitaCarrello" name="aggiornamento"
					method="post">
					<input value="<%=a.getID()%>" type="hidden" name="IDArticolo">
					<input type="number" min=1 max=10 id="carrello-aggiornaQuantita"
						name="quantita" value="<%=a.getQuantita()%>"> <input
						type="submit" id="carrello-aggiornaQuantitaAggiorna"
						value="Aggiorna">
				</form>
			</td>
		</tr>
		<%
		}
		%>
		<tr></tr>
		<tr>
			<td><b>Totale</b></td>
			<td><%=totale%>&euro;</td>
		</tr>

	</table>
	<form action="CreazioneOrdine" method="post">
		<input type="submit" id="carrello-acquista" value="Acquista!">
	</form>
	<%
	}
	%>

	<br>
	<br>
	<br>
	<hr>
	<div>
		<a href="paginainiziale.jsp" id="carrello-tornaAllaHome"><button>Torna
				Alla Home</button></a>
	</div>
</body>
</html>
