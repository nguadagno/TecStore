<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco Ordini</title>
</head>
<body>
<body>
	<form action="ElencoOrdiniCliente" method="post">
		<p>
			<br> <input type="text" name="testo" maxlength="45"
				placeholder="Cerca..." required> <select name="limit">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <input type="submit" value="Conferma">
		</p>
	</form>

	<%
	ArrayList<OrdineBean> ordini = (ArrayList<OrdineBean>) session.getAttribute("ordini");
	if (ordini == null || ordini.size() == 0) {
	%>
	<h3>Nessun Ordine effettuato</h3>
	<%
	return;
	} else {
	%><table>
		<tr>
			<th>Nome</th>
			<th>Quantita</th>
			<th>Data</th>
			<th></th>
		</tr>
		<%
		for (OrdineBean a : ordini) {
		%>
		<tr>
			<td><%=a.getStato()%></td>
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