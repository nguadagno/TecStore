<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
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
	<%
	OrdineBean ordine = (OrdineBean) session.getAttribute("ordine");
	ArticoloBean articolo = (ArticoloBean) session.getAttribute("articolo");
	UtenteBean cliente = (UtenteBean) session.getAttribute("cliente");

	if (ordine == null || cliente == null) {
	%>
	<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
	<%
	return;
	}

	if (request.getParameter("operazione") != null) {
	if (request.getParameter("operazione").equals("spedizione")) {
	%>
	<div align=center>
		<h2>
			<b>Dettagli Ordine</b>
		</h2>
		<br>
		<div>
			<h4>
				<b>Nome articolo:</b>
			</h4>
			<label><%=articolo.getNome()%></label>
		</div>
		<div>
			<h4>
				<b>Dati cliente</b>
			</h4>
			<label><b>Cognome:</b><%=cliente.getCognome()%>, <b>Nome:</b>
				<%=cliente.getNome()%></label>
		</div>
		<div>
			<h4>
				<b>Indirizzo</b>
			</h4>
			<label><b>Via:</b><%=cliente.getVia()%>, <b>N:</b> <%=cliente.getNumeroCivico()%>.</label><br>
			<label><b>Citta:</b> <%=cliente.getCitta()%>, <%=cliente.getProvincia()%>,<b>
					CAP:</b> <%=cliente.getCAP()%> </label><br> <br> <br>
			<hr>
			<br>
			<div>
				<form action="ConfermaOrdine" method="post">
					<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
					<input type=hidden name=stato value="Rimborsato"><input
						type="submit" value="Conferma rimborso">
				</form>
				<form action="ConfermaOrdine" method="post">
					<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
					<input type=hidden name=stato value="RimborsoRifiutato"><input
						type="submit" value="Rifiuta rimborso">
				</form>
			</div>
		</div>
	</div>
	<%
	} else if (request.getParameter("operazione").equals("rimborso")) {
	%>
	<div align=center>
		<h2>
			<b>Dettagli Rimborso</b>
		</h2>
		<br>
		<div>
			<h4>
				<b>Nome articolo:</b>
			</h4>
			<label><%=articolo.getNome()%></label>
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
			<label><b>Citta:</b> <%=cliente.getCitta()%>, <%=cliente.getProvincia()%>,<b>
					CAP:</b> <%=cliente.getCAP()%> </label><br> <br> <br>
			<hr>
			<br>
			<div>
				<form action="RimborsoMagazziniere" method="post">
					<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
					<input type=hidden name=stato value="Rimborsato"><input
						type="submit" value="Conferma rimborso">
				</form>
				<form action="RimborsoMagazziniere" method="post">
					<input type=hidden name=IDOrdine value="<%=ordine.getID()%>">
					<input type=hidden name=stato value="RimborsoRifiutato"> <input
						type="submit" value="Rifiuta rimborso">
				</form>
			</div>
		</div>

	</div>
	<%
	}
	}
	%>
</body>
</html>