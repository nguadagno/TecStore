<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (session.getAttribute("tipologia") == null
		|| session.getAttribute("tipologia").toString().isEmpty()) {
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
<title>Elenco Rimborsi</title>
</head>
<body>
<body>
	<div align=center>
		<form action="ElencoRimborsiMagazziniere" method="post">
			<select name="limit">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <input type="submit">
		</form>
		<%
		ArrayList<OrdineBean> elenco = (ArrayList<OrdineBean>) session.getAttribute("elenco");

		if (elenco == null || elenco.size() == 0) {
		%>
		<h3>Nessun rimborso in attesa di conferma</h3>
		<%
		return;
		} else {
		%>
		<table>
			<tr>
				<th>Data creazione ordine</th>
				<th>Dettagli</th>
			</tr>
			<%
			for (OrdineBean a : elenco) {
			%>
			<tr>
				<td><%=a.getData().toString()%></td>
				<td>
					<form action="DettagliOrdine" method="post">
						<input type="hidden" name="operazione" value="rimborso">
						<input type="hidden" name="IDOrdine" value="<%=a.getID()%>">
						<input type="submit" value="Dettagli">
					</form>
				</td>
				<%
				}
				}
				%>
			
		</table>
	</div>
</body>
</body>
</html>