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
    * Ressourcen (durch Uniform Resource Identifier identifizierbar) werden über die API-Schnittstellen bereitgestellt
    * HTTP-Methoden um die CRUD-Methoden abzubilden: siehe 2. Frage
    * HTTP-Statuscodes[3]: **1xx Information responses**, **2xx successful responses**, **3xx Redirection messages**, **4xx Client error**, **5xx Server error**
    * Stateless communication: Der Server speichert keinen Zustand der Client-Anfrage.
2. Wie stehen diese mit den HTTP-Befehlen in Verbindung?
   * **GET**: Ruft Daten einer Ressource ab, ohne diese zu verändern. *Lesezugriff* 
   * **POST**: Neue Ressourcen am Server anlegen, mehrere Anfragen können zu unterschiedlichen Ergebnissen führen. *Schreibzugriff*
   * **PUT**: Aktualisiert oder erstellt eine Ressource. Mehrfache Anfragen liefern das selbe Ergebnis. *Update*
   * **PATCH**: Aktualisiert eine Ressource teilweise.
   * **DELETE**: Löscht eine Ressource vom Server.
3. Welche Datenbasis bietet sich für einen solchen Use-Case an?
   * Eine relationale Datenbank wie *PostgreSQL* oder *MySQL*, da diese die ACID-Eigenschaften besitzen.
   * NoSQL-Datenbanken, wie MongoDB, um in spezifischen Fällen flexibilität in der Datenstruktur bieten zu können.
4. Welche Erfordernisse bezüglich der Datenbasis sollten hier bedacht werden?
   * Datenintegrität
   * Wiederherstellbarkeit (Backup)
   * Sicherheit
5. Verschiedene Frameworks bieten schnelle Umsetzungsmöglichkeiten, welche Eckpunkte müssen jedoch bei einer öffentlichen Bereitstellung (Production) von solchen Services beachtet werden?
   * Skalierbarkeit
   * Zugriffssicherheit: Authentifizierung wie z.B. OAuth oder JWT 
   * Security: Eingabevalidierung, Rate-Limiting, CORS
   * Monitoring / Logging

## Durchführung

### Java Dokumentation

### RFC Spezifikationen
#### Registrierung
Für die Registrierung wird HTTP **PUT** verwendet, weil das Erstellen einer neuen Ressource (Account) immer zum selben Ergebnis führt, auch ***idempotent***[c] gennant (wird angelegt oder existiert schon)[4].

#### Login
Für den Login wird HTTP **POST** verwendet, weil die selbe Anfrage zu unterschiedlichen Ereignissen (Auth. Tokens oder Sessions) führt[4].
Dieser Prozess ist daher auch ***nicht idempotent***[c].

#### Authentication
Um die Berechtigung zu überprüfen wird **GET** verwendet, da am Server die Berechtigung angefragt wird und daher nur ein Lesezugriff erfolgt[4].

### Deployment
Das Projekt (Datenbank Postgres & Springboot Application) wird über docker-compose deployed. Hierfür wird das *postgres:latest* und das *jelastic/maven:3.9.9-temurinjdk-21.0.2-almalinux-9* Image.

Notiz: Für das deployment des Springboot Programmes wurden wir von [Melissa Wallpach](https://github.com/melli736) und [Markus Stuppnig](https://github.com/Markus-Stuppnig) unterstützt.

Das [docker-compose.yml](/deployment/docker-compose.yml) File enthält die dafür notwendigen Konfigurationsschritte, wie DB Password oder notwendige Ports.

Nach dem deployen ist der `PUT` Request an `/init` zu schicken, dies wird einen Basis Datensatz aus der JSON in resources/user.json in die DB laden.

#### 1. Fehler
Zuerst wurde nur die JAR erstellt, jedoch hat der Server dann ein Exit mit Code 0 gehabt, weil das JAR-File nicht ausgeführt wurde. [b]

**Lösung**: Nach build in */target* wechseln und jar ausführen ```java -jar [filename].jar```

#### 2. Fehler
Springboot Applikation in eigenem Container konnte keine Verbindung zur Postgres DB herstellen.

**Lösung**: Statt *localhost* oder der IP des Containers verwendet man den Service Namen, welcher im docker-compose File deklariert wird: ```spring.datasource.url=jdbc:postgresql://db:5432/authentication``` In diesem Fall heißt der Service ***db*** und die database ***authentication***.

## Quellen
[1], *Red Hat*, **Red Hat Redaktion**, 10.10.2024, [Was ist eine REST-API? – Red Hat](https://www.redhat.com/de/topics/api/what-is-a-rest-api)

[2], *Computerwoche*, **Holger Reibold**, 10.10.2024, [HTTP-Grundlagen - Computerwoche](https://www.computerwoche.de/article/2852726/hypertext-transfer-protocol.html)

[3], *Developers Mozilla*, **MDN contributors**, 10.10.2024, [HTTP response status code](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status)

[4], *IETF*, **Henrik Nielsen , Jeffrey Mogul , Larry M Masinter , Roy T. Fielding , Jim Gettys , Paul J. Leach , Tim Berners-Lee**, 10.10.2024, [RFC 2616 HTTP v1.1](https://datatracker.ietf.org/doc/html/rfc2616#section-9.5)

## Prompt-Verzeichnis
[a] ChatGPT, *Model-4o*, "Schreibe ein init.sql für die Table mit folgenden Attributen in postgres: Diese soll mit einem Namen, einer eMail-Adresse als BenutzerID, einer Liste an Rollen (ADMIN, READER, MODERATOR) und einem Passwort erfolgen"

[b] Phind, *Fast-model*, "Mein Container für springboot stoppt automatisch nach dem maven build und führt die jar nicht aus: spring-app ...Fehlermeldung"

[c] ChatGPT, *Model-4o*, "Erkläre anhand von RFC 2616 warum beim Registrieren an einer REST Schnittstelle eine PUT Methode verwendet wird und beim Login eine POST"
