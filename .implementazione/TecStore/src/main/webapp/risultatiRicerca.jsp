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
	<br>
	<br>
	<br>
	<div align="center">
		<a href="paginainiziale.jsp" id="nuovaVendita-annulla"><button>Indietro</button></a>
	</div>
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
			<td>
				<%
				for (FotoBean f : foto) {
					if (f.getIDArticolo().equals(a.getID())) {
				%> <img style="max-width: 50px;" src="img?id=<%=f.getID()%>"> <%
 break;
 }
 }
 %>
			</td>
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
	<br>
	<br>
	<br>

	<div align="center">
		<a href="paginainiziale.jsp" id="nuovaVendita-annulla"><button>Indietro</button></a>
	</div>

</body>
</body>
</html>