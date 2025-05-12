#!/bin/bash

SERVICES=(
  "inventory-service"
  "notification-service"
  "order-service"
  "payment-service"
  "product-service"
  "recommendation-service"
  "user-service"
)

echo "Start services rebuilding..."

for SERVICE in "${SERVICES[@]}"; do
  echo "Execute Maven build for $SERVICE..."
  mvn clean package -DskipTests -f ./services/$SERVICE/pom.xml

  if [ $? -ne 0 ]; then
    echo "Error during Maven build for $SERVICE. We skip it..."
    continue
  fi

  echo "Building $SERVICE without Docker cache..."
  docker build --no-cache -t $SERVICE:latest ./services/$SERVICE

  echo "Loading $SERVICE image into kind..."
  kind load docker-image $SERVICE:latest

  echo "Restarting deployment $SERVICE..."
  kubectl rollout restart deployment $SERVICE
done

echo "Done! All services updated."
