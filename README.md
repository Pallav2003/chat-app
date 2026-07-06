# ByteBuzz Chat App

A real-time group chat application with room creation, WebSocket messaging, and MongoDB-backed message storage.

## Features

- Create and join chat rooms by room ID
- Real-time messaging using STOMP over WebSocket
- Message history persisted in MongoDB
- Responsive React frontend with animated UI
- Docker Compose support for backend + MongoDB

## Tech Stack

- Backend: Spring Boot 3, WebSocket, Spring Data MongoDB
- Frontend: React, Vite, Tailwind CSS, React Router
- WebSocket transport: SockJS + STOMP
- Database: MongoDB

## Prerequisites

- Java 17
- Maven or Maven Wrapper (`./mvnw`, `mvnw.cmd`)
- Node.js 18+ and npm
- Docker & Docker Compose (optional)

## Folder Structure

- `bytebuzz-backend/` — Spring Boot API, WebSocket endpoint, MongoDB integration
- `bytebuzz-frontend/` — React UI, room join/create flow, websocket chat client

## Local Setup

### 1. Start the backend

```powershell
cd bytebuzz-backend
./mvnw spring-boot:run
```

The backend listens on `http://localhost:9090` by default.

### 2. Start the frontend

```powershell
cd bytebuzz-frontend
npm install
npm run dev
```

The frontend runs on `http://localhost:5173`.

### 3. Open the app

Visit `http://localhost:5173` in your browser.

## Docker Compose Setup

Use Docker Compose to run MongoDB and the backend together:

```powershell
docker compose up --build
```

This starts:

- `mongo` on port `27018`
- `boot-app` backend on port `9090`

Then start the frontend separately with:

```powershell
cd bytebuzz-frontend
npm install
npm run dev
```

## Configuration

Backend configuration is loaded from `bytebuzz-backend/src/main/resources/application.properties`:

- `spring.data.mongodb.uri` defaults to `mongodb://localhost:27017/bytebuzz`
- `server.port` defaults to `9090`

You can override with environment variables:

- `SPRING_DATA_MONGODB_URI`
- `SERVER_PORT`

## API Endpoints

- `POST /api/v1/rooms` — create a new room
- `GET /api/v1/rooms/{roomId}` — join an existing room
- `GET /api/v1/rooms/{roomId}/messages` — fetch room messages

## WebSocket Endpoints

- STOMP endpoint: `http://localhost:9090/chat`
- Subscribe topic: `/topic/room/{roomId}`
- Send message: `/app/sendMessage/{roomId}`

## Notes

- The frontend expects the backend on `http://localhost:9090`.
- The default frontend origin is configured in backend CORS as `http://localhost:5173`.
- Room creation uses `text/plain` POST body for the room ID.

---

Enjoy building and customizing ByteBuzz Chat!