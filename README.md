# Todo Microservices

Architecture microservices en Spring Boot avec communication inter-services et Docker.

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                  Docker Network                         │
│  ┌────────────────────┐      ┌────────────────────┐   │
│  │   user-service     │      │   task-service     │   │
│  │   (port 8080)      │◄─────│   (port 8082)      │   │
│  │                    │ HTTP │                    │   │
│  │ - GET /users       │      │ - GET /tasks       │   │
│  │ - GET /users/{id}  │      │ - GET /tasks/{id}  │   │
│  │ - GET /users/ping  │      │ - GET /tasks/      │   │
│  │                    │      │   user/{userId}    │   │
│  │                    │      │ - GET /tasks/{id}/ │   │
│  │                    │      │   with-user        │   │
│  └────────────────────┘      └────────────────────┘   │
└─────────────────────────────────────────────────────────┘
```

## 📦 Services

### user-service (port 8080)
Gestion des utilisateurs en mémoire.

**Endpoints:**
- `GET /users` - Liste tous les utilisateurs
- `GET /users/{id}` - Récupère un utilisateur par ID
- `GET /users/ping` - Health check

### task-service (port 8082)
Gestion des tâches avec appels vers user-service.

**Endpoints:**
- `GET /tasks` - Liste toutes les tâches
- `GET /tasks/{id}` - Récupère une tâche par ID
- `GET /tasks/user/{userId}` - Tâches d'un utilisateur
- `GET /tasks/{id}/with-user` - Tâche enrichie avec infos utilisateur
- `GET /tasks/ping` - Health check

## 🚀 Démarrage avec Docker

### Prérequis
- Docker
- Docker Compose

### Build et lancement

```bash
# 1. Build les JARs
cd user-service && ./gradlew clean build && cd ..
cd task-service && ./gradlew clean build && cd ..

# 2. Build les images Docker
docker-compose build

# 3. Lancer les services
docker-compose up -d

# 4. Vérifier les containers
docker-compose ps

# 5. Voir les logs
docker-compose logs -f
```

### Arrêt

```bash
# Arrêter les services
docker-compose down

# Arrêter et supprimer les volumes
docker-compose down -v
```

## 📝 Tests

```bash
# Health checks
curl http://localhost:8080/users/ping
curl http://localhost:8082/tasks/ping

# Liste des utilisateurs
curl http://localhost:8080/users

# Liste des tâches
curl http://localhost:8082/tasks

# Communication inter-services
curl http://localhost:8082/tasks/1/with-user
```

## 🛠️ Développement local (sans Docker)

```bash
# Terminal 1 - user-service
cd user-service
./gradlew bootRun

# Terminal 2 - task-service
cd task-service
./gradlew bootRun
```

## 🔧 Technologies

- Java 21
- Spring Boot 3.2.1
- Gradle 8.5
- Docker & Docker Compose
- RestTemplate (communication HTTP)

## 📊 Données de test

### Utilisateurs (user-service)
- Alice (id: 1, email: alice@example.com)
- Bob (id: 2, email: bob@example.com)
- Charlie (id: 3, email: charlie@example.com)

### Tâches (task-service)
- Buy groceries (userId: 1, Alice)
- Finish report (userId: 2, Bob)
- Plan trip (userId: 1, Alice)
- Pay bills (userId: 3, Charlie)
- Clean house (userId: 2, Bob)

## 🌐 Communication inter-services

task-service communique avec user-service via HTTP:
- En local: `http://localhost:8080`
- Avec Docker: `http://user-service:8080` (nom du service)

La variable d'environnement `USER_SERVICE_URL` configure l'URL automatiquement.
