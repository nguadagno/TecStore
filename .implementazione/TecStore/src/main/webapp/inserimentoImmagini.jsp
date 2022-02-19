<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*,bean.*, control.*, model.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inserimento immagini</title>
</head>
<body>
	<div align=center>
		<h3>Utilizza questa pagina per caricare le foto per il tuo
			articolo!</h3>
		<form action=img method=post enctype="multipart/form-data">
			<input type="file" id="inserimentoImmagini-foto" name="foto"
				accept="image/png, image/jpeg"> <input type="submit"
				value="Carica foto" id="inserimentoImmagini-carica">
		</form>
		<form action="DettagliArticolo" method=post>
			<input type=hidden name=IDArticolo
				value=<%=((ArticoloBean) session.getAttribute("dettagliArticolo")).getID()%>>
			<input type=submit value="Dettagli Articolo">
		</form>
		<form action="paginainiziale.jsp">
			<input type=submit value="Torna alla pagina iniziale"
				id="inserimentoImmagini-torna">
		</form>
	</div>

</body>
</html>