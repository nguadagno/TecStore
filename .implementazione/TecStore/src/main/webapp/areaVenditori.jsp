<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,bean.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()) {
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
<title>Area Venditori</title>
</head>
<body>
	<%
	ArrayList<ArticoloBean> elencoVendite = (ArrayList<ArticoloBean>) session.getAttribute("risultati");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");
	%>

	<div align="center">
		<form action="RicercaVendita" method="post">
			<input type="text" name="nome" id="areaVenditori-ricercaTesto"
				maxlength="35" placeholder="Cerca vendita..." required> <input
				type="submit" id="areaVenditori-ricercaCerca" value="Cerca">
		</form>
	</div>
	<br>
	<br>
	<hr>
	<div>
		<form action="nuovaVendita.jsp" id="areaVenditori-nuovoArticolo"
			method="post">
			<input type="submit" value="Nuovo Articolo">
		</form>
	</div>

	<%
	if (elencoVendite == null || foto == null || elencoVendite.size() == 0) {
	%>
	<br>
	<br>
	<h3>Non hai nessun oggetto in vendita</h3>
	<%
	} else {
	%>
	<table>
		<tr>
			<th>Nome</th>
			<th>Descrizione</th>
			<th>Prezzo</th>
			<th>ID</th>
			<th></th>
		</tr>

		<%
		for (ArticoloBean a : elencoVendite) {
		%>
		<tr>
			<td><%=a.getNome()%></td>
			<td><%=a.getDescrizione()%></td>
			<td><%=a.getPrezzo()%></td>
			<td><%=a.getID()%></td>

			<td><form action="DettagliArticolo" method="post">
					<input type="hidden" name="IDArticolo" value="<%=a.getID()%>">
					<input type="submit" id="areaVenditori-dettagliArticolo"
						value="Dettagli">
				</form></td>

		</tr>

		<%
		}
		}
		%>
	</table>
</body>
</html>
