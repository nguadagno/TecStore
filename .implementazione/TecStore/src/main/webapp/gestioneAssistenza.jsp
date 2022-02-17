<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.ArrayList, Bean.TicketBean, java.util.Enumeration"%>
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

<title>Gestione Ticket</title>
</head>
<body>
	<div align="center">
		<form action="ElencoTicketCentralinista" method="post">
			<select name="limit" id="gestioneAssistenza-limit">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <input type="submit">
		</form>

		<table>
			<tr>
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
				<td><%=ticket.getTipologia()%></td>
				<td><%=ticket.getDataUltimoMessaggio()%></td>
				<td><form action="DettagliTicket" method="post">
						<input type="hidden" name="IDTicket"
							value=<%=ticket.getIDTicket()%>><input type="submit"
							value="Dettagli" id="elencoVenditeCentralinista-dettagli">
					</form></td>
			</tr>
			<%
			}
			%>
		</table>
	</div>
</body>
</html>