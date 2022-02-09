<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco Personale</title>
</head>
<body>


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
				<form action="dettagliUtente" method="post">
					<input type="hidden" name="CF" value="<%=a.getCF()%>"> <input
						type="submit" value="Dettagli">
				</form>
			</td>
			<%
			}
			}
			%>
		</tr>
	</table>

</body>
</body>
</html>
