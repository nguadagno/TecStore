<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*, Bean.* , control.*, model.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
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
	FotoBean foto = (FotoBean) session.getAttribute("foto");
	float totale = 0;
	if (carrello == null || foto == null) {
	%>
	<h3>Il Carrello &egrave; Vuoto!</h3>
	<%
	return;
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
			totale += a.getPrezzo();
		%>
		<tr>
			<td><%=a.getNome()%></td>
			<td><%=a.getPrezzo()%></td>
			<td>
				<form action="rimozioneArticoloCarrello" method="post">
					<input type="submit" value="Rimuovi"> <input
						value="<%=a.getID()%>" type="hidden" name="IDArticolo">

				</form>
			</td>
			<td>
				<form action="aggiornamentoQuantitaCarrello" name="aggiornamento"
					method="post">
					<input type="number" min=1 max=10
						onchange="javascript:document.aggiornamento.submit();"
						value="<%=a.getQuantita()%>">
				</form>
			</td>
			<%
			}
			}
			%>
			<td><h6>
					Totale:
					<%=totale%></h6></td>
		</tr>
	</table>
	<br>
	<br>
	<br>
	<hr>
	<form action="CreazioneOrdine" method="post">
		<input type="submit" value="Acquista!">
	</form>
</body>
</html>
