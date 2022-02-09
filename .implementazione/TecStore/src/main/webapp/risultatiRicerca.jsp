<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco Vendite</title>
</head>
<body>
	<div align="center">
		<h3>Ricerca Vendita</h3>
		<form action="RicercaVendita" method="post">
			<input type="text" name="nome" maxlength="35" required> <input
				type="submit" value="Cerca">
		</form>
	</div>

	<%
	ArrayList<ArticoloBean> risultati = (ArrayList<ArticoloBean>) session.getAttribute("risultati");
	if (risultati == null) {
		return;
	} else {
	%><table>
		<tr>
			<th>Nome</th>
			<th>Quantita</th>
			<td>Stato</td>
			<th>Data</th>
			<th></th>
		</tr>
		<%
		for (ArticoloBean a : risultati) {
		%>
		<tr>
			<td><%=a.getNome()%></td>
			<td><%=a.getQuantita()%></td>
			<td><%=a.getStato()%></td>
			<td><%=a.getData()%></td>
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
</body>
</html>