<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,bean.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Elenco Ordini</title>
</head>
<body>
<body>
	<form action="ElencoOrdiniCliente" method="post">
		<p>
			<br> <input type="text" name="nome" maxlength="45"
				placeholder="Cerca..." id="storicoOrdini-testoRicerca" required>
			<select name="limit">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <input type="submit" value="Conferma" id="storicoOrdini-Ricerca">
		</p>
	</form>

	<%
	ArrayList<OrdineBean> ordini = (ArrayList<OrdineBean>) session.getAttribute("ordini");
	ArrayList<ArticoloBean> articoli = (ArrayList<ArticoloBean>) session.getAttribute("articoli");
	ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");

	if (ordini == null || ordini.size() == 0 || articoli == null || articoli.size() == 0 || articoli.size() != ordini.size()
			|| foto == null) {
	%>
	<h3>Nessun Ordine effettuato</h3>
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
			<th>Stato</th>
			<th>Quantita</th>
			<th>Data ordine</th>
			<th></th>
		</tr>
		<%
		for (int i = 0; i < ordini.size(); i++) {
		%>
		<tr>
			<td>
				<%
				for (FotoBean f : foto) {
					if (f.getIDArticolo().equals(ordini.get(i).getIDArticolo())) {
				%> <img style="max-width: 50px;" alt=<%=articoli.get(i).getNome()%>
				src="img?id=<%=f.getID()%>"> <%
 break;
 }
 }
 %>
			</td>
			<td><%=articoli.get(i).getNome()%></td>
			<td><%=ordini.get(i).getStato()%></td>
			<td><%=ordini.get(i).getQuantita()%></td>
			<td><%=ordini.get(i).getData()%></td>
			<td>
				<form action="DettagliOrdine" method="post">
					<input type="hidden" name="IDOrdine"
						value="<%=ordini.get(i).getID()%>"> <input type="submit"
						value="Dettagli" id="storicoOrdini-dettagliOrdine">
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