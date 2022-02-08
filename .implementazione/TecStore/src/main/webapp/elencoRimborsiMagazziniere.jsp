<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>&euro;&euro;
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco Rimborsi</title>
</head>
<body>
<body>


	<form action="VisualizzaElencoRimborsiMagazziniere" method="post">

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

	<table>
		<tr>
			<th>Id:</th>
			<th>Quantita:</th>
			<th>Descizione:</th>
			<th></th>
		</tr>

		<tr>
			<td><%=a.getID()%></td>
			<td><%=a.getQuantita()%></td>
			<td><%=a.getDescrizione()%></td>
			<td>

				<form action="ElaborazioneOrdine" method="post">
					<input type="submit" value="Dettagli"> <input type="hidden"
						name="IDOrdine" value="<%=a.getID()%>">
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