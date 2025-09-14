# Task Manager

Vorraussetzungen:
maven 3.9.8
Java 17 jdk
Node.js 22


mvn verify
cd frontend
npm run build
cd ..
docker-compose up


## Überblick
Der Task Manager ist eine Anwendung zur Verwaltung von Aufgaben. Die Anwendung besteht aus einem **Frontend** (Vite + Vue.js) für die Benutzeroberfläche und einem **Backend** (Spring Boot, Java) für die Datenspeicherung.

Die Architektur folgt einem **klassischen Client-Server-Modell** mit klarer Trennung von Zuständigkeiten.

---

## Architektur

### Frontend
- Implementiert mit **Vite** und **Vue.js**
- Zuständig für Darstellung und Interaktion mit dem Benutzer
- Kommuniziert ausschließlich über **REST-APIs** mit dem Backend
- Enthält Komponenten zur Aufgabenanzeige, Erstellung und Bearbeitung

### Backend
- Implementiert mit **Spring Boot** (Java)
- Stellt eine **REST-API** für CRUD-Operationen bereit
- Architektur ist in Schichten unterteilt:
    - **Controller** – definiert die HTTP-Endpunkte
    - **Service** – enthält die Geschäftslogik
    - **Repository** – abstrahiert den Datenbankzugriff über JPA/Hibernate
    - **Model** – definiert Datenklassen wie `Task`
- Einstiegspunkt: `TaskManagerApplication.java`
