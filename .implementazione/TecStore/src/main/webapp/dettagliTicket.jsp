<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,Bean.* , control.*, model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Messaggi</title>
</head>
<body>
	<form>
		<%
		ArrayList<MessaggioBean> elenco = (ArrayList<MessaggioBean>) session.getAttribute("messaggi");
		%>
	</form>
	<table>
		<tr>
			<th>Contenuto</th>
			<th>Data</th>
			<th></th>
		</tr>
		<%
		if (elenco == null) {
		%>
		<h3>Non ci sono Messaggi...</h3>
		<h2>Puoi creare un ticket cliccando su "Nuovo Ticket" nella
			pagina Servizio Clienti</h2>
		<%
		return;
		}
		%>

		<%
		for (MessaggioBean messaggio : elenco) {
		%>
		<tr>
			<td><%=messaggio.getContenuto()%></td>
			<td><%=messaggio.getData()%></td>

		</tr>
	</table>
	<%
	}
	%>
	<br>
	<br>
	<br>
	<hr>
	<br>
	<div>
		<a href="rispostaTIcket.jsp">
			<button>Rispondi</button>
		</a>

	</div>

</body>
</html>