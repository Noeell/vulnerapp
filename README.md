# Vulnerapp

- [x] Verwendung von korrekten REST-Verben.
- [x] Implementierung einer Authentifizierungslösung (z.B. BasicAuth).
- [x] Enforce RBAC (z.B. User- und Admin-Services unterscheiden).
- [x] Aktivieren von CSRF-Protection und Erklärung, warum diese Implementation funktioniert.
- [x] Implementierung einer sicheren Passwort-Speicherung (Hashing, Passwortregeln).
- [x] Strikte Inputvalidierung (für REST-Endpunkte und Datenbank).
- [x] Behebung der initialen Sicherheitslücken (SQLi, XSS, CSRF).
- [x] Implementierung von securityrelevanten (Unit-)Tests.

## Was wurde umgesetzt?

Ich habe alle Anforderungen erfüllt und somit Sicherheitslücken geschlossen.
Die Authentifizierung der Benutzer erfolgt über BasicAuth, wobei das Passwort sicher in der Datenbank gespeichert wird.
Der Input wird validiert (auch mit passenden Annotationen wie `@Email`).
Auch nützliche Unit-Tests sind vorhanden (BasicAuth / verschiedene Pfade).
Gegen SQL-Injection wurde Schutz implementiert, wobei Spring Data JPA bereits einen Grossteil abdeckt.
Das RBAC ist eingebaut, jedoch konnte ich es trotz vielen verschiedenen Konfigurationen nicht erfolgreich zum Laufen
bringen.

## Was könnte man noch machen?

Zusätzlich könnte man noch ein Rate Limiting einbauen, um sich vor Brute-Force Angriffen zu schützen.
Auch die Aktivierung von HTTPS oder eine Content Security Policy wäre sinnvoll.
Für diese Features fehlte mir jedoch die Zeit / brauchte ich zu viel Zeit um Probleme zu lösen.

## Schwierigkeiten und Probleme

Am Anfang hatte ich Schwierigkeiten, mich in das Projekt einzuarbeiten.
Zunächst musste ich einen Überblick über den ursprünglichen Zustand gewinnen.
Nach und nach wurde es jedoch besser.
Es gab einige Male, bei denen ich mit kleinen, zeitaufwendigen Fehlern zu kämpfen hatte, die dazu führten, dass die
Anwendung nicht mehr funktionierte.
Ein Beispiel dafür war die Implementierung des CSRF-Schutzes: Hier konnte ich trotz korrekter Header und Cookies keine
Blogs veröffentlichen. Das Problem konnte jedoch gelöst werden.
RBAC bereitete mir die grössten Schwierigkeiten. Entweder wurden keine Daten zurückgegeben oder alle, unabhängig davon,
ob es sich um einen ADMIN oder USER handelte.
Auch mit der Hilfe von Debuggen und Herr von Känel konnte ich das Problem nicht lösen. Sobald ich mit `@Secured` oder
`@PreAuthorize` arbeite, lässt das Backend nichts mehr durch, egal ob ADMIN oder USER.
Dies war teilweise frustrierend, da man immer wieder ein negatives Erlebnis hat und lange Zeit nicht weiterkommt.

## Warum verbessern die Sicherheitsmechanismen die Applikation?

- Die Verwendung korrekter REST-Verben trägt zur Einhaltung der RESTful-Designprinzipien bei, um Konsistenz und
  Sicherheit der API zu verbessern.
- Basic Authentication trägt dazu bei, dass nur autorisierte Benutzer auf geschützte Ressourcen zugreifen können. Durch
  die Überprüfung von Benutzername und Passwort wird sichergestellt, dass nur authentifizierte Benutzer Zugriff
  erhalten.
- Die Implementierung von RBAC ermöglicht eine granulare Steuerung des Zugriffs auf Ressourcen basierend auf den
  Benutzerrollen. So kann ich zwischen ADMIN und USER Services unterscheiden.
- CSRF Schutz ist wichtig, um Angriffe zu verhindern, bei denen ein Angreifer Aktionen im Namen eines authentifizierten
  Benutzers ausführt, indem er eine manipulierte Anfrage einschleust.
- Die sichere Passwort-Speicherung trägt dazu bei, dass die Passwörter auf der DB verschlüsselt sind und so zur
  Sicherheit beizutragen.
- Eine strikte Inputvalidierung ist wichtig, um Sicherheitslücken wie SQL-Injection und XSS zu verhindern.

## Wieso funktioniert die CSRF-Protection?

Indem das CSRF-Token mitgesendet und überprüft wird, stellt diese Implementierung sicher, dass nur legitime Anfragen,
die das gültige CSRF-Token enthalten, akzeptiert werden, und schützt so vor CSRF-Angriffen.

### Backend

Im Backend wird ein `CsrfTokenRequestAttributeHandler` erstellt, der dafür zuständig ist, das CSRF-Token in einem
bestimmten Attribut der Anfrage zu platzieren.
Das CSRF-Token wird mithilfe des `CookieCsrfTokenRepository` als Token-Repository festgelegt. Dabei wird die Option "
HttpOnly" verwendet, um das Cookie vor clientseitigem Zugriff zu schützen.
Dieser Vorgang erfolgt in der Datei `BasicConfiguration.java`.

### Frontend

Im Frontend wird beim Senden eines POST-Requests an den Endpunkt "/api/blog" das CSRF-Token zusammen mit der Anfrage
übermittelt. Das CSRF-Token wird aus dem XSRF-TOKEN-Cookie extrahiert und im Header "X-XSRF-TOKEN" mitgeschickt. Dadurch
kann das Backend das CSRF-Token mit dem im Cookie gespeicherten Wert vergleichen und die Anfrage akzeptieren oder
ablehnen. Dieser Vorgang erfolgt in der Datei `script.js`.

## `Note: 5.2`