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
<title>Elenco Ordini</title>
</head>
<body>
<body>
	<form action="RicercaArticolo" method="post">
		<%
		ArrayList<ArticoloBean> ordini = (ArrayList<ArticoloBean>) session.getAttribute("elenco");
		%>
	</form>
	<%
	if (ordini == null) {
	%>
	<h3>Nessun Ordine in attesa</h3>
	<%
	return;
	} else {
	for (ArticoloBean a : ordini) {
	%>
	<div>
		<form action="DettagliOrdine" method="post">
			<label>Id Ordine:<%=a.getID()%></label> <input type="text"
				name="IDOrdine" value="<%=a.getID()%>"> <input type="submit"
				value="Dettagli">
		</form>
	</div>
	<%
	}
	}
	%>

</body>
</body>
</html>