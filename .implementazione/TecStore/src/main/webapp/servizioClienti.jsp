<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,Bean.* , control.*, model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Servizio Clienti</title>
</head>
<body>
	<form>
		<%
		ArrayList<TicketBean> elenco = (ArrayList<TicketBean>) session.getAttribute("elenco");
		%>
		<div>
			<a href="creazioneTicket.jsp">
				<button>Nuovo Ticket</button>
			</a>
		</div>
	</form>

	<table>
		<tr>
			<th>Codice Ticket</th>
			<th>Tipologia</th>
			<th>Data ultimo messaggio</th>
			<th></th>
		</tr>
		<%
		if (elenco == null) {
		%>
		<h3>Non ci sono Ticket aperti...</h3>
		<h2>Puoi creare un ticket cliccando su "Nuovo Ticket"</h2>
		<%
		return;
		}
		%>

		<%
		for (TicketBean ticket : elenco) {
		%>
		<tr>
			<td><%=ticket.getTipologia()%></td>
			<td><%=ticket.getDataUltimoMessaggio()%></td>
		</tr>
		<div>
			<form action="dettagliTicket" method="post">
				<input type="hidden" value=<%=ticket.getIDTicket()%>><input
					type="submit" value="Dettagli">

			</form>
		</div>


		<%
		}
		%>

	</table>

</body>
</html>