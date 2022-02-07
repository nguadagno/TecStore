<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* , control.*"
	session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Negozio</title>
</head>
<body>

	<div align="center">
		<form action="ricercaArticolo" method="post">
			<input type="text" name="testo" maxlength="35" id="testo"
				placeholder="Titolo..." required> <input type="submit"
				value="Cerca">
		</form>
	</div>
	<br>
	<br>
	<hr>


	<tr>
		<%
		ArticoloBean risultato = (ArticoloBean) session.getAttribute("articolo");
		FotoBean foto = (FotoBean) session.getAttribute("foto");
		if (risultato == null || foto == null) {
		%>
		<h3>Articolo non Trovato!</h3>
		<%
		return;
		} else {
	
		%>
		<br>
		<div>
			<!-- 
				<div>
					<img src="<%=//show foto%>" alt="Immagine non disponibile">
				</div>
 			-->
			<div align="center">
				<h3>
					Nome:
					<%=risultato.getNome()%>
					<h3>
						Prezzo:<%=risultato.getPrezzo()%>
					</h3>
			</div>
			<div align="center">
				<h3>
					Quantità:
					<%=risultato.getQuantita()%>
			</div>
			<div align="Right">

				<form action="AggiuntaAlCarrello" method="post">
					<input type="submit" value="Aggiungi al Carrello">
					<input type="hidden" name="IDArticolo"
						value="<%=risultato.getID()%>"> <select name="quantita">
						<option value="10">1</option>
						<option value="20">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
					</select>
				</form>
			</div>
		</div>
		<div>
			<h3>Descrizione</h3>
		</div>
		<div>
			<h4>
				<%=risultato.getDescrizione()%>
			</h4>
		</div>

		<%
		}
		%>
	
</body>
</html>