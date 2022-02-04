<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.ArrayList, Bean.TicketBean, java.util.Enumeration"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Gestione Ticket</title>
</head>
<body>
	<div align="center">
		<!-- <form action="gestioneassistenza.jsp" type="post"> -->
		<form action="gestioneassistenza.jsp?tipologia=<%=2%>&" method="post">
			<select name="limit">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <input type="submit">
		</form>

		<table>
			<tr>
				<th>Codice Ticket</th>
				<th>Tipologia</th>
				<th>Data ultimo messaggio</th>
				<th></th>
			</tr>
			<%
			ArrayList<TicketBean> elencoTicket = (ArrayList<TicketBean>) session.getAttribute("elencoTicket");

			if (elencoTicket == null) {
			%>
			<h3>Nessun ticket in attesa di risposta!</h3>
			<%
			return;
			}
			%>

			<%
			for (TicketBean ticket : elencoTicket) {
			%>
			<tr>
				<td><%=ticket.getIDTicket()%></td>
				<td><%=ticket.getTipologia()%></td>
				<td><%=ticket.getDataUltimoMessaggio()%></td>

			</tr>

		</table>
		<form action="dettagliTicket" method="post">
			<input type="hidden" value=<%=ticket.getIDTicket()%>><input
				type="submit" value="Dettagli">
		</form>

		<%
		}
		%>

	</div>
</body>
</html>