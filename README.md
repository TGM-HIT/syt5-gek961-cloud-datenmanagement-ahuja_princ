# GK961 Middleware Engineering "Cloud Data-Interface"
**Autoren**: Ankush Ahuja, Benjamin Princ

**Datum**: 10.10.2024

## Einführung
Diese Übung zeigt die Anwendung von verteilten Webservices an einer simplen Anforderung.
Ausarbeitung der [Aufgabe GK961](https://elearning.tgm.ac.at/mod/assign/view.php?id=136679).

### Ziele
Das Ziel dieser Übung ist eine Webanbindung zur Benutzeranmeldung umzusetzen. Dabei soll sich ein Benutzer registrieren und am System anmelden können.
Die Kommunikation zwischen Client und Service soll mit Hilfe einer REST Schnittstelle umgesetzt werden.

### Aufgabenstellung
Es ist ein Webservice zu implementieren, welches eine einfache Benutzerverwaltung implementiert. Dabei soll die Webapplikation mit den Endpunkten /auth/admin/register, /auth/signin und /auth/verify erreichbar sein.

### Fragestellungen
* Welche grundlegenden Elemente müssen bei einer REST Schnittstelle zur Verfügung gestellt werden?
* Wie stehen diese mit den HTTP-Befehlen in Verbindung?
* Welche Datenbasis bietet sich für einen solchen Use-Case an?
* Welche Erfordernisse bezüglich der Datenbasis sollten hier bedacht werden?
* Verschiedene Frameworks bieten schnelle Umsetzungsmöglichkeiten, welche Eckpunkte müssen jedoch bei einer öffentlichen Bereitstellung (Production) von solchen Services beachtet werden?


## Quellen
[1], *Red Hat*, 10.10.2024, [Was ist eine REST-API? – Red Hat](https://www.redhat.com/de/topics/api/what-is-a-rest-api)

## Prompt-Verzeichnis
[1] ChatGPT, *Model-4o*, "Schreibe ein init.sql für die Table mit folgenden Attributen in postgres: Diese soll mit einem Namen, einer eMail-Adresse als BenutzerID, einer Liste an Rollen (ADMIN, READER, MODERATOR) und einem Passwort erfolgen"
