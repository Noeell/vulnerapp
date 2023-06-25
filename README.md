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
Mit BasicAuth werden die User Authentifiziert und das Passwort wird sicher in der DB gespeichert.
Der Input wird validiert (auch mit passenden Annotationen wie @Email).
Auch passende Unit-Tests sind vorhanden.

## Was könnte man noch machen?

Zusätzlich könnte man noch ein Rate Limiting einbauen, um sich vor Brute-Force Angriffen zu schützen.
Auch die Aktivierung von HTTPS oder eine Content Security Policy wäre sinnvoll.
Für diese Features fehlte mir jedoch die Zeit.

## Schwierigkeiten und Probleme

Am Anfang hatte ich Schwierigkeiten, mit dem Projekt klarzukommen.
Ich musste mir zuerst einmal einen Überblick über den ursprünglichen Zustand verschaffen.
Nach und nach ging es dann immer besser.
Ein paar mal hatte ich mit kleinen, mühsamen Fehlern zu kämpfen, die hier sehr schnell dazu führen können, dass die
Applikaiton nicht mehr läuft.
Als Beispiel kann ich vom Einbauen der CSRF Protection erzählen: Hier war das Problem, dass ich trotz richtigen Headers
und Cookies keine Blogs veröffentlichen konnte.
Mit der Hilfe von Debuggen und Herr von Känel konnte ich das Problem dann lösen.

## Wieso funktioniert die CSRF-Protection?

Indem das CSRF-Token mitgesendet und überprüft wird, stellt diese Implementierung sicher, dass nur legitime Anfragen,
die das gültige CSRF-Token enthalten, akzeptiert werden, und schützt so vor CSRF-Angriffen.

### Backend

Es wird ein CsrfTokenRequestAttributeHandler erstellt, der dafür verantwortlich ist, das CSRF-Token auf einem bestimmten
Attribut im Request zu platzieren (File: BasicConfiguration.java).
Das CSRF-Token wird durch das CookieCsrfTokenRepository mit der Option "HttpOnly" als Token-Repository festgelegt.

### Frontend

Bei einem POST-Request an den "/api/blog"-Endpunkt wird das CSRF-Token zusammen mit dem Request gesendet. Das CSRF-Token
wird aus dem XSRF-TOKEN-Cookie extrahiert und im Header "X-XSRF-TOKEN" mitgeschickt. Dadurch kann das Backend das
CSRF-Token mit dem im Cookie gespeicherten Wert vergleichen und die Anfrage akzeptieren oder ablehnen (File: script.js). 

