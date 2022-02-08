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
	<form action="ElencoRimborsiMagazziniere" method="post">

		<%
		ArrayList<ArticoloBean> rimborsi = (ArrayList<ArticoloBean>) session.getAttribute("elenco");
		%>
	</form>
	<%
	if (rimborsi == null) {
	%>
	<h3>Nessun Rimborso in attesa</h3>
	<%
	return;
	} else {
	for (ArticoloBean a : rimborsi) {
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