INSERT IGNORE INTO `tecstore`.`utente` (`CF`, `Nome`, `Cognome`, `Email`, `Password`, `Via`, `NumeroCivico`, `Citta`, `Provincia`, `CAP`, `Tipologia`) VALUES ('1', 'asd', 'asd', 'asd', 'asd', '1', '1', '1', '1', '1', '1');
INSERT IGNORE INTO `tecstore`.`utente` (`CF`, `Nome`, `Cognome`, `Email`, `Password`, `Via`, `NumeroCivico`, `Citta`, `Provincia`, `CAP`, `Tipologia`) VALUES ('2', 'asd', 'asd', 'asd', 'asd', '1', '1', '1', '1', '2', '1');
INSERT IGNORE INTO `tecstore`.`utente` (`CF`, `Nome`, `Cognome`, `Email`, `Password`, `Via`, `NumeroCivico`, `Citta`, `Provincia`, `CAP`, `Tipologia`) VALUES ('3', 'asd', 'asd', 'asd', 'asd', '1', '1', '1', '1', '3', '1');
INSERT IGNORE INTO `tecstore`.`utente` (`CF`, `Nome`, `Cognome`, `Email`, `Password`, `Via`, `NumeroCivico`, `Citta`, `Provincia`, `CAP`, `Tipologia`) VALUES ('4', 'asd', 'asd', 'asd', 'asd', '1', '1', '1', '1', '4', '1');
INSERT IGNORE INTO `tecstore`.`ticket` (`IDCliente`, `Tipologia`, `Stato`) VALUES ('1', 'asd', 'InAttesa');

SELECT @id :=  IDTicket FROM ticket LIMIT 1;

INSERT IGNORE INTO `tecstore`.`messaggio` (`IDTicket`, `CF`, `Contenuto`, `Data`) VALUES (@id, '1', 'aaaa', CURRENT_TIMESTAMP);
INSERT IGNORE INTO `tecstore`.`messaggio` (`IDTicket`, `CF`, `Contenuto`, `Data`) VALUES (@id, '2', 'bbbb', CURRENT_TIMESTAMP+5);
INSERT IGNORE INTO `tecstore`.`messaggio` (`IDTicket`, `CF`, `Contenuto`, `Data`) VALUES (@id, '1', 'cccc', CURRENT_TIMESTAMP+10);
