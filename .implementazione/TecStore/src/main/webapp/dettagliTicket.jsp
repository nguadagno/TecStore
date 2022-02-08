<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,Bean.* , control.*, model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (request.getSession().getAttribute("tipologia") == null
		|| request.getSession().getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(request.getSession().getAttribute("tipologia").toString());

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
		%>
		<table>
			<tr>
				<th>Scritto da</th>
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
		<br> <br> <br> <a href="rispostaTicket.jsp">
			<button>Rispondi</button>
		</a>

	</div>
</body>
</html>