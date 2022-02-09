<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Inserimento Carta Di credito</title>
</head>
<body>
	<h2>Ciao, per continuare l'acquisto inserisci qui i dati della tua
		carta!</h2>
	<h4>Non preoccuparti, i nostri Data Base sono criptati!</h4>
	<hr>
	<br>
	<br>

	<Form action="inserimentoCartaDiCredito" method="post">
		<Div>
			<p>
				<Label><b> Nome: <Input type="text" name="nome">
						Cognome: <Input type="text" name="cognome"></b> </Label>
			</p>
		</Div>
		<hr>
		<br> <br>
		<Div>
			<p>
				<Label><b> Numero Carta: <input type="tel" size="35"
						inputmode="numeric" pattern="[0-9\s]{13,19}"
						autocomplete="cc-number" maxlength="19"
						placeholder="xxxx xxxx xxxx xxxx" name="numeroCarta"><br>
						<br> Data di Scadenza: <Input type="month" id="cardNumber"
						name="Data"><br> <br> cvv: <input type="number"
						maxlength="3" name="cvv"></b> </Label>
			</p>
		</Div>
		<div align="right">
			<input type="submit" value="Conferma">
		</div>
	</Form>
</body>
</html>