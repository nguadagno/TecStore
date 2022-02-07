<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
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

if (tipologia != 3) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Dettagli Ordine</title>
</head>
<body>
	<form action="creazioneOrdine" method="post">
		<%
		OrdineBean ordine = (OrdineBean) session.getAttribute("ordine");
		ClienteBean cliente = (ClienteBean) session.getAttribute("cliente");
		%>
	</form>

	<div>
		<h2>
			<b>Dettagli Ordine</b>
		</h2>
		<br>
		<div>
			<h4>
				<b>ID Ordine</b>
			</h4>
			<label><%=ordine.getID()%></label>
		</div>
		<div>
			<h4>
				<b>Destinatario</b>
			</h4>
			<label><b>Cognome:</b><%=cliente.getCognome()%>, <b>Nome:</b>
				<%=cliente.getNome()%></label>
		</div>
		<div>
			<h4>
				<b>Indirizzo</b>
			</h4>
			<label><b>Via:</b><%=cliente.getVia()%>, <b>N:</b> <%=cliente.getNumeroCivico()%>.</label><br>
			<label><b>Citta:</b> <%=cliente.getCitta()%>, (<%=cliente.getProvincia()%>,<b>
					CAP:</b> <%=cliente.getCAP()%>) </label><br> <br> <br>
			<hr>
			<br>
			<div>
				<form action="confermaOrdine" method="post">
					<label><b>Codice Tracking:</b></label><input type="text"
						name="Tracking"><br> <br> <input type="submit"
						value="Conferma "> <a href="elencoordini.jsp"><button>Annulla</button></a>
				</form>
			</div>
		</div>

	</div>
</body>
</html>