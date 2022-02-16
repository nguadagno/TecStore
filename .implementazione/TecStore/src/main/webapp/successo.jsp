<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Successo</title>
</head>
<body>
	<div align="center">
		<!-- TODO redirect a pagina corretta e messaggio corretto in base all'operazione -->
		<div align="center">
			<h2>Operazione avvenuta con successo</h2>
			<br> <br> <br>
			<hr>
			<%
			String successo = session.getAttribute("successo").toString();

			switch (successo) {
			case "aggiuntaCarrello":
			%>
			<h4>L'articolo &egrave; stato aggiunto al carrello!</h4>

			<%
			break;
			case "annullamentoOrdine":
			%>
			<h4>L'articolo &egrave; stato aggiunto al carrello!</h4>
			<%
			break;
			case "annullamentoVendita":
			%>
			<h4>Vendita annullata</h4>
			<%
			break;
			case "autorizzazioneVendita":
			%>
			<h4>Vendita autorizzata</h4>
			<%
			break;
			case "chiusuraTicket":
			%>
			<h4>Il ticket &egreve; stato chiuso</h4>
			<%
			break;
			case "confermaOrdine":
			%>
			<h4>Lo stato dell'ordine &egreve; stato confermato</h4>
			<%
			break;
			case "creazioneOrdine":
			%>
			<h4>L'ordine &egrave; stato inoltrato, puoi controllare lo stato
				nell'apposita area</h4>
			<%
			break;
			case "creazioneTicket":
			%>
			<h4>Le risponderemo appena possibile</h4>
			<%
			break;
			case "modificaArticolo":
			%>
			<h4>L'articolo &egrave; stato modificato</h4>
			<%
			break;
			case "modificaPassword":
			%>
			<h4>La password &egrave; stata modificata</h4>
			<%
			break;
			case "modificaUtente":
				if (session.getAttribute("tipologia") != null && session.getAttribute("tipologia").toString().equals("5")
				&& session.getAttribute("passwordUtente") != null
				&& !session.getAttribute("passwordUtente").toString().isEmpty()
				&& session.getAttribute("emailUtente") != null
				&& !session.getAttribute("emailUtente").toString().isEmpty()) {
			%>

			<div align=center>
				<h3>Dati dipendente modificati!</h3>
				<h4>Le nuove credenziali di accesso sono:</h4>
				<h5>
					<b>Email:</b> <BR>
					<%=session.getAttribute("emailUtente").toString()%></h5>
				<h5>
					<b>Password:</b><BR><%=session.getAttribute("passwordUtente").toString()%></h5>
			</div>

			<%
			} else {
			%>
			<h4>Il profilo &egreve; stato modificato correttamente!</h4>
			<%
			}
			break;
			case "registrazione":
			%>
			<h4>Registazione effettuata</h4>
			<%
			break;
			case "richiestaRimborso":
			%>
			<h4>La richiesta &egrave; stata inoltrata</h4>
			<%
			break;
			case "confermaRimborso":
			%>
			<h4>Rimborso confermato</h4>
			<%
			break;
			case "rimozioneArticolo":
			%>
			<h4>L'articolo &egrave; stato rimosso</h4>
			<%
			break;
			case "eliminaUtente":
			%>
			<h4>L'utente &egrave; stato eliminato</h4>
			<%
			break;
			case "rispostaTicketCentralinista":
			%>
			<h4>La risposta &egrave; stata inoltrata, speriamo che non rompa
				piu</h4>
			<%
			break;
			case "rispostaTicket":
			%>
			<h4>Il messaggio &egrave; stato inoltrato, le risponderemo al
				pi&ugrave; presto</h4>


			<%
			}
			%>
			<br> <br> <br> <a href="index.jsp"><button>Continua</button></a>



		</div>
	</div>
</body>
</html>