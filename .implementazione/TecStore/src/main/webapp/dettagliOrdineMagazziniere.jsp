<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dettagli Ordine</title>
</head>
<body>
	<%
	OrdineBean ordine = (OrdineBean) session.getAttribute("ordine");
	ClienteBean cliente = (ClienteBean) session.getAttribute("cliente");
	%>



	<h2>
		<b>Dettagli Ordine</b>
	</h2>
	<br>
	<br>
	<hr>
	<table>
		<tr>
			<th>ID Ordine</th>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Via</th>
			<th>Numero</th>
			<th>Citta</th>
			<th>Provincia</th>
			<th>CAP</th>

		</tr>

		<tr>
			<td><%=ordine.getID()%></td>
			<td><%=cliente.getNome()%></td>
			<td><%=cliente.getVia()%></td>
			<td><%=cliente.getNumeroCivico()%></td>
			<td><%=cliente.getCitta()%></td>
			<td><%=cliente.getProvincia()%></td>
			<td><%=cliente.getCAP()%></td>
		</tr>
	</table>

	<div>
		<form action="confermaOrdine" method="post">
			<label><b>Codice Tracking:</b></label><input type="text"
				name="Tracking" required><br> <br> <input
				type="submit" value="Conferma">
		</form>
	</div>
	<br>
	<br>

</body>
</html>