<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script type=text/javascript defer>
	window.onload = function() {
		document.getElementById("anno").min = new Date().getFullYear();
		document.getElementById("anno").max = new Date().getFullYear() + 15;
		document.getElementById("anno").value = new Date().getFullYear();
		document.getElementById("mese").value = new Date().getMonth();
	}
</script>

<script type=text/javascript defer>
	var checkLuhn = function(numero) {
		var s = 0;
		var doubleDigit = false;
		for (var i = numero.length - 1; i >= 0; i--) {
			var digit = +numero[i];
			if (doubleDigit) {
				digit *= 2;
				if (digit > 9)
					digit -= 9;
			}
			s += digit;
			doubleDigit = !doubleDigit;
		}
		return s % 10 == 0;
	}

	var checkCard = function() {
		numero = document.getElementById("numeroCarta");

		if (!( // checkLuhn(numero) && 
				numero.length == 16) {
			alert("Inserire una carta di credito valida!");
			numero.value = "";
		}
	}
</script>
<meta charset="ISO-8859-1">
<title>Inserimento Carta Di credito</title>
</head>
<body>
	<h2>Per continuare l'acquisto inserisci qui i dati della tua
		carta!</h2>
	<br>
	<br>

	<form name="cartaDiCredito" action="ModificaUtente" method="post">
		<div>
			<p>
				<label><b> Numero Carta: </b> </label> <input type="tel"
					style="width: 200px" inputmode="numeric" id="numeroCarta"
					pattern="[0-9\s]{16}" autocomplete="cc-number" maxlength="16"
					placeholder="xxxx xxxx xxxx xxxx" name="numeroCarta"
					onchange="checkCard()" id="inserimentoCarta-Numero"><br>
				<br> <label> <b> Data di Scadenza: </b>
				</label> <input type="month" id="inserimentoCarta-Data" min=1 max=12
					id="mese" name="mese"> <input type="number" min="" max=""
					step="1" value="" id="anno" name="anno" /><br> <br> CVV:
				<input type="number" id="inserimentoCarta-Cvv" maxlength="3"
					name="CVV">
			</p>
		</div>
		<div align="right">
			<input type="submit" value="Conferma" id="inserimentoCarta-Conferma">
		</div>
	</Form>
</body>
</html>