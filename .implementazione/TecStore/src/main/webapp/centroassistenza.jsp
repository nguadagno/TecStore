<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.*,bean.*, control.*, model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null || session.getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());

if (tipologia != 1) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Servizio Clienti</title>
</head>
<body>
	<%
	ArrayList<TicketBean> elenco = (ArrayList<TicketBean>) session.getAttribute("elenco");
	%>
	<div>
		<a href="creazioneTicket.jsp" id="centroAssistenza-creazioneTicket">
			<button>Nuovo Ticket</button>
		</a>
	</div>

	<%
	if (elenco == null || elenco.size() == 0) {
	%>
	<h3>Non ci sono Ticket aperti...</h3>
	<h2>Puoi creare un ticket cliccando su "Nuovo Ticket"</h2>
	<%
	return;
	}
	%>
	<table>
		<tr>
			<th>Stato</th>
			<th>Tipologia</th>
			<th>Data ultimo messaggio</th>
			<th></th>
		</tr>
		<%
		for (TicketBean ticket : elenco) {
			String stato = "";
			if (ticket.getStato().equals("InElaborazione"))
				stato = "In Elaborazione";
			else if (ticket.getStato().equals("InAttesa"))
				stato = "In Attesa di Risposta";
			else if (ticket.getStato().equals("Chiuso"))
				stato = "Chiuso";
			else if (ticket.getStato().equals("InAttesaCliente"))
				stato = "In Attesa di Risposta da parte del cliente";
		%>
		<tr>
			<td><%=stato%>
			<td><%=ticket.getTipologia()%></td>
			<td><%=ticket.getDataUltimoMessaggio()%></td>
			<td><form action="DettagliTicket" method="post">
					<input type="hidden" name="IDTicket"
						id="centroAssistenza-dettagliTicket"
						value=<%=ticket.getIDTicket()%>><input type="submit"
						value="Dettagli">

				</form></td>
		</tr>
		<%
		}
		%>

	</table>

</body>
</html>
