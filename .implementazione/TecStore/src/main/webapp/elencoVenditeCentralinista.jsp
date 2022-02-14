<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
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
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 2) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Gestione Vendite</title>
</head>
<body>
<body>

	<form action="ElencoVenditeCentralinista" method="post">
		<select name="limit">
			<option value="10">10</option>
			<option value="20">20</option>
			<option value="50">50</option>
		</select> <input type="submit">
	</form>

	<%
	ArrayList<ArticoloBean> risultati = (ArrayList<ArticoloBean>) session.getAttribute("elenco");
	%>

	<%
	if (risultati == null) {
	%>
	<h3>Nessun Articolo in attesa di revisione!</h3>
	<%
	return;
	}
	%>
	<table>
		<tr>
			<th>Tipologia</th>
			<th>Data ultimo messaggio</th>
			<th></th>
		</tr>
		<%
		for (ArticoloBean articolo : risultati) {
		%>
		<tr>
			<td><%=articolo.getNome()%></td>
			<td><form action="DettagliArticolo" method="post">
					<input type="hidden" name="IDArticolo" value=<%=articolo.getID()%>><input
						type="submit" value="Dettagli">
				</form></td>
		</tr>
		<%
		}
		%>
	</table>
</body>
</body>
</html>