<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dettagli Utente</title>
</head>
<body>
	<%
	UtenteBean a = (UtenteBean) session.getAttribute("utente");
	//UtenteBean a = new UtenteBean("RSSMRA80A01H703F","Mario","Rossi","mariorossi@gmail.com","australopitecoccc","monza",3,"Salerno","SA",84127,5,"ciao");
	if (a == null) {
		return;
	} else {
	%><table>
		<tr>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Codice Fiscale</th>
			<th>Tipologia</th>
			<th>Citta</th>
			<th>Email</th>
			<th></th>
		</tr>
		<td><%=a.getNome()%></td>
		<td><%=a.getCognome()%></td>
		<td><%=a.getCF()%></td>
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
		<td><%=a.getCitta()%></td>
		<td><%=a.getEmail()%></td>
		<td><form action="modificaUtente.jsp" method="post">
				<input type="submit" value="Modifica">
				<%
				session.setAttribute("utente", a);
				%>
			</form></td>
	</table>
	<%
	}
	%>

</body>
</html>