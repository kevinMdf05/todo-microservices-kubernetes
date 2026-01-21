#!/bin/bash

echo "🚀 Starting Todo Microservices..."
echo ""

# Build JARs if needed
if [ ! -f "user-service/build/libs/user-service-0.0.1-SNAPSHOT.jar" ]; then
    echo "📦 Building user-service..."
    cd user-service && ./gradlew clean build --no-daemon && cd ..
fi

if [ ! -f "task-service/build/libs/task-service-0.0.1-SNAPSHOT.jar" ]; then
    echo "📦 Building task-service..."
    cd task-service && ./gradlew clean build --no-daemon && cd ..
fi

echo ""
echo "🐳 Building Docker images..."
docker-compose build

echo ""
echo "🚀 Starting containers..."
docker-compose up -d

echo ""
echo "⏳ Waiting for services to be ready..."
sleep 10

echo ""
echo "✅ Services are running!"
echo ""
echo "📊 Status:"
docker-compose ps

echo ""
echo "🌐 Endpoints:"
echo "  - user-service: http://localhost:8080/users"
echo "  - task-service: http://localhost:8082/tasks"
echo "  - Communication: http://localhost:8082/tasks/1/with-user"
echo ""
echo "📝 Logs: docker-compose logs -f"
echo "🛑 Stop: docker-compose down"
