<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="java.util.*, Bean.* , control.*, model.*" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script type="text/javascript">
	var checkPassword = function() {
		password1 = document.getElementById("ps1");
		password2 = document.getElementById("ps2");

		if (password1.value === password2.value) {
			document.getElementById("f1").submit();
		} else
			alert("Le password non coincidono!");
	}
</script>
<title>Modifica Password</title>
</head>
<body>
	<h2>Modifica Password</h2>
	<form action="ModificaPassword" method="post" id="f1">
		<label for="password">Password</label> <br> <input id="ps1"
			minlength="10" maxlength="64" type="password" name="password"
			required value=""> <br> <label for="confermapassword">Conferma
			password</label> <br> <input minlength="10" maxlength="64" id="ps2"
			type="password" name="confermapassword" value=""><br> <br>

	</form>
	<button onclick="checkPassword()" id="modificaPassword-Conferma">Conferma</button>
	<a href="paginaIniziale.jsp" id="modificaPassword-Indietro"><button>Torna
			Alla Home</button></a>
</body>
</html>