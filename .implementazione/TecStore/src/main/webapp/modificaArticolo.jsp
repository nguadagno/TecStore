<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica Articolo</title>
</head>
<body>
	<div align="center">
		<div>
			<h3>Modifica Articolo</h3>
			<br> <br>
		</div>
		<div>
			<form action="modificaArtiolo" method="post">
				<div>
					<input type="hidden" name="IDArticolo"
						value="<%=request.getParameter("IDArticolo")%>"> <label><b>Nome</b></label>
					<br> <input type="text" name="nome"
						value="<%=request.getParameter("nome")%>"> <br> <br>
					<label><b>Descrizione</b></label> <br> <input type="text"
						name="descrizione"
						value="<%=request.getParameter("descrizione")%>"
						style="width: 300px; height: 200px;" required> <br> <br>
					<label><b>Quantità</b></label> <br> <input type="number"
						name="quantita" value="<%=request.getParameter("quantita")%>"
						required> <br> <br> <label><b>Prezzo</b></label>
					<br> <input type="number" name="prezzo"
						value="<%=request.getParameter("prezzo")%>" required> <br>
					<br> <label><b>Foto</b></label> <br> <input type="file"
						name="foto" accept="image/png, image/jpeg"> <br> <br>
					<label><b>Rimborsabile</b></label> <br> <label><b>Si</b></label>
					<input type="radio" name="rimborsabile" value="1"
						<%=request.getParameter("rimborsabile").equals("true") ? "required" : ""%>>
					<br> <label><b>No</b></label> <input type="radio"
						name="rimborsabile" value="0"
						<%=request.getParameter("rimborsabile").equals("false") ? "required" : ""%>>
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