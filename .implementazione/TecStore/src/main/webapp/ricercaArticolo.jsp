<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Negozio</title>
</head>
<body>

	<div align="center">
		<h3>Ricerca Articolo</h3>
		<form action="ricercaArticolo" method="post">
			<input id="markBar" type="text" name="testo" maxlength="35"
				id="testo" placeholder="Titolo..." required> <input
				type="submit" value="Cerca">
		</form>
	</div>
	<br>
	<br>
	<hr>
	<form action="ricercaArticolo" method="post">

		<%
		ArrayList<ArticoloBean> risultati = (ArrayList<ArticoloBean>) session.getAttribute("risultati");
		ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");
		%>

		<%
		if (risultati == null || foto == null) {
		%>
		<h3>Nessun Articolo Trovato!</h3>
		<%
		return;
		} else {
		for (ArticoloBean a : risultati) {
		
		%>

		<th>
			<div class="bigger">
			<!-- 
				<div>
					<img src="<%=//show foto%>" alt="Immagine non disponibile">
				</div>
 			-->
				<div class="little">
					<h2><%=a.getNome()%></h2>
					<span class="hiddenContent" style="display: none">
						<h6>Descrizione:<%=a.getDescrizione() %> </h6><br>
						<h6>Prezzo:</h6> <%=a.getPrezzo()%> &euro;<br> <br> <br>
					</span>
				</div>
			</div>
		</th>
		<%
		}
		}
		%>

	</form>
</body>
</html>