<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,bean.*" session="true"%>
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

if (tipologia != 3) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Elenco Ordini</title>
</head>
<body>
<body>
	<div align=center>
		<form action="ElencoOrdiniMagazziniere" method="post">
			<select name="limit" id="elencoOrdiniMagaziniere-ricercaLimit">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <input type="submit" id="elencoOrdiniMagaziniere-ricerca">
		</form>
		<%
		ArrayList<OrdineBean> elenco = (ArrayList<OrdineBean>) session.getAttribute("elenco");

		if (elenco == null || elenco.size() == 0) {
		%>
		<h3>Nessun ordine in attesa di spedizione</h3>
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
						<input type="hidden" name="operazione" value="spedizione">
						<input type="hidden" name="IDOrdine" value="<%=a.getID()%>">
						<input type="submit" value="Dettagli"
							id="elencoOrdiniMagaziniere-dettagli">
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