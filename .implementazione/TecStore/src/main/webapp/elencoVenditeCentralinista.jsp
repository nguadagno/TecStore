<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>&euro;&euro;
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Gestione Vendite</title>
</head>
<body>
<body>


	<form action="ElencoVenditeCentralinista" method="post">

		<%
		ArrayList<ArticoloBean> risultati = (ArrayList<ArticoloBean>) session.getAttribute("elenco");
		%>

		<%
		if (risultati == null) {
		%>
		<h3>Nessun Articolo in attesa</h3>
		<%
		return;
		} else {
		for (ArticoloBean a : risultati) {
		%>
		<!-- da rivedere le servlet
 			-->
		<%
		}
		}
		%>

	</form>
</body>
</body>
</html>