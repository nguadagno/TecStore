<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>&euro;&euro;
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco Ordini</title>
</head>
<body>
<body>

	<%
	ArrayList<OrdineBean> ordini = (ArrayList<OrdineBean>) session.getAttribute("elenco");
	if (ordini == null) {
	%>
	<h3>Nessun Ordine in attesa</h3>
	<%
	return;
	} else {
	%><table>
		<tr>
			<th>ID</th>
			<th>Quantita</th>
			<th>Data</th>
			<th></th>
		</tr>
		<%
		for (OrdineBean a : ordini) {
		%>
		<tr>
			<td><%=a.getID()%></td>
			<td><%=a.getQuantita()%></td>
			<td><%=a.getData()%></td>
			<td>
				<form action="dettagliOrdine" method="post">
					<input type="hidden" name="IDOrdine" value="<%=a.getID()%>">
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