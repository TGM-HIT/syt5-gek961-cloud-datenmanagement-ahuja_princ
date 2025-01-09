# EK961 Middleware Engineering: "Cloud-Datenmanagement"

Ankush Ahuja 5DHIT, Gioia Frolik 5BHIT

## Einführung
Diese Übung demonstriert die Anwendung von verteilten Webservices anhand einer einfachen Benutzeranmeldung.

### Ziele
Eine Webanbindung zur Benutzeranmeldung umzusetzen. Benutzer sollen sich registrieren und am System anmelden können. Die Kommunikation zwischen Client und Service erfolgt über eine REST-Schnittstelle.

## Sicherheitsüberlegungen

### Häufige Angriffsvektoren
1. **SQL-Injection**: Unsichere API-Endpunkte oder Abfragen können schadhaften Code ausführen.
2. **Cross-Site Scripting (XSS)**: Schadcode wird in Browsern ausgeführt.
4. **Man-in-the-Middle (MITM) Angriffe**: Daten während der Übertragung abgefangen.
5. **Brute-Force-Angriffe**: Automatisiertes Erraten von Anmeldedaten.
6. **Insecure Deserialization**: Manipulation durch unsichere Datenentpackung.

### Trennung von Registrierung, Login und Datenhaltung
1. **Token-basierte Authentifizierung**:
   - Verwendung von **JWT** oder **OAuth2**.
2. **Datenhaltung**:
   - Sicheres Hashing (z. B. bcrypt).
3. **Role-Based Access Control (RBAC)**:
   - Implementierung von rollenbasierten Zugriffsrichtlinien.

### Absicherung der Eingabe und Übermittlung
1. **Input Validation und Sanitization**:
   - Validierung aller Eingaben (E-Mails, Passwörter etc.).
2. **HTTPS verwenden**:
   - SSL/TLS zur Verschlüsselung.
3. **CSRF-Schutz**:
   - Einsatz von CSRF-Tokens.
4. **Rate Limiting und Captchas**:
   - Schutz vor Brute-Force-Angriffen.
5. **Secure Cookies**:
   - Cookies mit HttpOnly und Secure Flags.

### Verbreitete Services zur Absicherung
- **OAuth 2.0 und OpenID Connect**: Authentifizierung und Autorisierung.
- **JWT**: Verschlüsselte Benutzerinformationen.
- **Rate Limiting mit Redis oder API-Gateways**: Schutz vor Überlastung.
- **ReCaptcha von Google**: Schutz vor Bots.
- **Cloudflare**: Schutz vor DDoS-Angriffen.

---

## Sicherheitsmaßnahmen und Java-Dependencies

### 1. JWT-Token zur Authentifizierung
- **Vorteile**:
  - Stateless, skalierbar, sicher (mit Ablaufzeiten).
- **Sicherheitsmaßnahmen**:
  - Token signiert mit sicherem Schlüssel.
  - Token ausschließlich über HTTPS übertragen.
- **Dependency**:
  ```xml
  <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt</artifactId>
      <version>0.9.1</version>
  </dependency>
  ```

### 2. Schutz vor SQL-Injection
- **Framework**:
  - Verwendung von Hibernate zur sicheren Datenbankabfrage.

### 3. Sicheres Hashing von Passwörtern
- **Methoden**:
  - Hashing mit Salt und Pepper.
  - Algorithmen: bcrypt.

### 6. Fehlermeldungen bei falschen Anmeldeinformationen
- Verallgemeinerte Fehlermeldungen zur Vermeidung von Informationslecks.

### 7. Rate Limiting gegen Brute-Force-Angriffe
- **Dependency**:
  - Bucket4J:
  ```xml
  <dependency>
      <groupId>com.github.vladimir-bukhtoyarov</groupId>
      <artifactId>bucket4j-core</artifactId>
      <version>7.5.0</version>
  </dependency>
  ```
### Quellen
1. JWT: [https://jwt.io/introduction](https://jwt.io/introduction)
2. SQL Injection: [https://www.w3schools.com/sql/sql_injection.asp](https://www.w3schools.com/sql/sql_injection.asp)
3. Cross-Site Scripting: [https://portswigger.net/web-security/cross-site-scripting](https://portswigger.net/web-security/cross-site-scripting)
4. Microservices for Authentication: [https://frontegg.com/blog/authentication-in-microservices](https://frontegg.com/blog/authentication-in-microservices)
5. RBAC: [https://www.redhat.com/en/topics/security/what-is-role-based-access-control](https://www.redhat.com/en/topics/security/what-is-role-based-access-control)
6. Password Hashing: [https://nordpass.com/blog/password-hash/](https://nordpass.com/blog/password-hash/)
