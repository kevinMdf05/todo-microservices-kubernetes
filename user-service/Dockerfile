# Utilise une image Java 21
FROM eclipse-temurin:21-jre-alpine

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copie le JAR compilé depuis le dossier build/libs
COPY build/libs/*.jar app.jar

# Expose le port 8080
EXPOSE 8080

# Lance l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
