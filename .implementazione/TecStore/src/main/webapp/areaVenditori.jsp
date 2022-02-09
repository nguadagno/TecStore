<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null
		|| session.getAttribute("tipologia").toString().isEmpty()) {
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
			<input type="text" name="nome" maxlength="35" id="testo"
				placeholder="Cerca vendita..." required> <input
				type="submit" value="Cerca">
		</form>
	</div>
	<br>
	<br>
	<hr>
	<div>
	<form action="nuovaVendita.jsp" method="post">
		<input type="submit" value="Nuovo Articolo">
	</form>
	</div>
	<div>
		<%
		ArrayList<ArticoloBean> elencoVendite = (ArrayList<ArticoloBean>) session.getAttribute("risultati");
		ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");
		%>

	<%
	if (elencoVendite == null || foto == null) {
		return;
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
					<input type="submit" value="Dettagli">
				</form></td>

		</tr>

		<%
		}
		}
		%>
	</table>
</body>
</html>
