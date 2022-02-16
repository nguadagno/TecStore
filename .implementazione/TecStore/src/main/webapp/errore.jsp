<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Errore</title>
</head>
<body>

	<div align="center">
		<h2>Errore</h2>
		<br> <br> <br>
		<hr>
		<%
		String errore = session.getAttribute("errore").toString();

		switch (errore) {
		case "AccessoNonAutorizzato":
		%>
		<h4>Accesso Non Autorizzato!</h4>

		<%
		break;

		case "erroeSQL":
		%>
		<h4>Errore DataBase</h4>
		<%
		break;

		case "erroreAggiornamentoQuantitaCarrello":
		%>
		<h4>La quantita inserita non &egrave; valida</h4>
		<%
		break;

		case "erroreAggiuntaCarrello":
		%>
		<h4>Problema con l'aggiunta al carrello</h4>
		<%
		break;

		case "erroreQuantitaAggiuntaCarrello":
		%>
		<h4>La quantita inserita non &egrave; valida</h4>
		<%
		break;

		case "annullamentoOrdine":
		%>
		<h4>L'ordine non &egrave; stato annullato</h4>
		<%
		break;

		case "annullamentoVendita":
		%>
		<h4>La vendita non &egrave; stato annullata</h4>
		<%
		break;

		case "cambiaStato":
		%>
		<h4>Lo stato non &egrave; stato cambiato</h4>
		<%
		break;

		case "chiusuraTicket":
		%>
		<h4>Il ticket non &egrave; stato chiuso</h4>
		<%
		break;

		case "cambiastato_tracking":
		%>
		<h4>Errore inserimento codice tracking</h4>
		<%
		break;

		case "confermaOrdine":
		%>
		<h4>L'ordine non &egrave; stato confermato</h4>
		<%
		break;

		case "creazioneOrdine":
		%>
		<h4>L'ordine non &egrave; andato a buon fine</h4>
		<%
		break;

		case "creazioneTicket":
		%>
		<h4>Ticket non creato, riprova</h4>
		<%
		break;

		case "IDTicketmancante":
		%>
		<h4>Ticket non trovato</h4>
		<%
		break;

		case "erroreInserimentoImmagine":
		%>
		<h4>Problema con l'immagine</h4>
		<%
		break;

		case "inserimentoCampiArticolo":
		%>
		<h4>I campi inseriti sono invalidi</h4>
		<%
		break;

		case "erroreParametriNull":
		%>
		<h4>Articolo non trovato</h4>
		<%
		break;

		case "modificaArticolo":
		%>
		<h4>Articolo non &egrave; modificato</h4>
		<%
		break;

		case "modificaPassword":
		%>
		<h4>Password non &egrave; stata modificata</h4>
		<%
		break;

		case "modificaUtente":
		%>
		<h4>La modifica non &egrave; stata apportata</h4>
		<%
		break;

		case "registrazione":
		%>
		<h4>La registrazione non &egrave; andata a buon fine</h4>
		<%
		break;

		case "richiestaRimborso":
		%>
		<h4>La richiesta non &egrave; stata inoltrata</h4>
		<%
		break;

		case "confermaRimborso":
		%>
		<h4>Il rimborso non ha avuto successo</h4>
		<%
		break;

		case "rimozioneArticolo":
		%>
		<h4>L'ogetto non &egrave; stato rimosso</h4>
		<%
		break;

		case "rimozioneArticoloCarello":
		%>
		<h4>L'articolo non &egrave; stato rimosso correttamente</h4>
		<%
		break;

		case "eliminaUtente":
		%>
		<h4>L'utente non &egrave; stato eliminato correttamente</h4>
		<%
		break;

		case "rispostaTicket":
		%>
		<h4>L'inserimento del messaggio non &egrave; andato a buon fine</h4>
		<%
		break;
		}
		%>
		<a href="index.jsp"><button>Continua</button></a>

	</div>
</body>
</html>