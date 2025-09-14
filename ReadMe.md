# Task Manager

## Überblick

Die App ist in zwei Schichten aufgeteilt: ein Frontend (Vue 3 / Vite) für die UI und ein Backend (Spring Boot) für
die Logik und Persistenz.
Die Kommunikation läuft über eine REST-API unter dem Pfad ```/api/tasks```.

---

## Architektur

### Frontend

- Vite + Vue, enthält das UI (Formular zum Anlegen, Tabelle zur Anzeige, Edit/Delete).

### Backend

- Spring Boot Anwendung, stellt die REST-Endpunkte bereit und kapselt Logik in einer Service-Schicht.

### Datenmodel

- Entity Task (siehe ```model/Task.java```) — wichtige Felder:
    * ```id``` (Long, auto-generated)
    * ```title``` (String)
    * ```description``` (String)
    * ```status``` (Enum: ```OPEN```, ```IN_PROGRESS```, ```DONE```)
    * ```createdAt``` (LocalDateTime)

### REST API

Base: ```@RequestMapping("/api/tasks")``` → API unter ```/api/tasks```

Wesentliche Endpunkte (Controller ```TaskController.java```):

- ```GET /api/tasks``` — alle Tasks zurückgeben (200 OK)
- ```GET /api/tasks/{id}``` — einzelnen Task holen (200 OK oder 404 Not Found)
- ```POST /api/tasks``` — neuen Task anlegen → liefert 201 Created + Location-Header /api/tasks/{id}
- ```PUT /api/tasks/{id}``` — Task aktualisieren → liefert aktualisierten Task (200 OK)
- ```DELETE /api/tasks/{id}``` — Task löschen → 204 No Content

### Service- und Repository-Schicht

- Controller nimmt HTTP-Anfragen entgegen und delegiert an die Service-Schicht.
- TaskService (```ITaskService``` implementiert) kapselt Logik und verwendet TaskRepository (Spring Data JPA) für
  DB-Operationen:
    - ```findAll()```, ```findById(id)```, ```save(task)```, ```delete(id)```.

### Docker

- ```docker-compose.yaml``` definiert zwei Services: ```backend``` und ```frontend``` im gemeinsamen Netzwerk
  ```app-network```.
    - Backend: wird aus ```Dockerfile``` gebaut und wird auf Port ```8080``` gehosted
    - Frontend: verwendet Nginx und wird auf Port ```80``` gehosted. nginx proxyt ```/api``` intern an
      ```backend:8080```.

- Die Backend-Dockerfile erwartet das gebaute JAR unter ```target/task-manager-*.jar```. Das Frontend-Dockerfile kopiert
  das gebaute ```dist```.
  (Das heißt: vor dem Erstellen der Images müssen die Artefakte jeweils gebaut werden)

### Request-Flow

1. Nutzer interagiert mit UI (Form, Buttons).
2. Frontend sendet HTTP-Request (z. B. ```POST /api/tasks```) — in Docker an ```http://localhost/``` (nginx) —
   nginx proxyt /api intern an ```backend:8080```.
3. Controller empfängt Anfrage → validiert/transformiert und ruft ```TaskService``` auf.
4. ```TaskService``` nutzt ```TaskRepository``` (JPA) zum Lesen/Schreiben in-memory.
5. Ergebnis (z. B. gespeicherter Task) wird über Controller an das Frontend zurückgegeben und dort gerendert.

---

## Projekt Starten

### Vorraussetzungen

- maven 3.9.8
- Java 17 jdk
- Node.js 22

### Start mit Docker

```
mvn verify
cd frontend
npm run build
cd ..
docker-compose up
```

Das Projekt ist nun unter [localhost](http://localhost) erreichbar.
