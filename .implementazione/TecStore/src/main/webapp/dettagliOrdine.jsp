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
	//OrdineBean ordine = (OrdineBean) session.getAttribute("ordine");
	Date date2 = new Date(System.currentTimeMillis());
	OrdineBean ordine = new OrdineBean("ciao", "ciao", "ciao", 3, date2, "ciao", "nope");
	if (ordine == null) {
		return;
	}
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
			<th>Data</th>
			<th>Quantita</th>
			<th>Stato</th>
			<th>Codice Tracciamento</th>
		</tr>

		<tr>
			<td><%=ordine.getID()%></td>
			<td><%=ordine.getData()%></td>
			<td><%=ordine.getQuantita()%></td>
			<td><%=ordine.getStato()%></td>
			<%
			if (ordine.getCodiceTracciamento() == null) {
			%><td>In Attesa</td>
			<%
			} else {
			%><td><%=ordine.getCodiceTracciamento()%>
				<%
				}
				%></td>
		</tr>
	</table>

	<br>
	<br>

</body>
</html>