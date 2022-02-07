<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*, Bean.* , control.*, model.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
int tipologia = -1;
if (request.getSession().getAttribute("tipologia") == null
		|| request.getSession().getAttribute("tipologia").toString().isEmpty()) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}

tipologia = Integer.parseInt(request.getSession().getAttribute("tipologia").toString());

if (tipologia != 1) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Carrello</title>
</head>
<body>

	<form action="GetCarrello" method="post">
		<%
		ArrayList<ArticoloBean> carrello = (ArrayList<ArticoloBean> ) session.getAttribute("carrello");
		FotoBean foto = (FotoBean) session.getAttribute("foto");
		
		%>

	</form>
	<%
	float totale=0;
		if (carrello == null || foto==null) {
		%>
	<h3>Il Carrello é Vuoto!</h3>
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
	</div>
	<form action="confermaOrdine" method="post">
		<input type="submit" value="Acquista!"><input type="hidden"
			value="<%=session.getAttribute("CF")%>" name="CF">
	</form>

</body>
</html>