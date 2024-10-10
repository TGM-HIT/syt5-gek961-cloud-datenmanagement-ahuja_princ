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
1. Welche grundlegenden Elemente müssen bei einer REST Schnittstelle zur Verfügung gestellt werden?
    * Ressourcen werden über die API-Schnittstellen bereitgestellt
    * HTTP-Methoden um die CRUD-Operationen abzubilden: siehe 2. Frage
    * HTTP-Statuscodes[3]: **1xx Information responses**, **2xx successful responses**, **3xx Redirection messages**, **4xx Client error**, **5xx Server error**
    * Stateless communication 
2. Wie stehen diese mit den HTTP-Befehlen in Verbindung?
   * **GET**: Ruft Daten einer Ressource ab, ohne diese zu verändern. *Lesezugriff* 
   * **POST**: Neue Ressourcen am Server anlegen. *Schreibzugriff*
   * **PUT**: Aktualisiert und erstellt eine Ressource. *Update*
   * **PATCH**: Aktualisiert eine Ressource teilweise.
   * **DELETE**: Löscht eine Ressource vom Server.
3. Welche Datenbasis bietet sich für einen solchen Use-Case an?
   * Eine relationale Datenbank wie *PostgreSQL* oder *MySQL*, da diese Transaktionssicherheit und Datenintegrität liefern.
4. Welche Erfordernisse bezüglich der Datenbasis sollten hier bedacht werden?
   * Datenintegrität
   * Wiederherstellbarkeit (Backup)
   * Sicherheit
5. Verschiedene Frameworks bieten schnelle Umsetzungsmöglichkeiten, welche Eckpunkte müssen jedoch bei einer öffentlichen Bereitstellung (Production) von solchen Services beachtet werden?
   * Skalierbarkeit
   * Zugriffssicherheit
   * Security
   * Rate-Limiting

## Durchführung

### RFC Spezifikationen
#### Registrierung
Für die Registrierung wird HTTP **POST** verwendet, da diese Methode für "Formulareingaben" an einem Webserver verantwortlich ist[2].

#### Login

#### Authentication

### Deployment
Das Projekt (Datenbank Postgres & Springboot Application) wird über docker-compose deployed. Hierfür wird das *postgres:latest* und das *jelastic/maven:3.9.9-temurinjdk-21.0.2-almalinux-9* Image.

Notiz: Für das deployment des Springboot Programmes wurden wir von Melissa Wallpach und Markus Stuppnig unterstützt.

Das [docker-compose.yml](/deployment/docker-compose.yml) File enthält die dafür notwendigen Konfigurationsschritte, wie DB Password oder notwendige Ports.
## Quellen
[1], *Red Hat*, **Red Hat Redaktion**, 10.10.2024, [Was ist eine REST-API? – Red Hat](https://www.redhat.com/de/topics/api/what-is-a-rest-api)

[2], *Computerwoche*, **Holger Reibold**, 10.10.2024, [HTTP-Grundlagen - Computerwoche](https://www.computerwoche.de/article/2852726/hypertext-transfer-protocol.html)

[3], *Developers Mozilla*, **MDN contributors**, 10.10.2024, [HTTP response status code](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)
## Prompt-Verzeichnis
[a] ChatGPT, *Model-4o*, "Schreibe ein init.sql für die Table mit folgenden Attributen in postgres: Diese soll mit einem Namen, einer eMail-Adresse als BenutzerID, einer Liste an Rollen (ADMIN, READER, MODERATOR) und einem Passwort erfolgen"

[b] Phind, *Fast-model*, "Mein Container für springboot stoppt automatisch nach dem maven build und führt die jar nicht aus: spring-app ...Fehlermeldung"
