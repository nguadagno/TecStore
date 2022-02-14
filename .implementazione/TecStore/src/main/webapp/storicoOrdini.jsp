<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Elenco Ordini</title>
</head>
<body>
<body>
	<form action="ElencoOrdiniCliente" method="post">
		<p>
			<br> <input type="text" name="testo" maxlength="45"
				placeholder="Cerca..." required> <select name="limit">
				<option value="10">10</option>
				<option value="20">20</option>
				<option value="50">50</option>
			</select> <input type="submit" value="Conferma">
		</p>
	</form>

	<%
	ArrayList<OrdineBean> ordini = (ArrayList<OrdineBean>) session.getAttribute("ordini");
	ArrayList<ArticoloBean> articoli = (ArrayList<ArticoloBean>) session.getAttribute("articoli");

	if (ordini == null || ordini.size() == 0 || articoli == null || articoli.size() == 0
			|| articoli.size() != ordini.size()) {
	%>
	<h3>Nessun Ordine effettuato</h3>
	<%
	return;
	} else {
	%><table>
		<tr>
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
			<td><%=articoli.get(i).getNome()%></td>
			<td><%=ordini.get(i).getStato()%></td>
			<td><%=ordini.get(i).getQuantita()%></td>
			<td><%=ordini.get(i).getData()%></td>
			<td>
				<form action="DettagliOrdine" method="post">
					<input type="hidden" name="IDOrdine" value="<%=ordini.get(i).getID()%>">
					<input type="submit" value="Dettagli">
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