<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

if (tipologia != 1 && tipologia != 4) {
%>
<meta http-equiv="refresh" content="0; URL='paginainiziale.jsp'" />
<%
return;
}
%>
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<div>
			<h3>Modifica Articolo</h3>
			<br> <br>
		</div>
		<div>
			<form action="ModificaArticolo" method="post">
				<div>
					<label><b>Nome</b></label> <br> <input type="text" name="nome"
						required> <br> <br> <label><b>Descrizione</b></label>
					<br> <input type="text" name="descrizione"
						style="width: 300px; height: 200px;"> <br> <br>
					<label><b>Quantità</b></label> <br> <input type="number"
						name="quantita" required> <br> <br> <label><b>Prezzo</b></label>
					<br> <input type="number" name="prezzo" required> <br>
					<br> <label><b>Foto</b></label> <br> <input type="file"
						name="foto" accept="image/png, image/jpeg">> <br> <br>
					<label><b>Rimborsabile</b></label> <br> <label><b>Si</b></label>
					<input type="radio" name="rimborsabile" value="1"> <br>
					<label><b>No</b></label> <input type="radio" name="rimborsabile"
						value="0">
				</div>
				<div align="right">
					<a href="areaVenditori.jsp"><button>Annulla</button></a> <input
						type="submit" value="Continua">
				</div>

			</form>
		</div>
	</div>

</body>
</html>