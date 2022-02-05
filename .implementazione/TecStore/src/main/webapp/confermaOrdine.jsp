<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*, Bean.* , control.*"
	session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Conferma Ordine</title>
</head>
<body>

	<form action="getCarrello" method="post">
		<%
		ArrayList<ArticoloBean> carrello = (ArrayList<ArticoloBean> ) session.getAttribute("carrello");
		FotoBean foto = (FotoBean) session.getAttribute("foto");
		float totale=0;
		%>

	</form>
	<%
		if (carrello == null || foto==null) {
		%>
	<h3>Il Carrello � Vuoto!</h3>
	<%
		return;
		} else {
			for (ArticoloBean a : carrello) {
				totale=+a.getPrezzo();
				%>


	<div class="bigger">
		<!-- 
						<div>
							<img src="<%=//show foto%>" alt="Immagine non disponibile">
						</div>
		 			-->
		<div class="little">
			<h2><%=a.getNome()%></h2>


			<h6>Prezzo:</h6>
			<%=a.getPrezzo()%>
			&euro;<br> <br> <br>

		</div>
		<div>
			<form action="rimozioneArticoloCarrello" method="post">
				<input type="submit" value="Rimuovi"> <input
					value="<%=a.getID()%>" type="hidden" name="IDArticolo"><input
					type="hidden" value="<%=session.getAttribute("CF")%>" name="CF">
			</form>
		</div>
	</div>

	<%
	}
	}
	%>
	<div>
		<h4>
			Totale:<%=totale%></h4>

		<form action="confermaOrdine" method="post">
			<input type="submit" value="Acquista!"><input type="hidden"
				value="<%=session.getAttribute("CF")%>" name="CF">
		</form>
	</div>
</body>
</html>