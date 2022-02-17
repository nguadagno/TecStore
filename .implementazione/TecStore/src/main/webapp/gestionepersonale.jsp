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

if (tipologia != 5) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Elenco Personale</title>
</head>
<body>

	<div align=center>
		<%
		ArrayList<UtenteBean> elenco = (ArrayList<UtenteBean>) session.getAttribute("risultati");
		if (elenco == null) {
		%>
		<h3>Nessun Dipendente</h3>
		<%
		return;
		} else {
		%><table>
			<tr>
				<th>Nome</th>
				<th>Cognome</th>
				<th>Tipologia</th>
				<th>Email</th>
				<th>Tipologia</th>
				<th></th>
			</tr>
			<%
			for (UtenteBean a : elenco) {
			%>
			<tr>
				<td><%=a.getNome()%></td>
				<td><%=a.getCognome()%></td>
				<td>
					<%
					if (a.getTipologia() == 1) {
					%>Cliente<%
					}
					%> <%
 if (a.getTipologia() == 2) {
 %>Centralinista<%
 }
 %> <%
 if (a.getTipologia() == 3) {
 %>Magazziniere<%
 }
 %> <%
 if (a.getTipologia() == 4) {
 %>Amministratore Catalogo<%
 }
 %> <%
 if (a.getTipologia() == 5) {
 %>Amministratore Personale<%
 }
 %>
				</td>
				<td><%=a.getEmail()%></td>
				<td>
					<form action="DettagliUtente" method="post">
						<input type="hidden" name="CF" value="<%=a.getCF()%>"> <input
							type="submit" value="Dettagli" id="gestionePersonale-dettagli">
					</form>
				</td>
				<%
				}
				}
				%>
			</tr>
		</table>


	</div>

</body>
</body>
</html>
