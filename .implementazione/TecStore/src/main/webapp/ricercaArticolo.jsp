<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Negozio</title>
</head>
<body>

	<div align="center">
		<h3>Ricerca Articolo</h3>
		<form action="ricercaarticolo" method="post">
			<input type="text" name="testo" maxlength="35"
				placeholder="Titolo..." required> <input type="submit"
				value="Cerca">
		</form>
	</div>
	<br>
	<br>
	<hr>

	<%
	ArrayList<ArticoloBean> risultati = (ArrayList<ArticoloBean>) session.getAttribute("risultati");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");
	%>

	<%
	if (risultati == null || foto == null) {
	%>
	<h3>Nessun Articolo Trovato!</h3>
	<%
	return;
	} else {
	%>
	<table>
		<tr>
			<th>Nome</th>
			<th>Descrizione</th>
			<th></th>
		</tr>
		<%
		for (ArticoloBean a : risultati) {
		%>
		<tr>
			<td><%=a.getNome()%></td>
			<td><%=a.getDescrizione()%></td>
			<td>
				<form action="DettagliArticolo" method="post">
					<input type="hidden" name="IDArticolo" value="<%=a.getID()%>">
					<input type="submit" value="Dettagli">
				</form>
			</td>
			<%
			}
			}
			%>
		</tr>
	</table>
</body>
</html>