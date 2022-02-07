<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* " session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Area Venditore</title>
</head>
<body>

	<div align="center">
		<form action="ricercaVendita" method="post">
			<input type="text" name="nome" maxlength="35" id="testo"
				placeholder="Cerca vendita..." required> <input
				type="submit" value="Cerca">
		</form>
	</div>
	<br>
	<br>
	<hr>
	<div>
		<a href="nuovaVendita.jsp">
			<button>Nuova Vendita</button>
		</a>
	</div>
	<div>
		<%
		ArrayList<ArticoloBean> elencoVendite = (ArrayList<ArticoloBean>) session.getAttribute("risultati");
		ArrayList<FotoBean> foto = (ArrayList<FotoBean>) session.getAttribute("foto");
		%>

		<%
		if (elencoVendite == null || foto == null) {
		return;
		} else {
		for (ArticoloBean a : elencoVendite) {
		
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
						<h6>
							Descrizione:<%=a.getDescrizione()%>
						</h6> <br>
						<h6>Prezzo:</h6> <%=a.getPrezzo()%> &euro;<br> <br> <br>
						<form actione="DettagliArticolo" method="post">
							<input type="hidden" name="IDArticolo" value="<%=a.getID()%>">
							<input type="submit" value="Dettagli Articolo">
						</form>
					</span>
				</div>
			</div>
		</th>
		<%
		}
		}
		%>

		</form>
	</div>

</body>
</html>