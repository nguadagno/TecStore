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

if (tipologia != 1 && tipologia != 2) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>

<title>Messaggi</title>
</head>
<body>
	<div align=center>
		<%
		ArrayList<MessaggioBean> elenco = (ArrayList<MessaggioBean>) session.getAttribute("messaggi");
		String IDTicket = "";
		if (elenco != null)
			IDTicket = elenco.get(0).getIDTicket();
		%>
		<table>
			<tr>
				<th>Scritto da</th>
				<th>Contenuto</th>
				<th>Data</th>
				<th></th>
			</tr>
			<%
			if (elenco == null || elenco.size() == 0) {
			%>
			<h3>Non ci sono Messaggi...</h3>
			<h2>Puoi creare un ticket cliccando su "Nuovo Ticket" nella
				pagina Servizio Clienti</h2>
			<%
			return;
			}

			for (MessaggioBean messaggio : elenco) {
			%>
			<tr>
				<td><%=messaggio.getAutore().getNome()%> <%=messaggio.getAutore().getCognome()%>
					<BR> <%
 if (messaggio.getAutore().getTipologia() == 1) {
 %> (Cliente) <%
 } else {
 %> (Centralinista)<%
 }
 %></td>
				<td><%=messaggio.getContenuto()%></td>
				<td><%=messaggio.getData()%></td>

			</tr>
			<%
			}
			%>
		</table>
		<br> <br> <br> <a href="rispostaTicket.jsp"
			id="dettagliTicket-rispostaTicket">
			<button>Rispondi</button>
		</a>
		<form action="ChiusuraTicket" method=post>
			<input type=hidden name=IDTicket value=<%=IDTicket%>> <input
				type=submit value="Chiusura ticket"
				id="dettagliTicket-chiusuraTicket">
		</form>
	</div>
</body>
</html>