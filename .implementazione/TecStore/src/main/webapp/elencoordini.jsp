<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>&euro;&euro;
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco Ordini</title>
</head>
<body>
<body>


	<form action="ricercaArticolo" method="post">

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
		<form action="dettagliOrdine" method="post">
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