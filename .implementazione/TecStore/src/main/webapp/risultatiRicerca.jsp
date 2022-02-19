<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,bean.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ricerca Articolo</title>
</head>
<body>
	<div align="center">
		<h3>Ricerca Articolo</h3>
		<form action="ricercaArticolo" method="post">
			<input type="text" name="testo" id="risultatiRicerca-testoRicerca"
				maxlength="25" required> <input type="submit" value="Cerca"
				id="risultatiRicerca-Ricerca">
		</form>
	</div>

	<%
	ArrayList<ArticoloBean> risultati = (ArrayList<ArticoloBean>) session.getAttribute("risultati");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");

	if (risultati == null || foto == null || risultati.size() == 0) {
	%>
	<h3 id="esito">Nessun Articolo Trovato!</h3>
	<%
	return;
	} else {
	%><table>
		<tr>
			<th></th>
			<th>Nome</th>
			<th>Quantita</th>
			<th>Prezzo</th>
			<th></th>
		</tr>
		<%
		for (ArticoloBean a : risultati) {
		%>
		<tr>
			<%
			for (FotoBean f : foto) {
				if (f.getIDArticolo().equals(a.getID())) {
			%>
			<td><img style="max-width: 50px;" src="img?id=<%=f.getID()%>"></td>
			<%
			break;
			}
			}
			%>
			<td><%=a.getNome()%></td>
			<td><%=a.getQuantita() > 0 ? a.getQuantita() : "Non Disponibile"%></td>
			<td><%=a.getPrezzo()%>&euro;</td>
			<td>
				<form action="DettagliArticolo" method="post">
					<input type="hidden" name="IDArticolo" value="<%=a.getID()%>">
					<input type="submit" value="Dettagli"
						id="risultatiRicerca-dettagliArticolo">
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