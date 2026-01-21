# Rendu Projet - Microservices, Docker & Kubernetes

## 1. Informations generales

- **Titre du projet** : Todo Microservices - Users & Tasks
- **Travail** : individuel
- **Etudiant** : Kevin Chaillot
- **Date de rendu** : 21 janvier 2026

## 2. Lien GitHub du projet

- **Depot** : https://github.com/kevinMdf05/todo-microservices-kubernetes

Le depot contient :
- 2 microservices Spring Boot (`user-service`, `task-service`)
- Dockerfiles + `docker-compose.yml`
- Manifests Kubernetes dans `k8s/`
- Scripts `start.sh` / `stop.sh`
- README complet (architecture, Docker, Kubernetes, tests)

## 3. Description rapide du projet

Le projet implemente une mini application de gestion de taches, composee de deux microservices :

- **user-service** : gestion des utilisateurs (liste, detail par id, ping)
- **task-service** : gestion des taches (liste, detail, ping) + appel HTTP au `user-service` pour enrichir une tache avec les informations de l'utilisateur associe.

Les services communiquent entre eux via HTTP (REST, JSON), aussi bien en Docker Compose qu'en Kubernetes (DNS de service K8s).

## 4. Technologies utilisees

| Technologie | Usage |
|-------------|-------|
| Java 21 | Langage de programmation |
| Spring Boot 3.2.1 | Framework backend |
| Docker | Conteneurisation |
| Docker Compose | Orchestration multi-conteneurs |
| Kubernetes (Minikube) | Orchestration de conteneurs |
| Git / GitHub | Versioning et hebergement |

## 5. Points de maitrise des technos

### Microservices
- 2 services separes avec responsabilites distinctes
- Communication HTTP inter-services via RestTemplate
- Donnees en memoire (pas de BDD pour simplifier)

### Docker
- 2 Dockerfiles (multi-stage build avec Gradle)
- `docker-compose.yml` avec :
  - Reseau partage (`microservices-network`)
  - Health checks
  - Variables d'environnement pour la configuration
- Scripts de demarrage/arret (`start.sh`, `stop.sh`)

### Kubernetes
- 4 manifests YAML dans `k8s/` :
  - `user-deployment.yaml` : Deployment pour user-service
  - `user-service.yaml` : Service ClusterIP (interne)
  - `task-deployment.yaml` : Deployment pour task-service
  - `task-service.yaml` : Service NodePort (expose)
- Communication inter-service via DNS Kubernetes (`http://user-service:8080`)
- Readiness probes pour verifier le demarrage

## 6. Comment lancer

### Docker Compose

```bash
# Build et demarrage
docker-compose up -d

# Verification
docker-compose ps

# Arret
docker-compose down
```

### Kubernetes (Minikube)

```bash
# Demarrer Minikube
minikube start

# Charger les images locales dans Minikube
minikube image load todo-microservices-user-service:latest
minikube image load todo-microservices-task-service:latest

# Deployer les microservices
kubectl apply -f k8s/

# Verifier l'etat du cluster
kubectl get pods
kubectl get svc

# Acceder au service de task-service (NodePort)
minikube service task-service --url
```

## 7. Endpoints principaux

| Service | Endpoint | Description |
|---------|----------|-------------|
| user-service | GET /users | Liste des utilisateurs |
| user-service | GET /users/{id} | Detail d'un utilisateur |
| user-service | GET /users/ping | Health check |
| task-service | GET /tasks | Liste des taches |
| task-service | GET /tasks/{id} | Detail d'une tache |
| task-service | GET /tasks/{id}/with-user | Tache + utilisateur (inter-service) |
| task-service | GET /tasks/ping | Health check |

## 8. Captures d'ecran

Toutes les captures d'ecran (Docker, Kubernetes, endpoints REST, Git) se retrouvent dans le dossier d'explication du projet (document Google Docs exporte en PDF et depose sur Moodle).

---

*Projet realise dans le cadre du cours de Microservices, Docker et Kubernetes.*
