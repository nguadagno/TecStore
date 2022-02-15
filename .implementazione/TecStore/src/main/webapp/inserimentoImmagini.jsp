<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
			<input type="file" name="foto" accept="image/png, image/jpeg">
			<input type="submit" value="Carica foto">
		</form>
		<form action="paginainiziale.jsp">
			<input type=submit value="Torna alla pagina iniziale">
		</form>
	</div>

</body>
</html>