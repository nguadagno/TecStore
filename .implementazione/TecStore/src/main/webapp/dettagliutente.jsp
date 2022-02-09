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
	//UtenteBean utente = (UtenteBean) session.getAttribute("utente");
	int tipologia = -1;
	if (session.getAttribute("tipologia") != null) {
		tipologia = Integer.parseInt(session.getAttribute("tipologia").toString());
	}

	UtenteBean utente = new UtenteBean("RSSMRA80A01H703F", "Mario", "Rossi", "mariorossi@gmail.com", "australopitecoccc",
			"monza", 3, "Salerno", "SA", 84127, 5, "ciao");
	if (utente == null) {
		return;
	} else {
	%><table>
		<tr>
			<th>Nome</th>
			<th>Cognome</th>
			<th>Codice Fiscale</th>
			<%
			if (tipologia == 5) {
			%>
			<th>Tipologia</th>
			<%
			}
			%>
			<th>Citta</th>
			<th>Email</th>
			<th></th>
		</tr>
		<td><%=utente.getNome()%></td>
		<td><%=utente.getCognome()%></td>
		<td><%=utente.getCF()%></td>
		<%
		if (tipologia == 5) {
		%>
		<td>
			<%
			if (utente.getTipologia() == 1) {
			%>Cliente<%
			}
			%> <%
 if (utente.getTipologia() == 2) {
 %>Centralinista<%
 }
 %> <%
 if (utente.getTipologia() == 3) {
 %>Magazziniere<%
 }
 %> <%
 if (utente.getTipologia() == 4) {
 %>Amministratore Catalogo<%
 }
 %> <%
 if (utente.getTipologia() == 5) {
 %>Amministratore Personale<%
 }
 %>
		</td>
		<%
		}
		%>
		<td><%=utente.getCitta()%></td>
		<td><%=utente.getEmail()%></td>
		<td><form action="modificaUtente.jsp" method="post">
				<input type="submit" value="Modifica">
			</form></td>
	</table>
	<%
	}
	%>

</body>
</html>