# Todo Microservices

Application microservices pour la gestion de tâches, construite avec Spring Boot, Docker et Kubernetes.

- **2 microservices** : gestion d'utilisateurs (`user-service`) et gestion de tâches (`task-service`)
- **Communication HTTP** entre services via REST API
- **Déploiement** : Docker Compose et Kubernetes (Minikube)

---

## Architecture

```
┌─────────────────────────────────────────────────────────────┐
│              Docker Network / Kubernetes Cluster            │
│                                                             │
│  ┌────────────────────┐        ┌────────────────────┐      │
│  │   user-service     │        │   task-service     │      │
│  │   (port 8080)      │◄───────│   (port 8082)      │      │
│  │                    │  HTTP  │                    │      │
│  │ - GET /users       │        │ - GET /tasks       │      │
│  │ - GET /users/{id}  │        │ - GET /tasks/{id}  │      │
│  │ - GET /users/ping  │        │ - GET /tasks/      │      │
│  │                    │        │   user/{userId}    │      │
│  │                    │        │ - GET /tasks/{id}/ │      │
│  │                    │        │   with-user        │      │
│  └────────────────────┘        └────────────────────┘      │
│         ClusterIP                    NodePort              │
└─────────────────────────────────────────────────────────────┘
```

### Technologies utilisées

| Technologie | Version | Usage |
|-------------|---------|-------|
| Java | 21 | Langage de programmation |
| Spring Boot | 3.2.1 | Framework backend |
| Gradle | 8.5 | Build tool |
| Docker | - | Conteneurisation |
| Docker Compose | - | Orchestration multi-conteneurs |
| Kubernetes | - | Orchestration de conteneurs |
| Minikube | - | Cluster Kubernetes local |

---

## Services

### user-service (port 8080)

Gestion des utilisateurs en mémoire.

**Endpoints:**
| Methode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/users` | Liste tous les utilisateurs |
| GET | `/users/{id}` | Recupere un utilisateur par ID |
| GET | `/users/ping` | Health check |

### task-service (port 8082)

Gestion des taches avec appels vers user-service.

**Endpoints:**
| Methode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/tasks` | Liste toutes les taches |
| GET | `/tasks/{id}` | Recupere une tache par ID |
| GET | `/tasks/user/{userId}` | Taches d'un utilisateur |
| GET | `/tasks/{id}/with-user` | Tache enrichie avec infos utilisateur |
| GET | `/tasks/ping` | Health check |

---

## Lancement local (sans Docker)

```bash
# Terminal 1 - user-service (port 8080)
cd user-service
./gradlew bootRun

# Terminal 2 - task-service (port 8082)
cd task-service
./gradlew bootRun
```

**Tests:**
```bash
curl http://localhost:8080/users
curl http://localhost:8082/tasks
curl http://localhost:8082/tasks/1/with-user
```

---

## Lancement avec Docker Compose

### Prerequis
- Docker
- Docker Compose

### Commandes

```bash
# Build des images (si necessaire)
docker-compose build

# Demarrer les services
docker-compose up -d

# Verifier les conteneurs
docker-compose ps

# Voir les logs
docker-compose logs -f

# Arreter les services
docker-compose down
```

**Endpoints disponibles:**
- user-service: http://localhost:8080
- task-service: http://localhost:8082

---

## Deploiement sur Kubernetes (Minikube)

### Prerequis
- Minikube installe
- kubectl configure

### Architecture Kubernetes

```
┌─────────────────────────────────────────────────────────────┐
│                    Minikube Cluster                         │
│                                                             │
│  ┌──────────────────┐        ┌──────────────────┐          │
│  │   Deployment     │        │   Deployment     │          │
│  │   user-service   │        │   task-service   │          │
│  │   replicas: 1    │        │   replicas: 1    │          │
│  └────────┬─────────┘        └────────┬─────────┘          │
│           │                           │                     │
│  ┌────────▼─────────┐        ┌────────▼─────────┐          │
│  │    Service       │        │    Service       │          │
│  │   ClusterIP      │◄───────│   NodePort       │          │
│  │   port: 8080     │  HTTP  │   port: 8082     │          │
│  └──────────────────┘        │   nodePort:30082 │          │
│       (interne)              └──────────────────┘          │
│                                   (expose)                  │
└─────────────────────────────────────────────────────────────┘
```

### Deploiement

```bash
# 1. Demarrer Minikube
minikube start

# 2. Charger les images Docker dans Minikube
minikube image load todo-microservices-user-service:latest
minikube image load todo-microservices-task-service:latest

# 3. Appliquer les manifestes Kubernetes
kubectl apply -f k8s/

# 4. Verifier l'etat des pods
kubectl get pods

# 5. Verifier les services
kubectl get svc
```

### Acces aux services

```bash
# Exposer task-service via Minikube (ouvre un tunnel)
minikube service task-service --url

# Le terminal affiche une URL du type: http://127.0.0.1:XXXXX
# Utiliser cette URL pour les tests
```

### Tests Kubernetes

```bash
# Remplacer <URL> par l'URL obtenue avec minikube service
curl <URL>/tasks/ping
curl <URL>/tasks
curl <URL>/tasks/1/with-user
```

### Fichiers Kubernetes

| Fichier | Description |
|---------|-------------|
| `k8s/user-deployment.yaml` | Deployment pour user-service |
| `k8s/user-service.yaml` | Service ClusterIP pour user-service |
| `k8s/task-deployment.yaml` | Deployment pour task-service |
| `k8s/task-service.yaml` | Service NodePort pour task-service |

---

## Donnees de test

### Utilisateurs (user-service)

| ID | Nom | Email |
|----|-----|-------|
| 1 | Alice | alice@example.com |
| 2 | Bob | bob@example.com |
| 3 | Charlie | charlie@example.com |

### Taches (task-service)

| ID | Titre | Utilisateur |
|----|-------|-------------|
| 1 | Buy groceries | Alice (1) |
| 2 | Finish report | Bob (2) |
| 3 | Plan trip | Alice (1) |
| 4 | Pay bills | Charlie (3) |
| 5 | Clean house | Bob (2) |

---

## Communication inter-services

`task-service` communique avec `user-service` via HTTP REST:

| Environnement | URL de user-service |
|---------------|---------------------|
| Local | `http://localhost:8080` |
| Docker Compose | `http://user-service:8080` |
| Kubernetes | `http://user-service:8080` |

La variable d'environnement `USER_SERVICE_URL` configure l'URL automatiquement.

---

## Structure du projet

```
todo-microservices/
├── user-service/           # Microservice utilisateurs
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle
├── task-service/           # Microservice taches
│   ├── src/
│   ├── Dockerfile
│   └── build.gradle
├── k8s/                    # Manifestes Kubernetes
│   ├── user-deployment.yaml
│   ├── user-service.yaml
│   ├── task-deployment.yaml
│   └── task-service.yaml
├── docker-compose.yml      # Configuration Docker Compose
├── start.sh               # Script de demarrage
├── stop.sh                # Script d'arret
└── README.md
```

---

## Auteur

Projet realise dans le cadre du cours de Microservices, Docker et Kubernetes.
